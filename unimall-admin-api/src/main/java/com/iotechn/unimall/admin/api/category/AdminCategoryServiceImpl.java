package com.iotechn.unimall.admin.api.category;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.*;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.CategoryTreeNodeDTO;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/12.
 */
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CacheComponent cacheComponent;

    private static final String CA_CATEGORY_TREE = "CA_CATEGORY_TREE";

    private static final String CA_CATEGORY_LIST = "CA_CATEGORY_LIST";
    /**
     * @return
     * @throws ServiceException
     */
    //TODO 做下优化
    @Override
    public List<CategoryTreeNodeDTO> categoryTree() throws ServiceException {
        List<CategoryTreeNodeDTO> objList = cacheComponent.getObjList(CA_CATEGORY_TREE, CategoryTreeNodeDTO.class);
        if (objList != null) {
            return objList;
        }
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<>());
        List<CategoryTreeNodeDTO> list = categoryDOS.stream().filter((item) -> (item.getParentId().equals(0l))).map(item -> {
            CategoryTreeNodeDTO dto = new CategoryTreeNodeDTO();
            dto.setLabel(item.getTitle());
            dto.setLevel(0);
            dto.setFullName(dto.getLabel());
            dto.setValue(item.getId());
            dto.setChildren(new LinkedList<>());
            return dto;
        }).collect(Collectors.toList());
        list.forEach(item -> {
            categoryDOS.forEach(categoryDO -> {
                if (categoryDO.getParentId().equals(item.getValue())) {
                    CategoryTreeNodeDTO categoryTreeNodeDTO = new CategoryTreeNodeDTO();
                    categoryTreeNodeDTO.setChildren(new LinkedList<>());
                    categoryTreeNodeDTO.setValue(categoryDO.getId());
                    categoryTreeNodeDTO.setLabel(categoryDO.getTitle());
                    categoryTreeNodeDTO.setLevel(1);
                    categoryTreeNodeDTO.setParent(item.getValue());
                    categoryTreeNodeDTO.setFullName(item.getFullName() +"/"+ categoryDO.getTitle());
                    item.getChildren().add(categoryTreeNodeDTO);
                    categoryDOS.forEach(subCategoryDO -> {
                        if (subCategoryDO.getParentId().equals(categoryTreeNodeDTO.getValue())) {
                            CategoryTreeNodeDTO childCategoryNodeDTO = new CategoryTreeNodeDTO();
                            childCategoryNodeDTO.setLabel(subCategoryDO.getTitle());
                            childCategoryNodeDTO.setValue(subCategoryDO.getId());
                            childCategoryNodeDTO.setLevel(2);
                            childCategoryNodeDTO.setParent(categoryTreeNodeDTO.getValue());
                            childCategoryNodeDTO.setFullName(categoryTreeNodeDTO.getFullName() +"/"+ subCategoryDO.getTitle());
                            categoryTreeNodeDTO.getChildren().add(childCategoryNodeDTO);
                        }
                    });
                }
            });
        });
        cacheComponent.putObj(CA_CATEGORY_TREE, list, 60 * 60);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDO addCategory(Long adminId, String title, Long parentId, String iconUrl, String picUrl, Integer level) throws ServiceException {
        CategoryDO parent = null;
        if(parentId != 0){
            parent = categoryMapper.selectById(parentId);
            if(parent == null){
                throw new AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.CATEGORY_EXCEPTION,"副节点信息不准确"));
            }
        }
        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setLevel(parent.getLevel() + 1);
        categoryDO.setParentId(parentId);
        categoryDO.setIconUrl(iconUrl);
        categoryDO.setPicUrl(picUrl);
        categoryDO.setTitle(title);
        Date now = new Date();
        categoryDO.setGmtCreate(now);
        categoryDO.setGmtUpdate(now);

        if(categoryMapper.insert(categoryDO) <= 0){
            throw new AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.CATEGORY_EXCEPTION,"数据库类目插入失败"));
        }
        cacheComponent.del(CA_CATEGORY_TREE);
        cacheComponent.del(CA_CATEGORY_LIST);
        return categoryDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCategory(Long adminId,Long id) throws ServiceException {
        Integer count_category = categoryMapper.selectCount(new EntityWrapper<CategoryDO>().eq("parent_id", id));
        Integer count_spu = spuMapper.selectCount(new EntityWrapper<SpuDO>().eq("category_id",id ));

        if(count_category != 0 || count_spu != 0){
            throw new AppServiceException(ExceptionDefinition.CATEGORY_OUGHT_TO_EMPTY);
        }
        cacheComponent.del(CA_CATEGORY_TREE);
        cacheComponent.del(CA_CATEGORY_LIST);
        return categoryMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryTreeNodeDTO updateCategory(Long adminId, Long id, String title, Long parentId, String iconUrl, String picUrl, Integer level) throws ServiceException {
        CategoryDO categoryDO = new CategoryDO();
        if(id == null || parentId == null){
            throw  new  AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.CATEGORY_EXCEPTION,"传入ID，父节点ID不能为空"));
        }
        categoryDO.setId(parentId);
        CategoryDO categoryParent = categoryMapper.selectOne(categoryDO);
        if(categoryParent == null && parentId != 0){
            throw  new  AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.CATEGORY_EXCEPTION,"数据库查找失败"));
        }
        categoryDO.setId(id);
        categoryDO.setGmtUpdate(new Date());
        categoryDO.setTitle(title);
        categoryDO.setPicUrl(picUrl);
        categoryDO.setIconUrl(iconUrl);
        categoryDO.setLevel(categoryParent.getLevel() + 1);
        if(categoryMapper.insert(categoryDO) <= 0){
            throw  new  AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.CATEGORY_EXCEPTION,"修改失败，可能是ID错误"));
        }
        CategoryTreeNodeDTO categoryTreeNodeDTO = new CategoryTreeNodeDTO();
        List<CategoryTreeNodeDTO> list = getCategoryList();
        for(CategoryTreeNodeDTO temp : list){
            if(categoryDO.getId().equals(temp.getValue())){
                BeanUtils.copyProperties(temp,categoryTreeNodeDTO );
                break;
            }
        }
        cacheComponent.del(CA_CATEGORY_TREE);
        cacheComponent.del(CA_CATEGORY_LIST);
        return categoryTreeNodeDTO;
    }

    //首先的到所有的类目的List<CategoryTreeNodeDTO>,在根据SQL查询得到的数据转化成传往前端的数据
    @Override
    public Page<CategoryTreeNodeDTO> queryCategory(Long adminId, Long id, String title, Integer level,Integer pageNo,Integer limit) throws ServiceException {
        EntityWrapper wrapper = new EntityWrapper();
        if(id != null){
            wrapper.eq("id",id );
        }
        if(title != null){
            wrapper.like("title",title );
        }
        if(level != null){
            wrapper.eq("level",level );
        }
        Integer count = categoryMapper.selectCount(wrapper);

        List<CategoryDO> categoryDOS = categoryMapper.selectPage(new RowBounds((pageNo-1)*limit,limit),wrapper);
        List<CategoryTreeNodeDTO> totalCategory = getCategoryList();
        List<CategoryTreeNodeDTO> list = categoryDOS.stream().map(item -> {
            CategoryTreeNodeDTO dto = new CategoryTreeNodeDTO();
            for(CategoryTreeNodeDTO temp : totalCategory){
                if(temp.getValue().equals(item.getId())){
                    BeanUtils.copyProperties(temp,dto );
                    return dto;
                }
            }
            BeanUtils.copyProperties(item,dto );;
           return dto;
        }).collect(Collectors.toList());
        Page<CategoryTreeNodeDTO> page = new Page<>(list,pageNo,limit,count);
        return page;
    }


    //TODO 可以做出父节点查询所有子节点
    //获得所有类目的list
    private List<CategoryTreeNodeDTO> getCategoryList(){
        List<CategoryTreeNodeDTO> objList = cacheComponent.getObjList(CA_CATEGORY_TREE, CategoryTreeNodeDTO.class);
        if(objList != null){
            return objList;
        }
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.orderBy("level");
        List<CategoryDO> categoryDOS = categoryMapper.selectList(wrapper);
        List<CategoryTreeNodeDTO> list = categoryDOS.stream().map(item -> {
            CategoryTreeNodeDTO dto = new CategoryTreeNodeDTO();
            dto.setLabel(item.getTitle());
            dto.setLevel(item.getLevel());
            dto.setValue(item.getId());
            dto.setParent(item.getParentId());
            dto.setIconUrl(item.getIconUrl());
            dto.setPirUrl(item.getPicUrl());
            if(item.getLevel() == 0){
                dto.setFullName(dto.getLabel());
            }
            return dto;
        }).collect(Collectors.toList());

        for(CategoryTreeNodeDTO cOne : list){

            for(CategoryTreeNodeDTO cTwo : list){
                if(cOne.getParent().equals(cTwo.getValue())){
                    cOne.setFullName(cTwo.getFullName()+"/"+cOne.getLabel());
                    break;
                }
            }

        }
        cacheComponent.putObj(CA_CATEGORY_LIST, list, 60 * 60);
        return list;
    }


}

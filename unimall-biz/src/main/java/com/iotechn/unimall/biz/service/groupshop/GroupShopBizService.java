package com.iotechn.unimall.biz.service.groupshop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.domain.GroupShopDO;
import com.iotechn.unimall.data.domain.SkuActivityPriceDO;
import com.iotechn.unimall.data.dto.goods.GroupShopDTO;
import com.iotechn.unimall.data.dto.goods.GroupShopSkuDTO;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.GroupShopMapper;
import com.iotechn.unimall.data.mapper.SkuActivityPriceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/25
 * Time: 11:30
 */
@Service
public class GroupShopBizService {

    @Autowired
    private GroupShopMapper groupShopMapper;

    @Autowired
    private SkuActivityPriceMapper skuActivityPriceMapper;

    public GroupShopDTO getGroupShopById(Long id) {
        GroupShopDO groupShopDO = groupShopMapper.selectById(id);
        // 若团购还没有开始，则告诉上层，这个为空
        if (groupShopDO == null) {
            return null;
        }
        if (groupShopDO.getStatus() == StatusType.LOCK.getCode()) {
            return null;
        }
        GroupShopDTO groupShopDTO = new GroupShopDTO();
        BeanUtils.copyProperties(groupShopDO, groupShopDTO);
        List<GroupShopSkuDTO> groupShopSkuDTOList = skuActivityPriceMapper.selectList(
                new QueryWrapper<SkuActivityPriceDO>()
                        .eq("activity_id", id)
                        .eq("activity_type", SpuActivityType.GROUP_SHOP.getCode())).stream().map(
                item -> {
                    GroupShopSkuDTO dto = new GroupShopSkuDTO();
                    dto.setSkuGroupShopPrice(item.getActivityPrice());
                    dto.setSkuId(item.getSkuId());
                    dto.setId(item.getId());
                    dto.setGroupShopId(item.getActivityId());
                    dto.setGmtCreate(item.getGmtCreate());
                    dto.setGmtUpdate(item.getGmtUpdate());
                    return dto;
                }).collect(Collectors.toList());
        groupShopDTO.setGroupShopSkuDTOList(groupShopSkuDTOList);
        return groupShopDTO;
    }

    public Integer incGroupShopNum(Long groupShopId, Integer num) {
        return groupShopMapper.incCurrentNum(groupShopId, num);
    }

}

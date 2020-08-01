package com.iotechn.unimall.app.api.product;

import com.iotechn.unimall.biz.service.groupshop.GroupShopBizService;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by rize on 2019/7/2.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductBizService productBizService;

    @Autowired
    private GroupShopBizService groupShopBizService;

    @Override
    public Page<SpuDTO> getProductPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        return productBizService.getProductPage(pageNo, pageSize, categoryId, orderBy, isAsc, title);
    }

    @Override
    public SpuDTO getGoods(Long spuId, Long groupShopId, Long userId) throws ServiceException {
        //若团购Id不为空，则额外添加团购信息
        SpuDTO goods = productBizService.getGoods(spuId, userId);
        if (groupShopId != null) {
            goods.setGroupShop(groupShopBizService.getGroupShopById(groupShopId));
        }
        return goods;
    }
}

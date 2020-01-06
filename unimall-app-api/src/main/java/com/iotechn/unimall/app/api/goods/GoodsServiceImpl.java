package com.iotechn.unimall.app.api.goods;

import com.iotechn.unimall.biz.service.goods.GoodsBizService;
import com.iotechn.unimall.biz.service.groupshop.GroupShopBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.mapper.GroupShopMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;


/**
 * Created by rize on 2019/7/2.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsBizService goodsBizService;

    @Autowired
    private GroupShopBizService groupShopBizService;

    @Override
    public Page<SpuDTO> getGoodsPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy,Boolean isAsc, String title) throws ServiceException {
        return goodsBizService.getGoodsPage(pageNo, pageSize, categoryId, orderBy, isAsc, title);
    }

    @Override
    public SpuDTO getGoods(Long spuId, Long groupShopId, Long userId) throws ServiceException {
        //若团购Id不为空，则额外添加团购信息
        SpuDTO goods = goodsBizService.getGoods(spuId, userId);
        if (groupShopId != null) {
            goods.setGroupShop(groupShopBizService.getGroupShopById(groupShopId));
        }
        return goods;
    }
}

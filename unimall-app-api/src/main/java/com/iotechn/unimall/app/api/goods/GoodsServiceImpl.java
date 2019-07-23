package com.iotechn.unimall.app.api.goods;

import com.iotechn.unimall.biz.service.goods.GoodsBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by rize on 2019/7/2.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsBizService goodsBizService;

    @Override
    public Page<SpuDTO> getGoodsPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy,Boolean isAsc, String title) throws ServiceException {
        return goodsBizService.getGoodsPage(pageNo, pageSize, categoryId, orderBy, isAsc, title);
    }

    @Override
    public SpuDTO getGoods(Long spuId, Long userId) throws ServiceException {
        return goodsBizService.getGoods(spuId, userId);
    }

}

package com.iotechn.unimall.biz.service.cart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.domain.CartDO;
import com.iotechn.unimall.data.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * User: rize
 * Date: 2020/8/2
 * Time: 16:18
 */
@Service("cartBizService")
public class CartBizService {

    @Autowired
    private CartMapper cartMapper;

    public Integer deleteBySkuId(List<Long> skuIds, Long userId) {
        return cartMapper.delete(new QueryWrapper<CartDO>().in("sku_id", skuIds).eq("user_id", userId));
    }

}

package com.iotechn.unimall.app.api.cart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CartDO;
import com.iotechn.unimall.data.dto.CartDTO;
import com.iotechn.unimall.data.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rize on 2019/7/3.
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCartItem(Long skuId, Integer num, Long userId) throws ServiceException {
        CartDO cartFromDB = cartMapper.selectOne(
                new QueryWrapper<CartDO>()
                        .eq("sku_id", skuId)
                        .eq("user_id", userId));
        CartDO cartDO = new CartDO();
        Date now = new Date();
        if (cartFromDB != null) {
            //若非空
            cartDO.setId(cartFromDB.getId());
            cartDO.setNum(cartFromDB.getNum() + num);
            cartDO.setGmtUpdate(now);
            return cartMapper.updateById(cartDO) > 0;
        } else {
            //不存在，则添加购物车
            cartDO.setSkuId(skuId);
            cartDO.setNum(num);
            cartDO.setUserId(userId);
            cartDO.setGmtUpdate(now);
            cartDO.setGmtCreate(now);
            return cartMapper.insert(cartDO) > 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean subCartItem(Long skuId, Integer num, Long userId) throws ServiceException {
        CartDO cartFromDB = cartMapper.selectOne(
                new QueryWrapper<CartDO>()
                        .eq("sku_id", skuId)
                        .eq("user_id", userId));

        CartDO cartDO = new CartDO();
        if (cartDO != null) {
            cartDO.setId(cartFromDB.getId());
            cartDO.setNum(cartFromDB.getNum() - num);
            if (cartDO.getNum() <= 0) {
                //直接删除此商品
                return cartMapper.deleteById(cartDO.getId()) > 0;
            } else {
                return cartMapper.updateById(cartDO) > 0;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeCartItem(Long cartId, Long userId) throws ServiceException {
        return cartMapper.delete(
                new QueryWrapper<CartDO>()
                        .eq("id", cartId)
                        .eq("user_id", userId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeCartItemBatch(String cartIdList, Long userId) throws ServiceException {
        if (StringUtils.isEmpty(cartIdList)) {
            throw new AppServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        String[] split = cartIdList.split(",");
        if (split.length == 0) {
            throw new AppServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        List<Long> array = new ArrayList<>(split.length);
        for (String idRaw : split) {
            array.add(Long.parseLong(idRaw));
        }
        return cartMapper.delete(
                new QueryWrapper<CartDO>()
                        .in("id", array)
                        .eq("user_id", userId)) > 0;
    }

    @Override
    @Transactional
    public Boolean removeCartAll(Long userId) throws ServiceException {
        return cartMapper.delete(
                new QueryWrapper<CartDO>()
                        .eq("user_id", userId)) > 0;
    }

    @Override
    public Integer updateCartItemNum(Long cartId, Integer num, Long userId) throws ServiceException {
        CartDO cartDO = new CartDO();
        cartDO.setNum(num);
        Integer update = cartMapper.update(cartDO,
                new QueryWrapper<CartDO>()
                        .eq("id", cartId)
                        .eq("user_id", userId));
        if (update > 0) {
            return num;
        }
        throw new AppServiceException(ExceptionDefinition.CART_UPDATE_FAILED);
    }

    @Override
    public Integer countCart(Long userId) throws ServiceException {
        return cartMapper.countCart(userId);
    }

    @Override
    public List<CartDTO> getCartList(Long userId) throws ServiceException {
        List<CartDTO> cartList = cartMapper.getCartList(userId);
        for (CartDTO cartDTO : cartList) {
            List<Long> categoryFamily = categoryBizService.getCategoryFamily(cartDTO.getCategoryId());
            cartDTO.setCategoryIdList(categoryFamily);
        }
        return cartList;
    }
}

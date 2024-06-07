package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.CartDO;
import com.dobbinsoft.unimall.data.dto.CartDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/3.
 */
public interface CartMapper extends IMapper<CartDO> {

    public int countCart(Long userId);

    public List<CartDTO> getCartList(Long userId);

}

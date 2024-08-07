package com.dobbinsoft.unimall.admin.api.address;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.domain.AddressDO;
import com.dobbinsoft.unimall.data.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: rize
 * Date: 2020/8/13
 * Time: 10:01
 */
@Service("adminAddressService")
public class AdminAddressServiceImpl implements AdminAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Page<AddressDO> list(Long userId, Integer page, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<AddressDO> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        return addressMapper.selectPage(Page.div(page, limit, AddressDO.class), wrapper);
    }
}

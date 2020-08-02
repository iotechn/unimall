package com.iotechn.unimall.biz.service.address;

import com.iotechn.unimall.data.domain.AddressDO;
import com.iotechn.unimall.data.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: rize
 * Date: 2020/8/2
 * Time: 14:00
 */
@Service("addressBizService")
public class AddressBizService {

    @Autowired
    private AddressMapper addressMapper;

    public AddressDO getAddressById(Long id) {
        return addressMapper.selectById(id);
    }

}

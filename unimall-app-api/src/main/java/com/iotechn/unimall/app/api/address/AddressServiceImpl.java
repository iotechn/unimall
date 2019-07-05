package com.iotechn.unimall.app.api.address;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AddressDO;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.mapper.AddressMapper;
import com.iotechn.unimall.data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;
import java.util.Date;
import java.util.List;

/*
@author kbq
@date  2019/7/4 - 22:07
*/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public Boolean addAddress(String province, String city, String county, String street, String address, Long userId, String phone, String consignee) throws ServiceException {
        AddressDO addressDO = new AddressDO(province,city,county,street,address,userId,phone,consignee);
        System.out.println(phone + " " + consignee);
        Date now =  new Date();
        addressDO.setGmtCreate(now);
        addressDO.setGmtUpdate(addressDO.getGmtCreate());
        return addressMapper.insert(addressDO) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteAddress(Long addressId, Long userId) throws ServiceException {
      return addressMapper.delete(new EntityWrapper<AddressDO>()
              .eq("id",addressId)
              .eq("user_id",userId)) > 0;
    }

    @Override
    @Transactional
    public Boolean updateAddress(Long addressId, String province, String city, String county, String street, String address, Long userId, String phone, String consignee) throws ServiceException {
        AddressDO addressDO = new AddressDO(province,city,county,street,address,userId,phone,consignee);
        Date now =  new Date();
        addressDO.setGmtUpdate(now);
        return addressMapper.update(addressDO,new EntityWrapper<AddressDO>()
                .eq("id",addressId)
                .eq("user_id",userId)) > 0;
    }

    @Override
    public List<AddressDO> getAllAddress(Long userId) throws ServiceException {
        return addressMapper.selectList(new EntityWrapper<AddressDO>()
                .eq("user_id",userId));
    }

    @Override
    public AddressDO getAddressById(Long userId, Long addressId) throws ServiceException {
        AddressDO addressDO = new AddressDO();
        addressDO.setUserId(userId);
        addressDO.setUserId(userId);
        return addressMapper.selectOne(addressDO);
    }
}

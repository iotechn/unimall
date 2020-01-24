package com.iotechn.unimall.app.api.address;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AddressDO;
import com.iotechn.unimall.data.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAddress(String province, String city, String county, String address, Integer defaultAddress, Long userId, String phone, String consignee) throws ServiceException {
        Integer addressNum = addressMapper.selectCount(new EntityWrapper<AddressDO>().eq("user_id", userId));
        AddressDO addressDO = null;
        if (addressNum == 0) {
            addressDO = new AddressDO(province, city, county, address, 1, userId, phone, consignee);
        } else {
            if (defaultAddress != 0) {
                AddressDO preDefault = new AddressDO();
                preDefault.setDefaultAddress(0);
                if (!(addressMapper.update(preDefault //该用户有地址却没有默认地址，抛出该异常
                        , new EntityWrapper<AddressDO>()
                                .eq("user_id", userId)
                                .eq("default_address", 1)) > 0)) {
                    throw new AppServiceException(ExceptionDefinition.ADDRESS_QUERY_FAILED);
                }
                addressDO = new AddressDO(province, city, county, address, 1, userId, phone, consignee);
            } else {
                addressDO = new AddressDO(province, city, county, address, 0, userId, phone, consignee);
            }
        }
        Date now = new Date();
        addressDO.setGmtCreate(now);
        addressDO.setGmtUpdate(now);
        if (addressMapper.insert(addressDO) > 0) {
            return true;
        } else {
            throw new AppServiceException(ExceptionDefinition.ADDRESS_DATABASE_QUERY_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAddress(Long addressId, Long userId) throws ServiceException {
        Integer defaultNum = addressMapper.selectCount(new EntityWrapper<AddressDO>()
                .eq("user_id", userId)
                .eq("id", addressId)
                .eq("default_address", 1));
        if (defaultNum == 0) {
            return addressMapper.delete(new EntityWrapper<AddressDO>()
                    .eq("id", addressId)
                    .eq("user_id", userId)) > 0;
        } else {
            if (!(addressMapper.delete(new EntityWrapper<AddressDO>()
                    .eq("id", addressId)
                    .eq("user_id", userId)) > 0)) {
                throw new AppServiceException(ExceptionDefinition.ADDRESS_DATABASE_QUERY_FAILED);
            } else {
                List<AddressDO> addressDOS = addressMapper.selectList(new EntityWrapper<AddressDO>().eq("user_id", userId));
                if (addressDOS.size() != 0) {
                    AddressDO addressDO = addressDOS.get(0);
                    addressDO.setDefaultAddress(1);
                    return addressMapper.updateById(addressDO) > 0;
                }
                return true;
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAddress(Long addressId, String province, String city, String county, String address, Integer defaultAddress, Long userId, String phone, String consignee) throws ServiceException {
        AddressDO addressDO = new AddressDO(province, city, county, address, defaultAddress, userId, phone, consignee);
        Date now = new Date();
        if (defaultAddress != 0) {
            defaultAddress = 1;
            List<AddressDO> addressDOS = addressMapper.selectList(new EntityWrapper<AddressDO>().eq("user_id", userId).eq("default_address", 1));
            if (addressDOS.size() != 0) {
                AddressDO preDefault = addressDOS.get(0);
                preDefault.setDefaultAddress(0);
                addressMapper.updateById(preDefault);
            }
        }
        addressDO.setDefaultAddress(defaultAddress);
        addressDO.setGmtUpdate(now);
        return addressMapper.update(addressDO, new EntityWrapper<AddressDO>()
                .eq("id", addressId)
                .eq("user_id", userId)) > 0;

    }

    @Override
    public List<AddressDO> getAllAddress(Long userId) throws ServiceException {
        return addressMapper.selectList(new EntityWrapper<AddressDO>()
                .eq("user_id", userId));
    }

    @Override
    public AddressDO getAddressById(Long userId, Long addressId) throws ServiceException {
        AddressDO addressDO = new AddressDO();
        addressDO.setUserId(userId);
        addressDO.setId(addressId);
        return addressMapper.selectOne(addressDO);
    }

    @Override
    public AddressDO getDefAddress(Long userId) throws ServiceException {
        List<AddressDO> addressDOS = addressMapper.selectList(
                new EntityWrapper<AddressDO>()
                        .eq("user_id", userId)
                        .eq("default_address", 1));
        if (CollectionUtils.isEmpty(addressDOS)) {
            AddressDO addressDO = new AddressDO();
            addressDO.setCity("XXX");
            addressDO.setProvince("XXX");
            addressDO.setCounty("XXX");
            addressDO.setAddress("不需要收货地址");
            addressDO.setConsignee("匿名");
            addressDO.setPhone("XXXXXXXXXXX");
            return addressDO;
        } else {
            return addressDOS.get(0);
        }
    }

}

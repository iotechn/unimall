package com.iotechn.unimall.app.api.address;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
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
    public String create(String province, String city, String county, String address, Integer defaultAddress, Long userId, String phone, String consignee) throws ServiceException {
        Integer addressCount = addressMapper.selectCount(new QueryWrapper<AddressDO>().eq("user_id", userId));
        AddressDO addressDO = new AddressDO();
        addressDO.setProvince(province);
        addressDO.setCity(city);
        addressDO.setCounty(county);
        addressDO.setAddress(address);
        addressDO.setUserId(userId);
        addressDO.setPhone(phone);
        addressDO.setConsignee(consignee);
        if (addressCount == 0) {
            //  若没有地址，把当前增加的地址设为默认地址
            addressDO.setDefaultAddress(1);
        } else {
            // 若有地址
            if (defaultAddress != 0) {
                // 若这个地址需要是默认地址，则将其他的地址设置为非默认状态
                AddressDO preDefault = new AddressDO();
                preDefault.setDefaultAddress(0);
                addressMapper.update(preDefault, new QueryWrapper<AddressDO>()
                        .eq("user_id", userId)
                        .eq("default_address", 1));
                addressDO.setDefaultAddress(1);
            } else {
                addressDO.setDefaultAddress(0);
            }
        }
        Date now = new Date();
        addressDO.setGmtCreate(now);
        addressDO.setGmtUpdate(now);
        if (addressMapper.insert(addressDO) > 0) {
            return "ok";
        }
        throw new AppServiceException(ExceptionDefinition.ADDRESS_DATABASE_QUERY_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long addressId, Long userId) throws ServiceException {
        Integer defaultNum = addressMapper.selectCount(new QueryWrapper<AddressDO>()
                .eq("user_id", userId)
                .eq("id", addressId)
                .eq("default_address", 1));
        if (defaultNum == 0) {
            // 若不是默认地址，则直接删除
            if (addressMapper.delete(new QueryWrapper<AddressDO>()
                    .eq("id", addressId)
                    .eq("user_id", userId)) > 0) {
                return "ok";
            }
            throw new AppServiceException(ExceptionDefinition.ADDRESS_DATABASE_QUERY_FAILED);
        } else {
            // 若是默认地址，需要将其他1个设置为默认地址
            if (addressMapper.delete(new QueryWrapper<AddressDO>()
                    .eq("id", addressId)
                    .eq("user_id", userId)) <= 0) {
                throw new AppServiceException(ExceptionDefinition.ADDRESS_DATABASE_QUERY_FAILED);
            } else {
                List<AddressDO> addressDOS = addressMapper.selectList(new QueryWrapper<AddressDO>().eq("user_id", userId));
                if (!CollectionUtils.isEmpty(addressDOS)) {
                    AddressDO addressDO = addressDOS.get(0);
                    addressDO.setDefaultAddress(1);
                    if (addressMapper.updateById(addressDO) > 0) {
                        return "ok";
                    }
                }
                throw new AppServiceException(ExceptionDefinition.ADDRESS_DATABASE_QUERY_FAILED);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(Long addressId, String province, String city, String county, String address, Integer defaultAddress, Long userId, String phone, String consignee) throws ServiceException {
        AddressDO addressDO = new AddressDO();
        addressDO.setProvince(province);
        addressDO.setCity(city);
        addressDO.setCounty(county);
        addressDO.setAddress(address);
        addressDO.setUserId(userId);
        addressDO.setPhone(phone);
        addressDO.setConsignee(consignee);
        addressDO.setDefaultAddress(defaultAddress);
        Date now = new Date();
        if (defaultAddress != 0) {
            defaultAddress = 1;
            // 将所有地址更新为非默认
            AddressDO updateAddressDO = new AddressDO();
            updateAddressDO.setDefaultAddress(0);
            addressMapper.update(updateAddressDO, new QueryWrapper<AddressDO>().eq("user_id", userId));
        }
        addressDO.setDefaultAddress(defaultAddress);
        addressDO.setGmtUpdate(now);
        if (addressMapper.update(addressDO,
                new QueryWrapper<AddressDO>()
                        .eq("id", addressId)
                        .eq("user_id", userId)) > 0) {
            return "ok";
        }
        throw new AppServiceException(ExceptionDefinition.ADDRESS_DATABASE_QUERY_FAILED);
    }

    @Override
    public List<AddressDO> list(Long userId) throws ServiceException {
        return addressMapper.selectList(new QueryWrapper<AddressDO>()
                .eq("user_id", userId));
    }

    @Override
    public AddressDO getAddressById(Long userId, Long addressId) throws ServiceException {
        return addressMapper.selectOne(
                new QueryWrapper<AddressDO>()
                        .eq("id", addressId)
                        .eq("user_id", userId));
    }

    @Override
    public AddressDO getDefAddress(Long userId) throws ServiceException {
        List<AddressDO> addressDOS = addressMapper.selectList(
                new QueryWrapper<AddressDO>()
                        .eq("user_id", userId)
                        .eq("default_address", 1));
        if (CollectionUtils.isEmpty(addressDOS)) {
            AddressDO addressDO = new AddressDO();
            addressDO.setCity("XXX");
            addressDO.setProvince("XXX");
            addressDO.setCounty("XXX");
            addressDO.setAddress("请点击此处完善收货地址");
            addressDO.setConsignee("匿名");
            addressDO.setPhone("XXXXXXXXXXX");
            return addressDO;
        } else {
            return addressDOS.get(0);
        }
    }

}

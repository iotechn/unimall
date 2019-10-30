package com.iotechn.unimall.plugin.autoship.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.plugin.autoship.domain.SkuAutoShipKeysDO;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/29
 * Time: 20:26
 */
public interface SkuAutoShipKeysMapper extends BaseMapper<SkuAutoShipKeysDO> {

    public SkuAutoShipKeysDO getOneValidCDKDOBySkuId(Long SkuId);

    public Integer decCDKNum(Long keyId);

    public Object initDB();

    public String isInitDB();

}

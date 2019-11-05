package com.iotechn.unimall.plugin.autoship.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.plugin.autoship.domain.SkuAutoShipKeysDO;
import com.iotechn.unimall.plugin.autoship.dto.SkuAutoShipKeysDTO;
import com.iotechn.unimall.plugin.core.annotaion.Plugin;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/29
 * Time: 20:26
 */
public interface SkuAutoShipKeysMapper extends BaseMapper<SkuAutoShipKeysDO> {

    public Integer batchInsert(@Param("list") List<SkuAutoShipKeysDO> list, @Param("now") Date now);

    public List<SkuAutoShipKeysDTO> getSkuAutoShipKeysPage(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("skuId") Long skuId);

    public SkuAutoShipKeysDO getOneValidCDKDOBySkuId(Long SkuId);

    public Integer decCDKNum(Long keyId);

    public Object initDB();

    public String isInitDB();

}

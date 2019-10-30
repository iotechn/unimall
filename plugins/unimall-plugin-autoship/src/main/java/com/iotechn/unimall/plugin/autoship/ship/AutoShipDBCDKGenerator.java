package com.iotechn.unimall.plugin.autoship.ship;

import com.iotechn.unimall.plugin.autoship.domain.SkuAutoShipKeysDO;
import com.iotechn.unimall.plugin.autoship.inter.AutoShipCDKGenerator;
import com.iotechn.unimall.plugin.autoship.mapper.SkuAutoShipKeysMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/30
 * Time: 10:08
 */
public class AutoShipDBCDKGenerator implements AutoShipCDKGenerator {

    private SkuAutoShipKeysMapper skuAutoShipKeysMapper;

    @Override
    public String generate(Long skuId) {
        SkuAutoShipKeysDO skuAutoShipKeysDO = skuAutoShipKeysMapper.getOneValidCDKDOBySkuId(skuId);
        if (skuAutoShipKeysDO != null) {
            //若不为空，则返回此CDK，并减少times
            skuAutoShipKeysMapper.decCDKNum(skuAutoShipKeysDO.getId());
            return skuAutoShipKeysDO.getCdk();
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.skuAutoShipKeysMapper = applicationContext.getBean(SkuAutoShipKeysMapper.class);
    }
}

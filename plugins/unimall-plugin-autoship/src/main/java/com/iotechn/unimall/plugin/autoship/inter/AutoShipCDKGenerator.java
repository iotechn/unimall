package com.iotechn.unimall.plugin.autoship.inter;

import org.springframework.context.ApplicationContextAware;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/30
 * Time: 10:07
 */
public interface AutoShipCDKGenerator extends ApplicationContextAware {

    public String generate(Long skuId);

}

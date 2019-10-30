package com.iotechn.unimall.plugin.autoship.config;

import com.iotechn.unimall.plugin.autoship.inter.AutoShipCDKGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/30
 * Time: 10:10
 */
@Configuration
public class GeneratorConfig {

    @Value("${com.iotechn.unimall.plugin.autoship.generator-clazz:com.iotechn.unimall.plugin.autoship.ship.AutoShipDBCDKGenerator}")
    private String generatorClazz;

    @Bean
    public AutoShipCDKGenerator autoShipCDKGenerator() throws Exception {
        Class<?> clazz = Class.forName(generatorClazz);
        return (AutoShipCDKGenerator) clazz.newInstance();
    }

}

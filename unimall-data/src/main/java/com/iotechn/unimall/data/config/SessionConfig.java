package com.iotechn.unimall.data.config;

import com.dobbinsoft.fw.support.properties.FwSystemProperties;
import com.dobbinsoft.fw.support.session.SessionStorage;
import com.dobbinsoft.fw.support.session.SessionStorageRedisImpl;
import com.iotechn.unimall.data.dto.admin.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.dobbinsoft.fw.core.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: SessionConfig
 * Description: Session配置，使用Redis存储session
 */
@Configuration
public class SessionConfig {

    @Autowired
    private FwSystemProperties fwSystemProperties;

    @Bean
    public SessionStorage sessionStorage() {
        return new SessionStorageRedisImpl(fwSystemProperties.getMutexLogin());
    }

    @Bean
    public SessionUtil<UserDTO, AdminDTO> sessionUtil() {
        return new SessionUtil<>(UserDTO.class, AdminDTO.class);
    }

}

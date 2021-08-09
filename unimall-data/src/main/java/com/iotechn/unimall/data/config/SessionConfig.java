package com.iotechn.unimall.data.config;

import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.dobbinsoft.fw.core.util.SessionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: SessionConfig
 * Description: TODO
 *
 * @author: e-weichaozheng
 * @date: 2021-03-18
 */
@Configuration
public class SessionConfig {

    @Bean
    public SessionUtil<UserDTO, AdminDTO> sessionUtil() {
        return new SessionUtil<>(UserDTO.class, AdminDTO.class);
    }

}

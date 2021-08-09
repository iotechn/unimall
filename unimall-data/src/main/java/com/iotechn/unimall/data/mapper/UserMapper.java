package com.iotechn.unimall.data.mapper;

import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.dobbinsoft.fw.support.mapper.IMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by rize on 2019/7/1.
 */
public interface UserMapper extends IMapper<UserDO> {

    public UserDTO login(@Param("phone") String phone, @Param("cryptPassword") String cryptPassword);

}

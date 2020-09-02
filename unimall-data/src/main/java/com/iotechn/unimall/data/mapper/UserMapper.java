package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.annotations.Param;

/**
 * Created by rize on 2019/7/1.
 */
public interface UserMapper extends IMapper<UserDO> {

    public UserDTO login(@Param("phone") String phone, @Param("cryptPassword") String cryptPassword);

    public Page<UserDO> getUserList(
            IPage<UserDO> page,
            @Param("id") Long id, @Param("nickname") String nickname,
            @Param("level") Integer level, @Param("gender") Integer gender,
            @Param("status") Integer status);

    public Integer countUser(
            @Param("id") Long id, @Param("nickname") String nickname,
            @Param("level") Integer level, @Param("gender") Integer gender,
            @Param("status") Integer status);
}

package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/1.
 */
public interface UserMapper extends BaseMapper<UserDO> {

    public UserDTO login(@Param("phone") String phone, @Param("cryptPassword") String cryptPassword);

    public List<UserDO> getUserList(
            @Param("id") Long id, @Param("nickname") String nickname,
            @Param("level") Integer level, @Param("gender") Integer gender,
            @Param("status") Integer status, @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    public Integer countUser(
            @Param("id") Long id, @Param("nickname") String nickname,
            @Param("level") Integer level, @Param("gender") Integer gender,
            @Param("status") Integer status);
}

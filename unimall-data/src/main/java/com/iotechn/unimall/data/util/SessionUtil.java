package com.iotechn.unimall.data.util;


import com.iotechn.unimall.data.dto.UserDTO;

/**
 * Created by rize on 2019/2/27.
 */
public class SessionUtil {

    private static ThreadLocal<UserDTO> userLocal = new ThreadLocal<>();

    public static void setUser(UserDTO userDTO) {
        userLocal.set(userDTO);
    }

    public static UserDTO getUser() {
        return userLocal.get();
    }

}

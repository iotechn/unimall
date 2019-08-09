package com.iotechn.unimall.data.util;


import com.google.protobuf.ServiceException;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;

import java.util.List;

/**
 * Created by rize on 2019/2/27.
 */
public class SessionUtil {

    private static ThreadLocal<UserDTO> userLocal = new ThreadLocal<>();

    private static ThreadLocal<AdminDTO> adminLocal = new ThreadLocal<>();

    public static void setUser(UserDTO userDTO) {
        userLocal.set(userDTO);
    }

    public static UserDTO getUser() {
        return userLocal.get();
    }

    public static void setAdmin(AdminDTO adminDTO) {
        adminLocal.set(adminDTO);
    }

    public static AdminDTO getAdmin() {
        return adminLocal.get();
    }

    public static boolean hasPerm(String permission) throws ServiceException {
        //拥有的权限
        List<String> perms = getAdmin().getPerms();
        boolean hasPerm = false;
        //目标匹配权限
        String[] permissions = permission.split(":");
        outer : for(String item : perms) {
            //拥有的权限点
            String[] hasPer = item.split(":");
            inner : for (int i = 0; i < permissions.length; i++) {
                if ("*".equals(hasPer[i])) {
                    hasPerm = true;
                    break outer;
                } else if (hasPer[i].equals(permissions[i])){
                    //此层合格
                    if (i == permissions.length - 1) {
                        //若是目标层的最后一层。则表示所有层校验通过
                        hasPerm = true;
                    }
                } else {
                    break inner;
                }
            }
        }
        return hasPerm;
    }

}

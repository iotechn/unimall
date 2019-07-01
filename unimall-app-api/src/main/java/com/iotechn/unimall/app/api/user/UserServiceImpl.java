package com.iotechn.unimall.app.api.user;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rize on 2019/6/30.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String test(String say) throws ServiceException {
        return "您说:" + say;
    }
}

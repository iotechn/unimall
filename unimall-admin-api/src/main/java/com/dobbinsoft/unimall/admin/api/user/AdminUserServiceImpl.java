package com.dobbinsoft.unimall.admin.api.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.utils.RandomStringUtils;
import com.dobbinsoft.unimall.data.domain.UserDO;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.UserMapper;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-11
 * Time: 下午7:57
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id, Long adminId) throws ServiceException {
        return userMapper.delete(new QueryWrapper<UserDO>()
                .eq("id", id)) > 0;
    }

    // TODO 抽取DTO
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(UserDO user, Long adminId) throws ServiceException {
        if (user == null || user.getId() == null){
            throw new ServiceException(ExceptionDefinition.USER_INFORMATION_MISSING);
        }
        if(user.getPhone() == null){
            throw new ServiceException(ExceptionDefinition.USER_INFORMATION_MISSING);
        }
        if(userMapper.selectCount(new QueryWrapper<UserDO>().eq("phone",user.getPhone()).notIn("id",user.getId())) > 0){
            throw new ServiceException(ExceptionDefinition.USER_PHONE_ALREADY_EXIST);
        }
        LocalDateTime now = LocalDateTime.now();
        user.setGmtUpdate(now);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public Boolean editStatus(Long userId, Integer status, Long adminId) throws ServiceException {
        if(userId == null || status == null || (status != 0 && status != 1)){
            throw new ServiceException(ExceptionDefinition.USER_INFORMATION_MISSING);
        }
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setStatus(status);
        userDO.setGmtUpdate(LocalDateTime.now());
        return userMapper.updateById(userDO) > 0;
    }

    @Override
    public Page<UserDO> list(Long id, String nickname, Integer level, Integer gender, Integer status, Integer pageNo, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (level != null) {
            wrapper.eq("level", level);
        }
        if (gender != null) {
            wrapper.eq("gender", gender);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (!ObjectUtils.isEmpty(nickname)) {
            wrapper.like("nickname", nickname);
        }
        wrapper.orderByDesc("id");
        return userMapper.selectPage(Page.div(pageNo, limit, UserDO.class), wrapper);
    }
    
}

package com.iotechn.unimall.admin.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AdminLogDO;
import com.iotechn.unimall.data.mapper.AdminLogMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: rize
 * Date: 2020/8/11
 * Time: 16:40
 */
@Service("adminLogService")
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    private AdminLogMapper adminLogMapper;

    @Override
    public Page<AdminLogDO> list(Long targetAdminId, Integer page, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<AdminLogDO> wrapper = new QueryWrapper<>();
        if (targetAdminId != null)
            wrapper.eq("admin_id", targetAdminId);
        return adminLogMapper.selectPage(Page.div(page, limit, AdminLogDO.class), wrapper.orderByDesc("id"));
    }
}

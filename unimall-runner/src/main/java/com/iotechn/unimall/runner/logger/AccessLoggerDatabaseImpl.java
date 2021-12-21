package com.iotechn.unimall.runner.logger;

import com.iotechn.unimall.data.domain.AdminLogDO;
import com.iotechn.unimall.data.mapper.AdminLogMapper;
import com.dobbinsoft.fw.launcher.log.AccessLog;
import com.dobbinsoft.fw.launcher.log.AccessLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: AdminLoggerDatabaseImpl
 * Description: 管理员日志，数据库实现
 *
 * @author: e-weichaozheng
 * @date: 2021-03-18
 */
@Component
public class AccessLoggerDatabaseImpl implements AccessLogger {

    @Autowired
    private AdminLogMapper adminLogMapper;

    @Override
    public void save(AccessLog accessLog) {
        AdminLogDO adminLogDO = new AdminLogDO();
        adminLogDO.setAdminId(accessLog.getAdminId());
        adminLogDO.setApiGroup(accessLog.getGroup());
        adminLogDO.setApiMethod(accessLog.getMethod());
        adminLogDO.setRequestId(accessLog.getRequestId());
        adminLogDO.setGmtCreate(new Date());
        adminLogMapper.insert(adminLogDO);
    }
}

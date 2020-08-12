package com.iotechn.unimall.biz.service.admin;

import com.iotechn.unimall.data.domain.AdminLogDO;
import com.iotechn.unimall.data.mapper.AdminLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: rize
 * Date: 2020/8/11
 * Time: 16:09
 */
@Service("adminBizService")
public class AdminBizService {

    @Autowired
    private AdminLogMapper adminLogMapper;

    public void saveLog(AdminLogDO log) {
        adminLogMapper.insert(log);
    }

}

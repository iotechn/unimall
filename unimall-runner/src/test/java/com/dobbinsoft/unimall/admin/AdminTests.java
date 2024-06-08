package com.dobbinsoft.unimall.admin;

import com.dobbinsoft.fw.core.annotation.AiRefer;
import com.dobbinsoft.fw.support.utils.DigestUtils;
import com.dobbinsoft.fw.support.utils.RandomStringUtils;
import com.dobbinsoft.unimall.data.domain.AdminDO;
import com.dobbinsoft.unimall.data.enums.StatusType;
import com.dobbinsoft.unimall.data.mapper.AdminMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.dobbinsoft.unimall.runner.UnimallRunnerApplication.class)
public class AdminTests {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    @AiRefer(AdminDO.class)
    public void initAdmin() {
        // 初始化一个角色ID为1的管理员
        AdminDO adminDO = new AdminDO();
        adminDO.setUsername("admin");
        adminDO.setRealname("魏朝正");
        adminDO.setSalt(RandomStringUtils.randomNumeric(6));
        adminDO.setPassword(DigestUtils.md5Hex("123456" + adminDO.getSalt()));
        adminDO.setPhone("18584669549");
        adminDO.setRoleIds("[1]");
        adminDO.setStatus(StatusType.ACTIVE.getCode());
        adminMapper.insert(adminDO);
    }

}

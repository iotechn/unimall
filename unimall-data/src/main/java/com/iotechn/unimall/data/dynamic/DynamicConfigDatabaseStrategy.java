package com.iotechn.unimall.data.dynamic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.domain.DynamicConfigDO;
import com.iotechn.unimall.data.mapper.DynamicConfigMapper;
import com.dobbinsoft.fw.support.component.dynamic.DynamicStorageStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: DynamicConfigDatabaseStrategy
 * Description: 动态配置持久化 数据库策略
 *
 * @author: e-weichaozheng
 * @date: 2021-03-17
 */
@Component
public class DynamicConfigDatabaseStrategy implements DynamicStorageStrategy {

    @Autowired
    private DynamicConfigMapper dynamicConfigMapper;

    @Override
    public void write(String key, String value) {
        Integer count = dynamicConfigMapper.selectCount(new QueryWrapper<DynamicConfigDO>().eq("config_key", key));
        Date now = new Date();
        if (count == 0) {
            // 添加一条新的记录
            DynamicConfigDO insertDynamicConfigDO = new DynamicConfigDO();
            insertDynamicConfigDO.setConfigKey(key);
            insertDynamicConfigDO.setConfigValue(value);
            insertDynamicConfigDO.setGmtCreate(now);
            insertDynamicConfigDO.setGmtUpdate(now);
            dynamicConfigMapper.insert(insertDynamicConfigDO);
        } else {
            // 更新旧记录
            DynamicConfigDO updateDynamicConfigDO = new DynamicConfigDO();
            updateDynamicConfigDO.setConfigValue(value);
            updateDynamicConfigDO.setGmtCreate(now);
            dynamicConfigMapper.update(updateDynamicConfigDO, new QueryWrapper<DynamicConfigDO>().eq("config_key", key));
        }
        // 由于是无事务单条写SQL，此处已经完成持久化
    }

    @Override
    public String read(String key) {
        DynamicConfigDO dynamicConfigDO = dynamicConfigMapper.selectOne(new QueryWrapper<DynamicConfigDO>().eq("config_key", key));
        if (dynamicConfigDO == null) {
            return null;
        }
        return dynamicConfigDO.getConfigValue();
    }

    @Override
    public boolean del(String key) {
        return dynamicConfigMapper.delete(new QueryWrapper<DynamicConfigDO>().eq("config_key", key)) > 0;
    }
}

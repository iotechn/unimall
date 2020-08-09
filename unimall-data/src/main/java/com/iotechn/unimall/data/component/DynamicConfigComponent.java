package com.iotechn.unimall.data.component;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.constant.LockConst;
import com.iotechn.unimall.data.domain.DynamicConfigDO;
import com.iotechn.unimall.data.mapper.DynamicConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.function.Function;

/**
 * Description: 动态配置组件
 * User: rize
 * Date: 2020/8/5
 * Time: 11:31
 */
@Component
public class DynamicConfigComponent {

    @Autowired
    private DynamicConfigMapper dynamicConfigMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private LockComponent lockComponent;

    private static final Logger logger = LoggerFactory.getLogger(DynamicConfigComponent.class);

    /**
     * 写动态配置
     *
     * @param key
     * @param value
     */
    public void write(String key, String value) {
        // 防止重复提交
        if (lockComponent.tryLock(LockConst.DYNAMIC_CONFIG_LOCK + key, 15)) {
            try {
                Integer count = dynamicConfigMapper.selectCount(new QueryWrapper<DynamicConfigDO>().eq("`key`", key));
                Date now = new Date();
                if (count == 0) {
                    // 添加一条新的记录
                    DynamicConfigDO insertDynamicConfigDO = new DynamicConfigDO();
                    insertDynamicConfigDO.setKey(key);
                    insertDynamicConfigDO.setValue(value);
                    insertDynamicConfigDO.setGmtCreate(now);
                    insertDynamicConfigDO.setGmtUpdate(now);
                    dynamicConfigMapper.insert(insertDynamicConfigDO);
                } else {
                    // 更新旧记录
                    DynamicConfigDO updateDynamicConfigDO = new DynamicConfigDO();
                    updateDynamicConfigDO.setValue(value);
                    updateDynamicConfigDO.setGmtCreate(now);
                    dynamicConfigMapper.update(updateDynamicConfigDO, new QueryWrapper<DynamicConfigDO>().eq("`key`", key));
                }
                // 由于是无事务单条写SQL，此处已经完成持久化
                cacheComponent.del(CacheConst.DYNAMIC_CACHE + key);
            } catch (Exception e) {
                logger.error("[写动态配置] 异常", e);
            } finally {
                lockComponent.release(LockConst.DYNAMIC_CONFIG_LOCK + key);
            }
        }
    }

    public Integer readInt(String key, Integer defaultValue) {
        return catchNumberFormatException(this.readAction(key, defaultValue, Integer::parseInt), defaultValue);
    }

    public Long readLong(String key, Long defaultValue) {
        return catchNumberFormatException(this.readAction(key, defaultValue, Long::parseLong), defaultValue);
    }

    public String readString(String key, String defaultValue) {
        return this.readAction(key, defaultValue, item->item);
    }

    public <T> T readObj(String key, Class<T> clazz) {
        return this.readAction(key, null, item-> JSONObject.parseObject(item, clazz));
    }

    /**
     * 读取行为的统一封装
     * @param key
     * @param defaultValue
     * @param function 相当于是个返序列化的方法
     * @param <T>
     * @return
     */
    public <T> T readAction(String key, T defaultValue, Function<String, T> function) {
        String raw = cacheComponent.getRaw(CacheConst.DYNAMIC_CACHE + key);
        if (!StringUtils.isEmpty(raw)) {
            return function.apply(raw);
        }
        DynamicConfigDO dynamicConfigDO = dynamicConfigMapper.selectOne(new QueryWrapper<DynamicConfigDO>().eq("`key`", key));
        if (dynamicConfigDO == null) {
            return defaultValue;
        }
        // 放入缓存
        cacheComponent.putRaw(CacheConst.DYNAMIC_CACHE + key, dynamicConfigDO.getValue());
        return function.apply(dynamicConfigDO.getValue());
    }

    /**
     * 捕获调数字格式化
     * @param value
     * @param defaultValue
     * @param <T>
     * @return
     */
    private <T extends Number> T catchNumberFormatException(T value, T defaultValue) {
        try {
            return value;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}

package com.iotechn.unimall.data.component;

import com.iotechn.unimall.data.domain.DynamicConfigDO;
import com.iotechn.unimall.data.mapper.DynamicConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description: 动态配置组件
 * User: rize
 * Date: 2020/8/5
 * Time: 11:31
 */
public class DynamicConfigComponent {

    @Autowired
    private DynamicConfigMapper dynamicConfigMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private LockComponent lockComponent;

    /**
     * 写动态配置
     * @param key
     * @param value
     */
    public void write(String key, String value) {
//        lockComponent.tryLock(LockConst)
//        dynamicConfigMapper.
    }

    /**
     * 读动态配置
     * @param key
     * @return
     */
    public DynamicConfigDO read(String key) {
        return null;
    }

    public int readInt(String key, int defaultValue) {
        return 0;
    }

    public long readLong(String key, long defaultValue) {
        return 0l;
    }

    public String readString(String key, String defaultValue) {
        return "";
    }

    public <T> T readObj(String key, Class<T> clazz) {
        return null;
    }

}

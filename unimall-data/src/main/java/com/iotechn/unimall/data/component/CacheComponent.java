package com.iotechn.unimall.data.component;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by rize on 2019/3/22.
 */
@Component
public class CacheComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void putObj(String key, Object obj, Integer expireSec) {
        if (expireSec != null) {
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(obj), expireSec, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(obj));
        }
    }

    public Long incRaw(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    public  <T> T getObj(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSONObject.parseObject(json, clazz);
    }

    public <T> List<T> getObjList(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSONObject.parseArray(json, clazz);
    }

    public void putHashAll(String key, Map<String, String> map, Integer expireSec) {
        stringRedisTemplate.opsForHash().putAll(key, map);
        stringRedisTemplate.expire(key, expireSec, TimeUnit.SECONDS);
    }

    public Map<String,String> getHashAll(String key) {
        if (!stringRedisTemplate.hasKey(key)) {
            return null;
        }
        return (Map)stringRedisTemplate.opsForHash().entries(key);
    }

    public <T> T getHashObj(String hashName, String key, Class<T> clazz) {
        String o = (String) stringRedisTemplate.opsForHash().get(hashName, key);
        if (StringUtils.isEmpty(o)) {
            return null;
        }
        return JSONObject.parseObject(o, clazz);
    }

    public String getHashRaw(String hashName, String key) {
        String o = (String) stringRedisTemplate.opsForHash().get(hashName, key);
        if (StringUtils.isEmpty(o)) {
            return null;
        }
        return o;
    }

    public <T> List<T> getHashArray(String hashName, String key, Class<T> clazz) {
        String o = (String) stringRedisTemplate.opsForHash().get(hashName, key);
        if (StringUtils.isEmpty(o)) {
            return null;
        }
        return JSONObject.parseArray(o, clazz);
    }

    public Long incHashRaw(String hashName, String key, long delta) {
        return stringRedisTemplate.opsForHash().increment(hashName, key, delta);
    }

    public void putHashRaw(String hashName, String key, String str, Integer expireSec) {
        boolean hasKey = stringRedisTemplate.hasKey(key);
        stringRedisTemplate.opsForHash().put(hashName, key, str);
        if (!hasKey) {
            stringRedisTemplate.expire(key, expireSec, TimeUnit.SECONDS);
        }
    }

    public void putHashRaw(String hashName, String key, String str) {
        stringRedisTemplate.opsForHash().put(hashName, key, str);
    }

    public void putHashObj(String hashName, String key, Object obj, Integer expireSec) {
        boolean hasKey = stringRedisTemplate.hasKey(key);
        stringRedisTemplate.opsForHash().put(hashName, key, JSONObject.toJSONString(obj));
        if (!hasKey) {
            stringRedisTemplate.expire(key, expireSec, TimeUnit.SECONDS);
        }
    }

    public void delHashObj(String hashName, String key) {
        stringRedisTemplate.opsForHash().delete(hashName, key);
    }


    public void putRaw(String key, String value) {
        putRaw(key, value, null);
    }

    public void putRaw(String key, String value, Integer expireSec) {
        if (expireSec != null) {
            stringRedisTemplate.opsForValue().set(key, value, expireSec, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

    public String getRaw(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void putSetRaw(String key, String member, Integer expireSec) {
        stringRedisTemplate.opsForSet().add(key, member);
        stringRedisTemplate.expire(key, expireSec, TimeUnit.SECONDS);
    }

    public void putSetRawAll(String key, String[] set, Integer expireSec) {
        stringRedisTemplate.opsForSet().add(key, set);
        stringRedisTemplate.expire(key, expireSec, TimeUnit.SECONDS);
    }

    public void removeSetRaw(String key, String member) {
        stringRedisTemplate.opsForSet().remove(key, member);
    }

    public boolean isSetMember(String key, String member) {
        return stringRedisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 获取指定前缀的Key
     * @param prefix
     * @return
     */
    public Set<String> getPrefixKeySet(String prefix) {
        return stringRedisTemplate.keys(prefix + "*");
    }

    public void delPrefixKey(String prefix) {
        Set<String> prefixKeySet = getPrefixKeySet(prefix);
        for (String key : prefixKeySet) {
            stringRedisTemplate.delete(key);
        }
    }


}

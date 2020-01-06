package com.iotechn.unimall.plugin.core.inter;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.plugin.core.exception.PluginException;
import com.iotechn.unimall.plugin.core.util.RSAUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.security.interfaces.RSAPublicKey;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 抽象接口。在unimall-core中，提供常用的扩展接口，并将
 * 插件初始化脚本，可考虑使用在容器初始化时，通过对数据库表存在性判断，来确定是否已经初始化
 * 插件编写，不可入侵原程序的
 * maven所在层次 于biz之上
 * afterPropertiesSet()方法中校验权限
 * PublicKey: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCD1H2gmgFntBxKJLtHImyU4C4Egv7pfbuCzRY15HETXVri1esxdomZwdZQlTs4lZipifhZhQ8mBHzilKSzcnr839qqgIBIz6BlWzZdAqZoczWDBQ__GI8ke--bUOoERrfbkwBUIpcmLKP1z9-9kaeMRAzlDgSaumBkHWbPaSOuoQIDAQAB
 * User: rize
 * Date: 2019/10/23
 * Time: 22:29
 */
public abstract class AbstractPlugin implements Comparable<AbstractPlugin>, InitializingBean {

    @Value("${com.iotechn.unimall.wx.mini.app-id}")
    protected String key1;

    @Value("${com.iotechn.unimall.wx.app.app-id}")
    protected String key2;

    @Value("${com.iotechn.unimall.wx.h5.app-id}")
    protected String key3;

    /**
     * 默认权重为0.若需要调整顺序,需要在子类中覆盖
     */
    protected int weight = 0;

    public static final String PUBIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCD1H2gmgFntBxKJLtHImyU4C4Egv7pfbuCzRY15HETXVri1esxdomZwdZQlTs4lZipifhZhQ8mBHzilKSzcnr839qqgIBIz6BlWzZdAqZoczWDBQ__GI8ke--bUOoERrfbkwBUIpcmLKP1z9-9kaeMRAzlDgSaumBkHWbPaSOuoQIDAQAB";

    protected void afterPluginInit() throws Exception {

    }

    @Override
    public int compareTo(AbstractPlugin o) {
        return o.weight - this.weight;
    }

    public abstract void installPlugin() throws Exception;

    public abstract String getTag();

    public abstract String getSign();

}

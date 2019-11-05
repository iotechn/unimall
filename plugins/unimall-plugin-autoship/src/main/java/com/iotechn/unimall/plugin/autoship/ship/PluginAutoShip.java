package com.iotechn.unimall.plugin.autoship.ship;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.biz.service.user.UserBizService;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.iotechn.unimall.data.wx.WeChatCommonTemplateMessageModel;
import com.iotechn.unimall.data.wx.WeChatTemplateValueModel;
import com.iotechn.unimall.plugin.autoship.inter.AutoShipCDKGenerator;
import com.iotechn.unimall.plugin.autoship.mapper.SkuAutoShipKeysMapper;
import com.iotechn.unimall.plugin.core.annotaion.Plugin;
import com.iotechn.unimall.plugin.core.exception.PluginException;
import com.iotechn.unimall.plugin.core.inter.AbstractPlugin;
import com.iotechn.unimall.plugin.core.inter.IPluginPaySuccess;
import com.iotechn.unimall.plugin.core.manager.PluginsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/29
 * Time: 17:02
 */

@Plugin
public final class PluginAutoShip extends AbstractPlugin implements IPluginPaySuccess, ApplicationContextAware {

    @Value("${com.iotechn.unimall.plugin.autoship.sign}")
    protected String sign;

    @Value("${com.iotechn.unimall.plugin.autoship.template-id}")
    private String templateId;

    @Value("${com.iotechn.unimall.env}")
    private String env;

    @Autowired
    private PluginsManager pluginsManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SkuAutoShipKeysMapper skuAutoShipKeysMapper;

    @Autowired
    private AutoShipCDKGenerator autoShipCDKGenerator;

    @Autowired
    private OrderBizService orderBizService;

    private static final Logger logger = LoggerFactory.getLogger(PluginAutoShip.class);

    @Override
    protected void afterPluginInit() throws Exception {
        pluginsManager.registerPlugin(this);
        this.installPlugin();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void installPlugin() throws Exception {
        //安装插件
        Object o = skuAutoShipKeysMapper.isInitDB();
        if (o == null) {
            //1.复制前端文件到 对应 文件夹
            String projectDir = System.getProperty("user.dir");
            if ("1".equalsIgnoreCase(env)) {
                //1.1. 检测文件相对路径
                File baseDir = new File(projectDir);
                List<String> children = Arrays.asList(baseDir.list());
                if (!children.contains("unimall-app") || !children.contains("unimall-admin")) {
                    throw new PluginException("安装插件未检测到完整目录，请问您是否配置 com.iotechn.unimall.env 为生产环境");
                }

                //1.1. 复制文件
                String pluginsStr = projectDir + "/unimall-admin/src/views/plugins";
                File plugin = new File(pluginsStr);
                if (!plugin.exists()) {
                    plugin.mkdir();
                }
                String autoshipStr = projectDir + "/unimall-admin/src/views/plugins/autoship.vue";
                File autoship = new File(autoshipStr);
                if (!autoship.exists()) {
                    ClassPathResource file = new ClassPathResource("pluginres/admin/autoship.vue");
                    InputStream inputStream = file.getInputStream();
                    FileCopyUtils.copy(inputStream, new FileOutputStream(autoship));
                }

                //1.2. 复制apijs
                String apiJsStr = projectDir + "/unimall-admin/src/api/plugins";
                File apiJs = new File(apiJsStr);
                if (!apiJs.exists()) {
                    plugin.mkdir();
                }
                String autoshipJsStr = projectDir + "/unimall-admin/src/api/plugins/autoship.js";
                File autoshipJs = new File(autoshipJsStr);
                if (!autoshipJs.exists()) {
                    ClassPathResource file = new ClassPathResource("pluginres/admin/autoship.js");
                    InputStream inputStream = file.getInputStream();
                    FileCopyUtils.copy(inputStream, new FileOutputStream(autoshipJs));
                }

                //1.3. 配置页面JSON
                String routeJsonStr = projectDir + "/unimall-admin/src/router/route.json";
                File routeJson = new File(routeJsonStr);
                JSONArray array = JSONObject.parseArray(new String(FileCopyUtils.copyToByteArray(new FileInputStream(routeJson))));
                boolean hasInit = false;
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    if (!StringUtils.isEmpty(jsonObject.getString("name")) && jsonObject.getString("name").equals("goodsManage")) {
                        JSONArray routeChildren = jsonObject.getJSONArray("children");
                        for (int j = 0; j < routeChildren.size(); j++) {
                            JSONObject child = routeChildren.getJSONObject(j);
                            if (child.getString("name").equals("autoship")) {
                                hasInit = true;
                            }
                        }
                        if (!hasInit) {
                            JSONObject newJSONObject = new JSONObject();
                            newJSONObject.put("page", "/plugins/autoship");
                            newJSONObject.put("path", "autoship");
                            JSONObject newMetaJSONObject = new JSONObject();
                            newMetaJSONObject.put("noCache", true);
                            newMetaJSONObject.put("perms", Arrays.asList("plugin:autoship:create", "plugin:autoship:batchCreate", "plugin:autoship:edit", "plugin:autoship:list", "plugin:autoship:delete"));
                            newMetaJSONObject.put("title", "自动发货");
                            newJSONObject.put("meta", newMetaJSONObject);
                            newJSONObject.put("name", "autoship");
                            routeChildren.add(newJSONObject);
                       }
                    }
                }
                if (!hasInit) {
                    //将文件覆盖下去
                    String json = JSONObject.toJSONString(array, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                    routeJson.delete();
                    routeJson.createNewFile();
                    FileCopyUtils.copy(json.getBytes("utf-8"), new FileOutputStream(routeJson));
                }
            }
            //2.初始化DB
            skuAutoShipKeysMapper.initDB();
            logger.info("[插件] AutoShip 已经初始化成功");
            logger.info("[插件] AutoShip 已经初始化成功");
            logger.info("[插件] AutoShip 已经初始化成功");
        }
    }

    @Override
    public String getTag() {
        return "AUTO_SHIP";
    }

    @Override
    public String getSign() {
        return this.sign;
    }

    @Override
    public OrderDTO invoke(OrderDTO orderDTO, String prepayId) {
        //在外部申明式事务中开启子事务，防止发货失败，而导致父事务被回滚。
        return transactionTemplate.execute(transactionStatus -> {
            try {
                if (!StringUtils.isEmpty(prepayId) || orderDTO == null || CollectionUtils.isEmpty(orderDTO.getSkuList())) {
                    //1.标记订单发货
                    OrderDO updateOrderDO = new OrderDO();
                    Date now = new Date();
                    updateOrderDO.setGmtUpdate(now);
                    updateOrderDO.setGmtShip(now);
                    updateOrderDO.setStatus(OrderStatusType.WAIT_CONFIRM.getCode());
                    orderBizService.changeOrderStatus(orderDTO.getOrderNo(), OrderStatusType.WAIT_STOCK.getCode(), updateOrderDO);
                    //2.发送微信模板消息
                    WeChatCommonTemplateMessageModel model = new WeChatCommonTemplateMessageModel();
                    UserDO userDO = userMapper.selectById(orderDTO.getUserId());
                    model.setTemplate_id(templateId);
                    model.setForm_id(prepayId);
                    model.setTouser(userDO.getOpenId());
                    Map<String, WeChatTemplateValueModel> param = new HashMap<>();
                    StringBuilder sbSku = new StringBuilder();
                    StringBuilder sbMono = new StringBuilder();
                    orderDTO.getSkuList().forEach(item ->{
                        sbSku.append(item.getSpuTitle() + "_" +item.getTitle() + " * " + item.getNum() + "\n");
                        for (int i = 0; i < item.getNum(); i++) {
                            String cdk = autoShipCDKGenerator.generate(item.getSkuId());
                            sbMono.append(item.getSpuTitle() + "_" +item.getTitle() + ":\n\t");
                            sbMono.append(StringUtils.isEmpty(cdk) ? "缺卡" : cdk);
                            sbMono.append("\n");
                        }
                    });
                    param.put("keyword1", new WeChatTemplateValueModel(orderDTO.getOrderNo()));
                    param.put("keyword2", new WeChatTemplateValueModel(sbSku.toString()));
                    param.put("keyword3", new WeChatTemplateValueModel(sbMono.toString()));
                    model.setData(param);
                    if (!userBizService.sendWechatMiniTemplateMessage(model)) {
                        throw new AppServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.PLUGIN_EXCEPTION, "微信模板消息发送失败"));
                    }
                    return orderDTO;
                }
                logger.info("[放弃自动发货] 参数校验失败");
            } catch (Exception e) {
                logger.error("[自动发货] 发生异常", e);
                transactionStatus.setRollbackOnly();
            }
            return orderDTO;
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println();
    }
}

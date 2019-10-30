package com.iotechn.unimall.plugin.autoship.ship;

import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.biz.service.user.UserBizService;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.iotechn.unimall.data.wx.WeChatCommonTemplateMessageModel;
import com.iotechn.unimall.data.wx.WeChatTemplateValueModel;
import com.iotechn.unimall.plugin.autoship.inter.AutoShipCDKGenerator;
import com.iotechn.unimall.plugin.core.annotaion.Plugin;
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
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/29
 * Time: 17:02
 */

@Plugin
@ComponentScan(basePackages = "com.iotechn.unimall")
public class PluginAutoShip extends AbstractPlugin implements IPluginPaySuccess, ApplicationContextAware {

    @Value("${com.iotechn.unimall.plugin.autoship.sign}")
    protected String sign;

    @Value("${com.iotechn.unimall.plugin.autoship.template-id}")
    private String templateId;

    @Autowired
    private PluginsManager pluginsManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AutoShipCDKGenerator autoShipCDKGenerator;

    private OrderBizService orderBizService;

    private static final Logger logger = LoggerFactory.getLogger(PluginAutoShip.class);

    @Override
    protected void afterPluginInit() throws Exception {
        pluginsManager.registerPlugin(this);
    }

    @Override
    public void installPlugin() {

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
                    userBizService.sendWechatMiniTemplateMessage(model);
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

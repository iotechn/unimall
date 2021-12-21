package com.iotechn.unimall.app.api.vip;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.AppServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.util.GeneratorUtil;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.component.MachineComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.properties.FwWxPayProperties;
import com.dobbinsoft.fw.support.service.BaseService;
import com.iotechn.unimall.biz.util.PaySelector;
import com.iotechn.unimall.data.domain.VipOrderDO;
import com.iotechn.unimall.data.domain.VipTemplateDO;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.enums.VipOrderStatusType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.VipOrderMapper;
import com.iotechn.unimall.data.mapper.VipTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VipOrderServiceImpl extends BaseService<UserDTO, AdminDTO> implements VipOrderService {

    @Autowired
    private VipTemplateMapper templateMapper;

    @Autowired
    private VipOrderMapper vipOrderMapper;

    @Autowired
    private MatrixPayService matrixPayService;

    @Autowired
    private FwWxPayProperties fwWxPayProperties;

    @Autowired
    private MachineComponent machineComponent;

    @Value("${com.dobbinsoft.fw.env}")
    private String ENV;

    @Autowired
    private PaySelector paySelector;

    private static final Logger logger = LoggerFactory.getLogger(VipOrderServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object prepay(Integer payPlatform, String payChannel, Long templateId, String ip, Long userId) throws ServiceException {
        VipTemplateDO vipTemplateDO = templateMapper.selectById(templateId);
        if (vipTemplateDO == null || 1 != vipTemplateDO.getDisplay().intValue()) {
            throw new AppServiceException(ExceptionDefinition.VIP_TEMPLATE_NULL_OR_NOT_DISPLAY);
        }
        String orderNo = GeneratorUtil.genOrderId(this.machineComponent.getMachineNo() + "", this.ENV);
        VipOrderDO vipOrderDO = new VipOrderDO();
        vipOrderDO.setDayNum(vipTemplateDO.getDayNum());
        vipOrderDO.setPrice(vipTemplateDO.getPrice());
        vipOrderDO.setStatus(VipOrderStatusType.WAIT_BUY.getCode());
        vipOrderDO.setTemplateId(vipTemplateDO.getId());
        vipOrderDO.setOrderNo(orderNo);
        vipOrderDO.setUserId(userId);
        vipOrderDO.setTitle(vipTemplateDO.getTitle());
        vipOrderDO.setDescription(vipTemplateDO.getDescription());
        vipOrderMapper.insert(vipOrderDO);
        try {
            // 前端来决定支付方式
            MatrixPayUnifiedOrderRequest orderRequest = new MatrixPayUnifiedOrderRequest();
            paySelector.packPayChannel(orderRequest, payPlatform, payChannel);
            orderRequest.setNotifyUrl(fwWxPayProperties.getNotifyUrl() + "/vip");
            // 区分回调 直接通过 S 来判断
            orderRequest.setOutTradeNo(orderNo);
            orderRequest.setOpenid(sessionUtil.getUser().getWxMpOpenId());
            orderRequest.setBody("vip_" + orderNo);
            orderRequest.setTotalFee(vipOrderDO.getPrice());
            orderRequest.setSpbillCreateIp(ip);
            orderRequest.setPayPlatform(PayPlatformType.getByCode(payPlatform));
            orderRequest.setPayChannel(PayChannelType.getByCode(payChannel));
            Object object = matrixPayService.createOrder(orderRequest);
            return object;
        } catch (MatrixPayException e) {
            logger.error("[Matrix支付] 异常", e);
            throw new AppServiceException(e.getErrCodeDes(), ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
        } catch (Exception e) {
            logger.error("[预付款异常]", e);
            throw new AppServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public Page<VipOrderDO> queryVipOrder(Integer status, Integer pageNo, Integer limit, Long userId) throws ServiceException {
        QueryWrapper<VipOrderDO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null) {
            wrapper.eq("status", status);
        }
        return vipOrderMapper.selectPage(Page.div(pageNo, limit, VipOrderDO.class), wrapper);
    }

}

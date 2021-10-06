package com.iotechn.unimall.app.api.vip;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.AppServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.util.GeneratorUtil;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.service.BaseService;
import com.iotechn.unimall.biz.service.pay.PayBizService;
import com.iotechn.unimall.data.domain.VipOrderDO;
import com.iotechn.unimall.data.domain.VipTemplateDO;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.enums.VipOrderStatusType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.VipOrderMapper;
import com.iotechn.unimall.data.mapper.VipTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class VipOrderServiceImpl extends BaseService<UserDTO, AdminDTO> implements VipOrderService {

    @Autowired
    private VipTemplateMapper templateMapper;

    @Autowired
    private VipOrderMapper vipOrderMapper;

    @Autowired
    private PayBizService payBizService;

    @Value("${com.iotechn.unimall.machine-no}")
    private String MACHINE_NO;

    @Value("${com.iotechn.unimall.env}")
    private String ENV;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object prepay(Long templateId, String ip, Integer platform, String payChannel, Long userId) throws ServiceException {
        VipTemplateDO vipTemplateDO = templateMapper.selectById(templateId);
        if (vipTemplateDO == null || 1 != vipTemplateDO.getDisplay().intValue()) {
            throw new AppServiceException(ExceptionDefinition.VIP_TEMPLATE_NULL_OR_NOT_DISPLAY);
        }
        String orderNo = GeneratorUtil.genOrderId(this.MACHINE_NO, this.ENV);
        VipOrderDO vipOrderDO = new VipOrderDO();
        vipOrderDO.setDayNum(vipTemplateDO.getDayNum());
        vipOrderDO.setPrice(vipTemplateDO.getPrice());
        vipOrderDO.setStatus(VipOrderStatusType.WAIT_BUY.getCode());
        vipOrderDO.setPayChannel(payChannel);
        vipOrderDO.setTemplateId(vipTemplateDO.getId());
        vipOrderDO.setOrderNo(orderNo);
        vipOrderDO.setUserId(userId);
        vipOrderDO.setTitle(vipTemplateDO.getTitle());
        vipOrderDO.setDescription(vipTemplateDO.getDescription());
        vipOrderMapper.insert(vipOrderDO);
        // 前端来决定支付方式
        return payBizService.commonPrepay(orderNo, vipTemplateDO.getPrice(), platform, payChannel, ip);
    }

    @Override
    public String check(String orderNo, String phone, Long userId) throws ServiceException {
        check(orderNo, phone);
        return "ok";
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

    private VipOrderDO check(String orderNo, String phone) throws ServiceException {
        QueryWrapper<VipOrderDO> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        List<VipOrderDO> vipOrderDOS = vipOrderMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(vipOrderDOS)) {
            throw new AppServiceException(ExceptionDefinition.VIP_ORDER_CHECK_FAIL);
        }
        VipOrderDO vipOrderDO = vipOrderDOS.get(0);
        if (!phone.equals(vipOrderDO.getPhone())) {
            throw new AppServiceException(ExceptionDefinition.VIP_ORDER_CHECK_FAIL);
        }
        return vipOrderDO;
    }
}

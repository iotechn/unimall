package com.iotechn.unimall.admin.api.dashboard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.DashboardIntegralDTO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.mapper.OrderMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.KVModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rize on 2019/7/15.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Override
    public Object integral(Long adminId) throws ServiceException {
        DashboardIntegralDTO dto = new DashboardIntegralDTO();
        Integer orderWaitStock = orderMapper.selectCount(new QueryWrapper<OrderDO>().eq("status", OrderStatusType.WAIT_STOCK.getCode()));
        Integer spuCount = spuMapper.selectCount(new QueryWrapper<SpuDO>());
        List<KVModel<String, Long>> area = orderMapper.selectAreaStatistics();
        List<KVModel<String, Long>> channel = orderMapper.selectChannelStatistics();
        dto.setArea(area);
        dto.setChannel(channel);
        dto.setWaitStockCount(orderWaitStock);
        dto.setGoodsCount(spuCount);
        Integer days = 7;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startDay = sdf.format(new Date(System.currentTimeMillis() - 1000l * 60 * 60 * 24 * days));
        List<KVModel<String, Long>> orderCountKVList = orderMapper.selectOrderCountStatistics(startDay);
        List<KVModel<String, Long>> orderSumKVList = orderMapper.selectOrderSumStatistics(startDay);
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        List<Object[]> orderCount = new LinkedList<>();
        Object[] orderCountNameArray = new Object[days];
        Object[] orderCountValueArray = new Object[days];
        orderCount.add(orderCountNameArray);
        orderCount.add(orderCountValueArray);
        dto.setDaysOrder(orderCount);
        List<Object[]> orderSum = new LinkedList<>();
        Object[] orderSumNameArray = new Object[days];
        Object[] orderSumValueArray = new Object[days];
        orderSum.add(orderSumNameArray);
        orderSum.add(orderSumValueArray);
        dto.setDaysSum(orderSum);
        //这里是在补全 group by 为 0 的情况
        for (int i = 0; i < days; i++) {
            Date date = new Date(System.currentTimeMillis() - 1000l * 60 * 60 * 24 * i);
            String key = sdfDay.format(date);
            int i1 = orderCountKVList.indexOf(new KVModel<>(key, null));
            if (i1 >= 0) {
                orderCountNameArray[days - i - 1] = key;
                orderCountValueArray[days - i - 1] = orderCountKVList.get(i1).getValue();
            } else {
                orderCountNameArray[days - i - 1] = key;
                orderCountValueArray[days - i - 1] = 0;
            }

            int i2 = orderSumKVList.indexOf(new KVModel<>(key, null));
            if (i2 >= 0) {
                orderSumNameArray[days - i - 1] = key;
                orderSumValueArray[days - i - 1] = orderSumKVList.get(i2).getValue();
            } else {
                orderSumNameArray[days - i - 1] = key;
                orderSumValueArray[days - i - 1] = 0;
            }
        }
        return dto;
    }
}

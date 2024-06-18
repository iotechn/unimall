package com.dobbinsoft.unimall.admin.api.dashboard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.utils.TimeUtils;
import com.dobbinsoft.unimall.data.domain.OrderDO;
import com.dobbinsoft.unimall.data.domain.SpuDO;
import com.dobbinsoft.unimall.data.dto.DashboardIntegralDTO;
import com.dobbinsoft.unimall.data.enums.OrderStatusType;
import com.dobbinsoft.unimall.data.mapper.OrderMapper;
import com.dobbinsoft.unimall.data.mapper.SpuMapper;
import com.dobbinsoft.unimall.data.model.KVModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Long orderWaitStock = orderMapper.selectCount(new QueryWrapper<OrderDO>().eq("status", OrderStatusType.WAIT_STOCK.getCode()));
        Long spuCount = spuMapper.selectCount(new QueryWrapper<SpuDO>());
        List<KVModel<String, Long>> area = orderMapper.selectAreaStatistics();
        List<KVModel<String, Long>> channel = orderMapper.selectChannelStatistics();
        dto.setArea(area);
        dto.setChannel(channel);
        dto.setWaitStockCount(orderWaitStock.intValue());
        dto.setGoodsCount(spuCount.intValue());
        int days = 7;
        LocalDate startDay = LocalDate.now().plusDays(-days);
        List<KVModel<String, Long>> orderCountKVList = orderMapper.selectOrderCountStatistics(startDay);
        List<KVModel<String, Long>> orderSumKVList = orderMapper.selectOrderSumStatistics(startDay);
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
            LocalDate date = LocalDate.now().plusDays(-i);
            String key = TimeUtils.localDateToString(date, "yyyy-MM-dd");
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

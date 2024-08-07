package com.dobbinsoft.unimall.app.api.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.component.LockComponent;
import com.dobbinsoft.fw.support.component.MachineComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.mq.DelayedMessageQueue;
import com.dobbinsoft.fw.support.service.BaseService;
import com.dobbinsoft.unimall.biz.client.erp.ErpClient;
import com.dobbinsoft.unimall.biz.executor.GlobalExecutor;
import com.dobbinsoft.unimall.biz.service.address.AddressBizService;
import com.dobbinsoft.unimall.biz.service.cart.CartBizService;
import com.dobbinsoft.unimall.biz.service.coupon.CouponBizService;
import com.dobbinsoft.unimall.biz.service.freight.FreightTemplateBizService;
import com.dobbinsoft.unimall.biz.service.groupshop.GroupShopBizService;
import com.dobbinsoft.unimall.biz.service.notify.AdminNotifyBizService;
import com.dobbinsoft.unimall.biz.service.order.OrderBizService;
import com.dobbinsoft.unimall.biz.service.pay.PayBizService;
import com.dobbinsoft.unimall.biz.service.product.ProductBizService;
import com.dobbinsoft.unimall.biz.util.GeneratorUtil;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.constant.LockConst;
import com.dobbinsoft.unimall.data.domain.AddressDO;
import com.dobbinsoft.unimall.data.domain.OrderDO;
import com.dobbinsoft.unimall.data.domain.OrderSkuDO;
import com.dobbinsoft.unimall.data.domain.SpuDO;
import com.dobbinsoft.unimall.data.dto.admin.AdminDTO;
import com.dobbinsoft.unimall.data.dto.coupon.CouponUserDTO;
import com.dobbinsoft.unimall.data.dto.UserDTO;
import com.dobbinsoft.unimall.data.dto.freight.ShipTraceDTO;
import com.dobbinsoft.unimall.data.dto.order.OrderDTO;
import com.dobbinsoft.unimall.data.dto.order.OrderRequestDTO;
import com.dobbinsoft.unimall.data.dto.order.OrderRequestSkuDTO;
import com.dobbinsoft.unimall.data.dto.product.GroupShopDTO;
import com.dobbinsoft.unimall.data.dto.product.GroupShopSkuDTO;
import com.dobbinsoft.unimall.data.dto.product.SkuDTO;
import com.dobbinsoft.unimall.data.enums.*;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.OrderMapper;
import com.dobbinsoft.unimall.data.mapper.OrderSkuMapper;
import com.dobbinsoft.unimall.data.mapper.SkuMapper;
import com.dobbinsoft.unimall.data.model.FreightCalcModel;
import com.dobbinsoft.unimall.data.model.OrderCalcSkuModel;
import com.dobbinsoft.unimall.data.model.SkuStockInfoModel;
import com.dobbinsoft.unimall.data.properties.UnimallOrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.dobbinsoft.fw.support.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/4.
 */
@Service
public class OrderServiceImpl extends BaseService<UserDTO, AdminDTO> implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CouponBizService couponBizService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private CartBizService cartBizService;

    @Autowired
    private MatrixPayService payService;

    @Autowired
    private PayBizService payBizService;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private AddressBizService addressBizService;

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private FreightTemplateBizService freightTemplateBizService;

    @Autowired
    private GroupShopBizService groupShopBizService;

    @Autowired
    private AdminNotifyBizService adminNotifyBizService;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private ProductBizService productBizService;

    @Autowired
    private DelayedMessageQueue delayedMessageQueue;

    @Autowired
    private MachineComponent machineComponent;

    @Autowired
    private UnimallOrderProperties unimallOrderProperties;

    @Autowired
    private ErpClient erpClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String takeOrder(OrderRequestDTO orderRequest, String channel, Long userId) throws ServiceException {
        if (lockComponent.tryLock(LockConst.TAKE_ORDER_LOCK + userId, 20)) {
            //加上乐观锁，防止用户重复提交订单
            List<OrderRequestSkuDTO> skuList = orderRequest.getSkuList();
            boolean calcStockFlag = false;
            try {
                UserDTO user = sessionUtil.getUser();
                //用户会员等级
                Integer userLevel = user.getLevel();
                if (user.getStatus().intValue() != UserStatusType.ACTIVE.getCode()) {
                    throw new ServiceException(ExceptionDefinition.ORDER_USER_IS_NOT_ACTIVE);
                }
                // 对Sku排序，防止相互拿锁，两边都无法结算的情况。
                orderRequest.getSkuList().sort((o1, o2) -> (int) (o1.getSkuId() - o2.getSkuId()));
                //参数强校验 START
                if (CollectionUtils.isEmpty(skuList) || orderRequest.getTotalPrice() == null) {
                    throw new ServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
                }
                if (orderRequest.getTotalPrice() <= 0) {
                    throw new ServiceException(ExceptionDefinition.ORDER_PRICE_MUST_GT_ZERO);
                }
                // 若是卖虚拟物品，不需要收货地址，可以将此行注释掉
                if (orderRequest.getAddressId() == null) {
                    throw new ServiceException(ExceptionDefinition.ORDER_ADDRESS_CANNOT_BE_NULL);
                }
                // 收货地址对象
                AddressDO addressDO = addressBizService.getAddressById(orderRequest.getAddressId());
                // 库存不足列表，用于提示前端
                List<SkuStockInfoModel> stockErrorSkuList = new LinkedList<>();
                // 商品状态异常列表，用于提示前端
                List<Long> statusErrorSkuList = new LinkedList<>();
                // 从缓存读取，用于计算的列表。属于SkuDO的部分属性。
                List<OrderCalcSkuModel> calcSkuList = new ArrayList<>();
                // 将SkuIds 查取出来
                List<Long> skuIds = new ArrayList<>();
                for (OrderRequestSkuDTO orderRequestSkuDTO : skuList) {
                    Long skuId = orderRequestSkuDTO.getSkuId();
                    skuIds.add(skuId);
                    OrderCalcSkuModel orderCalcSpuDTO = cacheComponent.getHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + orderRequestSkuDTO.getSpuId(), OrderCalcSkuModel.class);
                    if (orderCalcSpuDTO == null) {
                        // 尝试从DB中读取
                        SpuDO spuFromDB = productBizService.getProductByIdFromDB(orderRequestSkuDTO.getSpuId());
                        if (spuFromDB == null || (spuFromDB.getStatus() == SpuStatusType.STOCK.getCode())) {
                            // 不存在的或下架的商品
                            statusErrorSkuList.add(skuId);
                            continue;
                        } else {
                            orderCalcSpuDTO = new OrderCalcSkuModel();
                            BeanUtils.copyProperties(spuFromDB, orderCalcSpuDTO);
                        }

                    }
                    long surplus = cacheComponent.decrementHashKey(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuId, orderRequestSkuDTO.getNum());
                    if (surplus < 0) {
                        // 若余量小于0，则表示该商品不存在或库存不足。
                        SkuStockInfoModel skuStockInfo = new SkuStockInfoModel();
                        skuStockInfo.setSkuId(skuId);
                        skuStockInfo.setExpect(orderRequestSkuDTO.getNum());
                        // 扣减之后的余量 + 用户期望量 = 扣减之前的余量
                        skuStockInfo.setSurplus((int) surplus + orderRequestSkuDTO.getNum());
                        stockErrorSkuList.add(skuStockInfo);
                        continue;
                    }
                    // 将SkuId设置进去
                    orderCalcSpuDTO.setSkuId(skuId);
                    orderCalcSpuDTO.setNum(orderRequestSkuDTO.getNum());
                    calcSkuList.add(orderCalcSpuDTO);
                }

                calcStockFlag = true;

                // 商品库存不足列表，用于前端提示 使用异常的Attach方法
                if (!CollectionUtils.isEmpty(stockErrorSkuList)) {
                    throw new ServiceException(ExceptionDefinition.ORDER_SKU_STOCK_NOT_ENOUGH).attach(stockErrorSkuList);
                }

                // 状态错误列表，用于前端提示
                if (!CollectionUtils.isEmpty(statusErrorSkuList)) {
                    throw new ServiceException(ExceptionDefinition.ORDER_SKU_NOT_EXIST).attach(statusErrorSkuList);
                }

                LocalDateTime now = LocalDateTime.now();
                // 若库存充足，也没下架，则获取所有sku实体对象
                List<SkuDTO> skuDTOListOfAll = productBizService.getSkuListByIds(skuIds);
                // 优惠券实例
                CouponUserDTO couponUserDTO = null;
                if (orderRequest.getCouponId() != null) {
                    couponUserDTO = couponBizService.getCouponUserById(orderRequest.getCouponId(), userId);
                    if (couponUserDTO == null || couponUserDTO.getGmtUsed() != null) {
                        throw new ServiceException(ExceptionDefinition.ORDER_COUPON_NOT_EXIST);
                    }
                }

                List<OrderCalcSkuModel> orderCalcSkuList = calcSkuList;
                // 进行主键排序
                orderCalcSkuList.sort((o1, o2) -> (int) (o1.getSkuId() - o2.getSkuId()));
                // 查出来的数据是按主键排序的
                List<SkuDTO> skuDTOList = skuDTOListOfAll;
                skuDTOList.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
                // 商品总价 = 普通商品价格 + 团购商品价格 (暂未使用)
                int totalSkuPrice = 0;
                // 普通商品价格
                int skuPrice = 0;
                int skuOriginalPrice = 0;
                // 团购商品价格映射
                Map<Long, Integer> groupShopPriceMap = new HashMap<>();
                Map<Long, Integer> groupShopOriginalPriceMap = new HashMap<>();
                for (int i = 0; i < skuDTOList.size(); i++) {
                    OrderCalcSkuModel orderCalcSkuDTO = orderCalcSkuList.get(i);
                    SkuDTO skuDTO = skuDTOList.get(i);
                    Integer originalPrice = skuDTO.getOriginalPrice();
                    // FIXME 对于活动价格，需要在skuDTO中覆盖掉之前价格
                    // FIXME 对于参加活动的商品，商品的非活动价格已经无参考价值
                    if ((skuDTO.getActivityType() != null
                            && skuDTO.getActivityType().intValue() == SpuActivityType.GROUP_SHOP.getCode() && skuDTO.getGmtActivityStart() != null
                            && skuDTO.getGmtActivityEnd() != null && skuDTO.getGmtActivityStart().isBefore(LocalDateTime.now()) && skuDTO.getGmtActivityEnd().isAfter(now))) {
                        // 这个判断，表示该商品与正在进行的团购活动
                        GroupShopDTO groupShopActivity = groupShopBizService.getGroupShopById(skuDTO.getActivityId());
                        if (groupShopActivity == null) {
                            throw new ServiceException(ExceptionDefinition.ORDER_GROUP_SHOP_ACTIVITY_HAS_OVER);
                        }
                        // 有可能两个SKU是同一个团购活动，所以需要将这两个活动价格合并
                        Integer oldPrice = groupShopPriceMap.get(groupShopActivity.getId());
                        Integer oldOriginalPrice = groupShopOriginalPriceMap.get(groupShopActivity.getId());
                        Map<Long, GroupShopSkuDTO> groupShopSkuMap = groupShopActivity.getGroupShopSkuDTOList().stream().collect(Collectors.toMap(GroupShopSkuDTO::getSkuId, v -> v));
                        // 累加商品总价
                        totalSkuPrice += groupShopSkuMap.get(skuDTO.getId()).getSkuGroupShopPrice() * orderCalcSkuDTO.getNum();
                        // 将价格覆盖掉
                        skuDTO.setPrice(groupShopSkuMap.get(skuDTO.getId()).getSkuGroupShopPrice());
                        skuDTO.setVipPrice(groupShopSkuMap.get(skuDTO.getId()).getSkuGroupShopPrice());
                        if (oldPrice != null) {
                            groupShopPriceMap.put(groupShopActivity.getId(), oldPrice + (groupShopSkuMap.get(skuDTO.getId()).getSkuGroupShopPrice() * orderCalcSkuDTO.getNum()));
                        } else {
                            groupShopPriceMap.put(groupShopActivity.getId(), groupShopSkuMap.get(skuDTO.getId()).getSkuGroupShopPrice() * orderCalcSkuDTO.getNum());
                        }
                        if (oldOriginalPrice != null) {
                            groupShopOriginalPriceMap.put(groupShopActivity.getId(), oldOriginalPrice + (skuDTO.getOriginalPrice() * orderCalcSkuDTO.getNum()));
                        } else {
                            groupShopOriginalPriceMap.put(groupShopActivity.getId(), skuDTO.getOriginalPrice() * orderCalcSkuDTO.getNum());
                        }
                    } else if (userLevel == UserLevelType.VIP.getCode().intValue()) {
                        skuOriginalPrice += originalPrice * orderCalcSkuDTO.getNum();
                        skuPrice += skuDTO.getVipPrice() * orderCalcSkuDTO.getNum();
                        totalSkuPrice += skuDTO.getVipPrice() * orderCalcSkuDTO.getNum();
                    } else {
                        skuOriginalPrice += originalPrice * orderCalcSkuDTO.getNum();
                        skuPrice += skuDTO.getPrice() * orderCalcSkuDTO.getNum();
                        totalSkuPrice += skuDTO.getPrice() * orderCalcSkuDTO.getNum();
                    }
                    // 将DB中的价格，重量赋值给计算模型
                    orderCalcSkuDTO.setFreightTemplateId(skuDTO.getFreightTemplateId());
                    orderCalcSkuDTO.setWeight(skuDTO.getWeight());
                    orderCalcSkuDTO.setPrice(skuDTO.getPrice());
                    orderCalcSkuDTO.setVipPrice(skuDTO.getVipPrice());
                }
                // 拆分单序号
                int childIndex = 1;
                // 获取邮费
                FreightCalcModel freightCalcModel = new FreightCalcModel();
                // 将SKU按照不同的运费模板进行分组
                Map<Long, List<OrderCalcSkuModel>> freightTemplateCalcMap = orderCalcSkuList
                        .stream()
                        .collect(Collectors.groupingBy(OrderCalcSkuModel::getFreightTemplateId));
                // 获取SKU数量映射表
                List<FreightCalcModel.FreightAndWeight> faws = new LinkedList<>();
                freightTemplateCalcMap.forEach((k, v) -> {
                    FreightCalcModel.FreightAndWeight faw = new FreightCalcModel.FreightAndWeight();
                    faw.setId(k);
                    int weight = 0;
                    int price = 0;
                    for (OrderCalcSkuModel orderCalcSkuModel : v) {
                        weight += orderCalcSkuModel.getWeight() * orderCalcSkuModel.getNum();
                        price += (userLevel == UserLevelType.VIP.getCode() ? orderCalcSkuModel.getVipPrice() : orderCalcSkuModel.getPrice()) * orderCalcSkuModel.getNum();
                    }
                    faw.setPrice(price);
                    faw.setWeight(weight);
                    faws.add(faw);
                });
                freightCalcModel.setFreightAndWeights(faws);
                Integer freightPrice = freightTemplateBizService.computePostage(freightCalcModel);
                // 是否已经计算过单次费用，例如优惠券、运费。同一个商家只计算一次（因为团购订单可能会被拆为两单，但是邮费只应该计算一次）
                boolean singleFee = false;
                // 使用优惠券的订单
                Long useCouponOrderId = null;
                // 生成一个父单号
                String parentOrderNo = GeneratorUtil.genOrderId(this.machineComponent.getMachineNo() + "");
                if (skuPrice > 0) {
                    // 这是普通商品
                    // 将普通商品(非团购等需要单独拆单的商品)的SkuList过滤出来
                    List<SkuDTO> commonSkuList = new ArrayList<>();
                    List<OrderCalcSkuModel> commonOrderCalcSkuList = new ArrayList<>();
                    for (int i = 0; i < skuDTOList.size(); i++) {
                        SkuDTO item = skuDTOList.get(i);
                        if (!(item.getActivityType() != null
                                && item.getActivityType().intValue() == SpuActivityType.GROUP_SHOP.getCode() && item.getGmtActivityStart() != null
                                && item.getGmtActivityEnd() != null && item.getGmtActivityStart().isBefore(now) && item.getGmtActivityEnd().isAfter(now))) {
                            commonSkuList.add(item);
                            commonOrderCalcSkuList.add(orderCalcSkuList.get(i));
                        }
                    }
                    // 保存订单
                    OrderDO o = save(skuOriginalPrice, skuPrice, channel, freightPrice,
                            couponUserDTO, orderRequest, parentOrderNo, childIndex,
                            userId, now, addressDO, commonSkuList, commonOrderCalcSkuList, userLevel, null, null);
                    useCouponOrderId = o.getId();
                    // 标记已经计算过运费和优惠券了
                    singleFee = true;
                }
                if (!groupShopPriceMap.isEmpty()) {
                    // 存在团购商品，分别分单处理团购商品
                    Set<Long> groupShopIds = groupShopPriceMap.keySet();
                    for (Long groupShopId : groupShopIds) {
                        Integer groupShopSkuOriginalPrice = groupShopOriginalPriceMap.get(groupShopId);
                        Integer groupShopSkuPrice = groupShopPriceMap.get(groupShopId);
                        // 过滤出当前团购的团购商品，即SkuDTOList
                        List<SkuDTO> groupShopSkuList = new ArrayList<>();
                        List<OrderCalcSkuModel> groupShopOrderCalcSkuList = new ArrayList<>();
                        for (int i = 0; i < skuDTOList.size(); i++) {
                            SkuDTO item = skuDTOList.get(i);
                            if (item.getActivityType() != null
                                    && item.getActivityType().intValue() == SpuActivityType.GROUP_SHOP.getCode() && item.getGmtActivityStart() != null && item.getActivityId().longValue() == groupShopId.longValue()
                                    && item.getGmtActivityEnd() != null && item.getGmtActivityStart().isBefore(now) && item.getGmtActivityEnd().isAfter(now)) {
                                groupShopSkuList.add(item);
                                groupShopOrderCalcSkuList.add(calcSkuList.get(i));
                            }
                        }
                        if (singleFee) {
                            // 保存子单前，将子单序列累加
                            childIndex++;
                            save(groupShopSkuOriginalPrice, groupShopSkuPrice, channel, 0,
                                    couponUserDTO, orderRequest, parentOrderNo, childIndex,
                                    userId, now, addressDO, groupShopSkuList, groupShopOrderCalcSkuList, userLevel, SpuActivityType.GROUP_SHOP.getCode(), groupShopId);
                        } else {
                            // 保存子单前，将子单序列累加
                            childIndex++;
                            save(groupShopSkuOriginalPrice, groupShopSkuPrice, channel, freightPrice,
                                    couponUserDTO, orderRequest, parentOrderNo, childIndex,
                                    userId, now, addressDO, groupShopSkuList, groupShopOrderCalcSkuList, userLevel, SpuActivityType.GROUP_SHOP.getCode(), groupShopId);
                            // 只收一次运费
                            singleFee = true;
                        }
                    }
                }
                Map<Long, Integer> skuStockMap = skuList.stream().collect(Collectors.toMap(OrderRequestSkuDTO::getSkuId, OrderRequestSkuDTO::getNum));
                // 减少商品库存，microFix。使用事务通知
                productBizService.decSkuStock(skuStockMap);
                // 扣除优惠券，microFix。使用事务通知
                if (couponUserDTO != null) {
                    couponBizService.useCoupon(orderRequest.getCouponId(), useCouponOrderId);
                }
                // 若购物车结算，删除这些购物车的商品
                if (orderRequest.getTakeWay().equals("cart")) {
                    cartBizService.deleteBySkuId(skuIds, userId);
                }
                // 与前端预览的金额比对，若因为时间差不一致，则告诉用户
                if (orderRequest.getExceptPrice() != null) {
                    int exceptPrice = this.checkPrepay(parentOrderNo, null, userId);
                    if (exceptPrice != orderRequest.getExceptPrice().intValue()) {
                        throw new ServiceException(ExceptionDefinition.ORDER_PRODUCT_PRICE_HAS_BEEN_CHANGED);
                    }
                }
                // 日志 & 发送一个订单自动取消定时任务
                for (int i = 1; i <= childIndex; i++) {
                    String childOrderNo = parentOrderNo + "S" + ((1000) + i);
                    logger.info("订单创建成功:" + childOrderNo);
                    delayedMessageQueue.publishTask(DMQHandlerType.ORDER_AUTO_CANCEL.getCode(), childOrderNo, unimallOrderProperties.getAutoCancelTime().intValue());
                }

                return parentOrderNo;
            } catch (Exception e) {
                if (calcStockFlag) {
                    for (OrderRequestSkuDTO orderRequestSkuDTO : skuList) {
                        cacheComponent.incrementHashKey(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + orderRequestSkuDTO.getSkuId(), orderRequestSkuDTO.getNum());
                    }
                }
                if (e instanceof ServiceException) {
                    // 服务异常
                    throw e;
                }
                // 未知异常
                logger.error("[提交订单] 异常", e);
                throw e;
            } finally {
                lockComponent.release(LockConst.TAKE_ORDER_LOCK + userId);
            }
        }
        throw new ServiceException(ExceptionDefinition.ORDER_SYSTEM_BUSY);
    }

    @Override
    public Page<OrderDTO> getOrderPage(Integer pageNo, Integer pageSize, Integer status, Long userId) throws ServiceException {
        List<OrderDTO> orderDTOList = orderMapper.selectOrderPage(status, (pageNo - 1) * pageSize, pageSize, userId);
        Long count = orderMapper.countOrder(status, (pageNo - 1) * pageSize, pageSize, userId);
        //封装SKU
        orderDTOList.forEach(item -> {
            item.setSkuList(orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", item.getId())));
        });
        return new Page<>(orderDTOList, pageNo, pageSize, count);
    }

    @Override
    public OrderDTO getOrderDetail(Long orderId, Long userId) throws ServiceException {
        return orderBizService.getOrderDetail(orderId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object prepay(String parentOrderNo, String orderNo, Integer platform, String payChannel, String ip, Long userId) throws ServiceException {
        int actualPrice = this.checkPrepay(parentOrderNo, orderNo, userId);
        // 前端来决定支付方式
        return payBizService.commonPrepay(ObjectUtils.isEmpty(parentOrderNo) ? orderNo : parentOrderNo, actualPrice, platform, payChannel, ip);
    }

    private int checkPrepay(String parentOrderNo, String orderNo, Long userId) throws ServiceException {
        // 两个都为空 和 两个都不为空是不合法的
        if ((ObjectUtils.isEmpty(parentOrderNo) && ObjectUtils.isEmpty(orderNo)) || (!ObjectUtils.isEmpty(parentOrderNo) && !ObjectUtils.isEmpty(orderNo))) {
            throw new ServiceException(ExceptionDefinition.ORDER_PARAM_CHECK_FAILED);
        }
        List<OrderDO> orderList;
        if (!ObjectUtils.isEmpty(parentOrderNo))
            orderList = orderBizService.checkOrderExistByParentNo(parentOrderNo, userId);
        else
            orderList = orderBizService.checkOrderExistByNo(orderNo, userId);
        // 检测订单状态
        int actualPrice = 0;
        for (OrderDO orderDO : orderList) {
            Integer status = orderDO.getStatus();
            if (!Objects.equals(status, OrderStatusType.UNPAY.getCode())) {
                throw new ServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_PAY);
            }
            actualPrice += orderDO.getActualPrice();
        }
        return actualPrice;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object offlinePrepay(String parentOrderNo, String orderNo, Long userId) throws ServiceException {
        // 两个都为空 和 两个都不为空是不合法的
        if ((ObjectUtils.isEmpty(parentOrderNo) && ObjectUtils.isEmpty(orderNo)) || (!ObjectUtils.isEmpty(parentOrderNo) && !ObjectUtils.isEmpty(orderNo))) {
            throw new ServiceException(ExceptionDefinition.ORDER_PARAM_CHECK_FAILED);
        }
        List<OrderDO> orderList;
        if (!ObjectUtils.isEmpty(parentOrderNo))
            orderList = orderBizService.checkOrderExistByParentNo(parentOrderNo, userId);
        else
            orderList = orderBizService.checkOrderExistByNo(orderNo, userId);
        // 检测订单状态
        for (OrderDO orderDO : orderList) {
            Integer status = orderDO.getStatus();
            if (status != OrderStatusType.UNPAY.getCode()) {
                throw new ServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_PAY);
            }
        }
        LocalDateTime now = LocalDateTime.now();
        for (OrderDO orderDO : orderList) {
            List<OrderSkuDO> orderSkuDOList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
            List<OrderSkuDO> groupShopSkuList = orderSkuDOList.stream().filter(item -> (item.getActivityType() != null && item.getActivityType() == SpuActivityType.GROUP_SHOP.getCode())).collect(Collectors.toList());
            if (!groupShopSkuList.isEmpty()) {
                // 订单中是否是团购商品
                OrderDO groupShopUpdateDO = new OrderDO();
                groupShopUpdateDO.setPayId("OFFLINE");
                groupShopUpdateDO.setPayChannel(PayChannelType.OFFLINE.getCode());
                groupShopUpdateDO.setPayPrice(orderDO.getActualPrice());
                groupShopUpdateDO.setGmtPay(now);
                groupShopUpdateDO.setGmtUpdate(now);
                groupShopUpdateDO.setStatus(OrderStatusType.GROUP_SHOP_WAIT.getCode());
                groupShopUpdateDO.setSubPay(1);
                // 增加buyer count
                for (OrderSkuDO orderSkuDO : groupShopSkuList) {
                    groupShopBizService.incGroupShopNum(orderSkuDO.getActivityId(), orderSkuDO.getNum());
                }
                orderBizService.changeOrderSubStatus(orderDO.getOrderNo(), OrderStatusType.UNPAY.getCode(), groupShopUpdateDO);
            } else {
                // 下销售制单
                erpClient.takeSalesHeader(orderDO.getOrderNo());
                OrderDO updateOrderDO = new OrderDO();
                updateOrderDO.setPayChannel(PayChannelType.OFFLINE.getCode());
                updateOrderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
                updateOrderDO.setGmtUpdate(LocalDateTime.now());
                boolean succ = orderBizService.changeOrderSubStatus(orderDO.getOrderNo(), OrderStatusType.UNPAY.getCode(), updateOrderDO);
                if (!succ) {
                    throw new ServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
                }
            }
            // 增加商品销量
            Map<Long, Integer> salesMap = orderSkuDOList.stream().collect(Collectors.toMap(OrderSkuDO::getSpuId, OrderSkuDO::getNum, (k1, k2) -> k1.intValue() + k2.intValue()));
            productBizService.incSpuSales(salesMap);
        }
        // 删除自动取消订单消息
        delayedMessageQueue.deleteTask(DMQHandlerType.ORDER_AUTO_CANCEL.getCode(), orderNo);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(String orderNo, String reason, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExistByNo(orderNo, userId).get(0);
        if (PayChannelType.OFFLINE.getCode().equals(orderDO.getPayChannel())) {
            throw new ServiceException(ExceptionDefinition.ORDER_PAY_CHANNEL_NOT_SUPPORT_REFUND);
        }
        if (OrderStatusType.refundable(orderDO.getStatus())) {
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setRefundReason(reason);
            updateOrderDO.setStatus(OrderStatusType.REFUNDING.getCode());
            orderBizService.changeOrderSubStatus(orderNo, orderDO.getStatus(), updateOrderDO);
            GlobalExecutor.execute(() -> {
                OrderDTO orderDTO = new OrderDTO();
                BeanUtils.copyProperties(orderDO, orderDTO);
                List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_no", orderDO.getOrderNo()));
                orderDTO.setSkuList(orderSkuList);
                adminNotifyBizService.refundOrder(orderDTO);
            });
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String cancel(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExistByNo(orderNo, userId).get(0);
        if (orderDO.getStatus() != OrderStatusType.UNPAY.getCode()) {
            throw new ServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CANCEL);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setStatus(OrderStatusType.CANCELED.getCode());
        updateOrderDO.setGmtUpdate(LocalDateTime.now());
        List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
        orderSkuList.forEach(item -> {
            skuMapper.returnSkuStock(item.getSkuId(), item.getNum());
        });
        orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.UNPAY.getCode(), updateOrderDO);
        delayedMessageQueue.deleteTask(DMQHandlerType.ORDER_AUTO_CANCEL.getCode(), orderNo);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String confirm(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExistByNo(orderNo, userId).get(0);
        if (orderDO.getStatus() != OrderStatusType.WAIT_CONFIRM.getCode()) {
            throw new ServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CONFIRM);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setStatus(OrderStatusType.WAIT_APPRAISE.getCode());
        updateOrderDO.setGmtUpdate(LocalDateTime.now());
        orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.WAIT_CONFIRM.getCode(), updateOrderDO);
        delayedMessageQueue.deleteTask(DMQHandlerType.ORDER_AUTO_CONFIRM.getCode(), orderNo);
        return "ok";
    }

    @Override
    public ShipTraceDTO queryShip(String orderNo, Long userId) throws ServiceException {
        // TODO KD100订阅
        return null;
//        OrderDO orderDO = orderBizService.checkOrderExistByNo(orderNo, userId).get(0);
//        if (orderDO.getStatus() < OrderStatusType.WAIT_CONFIRM.getCode()) {
//            throw new ServiceException(ExceptionDefinition.ORDER_HAS_NOT_SHIP);
//        }
//        if (StringUtils.isEmpty(orderDO.getShipCode()) || StringUtils.isEmpty(orderDO.getShipNo())) {
//            throw new ServiceException(ExceptionDefinition.ORDER_DID_NOT_SET_SHIP);
//        }
//        ShipTraceDTO shipTraceList = freightTemplateBizService.getShipTraceList(orderDO.getShipNo(), orderDO.getShipCode());
//        if (CollectionUtils.isEmpty(shipTraceList.getTraces())) {
//            throw new ServiceException(ExceptionDefinition.ORDER_DO_NOT_EXIST_SHIP_TRACE);
//        }
//        return shipTraceList;
    }

    @Override
    public Integer previewFreight(OrderRequestDTO orderRequest, Long userId) throws ServiceException {
        List<OrderRequestSkuDTO> skuList = orderRequest.getSkuList();
        AddressDO addressDO = null;
        if (orderRequest.getAddressId() != null) {
            addressDO = addressBizService.getAddressById(orderRequest.getAddressId());
        }
        FreightCalcModel calcModel = new FreightCalcModel();
        if (addressDO == null) {
            // 若没穿省份，则传一个不存在的省份。系统会默认他是全国。
            calcModel.setProvince("一个不存在的地址");
        } else {
            calcModel.setProvince(addressDO.getProvince());
        }
        // 由于是预览，此处可详细用户从前端传入进来的 商品运费模板Id 重量 价格等信息
        UserDTO user = sessionUtil.getUser();
        // 将SKU按照运费模板分组
        Map<Long, List<OrderRequestSkuDTO>> calcMap = skuList.stream().collect(Collectors.groupingBy(OrderRequestSkuDTO::getFreightTemplateId));
        List<FreightCalcModel.FreightAndWeight> faws = new LinkedList<>();
        calcMap.forEach((k, v) -> {
            FreightCalcModel.FreightAndWeight faw = new FreightCalcModel.FreightAndWeight();
            faw.setId(k);
            int weight = 0;
            int price = 0;
            for (OrderRequestSkuDTO skuDTO : v) {
                weight += skuDTO.getWeight() * skuDTO.getNum();
                price += (user.getLevel().intValue() == UserLevelType.VIP.getCode() ? skuDTO.getVipPrice() : skuDTO.getPrice()) * skuDTO.getNum();
            }
            faw.setWeight(weight);
            faw.setPrice(price);
            faws.add(faw);
        });
        calcModel.setFreightAndWeights(faws);
        int sum = freightTemplateBizService.computePostage(calcModel);
        return sum;
    }


    /**
     * 保存订单抽取接口
     *
     * @param skuOriginalPrice
     * @param skuPrice
     * @param channel
     * @param freightPrice
     * @param couponUserDTO
     * @param orderRequest
     * @param parentOrderNo
     * @param childIndex
     * @param userId
     * @param now
     * @param addressDO
     * @param skuDTOList
     * @param orderCalcSkuList
     * @param userLevel
     * @param activityType
     * @param activityId
     * @return
     * @throws ServiceException
     */
    private OrderDO save(int skuOriginalPrice, int skuPrice, String channel,
                         int freightPrice, CouponUserDTO couponUserDTO,
                         OrderRequestDTO orderRequest, String parentOrderNo, int childIndex,
                         Long userId, LocalDateTime now, AddressDO addressDO, List<SkuDTO> skuDTOList,
                         List<OrderCalcSkuModel> orderCalcSkuList, Integer userLevel,
                         Integer activityType, Long activityId) throws ServiceException {
        OrderDO orderDO = new OrderDO();
        // 设置商品原价，总价
        orderDO.setSkuOriginalTotalPrice(skuOriginalPrice);
        orderDO.setSkuTotalPrice(skuPrice);
        // 下单渠道
        orderDO.setChannel(channel);
        // 计算订单价格
        orderDO.setFreightPrice(freightPrice);
        // 计算订单优惠券价格
        int couponPrice = 0;
        if (couponUserDTO != null) {
            couponPrice = couponUserDTO.getDiscount();
            // 这里根据实际情况决定是否需要将邮费加入到优惠券计算中
            if (couponUserDTO.getMin() > skuPrice) {
                throw new ServiceException(ExceptionDefinition.ORDER_COUPON_PRICE_NOT_ENOUGH);
            }
        }
        orderDO.setCouponPrice(couponPrice);
        orderDO.setActualPrice(skuPrice + freightPrice - couponPrice);
        orderDO.setMono(orderRequest.getMono());
        orderDO.setParentOrderNo(parentOrderNo);
        orderDO.setOrderNo(parentOrderNo + "S" + (1000 + childIndex));
        orderDO.setUserId(userId);
        orderDO.setStatus(OrderStatusType.UNPAY.getCode());
        orderDO.setGmtUpdate(now);
        orderDO.setGmtCreate(now);
        if (!userId.equals(addressDO.getUserId())) {
            throw new ServiceException(ExceptionDefinition.ORDER_ADDRESS_NOT_BELONGS_TO_YOU);
        }
        orderDO.setConsignee(addressDO.getConsignee());
        orderDO.setPhone(addressDO.getPhone());
        orderDO.setProvince(addressDO.getProvince());
        orderDO.setCity(addressDO.getCity());
        orderDO.setCounty(addressDO.getCounty());
        orderDO.setAddress(addressDO.getAddress());
        // 冗余一个团购信息
        if (activityType != null && activityType == SpuActivityType.GROUP_SHOP.getCode()) {
            orderDO.setGroupShopId(activityId);
        }
        orderMapper.insert(orderDO);

        for (int i = 0; i < skuDTOList.size(); i++) {
            OrderCalcSkuModel orderCalcSpuDTO = orderCalcSkuList.get(i);
            SkuDTO skuDTO = skuDTOList.get(i);
            Assert.isTrue(orderCalcSpuDTO.getSkuId().longValue() == skuDTO.getId().longValue(), "断言失败！");
            OrderSkuDO orderSkuDO = new OrderSkuDO();
            orderSkuDO.setBarCode(skuDTO.getBarCode());
            orderSkuDO.setTitle(skuDTO.getTitle());
            orderSkuDO.setUnit(skuDTO.getUnit());
            orderSkuDO.setSpuTitle(skuDTO.getSpuTitle());
            orderSkuDO.setImg(skuDTO.getImg() == null ? skuDTO.getSpuImg() : skuDTO.getImg());
            orderSkuDO.setNum(orderCalcSpuDTO.getNum());
            orderSkuDO.setOriginalPrice(skuDTO.getOriginalPrice());
            orderSkuDO.setPrice(skuDTO.getPrice());
            if (userLevel.intValue() == UserLevelType.VIP.getCode()) {
                orderSkuDO.setPrice(skuDTO.getVipPrice());
            } else {
                orderSkuDO.setPrice(skuDTO.getPrice());
            }
            orderSkuDO.setWeight(skuDTO.getWeight());
            orderSkuDO.setSkuId(skuDTO.getId());
            orderSkuDO.setSpuId(skuDTO.getSpuId());
            orderSkuDO.setOrderNo(orderDO.getOrderNo());
            orderSkuDO.setOrderId(orderDO.getId());
            orderSkuDO.setGmtCreate(now);
            orderSkuDO.setGmtUpdate(now);
            // 考虑到有的活动又不需要拆单，所以这里活动就只能关联到订单商品。也就是商品参加促销活动，而非订单参加。
            orderSkuDO.setActivityType(activityType);
            orderSkuDO.setActivityId(activityId);
            orderSkuMapper.insert(orderSkuDO);
        }
        return orderDO;
    }

}

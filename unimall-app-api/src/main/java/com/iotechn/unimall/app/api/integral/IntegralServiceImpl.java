package com.iotechn.unimall.app.api.integral;

import com.iotechn.unimall.app.api.advert.AdvertService;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.annotation.AspectCommonCache;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.AdvertDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.AdvertDTO;
import com.iotechn.unimall.data.dto.IntegralIndexDataDTO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.enums.AdvertType;
import com.iotechn.unimall.data.enums.AdvertUnionType;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.properties.UnimallAdvertProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/14.
 */
@Service
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    private AdvertService advertService;

    @Autowired
    private ProductBizService productBizService;

    @Autowired
    private UnimallAdvertProperties unimallAdvertProperties;

    private static final Logger logger = LoggerFactory.getLogger(IntegralServiceImpl.class);

    @Override
    @AspectCommonCache(value = CacheConst.INTEGRAL_INDEX, second = 5 * 60)
    public IntegralIndexDataDTO getIndexData() throws ServiceException {
        //分类
        List<AdvertDO> activeAd = advertService.getActiveAd(null);
        Map<String, List<AdvertDTO>> adDTOMap = activeAd.stream().map(item -> {
            AdvertDTO advertDTO = new AdvertDTO();
            BeanUtils.copyProperties(item, advertDTO);
            return advertDTO;
        }).collect(Collectors.groupingBy(item -> "t" + item.getType()));
        List<AdvertDTO> categoryPickAd = adDTOMap.get("t" + AdvertType.CATEGORY_PICK.getCode());
        List<AdvertDTO> recommendAd = adDTOMap.get("t" + AdvertType.PRODUCT_RECOMMEND.getCode());
        //封装 分类精选 商品
        if (!CollectionUtils.isEmpty(categoryPickAd)) {
            categoryPickAd = categoryPickAd.stream().filter(item -> {
                if (item.getUnionType().intValue() == AdvertUnionType.CATEGORY.getCode()) {
                    try {
                        Page<SpuDO> pickPage = productBizService.getProductPage(1, 10, Long.parseLong(item.getUnionValue()), "sales", false, null);
                        item.setData(pickPage.getItems());
                        return true;
                    } catch (Exception e) {
                        logger.info("[Advert 获取分类精选商品] 异常", e);
                    }
                }
                return false;
            }).collect(Collectors.toList());
            adDTOMap.put("t" + AdvertType.CATEGORY_PICK.getCode(), categoryPickAd);
        }
        //封装 橱窗推荐 商品
        if (!CollectionUtils.isEmpty(recommendAd)) {
            recommendAd = recommendAd.stream().filter(item -> {
                try {
                    if (item.getUnionType() == AdvertUnionType.PRODUCT.getCode()) {
                        SpuDTO spuDTO = productBizService.getProductByIdFromCache(Long.parseLong(item.getUnionValue()));
                        item.setData(spuDTO);
                        return true;
                    }
                } catch (Exception e) {
                    logger.info("[Advert 获取橱窗推荐商品] 异常", e);
                }
                return false;
            }).collect(Collectors.toList());
            adDTOMap.put("t" + AdvertType.PRODUCT_RECOMMEND.getCode(), recommendAd);
        }
        IntegralIndexDataDTO integralIndexDataDTO = new IntegralIndexDataDTO();
        integralIndexDataDTO.setAdvertisement(adDTOMap);

        /**
         * 销量冠军
         */
        List<SpuDO> salesTop = productBizService.getProductPage(1, unimallAdvertProperties.getTopSalesNum() == null ? 8 : unimallAdvertProperties.getTopSalesNum(), null, "sales", false, null).getItems();
        integralIndexDataDTO.setSalesTop(salesTop);

        /**
         * 最近上新
         */
        List<SpuDO> newTop = productBizService.getProductPage(1, 8, null, "id", false, null).getItems();
        integralIndexDataDTO.setNewTop(newTop);
        return integralIndexDataDTO;
    }

}

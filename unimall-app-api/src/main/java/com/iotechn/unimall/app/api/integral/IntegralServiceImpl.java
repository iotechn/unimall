package com.iotechn.unimall.app.api.integral;

import com.iotechn.unimall.app.api.advert.AdvertService;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AdvertDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.AdvertDTO;
import com.iotechn.unimall.data.dto.IntegralIndexDataDTO;
import com.iotechn.unimall.data.enums.AdvertisementType;
import com.iotechn.unimall.data.model.Page;
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

    @Override
    public IntegralIndexDataDTO getIndexData() throws ServiceException {
        //分类
        List<AdvertDO> activeAd = advertService.getActiveAd(null);
        Map<String, List<AdvertDTO>> adDTOMap = activeAd.stream().map(item -> {
            AdvertDTO advertDTO = new AdvertDTO();
            BeanUtils.copyProperties(item, advertDTO);
            return advertDTO;
        }).collect(Collectors.groupingBy(item -> "t" + item.getAdType()));
        List<AdvertDTO> categoryPickAd = adDTOMap.get("t" + AdvertisementType.CATEGORY_PICK.getCode());
        //封装 分类精选 商品
        if (!CollectionUtils.isEmpty(categoryPickAd)) {
            for (AdvertDTO item : categoryPickAd) {
                Page<SpuDO> pickPage = productBizService.getProductPage(1, 10, new Long(item.getUrl().substring(item.getUrl().lastIndexOf("=") + 1)), "sales", false,null);
                item.setData(pickPage.getItems());
            }
        }
        IntegralIndexDataDTO integralIndexDataDTO = new IntegralIndexDataDTO();
        integralIndexDataDTO.setAdvertisement(adDTOMap);

        /**
         * 销量冠军
         */
        List<SpuDO> salesTop = productBizService.getProductPage(1, 8, null, "sales", false, null).getItems();
        integralIndexDataDTO.setSalesTop(salesTop);

        /**
         * 最近上新
         */
        List<SpuDO> newTop = productBizService.getProductPage(1, 8, null, "id", false, null).getItems();
        integralIndexDataDTO.setNewTop(newTop);
        return integralIndexDataDTO;
    }

}

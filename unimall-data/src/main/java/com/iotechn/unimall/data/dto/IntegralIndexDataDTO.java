package com.iotechn.unimall.data.dto;

import com.iotechn.unimall.data.domain.SpuDO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 首页聚合接口DTO
 * Created by rize on 2019/7/14.
 */
@Data
public class IntegralIndexDataDTO {

    private Map<String, List<AdvertDTO>> advertisement;

    private List<SpuDO> salesTop;

    private List<SpuDO> newTop;

}

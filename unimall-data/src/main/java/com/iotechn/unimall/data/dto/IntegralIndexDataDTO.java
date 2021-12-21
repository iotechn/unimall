package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.iotechn.unimall.data.domain.SpuDO;
import lombok.Data;
import org.apache.pulsar.shade.io.swagger.annotations.Api;

import java.util.List;
import java.util.Map;

/**
 * 首页聚合接口DTO
 * Created by rize on 2019/7/14.
 */
@Data
@ApiEntity(description = "首页聚合接口")
public class IntegralIndexDataDTO {

    @ApiField(description = "广告映射")
    private Map<String, List<AdvertDTO>> advertisement;

    @ApiField(description = "热销榜")
    private List<SpuDO> salesTop;

    @ApiField(description = "最新上架")
    private List<SpuDO> newTop;

}

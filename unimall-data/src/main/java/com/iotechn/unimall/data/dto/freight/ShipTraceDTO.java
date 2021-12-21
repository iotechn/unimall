package com.iotechn.unimall.data.dto.freight;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/10.
 */
@Data
@ApiEntity(description = "配送轨迹信息")
public class ShipTraceDTO {

    @ApiField(description = "运单号")
    private String shipNo;

    @ApiField(description = "承运商")
    private String shipCode;


    @ApiField(description = "承运商名称")
    private String shipName;

    @ApiField(description = "配送轨迹")
    private List<ShipTraceItemDTO> traces;

}

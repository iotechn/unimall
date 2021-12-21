package com.iotechn.unimall.data.dto.freight;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

/**
 * Created by rize on 2019/7/10.
 */
@Data
public class ShipTraceItemDTO {

    @ApiField(description = "站点")
    private String station;

    @ApiField(description = "时间")
    private String time;

}

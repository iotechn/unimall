package com.iotechn.unimall.data.dto.freight;

import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/10.
 */
@Data
public class ShipTraceDTO {

    /**
     * 运单号
     */
    private String shipNo;

    /**
     * 承运商
     */
    private String shipCode;

    /**
     * 承运商名称
     */
    private String shipName;

    private String errmsg;

    private Integer errcode;

    private List<ShipTraceItemDTO> traces;

}

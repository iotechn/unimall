package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: rize
 * Date: 2020/8/26
 * Time: 15:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErpSalesHeader extends SuperDTO implements Serializable {

    @ApiField(description = "渠道编号")
    private String placeCode;

    @ApiField(description = "销售时间")
    private Date gmtSales;

    @ApiField(description = "税率")
    private Integer taxRate;

    @ApiField(description = "签收人")
    private String recvConsignee;

    @ApiField(description = "签收电话")
    private String recvPhone;

    @ApiField(description = "签收地址")
    private String recvAddress;

    @ApiField(description = "销售总价")
    private Integer total;

    @ApiField(description = "订金")
    private Integer deposit;

    @ApiField(description = "备注")
    private String mono;

    @ApiField(description = "票据列表")
    private List<String> ticketList;

    @ApiField(description = "商品列表")
    private List<ErpSalesHeaderSku> skuList;

}

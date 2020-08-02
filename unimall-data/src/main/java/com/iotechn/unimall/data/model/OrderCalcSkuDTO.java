package com.iotechn.unimall.data.model;

import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/26
 * Time: 15:57
 */
@Data
public class OrderCalcSkuDTO extends SuperDTO {

    private Long skuId;

    private String unit;

    private Integer num;

}

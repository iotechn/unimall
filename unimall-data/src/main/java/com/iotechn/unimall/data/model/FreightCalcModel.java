package com.iotechn.unimall.data.model;

import lombok.Data;

import java.util.List;

/**
 * @author kbq
 * @ClassName: FreightCalcDTO
 * @Description:
 * @date 20-4-5下午5:52
 */

@Data
public class FreightCalcModel {

    private String province;

    private List<FreightAndWeight> freightAndWeights;

    @Data
    public static class FreightAndWeight {

        /**
         * 邮费模板ID
         */
        private Long id;


        private Integer price;

        /**
         * 该订单该邮费模板重量和
         */
        private Integer weight;

    }


}

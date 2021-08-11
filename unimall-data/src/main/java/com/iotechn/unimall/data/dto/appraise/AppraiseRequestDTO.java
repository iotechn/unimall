package com.iotechn.unimall.data.dto.appraise;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

import java.util.List;

/*
前端评价时，传入信息的数据结构
@author kbq
@date  2019/7/6 - 14:26
*/
@Data
@ApiEntity(description = "评价请求实体")
public class AppraiseRequestDTO extends SuperDTO {

    @ApiField(description = "订单ID")
    private Long orderId;

    /**
     * TODO 取调DTO名字
     */
    private List<AppraiseRequestItemDTO> appraiseDTOList;

}



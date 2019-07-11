package com.iotechn.unimall.data.dto.appraise;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.data.domain.AppraiseDO;
import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
@author kbq
@date  2019/7/6 - 14:26
*/
/*
*
* 前端评价时，传入信息的数据结构
* */
@Data
public class AppraiseRequestDTO extends SuperDTO {
    private Long orderId;
    private List<AppraiseDTO> appraiseDTOList;
}



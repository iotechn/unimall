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

    public static void main(String[] args) {
        AppraiseRequestDTO appraiseRequestDTO = new AppraiseRequestDTO();
        appraiseRequestDTO.setOrderId(2l);
        List<AppraiseDTO> list = new ArrayList<>();
        AppraiseDTO appraiseDTO = new AppraiseDTO();
        appraiseDTO.setContent("我来自json的测试");
        appraiseDTO.setSpuId(28l);
        appraiseDTO.setSkuId(2l);
        appraiseDTO.setImgUrl("https://easycampus-ops.oss-cn-shenzhen.aliyuncs.com/ef1c5af8d1c34582a5bcda0d48ef9437.png");
        appraiseDTO.setScore(5);
        list.add(appraiseDTO);
        appraiseRequestDTO.setAppraiseDTOList(list);
        String string = JSONObject.toJSONString(appraiseRequestDTO);
        System.out.println(string);
    }
}



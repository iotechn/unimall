package com.iotechn.unimall.data.dto;

import com.iotechn.unimall.data.domain.SpuDO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

/*
@author kbq
@date  2019/7/5 - 10:33
*/
@Data
public class CollectDTO extends SuperDTO {
    private Long userId;

    private SpuDO spuDO;

}

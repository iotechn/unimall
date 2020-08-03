package com.iotechn.unimall.data.dto.freight;

import com.baomidou.mybatisplus.annotation.TableField;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
import com.iotechn.unimall.data.domain.FreightTemplateDO;
import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午3:09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FreightTemplateDTO extends SuperDTO {

    // private FreightTemplateDO freightTemplateDO;

    private List<FreightTemplateCarriageDO> carriageDOList;

    private String templateName;

    private String spuLocation;

    private Integer deliveryDeadline;

    private Integer defaultFreePrice;

    private Integer defaultFirstNum;

    private Integer defaultFirstMoney;

    private Integer defaultContinueNum;

    private Integer defaultContinueMoney;

}

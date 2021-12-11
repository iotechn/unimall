package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.enums.CouponType;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-13
 * Time: 下午10:24
 */
@Data
@ApiEntity(description = "管理员列表(回显)优惠券传输实体")
public class CouponAdminDTO extends SuperDTO {

    @ApiField(description = "优惠券名称")
    private String title;

    @ApiField(description = "类型", enums = CouponType.class)
    private Integer type;

    @ApiField(description = "是否VIP专享")
    private Integer isVip;

    @ApiField(description = "描述")
    private String description;

    @ApiField(description = "总共有多少张")
    private Integer total;

    @ApiField(description = "当前剩余多少张")
    private Integer surplus;

    @ApiField(description = "每个用户限制领取张数")
    private Integer limit;

    @ApiField(description = "折扣")
    private Integer discount;

    @ApiField(description = "最低满多少钱可以使用")
    private Integer min;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    @ApiField(description = "类目标题")
    private String categoryTitle;

    @ApiField(description = "类目ID")
    private Long categoryId;

    @ApiField(description = "优惠券有效期天数")
    private Integer days;

    @ApiField(description = "领取开始时间")
    private Date gmtStart;

    @ApiField(description = "领域结束时间")
    private Date gmtEnd;
}

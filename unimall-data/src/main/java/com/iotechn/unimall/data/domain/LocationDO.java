package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.LocationType;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

/**
 * Description: 门店，仓库，云仓DOMAIN
 * User: rize
 * Date: 2020/9/9
 * Time: 21:30
 */
@Data
@ApiEntity(description = "仓库(位置)领域模型")
@TableName("unimall_location")
public class LocationDO extends SuperDO {

    /**
     * 地点名称
     */
    @ApiField(description = "位置名称")
    private String title;

    /**
     * 门店1，仓库2，虚拟仓3
     */
    @ApiField(description = "类型", enums = LocationType.class)
    private Integer type;

    @ApiField(description = "精度")
    private String lng;

    @ApiField(description = "维度")
    private String lat;

    /**
     * 服务半径 单位（米）
     */
    @ApiField(description = "服务范围")
    private Integer serviceRange;

    @ApiField(description = "当用户无法通过定位获取到最近位置时，是否以此为默认位置")
    private Integer defaultLocation;

    /**
     * 最后一个门店将无法被禁用
     */
    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

}

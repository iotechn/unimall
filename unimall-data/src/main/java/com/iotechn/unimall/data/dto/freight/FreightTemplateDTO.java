package com.iotechn.unimall.data.dto.freight;

import com.baomidou.mybatisplus.annotation.TableField;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
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
@Data
@ApiEntity(description = "运费模板传输实体")
public class FreightTemplateDTO extends SuperDTO {

    /**
     * TODO 去掉DO
     */
    @ApiField(description = "特殊地区列表")
    private List<FreightTemplateCarriageDO> carriageDOList;

    @ApiField(description = "模板标题")
    private String title;

    @ApiField(description = "商品发货地址")
    private String spuLocation;

    @ApiField(description = "发货期限 eg 7天内发货 则输入7")
    private Integer deliveryDeadline;

    @ApiField(description = "免邮费价格 0包邮 -1永不包邮，正数表示满好多包邮 单位 分")
    @TableField("default_free_price")
    private Integer defaultFreePrice;

    /**
     * 第一次计价后，可以包含的商品重量
     * default:为了区分特殊地区字段，加的
     */
    @ApiField(description = "以起步价计算的 重量 eg 1000 代表1KG内 以defaultFirstPrice计算")
    private Integer defaultFirstWeight;

    /**
     * 第一次计价的价格
     */
    @ApiField(description = "起步价")
    private Integer defaultFirstPrice;

    /**
     * 商品数量超过了第一次计价后的商品重量，会续加一次价格，
     * 每续加一次价格，包含的商品重量
     */
    @ApiField(description = "续重 每增加N的重量")
    private Integer defaultContinueWeight;

    /**
     * 续加计价的价格
     */
    @ApiField(description = "续重价格 每增加N的重量 增加M的运费")
    private Integer defaultContinuePrice;

}

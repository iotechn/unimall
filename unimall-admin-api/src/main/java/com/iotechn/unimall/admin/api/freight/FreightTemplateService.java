package com.iotechn.unimall.admin.api.freight;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午3:37
 */
@HttpOpenApi(group = "freightTemplate",description = "运费模板")
public interface FreightTemplateService {

    @HttpMethod(description = "增加运费模板")
    public boolean addFreightTemplate(@NotNull @HttpParam(name = "templateName",type = HttpParamType.COMMON,description = "模板名称")String templateName
            ,@NotNull @HttpParam(name = "spuLocation",type = HttpParamType.COMMON,description = "宝贝地址")String spuLocation
            ,@NotNull @HttpParam(name = "deliveryDeadline",type = HttpParamType.COMMON,description = "发货期限")Integer deliveryDeadline
            ,@NotNull @HttpParam(name = "defaultFreePrice",type = HttpParamType.COMMON,description = "默认满价包邮系列")Integer defaultFreePrice
            ,@NotNull @HttpParam(name = "defaultFirstNum",type = HttpParamType.COMMON,description = "默认首费")Integer defaultFirstNum
            ,@NotNull @HttpParam(name = "defaultFirstPrice",type = HttpParamType.COMMON,description = "默认首件数量")Integer defaultFirstPrice
            ,@NotNull @HttpParam(name = "defaultContinueNum",type = HttpParamType.COMMON,description = "默认续费")Integer defaultContinueNum
            ,@NotNull @HttpParam(name = "defaultContinuePrice",type = HttpParamType.COMMON,description = "默认续件数量")Integer defaultContinuePrice
            ,@HttpParam(name = "templateCarriageDOList",type = HttpParamType.COMMON,description = "特殊运费区")List<FreightTemplateCarriageDO> freightTemplateCarriageDOList
            ,@NotNull @HttpParam(name = "adminId",type = HttpParamType.ADMIN_ID,description = "管理员ID")Long adminId
    ) throws ServiceException;

    @HttpMethod(description = "删除运费模板")
    public boolean deleteFreightTemplate(@NotNull @HttpParam(name="templateId",type = HttpParamType.COMMON,description = "模板ID")Long templateId
                                         ,@NotNull @HttpParam(name="adminId",type = HttpParamType.ADMIN_ID,description = "管理员ID")Long adminId)throws ServiceException;

    @HttpMethod(description = "修改运费模板")
    public boolean updateFreightTemplate(@NotNull @HttpParam(name = "templateId",type = HttpParamType.COMMON,description = "模板名称")Long templateId
            ,@NotNull @HttpParam(name = "templateName",type = HttpParamType.COMMON,description = "模板名称")String templateName
            ,@NotNull @HttpParam(name = "spuLocation",type = HttpParamType.COMMON,description = "宝贝地址")String spuLocation
            ,@NotNull @HttpParam(name = "deliveryDeadline",type = HttpParamType.COMMON,description = "发货期限")Integer deliveryDeadline
            ,@NotNull @HttpParam(name = "defaultFreePrice",type = HttpParamType.COMMON,description = "默认满价包邮系列")Integer defaultFreePrice
            ,@NotNull @HttpParam(name = "defaultFirstNum",type = HttpParamType.COMMON,description = "默认首费")Integer defaultFirstNum
            ,@NotNull @HttpParam(name = "defaultFirstPrice",type = HttpParamType.COMMON,description = "默认首件数量")Integer defaultFirstPrice
            ,@NotNull @HttpParam(name = "defaultContinueNum",type = HttpParamType.COMMON,description = "默认续费")Integer defaultContinueNum
            ,@NotNull @HttpParam(name = "defaultContinuePrice",type = HttpParamType.COMMON,description = "默认续件数量")Integer defaultContinuePrice
            ,@HttpParam(name = "templateCarriageDOList",type = HttpParamType.COMMON,description = "特殊运费区")List<FreightTemplateCarriageDO> templateCarriageDOList
            ,@NotNull @HttpParam(name = "adminId",type = HttpParamType.ADMIN_ID,description = "管理员ID")Long adminId)throws ServiceException;

    @HttpMethod(description = "查询所有运费模板")
    public List<FreightTemplateDTO> getAllFreightTemplate(@NotNull @HttpParam(name="adminId",type = HttpParamType.ADMIN_ID,description = "管理员ID")Long adminId)throws ServiceException;

    @HttpMethod(description = "根据ID查询一个运费模板")
    public FreightTemplateDTO getTemplateById(@NotNull @HttpParam(name="adminId",type = HttpParamType.ADMIN_ID,description = "管理员ID")Long adminId
            ,@NotNull @HttpParam(name="templateId",type = HttpParamType.COMMON,description = "模板ID")Long templateId)throws ServiceException;
}

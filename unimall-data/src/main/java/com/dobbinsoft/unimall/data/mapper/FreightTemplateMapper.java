package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.FreightTemplateDO;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午3:26
 */
public interface FreightTemplateMapper extends IMapper<FreightTemplateDO> {

    public FreightTemplateDO selectFreightBySkuId(@Param("skuId") Long skuId);
}

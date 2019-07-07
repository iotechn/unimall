package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.AppraiseDO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponsetDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
@author kbq
@date  2019/7/6 - 10:17
*/
public interface AppraiseMapper extends BaseMapper<AppraiseDO> {

    //根据用户id，分页获取所有评价
    public List<AppraiseResponsetDTO> selectUserAllAppraise(@Param("userId")Long userId,@Param("offset")Integer offset,@Param("size")Integer size);

    //根据商品spu_id，分页获取所有评价
    public List<AppraiseResponsetDTO> selectSpuAllAppraise(@Param("spuId")Long spuId,@Param("offset")Integer offset,@Param("size")Integer size);

    //根据评价ID，查询评价
    public AppraiseResponsetDTO selectOneById(@Param("appraise") Long appraise);
}

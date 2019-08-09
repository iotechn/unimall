package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.AppraiseDO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
@author kbq
@date  2019/7/6 - 10:17
*/
public interface AppraiseMapper extends BaseMapper<AppraiseDO> {

    //根据用户id，分页获取所有评价
    public List<AppraiseResponseDTO> selectUserAllAppraise(@Param("userId")Long userId, @Param("offset")Integer offset, @Param("size")Integer size);

    //根据商品spu_id，分页获取所有评价
    public List<AppraiseResponseDTO> selectSpuAllAppraise(@Param("spuId")Long spuId, @Param("offset")Integer offset, @Param("size")Integer size);

    //根据评价ID，查询评价
    public AppraiseResponseDTO selectOneById(@Param("appraiseId") Long appraiseId);

    //根据传入条件，查询
    public List<AppraiseResponseDTO> selectAppraiseCondition(@Param("id") Long id,@Param("userName") String userName,@Param("spuName") String spuName,@Param("orderId") Long orderId,@Param("score") Integer score,@Param("content") String content,@Param("offset") Integer offset,@Param("limit")Integer limit);

    //根据传入条件，查询符合条件总数
    public Integer countAppraiseCondition(@Param("id") Long id,@Param("userName") String userName,@Param("spuName") String spuName,@Param("orderId") Long orderId,@Param("score") Integer score,@Param("content") String content);
}

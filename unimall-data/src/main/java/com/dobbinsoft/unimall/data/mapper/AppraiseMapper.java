package com.dobbinsoft.unimall.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.domain.AppraiseDO;
import com.dobbinsoft.unimall.data.dto.appraise.AppraiseResponseDTO;
import org.apache.ibatis.annotations.Param;

/*
@author kbq
@date  2019/7/6 - 10:17
*/
public interface AppraiseMapper extends IMapper<AppraiseDO> {

    /**
     * 根据用户id，分页获取所有评价
     * @param page 分页对象
     * @param userId
     * @return
     */
    public Page<AppraiseResponseDTO> selectUserAppraisePage(IPage page, @Param("userId") Long userId);

    /**
     * 根据商品spu_id，分页获取所有评价
     * @param page 分页对象
     * @param spuId
     * @return
     */
    public Page<AppraiseResponseDTO> selectSpuAppraisePage(IPage page, @Param("spuId") Long spuId);

    //根据评价ID，查询评价
    public AppraiseResponseDTO selectOneById(@Param("appraiseId") Long appraiseId);

    //根据传入条件，查询
    public Page<AppraiseResponseDTO> selectAppraisePage(
            IPage page,
            @Param("id") Long id,
            @Param("userName") String userName,
            @Param("spuName") String spuName,
            @Param("orderId") Long orderId,
            @Param("score") Integer score,
            @Param("content") String content);

}

package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.ImgDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/1.
 */
public interface ImgMapper extends IMapper<ImgDO> {

    public List<String> getImgs(@Param("bizType") Integer bizType, @Param("bizId") Long bizId);

}

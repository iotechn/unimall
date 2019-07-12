package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.SpuDO;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
public interface SpuMapper extends BaseMapper<SpuDO> {

    /**
     * 仅可传入叶子类目
     * @param categoryId
     * @return
     */
    public List<SpuDO> getSpuTitleByCategoryId(Long categoryId);
}

package com.dobbinsoft.unimall.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.domain.GroupShopDO;
import com.dobbinsoft.unimall.data.dto.product.GroupShopDTO;
import org.apache.ibatis.annotations.Param;

public interface GroupShopMapper extends IMapper<GroupShopDO> {

    public Page<GroupShopDTO> getGroupShopPage(IPage page);

    public GroupShopDTO detail(Long groupShopId);

    /**
     * 增加当前人数
     * @param id 团购活动Id
     * @param num 增加人数量
     * @return
     */
    public Integer incCurrentNum(@Param("id") Long id, @Param("num") Integer num);

}

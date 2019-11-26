package com.iotechn.unimall.biz.service.groupshop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.data.domain.GroupShopSkuDO;
import com.iotechn.unimall.data.dto.goods.GroupShopDTO;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.GroupShopMapper;
import com.iotechn.unimall.data.mapper.GroupShopSkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/25
 * Time: 11:30
 */
@Service
public class GroupShopBizService {

    @Autowired
    private GroupShopMapper groupShopMapper;

    @Autowired
    private GroupShopSkuMapper groupShopSkuMapper;

    public GroupShopDTO getGroupShopById(Long id) {
        GroupShopDTO detail = groupShopMapper.detail(id);
        if (detail == null || detail.getStatus() == StatusType.LOCK.getCode()) {
            return null;
        }
        List<GroupShopSkuDO> groupShopSkuList = groupShopSkuMapper.selectList(new EntityWrapper<GroupShopSkuDO>().eq("group_shop_id", id));
        detail.setGroupShopSkuList(groupShopSkuList);
        return detail;
    }

}

package com.iotechn.unimall.app.api.groupshop;

import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.dto.product.GroupShopDTO;
import com.iotechn.unimall.data.mapper.GroupShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/24
 * Time: 15:26
 */
@Service
public class GroupShopServiceImpl implements GroupShopService {

    @Autowired
    private GroupShopMapper groupShopMapper;

    @Override
    public Page<GroupShopDTO> getGroupShopPage(Integer pageNo, Integer pageSize) throws ServerException {
        return groupShopMapper.getGroupShopPage(Page.div(pageNo, pageSize, GroupShopDTO.class));
    }


}

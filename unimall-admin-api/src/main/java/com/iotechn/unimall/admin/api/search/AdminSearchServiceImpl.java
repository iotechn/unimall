package com.iotechn.unimall.admin.api.search;

import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.model.SearchEngineInitModel;
import com.iotechn.unimall.data.search.SearchEngine;
import com.iotechn.unimall.data.search.exception.SearchEngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Description:
 * User: rize
 * Date: 2020/8/15
 * Time: 11:22
 */
@Service("adminSearchService")
public class AdminSearchServiceImpl implements AdminSearchService {

    @Autowired(required = false)
    private SearchEngine searchEngine;

    @Autowired
    private ProductBizService productBizService;

    @Override
    public String init(Long adminId) throws ServiceException {
        this.checkSearchEngineExist();
        if (searchEngine.initAble()) {
            throw new AdminServiceException(ExceptionDefinition.SEARCH_ENGINE_NOT_SUPPORT_AUTO_INIT);
        }
        if (searchEngine.isInit()) {
            throw new AdminServiceException(ExceptionDefinition.SEARCH_ENGINE_HAS_INITED);
        }
        // 传入业务层需要初始化的表
        SearchEngineInitModel initModel = new SearchEngineInitModel();
        // 目前只需要传入SPU即可
        initModel.setTables(Arrays.asList(SpuDO.class));
        try {
            searchEngine.init(initModel);
        } catch (SearchEngineException e) {
            throw new AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.SEARCH_ENGINE_INNER_EXCEPTION, e.getMessage()));
        }
        return "ok";
    }

    @Override
    public String rebuild(Long adminId) throws ServiceException {
        this.checkSearchEngineExist();
        // 1. 目前只需要重建SPU即可
        Page<SpuDO> page;
        int pageSize = 100;
        int pageNo = 1;
        try {
            do {
                page = productBizService.getProductPageFromDB(pageNo, pageSize, null, "id", false, null);
                searchEngine.dataTransmissionList(page.getItems());
                pageNo++;
            } while (page.hasNext());
        } catch (SearchEngineException e) {
            throw new AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.SEARCH_ENGINE_INNER_EXCEPTION, e.getMessage()));
        }
        return "ok";
    }

    @Override
    public String reloadProperties(Long adminId) throws ServiceException {
        this.checkSearchEngineExist();
        searchEngine.reloadProperties();
        return "ok";
    }

    private void checkSearchEngineExist() throws ServiceException {
        if (searchEngine == null) {
            throw new AdminServiceException(ExceptionDefinition.SEARCH_ENGINE_NOT_SET);
        }
    }

}

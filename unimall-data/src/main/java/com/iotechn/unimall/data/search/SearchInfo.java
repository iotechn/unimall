package com.iotechn.unimall.data.search;

import com.iotechn.unimall.data.model.Page;

import java.util.List;
import java.util.Map;

public interface SearchInfo {

    /**
     * 传入数据，推送至开放搜索
     * @return
     */
    public Boolean pushData(List list);

    /**
     * 根据搜索条件，搜索信息，详细条件看具体实现
     * @param map
     * @return
     */
    public Page search(Map map);
}

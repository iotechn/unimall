package com.iotechn.unimall.data.search;

import com.alibaba.fastjson.JSON;
import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.SearcherClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONArray;
import com.aliyun.opensearch.sdk.dependencies.org.json.JSONObject;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.document.Command;
import com.aliyun.opensearch.sdk.generated.document.DocumentConstants;
import com.aliyun.opensearch.sdk.generated.search.*;
import com.aliyun.opensearch.sdk.generated.search.general.SearchResult;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.domain.SuperDO;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.util.SearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class SpuSearchInfoImpl implements SearchInfo {

    private static final Logger logger = LoggerFactory.getLogger(SpuSearchInfoImpl.class);

    private String accesskey;

    private String secret;

    private String host;

    private String appName;

    private String tableName;

    // 创建并构造OpenSearch对象
    private OpenSearch openSearch;

    //创建OpenSearchClient对象，并以OpenSearch对象作为构造参数
    private OpenSearchClient serviceClient;

    //定义DocumentClient对象添加json格式doc数据批量提交
    DocumentClient documentClient;

    //创建SearcherClient对象，并以OpenSearchClient对象作为构造参数
    SearcherClient searcherClient;

    public SpuSearchInfoImpl(String accesskey, String secret, String host,String appName, String tableName){
        this.accesskey = accesskey;
        this.secret = secret;
        this.host = host;
        this.appName = appName;
        this.tableName = tableName;
        openSearch = new OpenSearch(accesskey, secret, host);
        serviceClient = new OpenSearchClient(openSearch);
        documentClient = new DocumentClient(serviceClient);
        searcherClient = new SearcherClient(serviceClient);
    }

    @Override
    public Boolean pushData(List list) {
        if(CollectionUtils.isEmpty(list)){
            return true;
        }

        List<SpuDO> spuDOList = (List<SpuDO>) list;

        int index = 0;
        do{
            List<SpuDO> subList;
            if(spuDOList.size() > (index + 100)){
                subList = spuDOList.subList(index,index + 100);
            }else {
                subList = spuDOList.subList(index,spuDOList.size());
            }
            index += 100;
            JSONArray docsJsonArr = new JSONArray();
            for (SpuDO spuDO : subList) {
                spuDO.setDetail(null);
                JSONObject json = new JSONObject();
                json.put(DocumentConstants.DOC_KEY_CMD, Command.ADD.toString());
                json.put(DocumentConstants.DOC_KEY_FIELDS, SearchUtil.setConditionMap(spuDO));
                docsJsonArr.put(json);
            }

            String docsJson = docsJsonArr.toString();
            System.out.println(docsJson);
            try {
                //执行推送操作
                OpenSearchResult osr = documentClient.push(docsJson, appName, tableName);
                //判断数据是否推送成功，主要通过判断2处，第一处判断用户方推送是否成功，第二处是应用控制台中有无报错日志
                //用户方推送成功后，也有可能在应用端执行失败，此错误会直接在应用控制台错误日志中生成，比如字段内容转换失败
                if (osr.getResult().equalsIgnoreCase("true")) {
                    logger.info("用户方推送无报错！\n以下为getTraceInfo推送请求Id:" + osr.getTraceInfo().getRequestId());
                } else {
                    logger.error("用户方推送报错！" + osr.getTraceInfo());
                }
            } catch (OpenSearchException e) {
                e.printStackTrace();
            } catch (OpenSearchClientException e) {
                e.printStackTrace();
            }
        }while(spuDOList.size() > index);

        return true;
    }

    /**
     *
     * @param map
     * page:搜索开始的量
     * limit:长度
     * orderBy: 排序字段
     * isAsc: 增还是减
     * categoryId: 查询类目Id
     * title: 查询商品title
     * @return
     */
    @Override
    public Page search(Map map) {

        Object pageObj = map.get("page");
        Object limitObj = map.get("limit");
        Object orderByObj = map.get("orderBy");
        Object isAscObj = map.get("isAsc");
        Object categoryIdObj = map.get("categoryId");
        Object titleObj = map.get("title");
        //定义Config对象，用于设定config子句参数，用于分页或设置数据返回格式
        Config config = new Config(Lists.newArrayList(appName));
        Integer page;
        Integer limit;
        if(pageObj != null && limitObj != null){
             page = Integer.valueOf(String.valueOf(pageObj));
             limit = Integer.valueOf(String.valueOf(limitObj));
            config.setStart( page * limit);
            config.setHits(limit);
        }else {
            page = 0;
            limit = 10;
        }

        config.setFetchFields(getSpuField());
        //设置返回格式为json,目前只支持返回xml和json格式，fulljson类型
        config.setSearchFormat(SearchFormat.JSON);
        // 创建参数对象
        SearchParams searchParams = new SearchParams(config);
        if(categoryIdObj != null){
            String categoryId = String.valueOf(categoryIdObj);
            searchParams.setQuery("category_id:[" + categoryId + "," + categoryId + "]");
        }
        if(titleObj != null){
            String title = String.valueOf(titleObj);
            title.replace("\"","");
            title.replace("\'","");
            if(searchParams.isSetQuery()){
                searchParams.setQuery(searchParams.getQuery() + " AND " + "default: \'" + title + "\'");
            }else {
                searchParams.setQuery("default:\'" + title + "\'");
            }
        }
        // 设置sort条件
        Sort sorter = new Sort();
        if(isAscObj != null && orderByObj != null){
            String orderBy = String.valueOf(orderByObj);
            Boolean isAsc = Boolean.valueOf(String.valueOf(isAscObj));
            sorter.addToSortFields(new SortField(orderBy, isAsc?Order.INCREASE : Order.DECREASE)); //设置id字段降序
        }
        //添加Sort对象参数
        searchParams.setSort(sorter);
        // 执行返回查询结果
        SearchResult searchResult;
        try {
            searchResult = searcherClient.execute(searchParams);
            String resultStr = searchResult.getResult();
            System.out.println(resultStr);
            JSONObject obj = new JSONObject(resultStr);
            JSONObject result = obj.getJSONObject("result");
            JSONArray items = result.getJSONArray("items");
            int total = result.getInt("total");

            if(items != null && items.length() != 0){
                List<SpuDO> spuDOList = parseJson(items);
                Page<SpuDO> spuDOPage = new Page<>(spuDOList,page,limit,total);
                return spuDOPage;
            }
        } catch (OpenSearchException e) {
            e.printStackTrace();
        } catch (OpenSearchClientException e) {
            e.printStackTrace();
        }
        return new Page<SpuDO>(null,0,10,0);
    }

    private List<SpuDO> parseJson(JSONArray items){
        List<SpuDO> list = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject jsonObject = items.getJSONObject(i);
            SpuDO spuDO = new SpuDO();
            if(jsonObject.has("id")){
                spuDO.setId(jsonObject.getLong("id"));
            }
            if(jsonObject.has("original_price")){
                spuDO.setOriginalPrice(jsonObject.getInt("original_price"));
            }
            if(jsonObject.has("price")){
                spuDO.setPrice(jsonObject.getInt("price"));
            }
            if(jsonObject.has("vip_price")){
                spuDO.setVipPrice(jsonObject.getInt("vip_price"));
            }
            if(jsonObject.has("title")){
                spuDO.setTitle(jsonObject.getString("title"));
            }
            if(jsonObject.has("sales")){
                spuDO.setSales(jsonObject.getInt("sales"));
            }
            if(jsonObject.has("img")){
                spuDO.setImg(jsonObject.getString("img"));
            }
            if(jsonObject.has("description")){
                spuDO.setDescription(jsonObject.getString("description"));
            }
            if(jsonObject.has("category_id")){
                spuDO.setCategoryId(jsonObject.getLong("category_id"));
            }
            if(jsonObject.has("freight_template_id")){
                spuDO.setFreightTemplateId(jsonObject.getLong("freight_template_id"));
            }
            if(jsonObject.has("unit")){
                spuDO.setUnit(jsonObject.getString("unit"));
            }
            if(jsonObject.has("status")){
                spuDO.setStatus(jsonObject.getInt("status"));
            }
            if(jsonObject.has("activity_type")){
                spuDO.setActivityType(jsonObject.getInt("activity_type"));
            }
            if(jsonObject.has("activity_id")){
                spuDO.setActivityId(jsonObject.getLong("activity_id"));
            }
            if(jsonObject.has("gmt_activity_start")){
                String gmt_activity_start = jsonObject.getString("gmt_activity_start");
                if(gmt_activity_start != null){
                    spuDO.setGmtActivityStart(new Date(Long.valueOf(gmt_activity_start)));
                }
            }
            if(jsonObject.has("gmt_activity_end")){
                String gmtActivityEnd = jsonObject.getString("gmt_activity_end");
                if(gmtActivityEnd != null){
                    spuDO.setGmtActivityEnd(new Date(Long.valueOf(gmtActivityEnd)));
                }
            }
            if(jsonObject.has("gmt_update")){
                String gmtUpdate = jsonObject.getString("gmt_update");
                if(gmtUpdate != null){
                    spuDO.setGmtUpdate(new Date(Long.valueOf(gmtUpdate)));
                }
            }
            if(jsonObject.has("gmt_create")){
                String gmtCreate = jsonObject.getString("gmt_create");
                if(gmtCreate != null){
                    spuDO.setGmtCreate(new Date(Long.valueOf(gmtCreate)));
                }
            }
            list.add(spuDO);
        }
        return list;
    }

    private List<String> getSpuField(){
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("original_price");
        fields.add("price");
        fields.add("vip_price");
        fields.add("title");
        fields.add("sales");
        fields.add("img");
        fields.add("description");
        fields.add("category_id");
        fields.add("freight_template_id");
        fields.add("unit");
        fields.add("status");
        fields.add("activity_type");
        fields.add("activity_id");
        fields.add("gmt_activity_start");
        fields.add("gmt_activity_end");
        fields.add("gmt_update");
        fields.add("gmt_create");
        return fields;
    }
}

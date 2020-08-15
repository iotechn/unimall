package com.iotechn.unimall.data.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Description: 搜索引擎，搜索条件封装模型
 * User: rize
 * Date: 2020/8/15
 * Time: 15:28
 */
public class SearchWrapperModel {

    public static final int CONDITION_TYPE_ACCURATE = 1;

    public static final int CONDITION_TYPE_FUZZY = 2;

    public static final int CONDITION_TYPE_GT = 3;

    public static final int CONDITION_TYPE_GTE = 4;

    public static final int CONDITION_TYPE_LT = 5;

    public static final int CONDITION_TYPE_LTE = 6;

    public static final int CONDITION_TYPE_BETWEEN = 7;


    private List<Condition> conditions = new LinkedList<>();

    private Integer pageNo;

    private Integer pageSize;

    private String orderByField = "id";

    private Boolean isAsc = true;

    public List<Condition> getConditions() {
        return conditions;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public Boolean getIsAsc() {
        return isAsc;
    }

    public SearchWrapperModel div(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        return this;
    }

    public SearchWrapperModel like(String field, Object keyword) {
        return condition(CONDITION_TYPE_FUZZY, field, keyword);
    }

    public SearchWrapperModel eq(String field, Number keyword) {
        return condition(CONDITION_TYPE_ACCURATE, field, keyword);
    }

    public SearchWrapperModel gt(String field, Number keyword) {
        return condition(CONDITION_TYPE_GT, field, keyword);
    }

    public SearchWrapperModel gte(String field, Number keyword) {
        return condition(CONDITION_TYPE_GTE, field, keyword);
    }

    public SearchWrapperModel lt(String field, Number keyword) {
        return condition(CONDITION_TYPE_LT, field, keyword);
    }

    public SearchWrapperModel lte(String field, Number keyword) {
        return condition(CONDITION_TYPE_LTE, field, keyword);
    }

    public SearchWrapperModel between(String field, Number keyword, Number keyword2) {
        Condition condition = new Condition();
        condition.setField(field);
        condition.setType(CONDITION_TYPE_BETWEEN);
        condition.setKeyword(keyword);
        condition.setKeyword2(keyword2);
        this.conditions.add(condition);
        return this;
    }

    public SearchWrapperModel orderByAsc(String field) {
        this.orderByField = field;
        this.isAsc = true;
        return this;
    }

    public SearchWrapperModel orderByDesc(String field) {
        this.orderByField = field;
        this.isAsc = false;
        return this;
    }

    @Data
    public static class Condition {

        /**
         * 字段
         */
        private String field;

        /**
         * 搜索方式 1. 全字匹配 2. 模糊查询
         */
        private Integer type;

        /**
         * 关键字
         */
        private Object keyword;

        /**
         * 有的表达式需要两个关键字
         */
        private Object keyword2;

    }

    private SearchWrapperModel condition(int type, String field, Object keyword) {
        Condition condition = new Condition();
        condition.setField(field);
        condition.setKeyword(keyword);
        condition.setType(type);
        this.conditions.add(condition);
        return this;
    }

}

package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 代码逆向生成，传输模型
 * User: rize
 * Date: 2020/3/11
 * Time: 10:46
 */
@Data
public class CodeReverseGenerateDTO {

    private String tableName;

    /**
     * 服务的包名和服务名
     */
    private String title;

    private String folder;

    private String pageName;

    private String doName;

    private String doLowCaseName;

    private String packageName;

    private String serviceName;

    private String serviceLowCaseName;

    private Set<String> importClasses;

    private List<ColumnDefinition> columnDefinitionList;

    @Data
    public static class ColumnDefinition {

        /**
         * 字段名称
         */
        private String name;

        private String alias;

        private String aliasUpCase;
        /**
         * 列表显示中文
         */
        private String chinese;

        /**
         * 映射的Java类型
         */
        private String clazz;

        private String clazzName;

        /**
         * 是否是钱。无法从类型中判断是否是钱，只能人为设定
         */
        private Boolean money;

        /**
         * 是否在列表中展示
         */
        private Boolean showInList;

        /**
         * 是否为查询字段
         */
        private Boolean query;

        /**
         * 是否为 like 查询
         */
        private Boolean likeQuery;

        /**
         * 是否是插入必选字段
         */
        private Boolean insertColumn;

        /**
         * 非空校验。（非空校验必须是插入必选字段）
         */
        private Boolean notnull;

        private String notice;

    }

}

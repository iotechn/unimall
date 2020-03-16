package com.iotechn.unimall.data.mapper;

import com.iotechn.unimall.data.domain.GeneratorTableColumnDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/11
 * Time: 11:10
 */
public interface GeneratorMapper {

    public List<GeneratorTableColumnDO> listColumns(@Param("tableName") String tableName);

    public List<String> listTables();
}

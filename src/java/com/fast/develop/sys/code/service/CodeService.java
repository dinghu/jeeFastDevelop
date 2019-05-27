package com.fast.develop.sys.code.service;

import java.util.List;

import com.fast.develop.sys.code.entity.ColumnInfo;
import com.fast.develop.sys.code.entity.TableInfo;


public interface CodeService {

    public List<String> findTableList();

    public List<ColumnInfo> findColumnsByTable(String tableName);

    public TableInfo getTableInfoByName(String tableName);
}

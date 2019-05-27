package com.fast.develop.sys.code.dao;

import java.util.List;

import com.fast.develop.sys.code.entity.TableInfo;
import org.apache.ibatis.annotations.Param;

import com.fast.develop.sys.code.entity.ColumnInfo;

public interface CodeDao  {
	public List<String> findTableList();
	public String getDataBaseName();
	public TableInfo getTableInfoByName(@Param("tableName") String tableName, @Param("dbName") String dbName);
	public List<ColumnInfo> findColumnsByTable(@Param("tableName") String tableName,@Param("dbName") String dbName);
}

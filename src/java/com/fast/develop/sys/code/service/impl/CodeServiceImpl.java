package com.fast.develop.sys.code.service.impl;

import java.util.List;

import com.fast.develop.sys.code.dao.CodeDao;
import com.fast.develop.sys.code.entity.ColumnInfo;
import com.fast.develop.sys.code.entity.TableInfo;
import com.fast.develop.sys.code.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {


    @Autowired
    private CodeDao codeDao;

    public List<String> findTableList() {
        return codeDao.findTableList();
    }

    public List<ColumnInfo> findColumnsByTable(String tableName) {
        String dbName = codeDao.getDataBaseName();

        return codeDao.findColumnsByTable(tableName, dbName);
    }

    public TableInfo getTableInfoByName(String tableName) {
        String dbName = codeDao.getDataBaseName();
        return codeDao.getTableInfoByName(tableName, dbName);
    }
}

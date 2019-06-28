package com.fast.develop.sys.dict.service.impl;

import com.fast.develop.framework.service.ServiceSupportAbs;
import com.fast.develop.sys.dict.dao.DictDao;
import com.fast.develop.sys.dict.entity.DictItem;
import com.fast.develop.sys.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fast.develop.framework.dao.BaseDAO;

@Service
public class DictServiceImplAbs extends ServiceSupportAbs<DictItem> implements DictService {

    // 注入Service依赖
    @Autowired
    private DictDao dictDao;


    @Override
    public BaseDAO<DictItem> getDao() {

        return dictDao;
    }


}

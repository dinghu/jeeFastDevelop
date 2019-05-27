package com.fast.develop.sys.dict.service.impl;

import com.fast.develop.sys.dict.dao.DictDao;
import com.fast.develop.sys.dict.entity.DictItem;
import com.fast.develop.sys.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fast.develop.framework.dao.XjjDAO;
import com.fast.develop.framework.service.XjjServiceSupport;

@Service
public class DictServiceImpl extends XjjServiceSupport<DictItem> implements DictService {

	// 注入Service依赖
	@Autowired
	private DictDao dictDao;


	@Override
	public XjjDAO<DictItem> getDao() {
		
		return dictDao;
	}


}

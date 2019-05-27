/****************************************************
 * Description: ServiceImpl for t_sys_xfile
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-05-04 zhanghejie Create File
**************************************************/

package com.fast.develop.sys.xfile.service.impl;

import com.fast.develop.framework.dao.XjjDAO;
import com.fast.develop.sys.xfile.entity.XfileEntity;
import com.fast.develop.sys.xfile.service.XfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.develop.framework.service.XjjServiceSupport;
import com.fast.develop.sys.xfile.dao.XfileDao;

@Service
public class XfileServiceImpl extends XjjServiceSupport<XfileEntity> implements XfileService {

	@Autowired
	private XfileDao xfileDao;

	@Override
	public XjjDAO<XfileEntity> getDao() {
		
		return xfileDao;
	}
}
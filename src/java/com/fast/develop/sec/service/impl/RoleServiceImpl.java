/****************************************************
 * Description: ServiceImpl for 角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/

package com.fast.develop.sec.service.impl;

import java.util.List;

import com.fast.develop.framework.dao.XjjDAO;
import com.fast.develop.framework.service.XjjServiceSupport;
import com.fast.develop.sec.dao.RoleDao;
import com.fast.develop.sec.entity.RoleEntity;
import com.fast.develop.sec.entity.RolePrivilegeEntity;
import com.fast.develop.sec.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends XjjServiceSupport<RoleEntity> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public XjjDAO<RoleEntity> getDao() {
		
		return roleDao;
	}
	
	public List<RoleEntity> findListNoUser(Long userId)
	{
		return roleDao.findListNoUser(userId);
	}
	
	public RoleEntity getByCode(String code)
	{
		return roleDao.getByCode(code);
	}
	
	public List<RolePrivilegeEntity> findPrivilegeByRole(Long roleId)
	{
		return roleDao.findPrivilegeByRole(roleId);
	}
	
	
	public List<RoleEntity> findListByUserId(Long userId)
	{
		return roleDao.findListByUserId(userId);
	}
}
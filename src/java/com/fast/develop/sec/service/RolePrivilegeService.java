/****************************************************
 * Description: Service for t_sec_role_privilege
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author      zhanghejie
 * @version     1.0
 * @see
	HISTORY
    *  2018-04-18 zhanghejie Create File
**************************************************/
package com.fast.develop.sec.service;
import java.util.List;

import com.fast.develop.framework.security.dto.TreeNode;
import com.fast.develop.framework.service.XjjService;
import com.fast.develop.framework.web.support.XJJParameter;
import com.fast.develop.sec.entity.RolePrivilegeEntity;

public interface RolePrivilegeService  extends XjjService<RolePrivilegeEntity> {
	
	/**
	 * 通过角色id取得权限树
	 * @param roleid
	 * @return
	 */
	public List<TreeNode> listpri(Long roleid);
	
	public RolePrivilegeEntity getByParam(XJJParameter param);
}

/****************************************************
 * Description: DAO for t_sec_role_privilege
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author zhanghejie
 * @version 1.0
 * @see
HISTORY
 *  2018-04-18 zhanghejie Create File
 **************************************************/
package com.fast.develop.sec.dao;

import java.util.List;

import com.fast.develop.framework.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.fast.develop.sec.entity.RolePrivilegeEntity;

public interface RolePrivilegeDao extends BaseDAO<RolePrivilegeEntity> {
    public RolePrivilegeEntity getByRolePri(@Param("roleId") Long roleId, @Param("pcode") String pcode);

    /**
     * 角色查询有权浏览（list）的菜单
     *
     * @param roleIds
     * @return
     */
    public List<RolePrivilegeEntity> findListByRoleHasListPri(@Param("roleIds") Long[] roleIds, @Param("listCode") String listCode);
}


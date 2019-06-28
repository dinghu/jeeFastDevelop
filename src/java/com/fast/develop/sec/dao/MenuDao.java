/****************************************************
 * Description: DAO for 菜单
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author zhanghejie
 * @version 1.0
 * @see
HISTORY
 *  2018-04-12 zhanghejie Create File
 **************************************************/
package com.fast.develop.sec.dao;

import java.util.List;

import com.fast.develop.framework.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.fast.develop.sec.entity.MenuEntity;

public interface MenuDao extends BaseDAO<MenuEntity> {

    public List<MenuEntity> findMenusByPid(@Param("pid") Long pid);
}


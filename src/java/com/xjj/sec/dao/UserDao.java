package com.xjj.sec.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xjj.framework.dao.XjjDAO;
import com.xjj.sec.entity.RoleEntity;
import com.xjj.sec.entity.XjjUser;

public interface UserDao  extends XjjDAO<XjjUser> {
	public List<XjjUser> managerPage (@Param("query") HashMap<String,HashMap<String,Object>> queryMap, @Param("offset") int offset, @Param("limit") int limit);
}

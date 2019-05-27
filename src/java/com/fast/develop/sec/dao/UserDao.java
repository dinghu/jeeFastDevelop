package com.fast.develop.sec.dao;

import java.util.HashMap;
import java.util.List;

import com.fast.develop.framework.dao.XjjDAO;
import org.apache.ibatis.annotations.Param;

import com.fast.develop.sec.entity.XjjUser;

public interface UserDao  extends XjjDAO<XjjUser> {
	public List<XjjUser> managerPage (@Param("query") HashMap<String,HashMap<String,Object>> queryMap, @Param("offset") int offset, @Param("limit") int limit);
}

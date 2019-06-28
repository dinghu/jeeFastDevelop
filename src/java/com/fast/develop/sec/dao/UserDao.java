package com.fast.develop.sec.dao;

import java.util.HashMap;
import java.util.List;

import com.fast.develop.framework.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import com.fast.develop.sec.entity.User;

public interface UserDao extends BaseDAO<User> {
    public List<User> managerPage(@Param("query") HashMap<String, HashMap<String, Object>> queryMap, @Param("offset") int offset, @Param("limit") int limit);
}

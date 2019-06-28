package com.fast.develop.sec.service;

import java.util.Map;

import com.fast.develop.framework.exception.ValidationException;
import com.fast.develop.framework.service.BaseService;
import com.fast.develop.sec.entity.User;

public interface UserService extends BaseService<User> {
    /**
     * 导入用户
     *
     * @param fileId
     * @param orgId
     * @param loginInfo
     */
    public Map<String, Object> saveImportUser(Long fileId) throws ValidationException;
}

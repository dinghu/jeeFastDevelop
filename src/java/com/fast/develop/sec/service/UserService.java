package com.fast.develop.sec.service;

import java.util.Map;

import com.fast.develop.framework.exception.ValidationException;
import com.fast.develop.framework.service.XjjService;
import com.fast.develop.sec.entity.XjjUser;

public interface UserService extends XjjService<XjjUser> {
    /**
     * 导入用户
     *
     * @param fileId
     * @param orgId
     * @param loginInfo
     */
    public Map<String, Object> saveImportUser(Long fileId) throws ValidationException;
}

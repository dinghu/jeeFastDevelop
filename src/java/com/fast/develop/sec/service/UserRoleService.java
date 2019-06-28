/****************************************************
 * Description: Service for 用户角色
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author zhanghejie
 * @version 1.0
 * @see
HISTORY
 *  2018-04-18 zhanghejie Create File
 **************************************************/
package com.fast.develop.sec.service;

import com.fast.develop.framework.service.BaseService;
import com.fast.develop.sec.entity.UserRoleEntity;

public interface UserRoleService extends BaseService<UserRoleEntity> {

    public void deleteBy2Id(Long userId, Long roleId);
}

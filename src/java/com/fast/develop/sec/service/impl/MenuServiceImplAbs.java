/****************************************************
 * Description: ServiceImpl for 菜单
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author zhanghejie
 * @version 1.0
 * @see
HISTORY
 *  2018-04-12 zhanghejie Create File
 **************************************************/

package com.fast.develop.sec.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fast.develop.common.Constants;
import com.fast.develop.framework.dao.BaseDAO;
import com.fast.develop.framework.security.SecurityConstants;
import com.fast.develop.framework.service.ServiceSupportAbs;
import com.fast.develop.framework.web.support.QueryParameters;
import com.fast.develop.sec.dao.MenuDao;
import com.fast.develop.sec.dao.RolePrivilegeDao;
import com.fast.develop.sec.entity.MenuEntity;
import com.fast.develop.sec.entity.RolePrivilegeEntity;
import com.fast.develop.sec.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImplAbs extends ServiceSupportAbs<MenuEntity> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;

    @Override
    public BaseDAO<MenuEntity> getDao() {

        return menuDao;
    }

    public List<MenuEntity> findMenusByPid(Long pid) {
        return menuDao.findMenusByPid(pid);
    }

    /**
     * 查询所有有效的菜单
     */
    public List<MenuEntity> findAllValid() {
        QueryParameters query = new QueryParameters();
        //查询所有有效的菜单
        query.addQuery("query.status@eq@s", Constants.COMMON_STATUS_VALID);
        query.addOrderByAsc("code");
        List<MenuEntity> menuList = this.findList(query);

        return menuList;
    }

    /**
     *
     */
    public List<MenuEntity> findMenusByRoleIds(Long[] roleIds) {
        if (null == roleIds || roleIds.length == 0) {
            return null;
        }
        //查询出角色所有有权限访问的菜单privilegeCode
        List<RolePrivilegeEntity> rpList = rolePrivilegeDao.findListByRoleHasListPri(roleIds, SecurityConstants.SECURITY_LIST_CODE);
        String[] priCodeArr = new String[rpList.size()];

        for (int i = 0; i < rpList.size(); i++) {
            priCodeArr[i] = rpList.get(i).getPrivilegeCode();
        }

        //查出所有有权访问的菜单
        List<MenuEntity> hasSecMenuList = menuDao.findListByColumnValues("privilege_code", priCodeArr);
        List<String> hasSecCodeList = new ArrayList<String>();

        for (int i = 0; i < hasSecMenuList.size(); i++) {
            hasSecCodeList.add(hasSecMenuList.get(i).getCode());
        }

        //查出所有的有效菜单
        List<MenuEntity> allMmenuList = this.findAllValid();
        //存取用户有权访问的菜单
        List<MenuEntity> userMenuList = new ArrayList<MenuEntity>();
        for (int i = 0; i < allMmenuList.size(); i++) {
            if (checkHasPri(allMmenuList.get(i).getCode(), hasSecCodeList)) {
                userMenuList.add(allMmenuList.get(i));
            }
        }

        //把菜单拼装为树状包含结构
        List<MenuEntity> treeMenuList = new ArrayList<MenuEntity>();
        String menuCode = null;
        MenuEntity rootMenu = null;
        for (int i = 0; i < userMenuList.size(); i++) {

            menuCode = userMenuList.get(i).getCode();
            if (menuCode.length() == 2) {
                treeMenuList.add(userMenuList.get(i));
            } else if (menuCode.length() == 4) {
                rootMenu = treeMenuList.get(treeMenuList.size() - 1);
                rootMenu.addSubMenu(userMenuList.get(i).copy());
            }
        }
        return treeMenuList;

    }

    /**
     * 根据code判断是否有该菜单的权限
     *
     * @param code        菜单code
     * @param hasSecCodes 拥有的所有菜单code
     * @return
     */
    private boolean checkHasPri(String code, List<String> hasSecCodes) {
        if (hasSecCodes.contains(code)) {
            return true;
        }

        String tempCode = null;
        for (int i = 0; i < hasSecCodes.size(); i++) {
            tempCode = hasSecCodes.get(i);

            if (tempCode.startsWith(code)) {
                return true;
            }
        }
        return false;
    }
}
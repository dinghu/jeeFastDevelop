/****************************************************
 * Description: Controller for t_sec_role_privilege
 * Copyright:   Copyright (c) 2018
 * Company:     xjj
 * @author zhanghejie
 * @version 1.0
 * @see
HISTORY
 *  2018-04-18 zhanghejie Create File
 **************************************************/
package com.fast.develop.sec.web;

import java.util.List;

import com.fast.develop.framework.json.JsonResult;
import com.fast.develop.framework.security.annotations.SecFunction;
import com.fast.develop.framework.security.dto.Privilege;
import com.fast.develop.framework.security.dto.TreeNode;
import com.fast.develop.framework.web.SpringControllerSupport;
import com.fast.develop.framework.web.support.Pagination;
import com.fast.develop.framework.web.support.QueryParameter;
import com.fast.develop.framework.web.support.QueryParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fast.develop.framework.security.PrivilegeService;
import com.fast.develop.framework.utils.StringUtils;
import com.fast.develop.sec.entity.RolePrivilegeEntity;
import com.fast.develop.sec.service.RolePrivilegeService;

@Controller
@RequestMapping("/sec/role/privilege")
public class RolePrivilegeController extends SpringControllerSupport {
    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        String page = this.getViewPath("index");
        return page;
    }

    @RequestMapping(value = "/list")
    public String list(Model model,
                       @QueryParameter QueryParameters query,
                       @ModelAttribute("page") Pagination page
    ) {
        page = rolePrivilegeService.findPage(query, page);
        return getViewPath("list");
    }


    @RequestMapping("/input")
    public String create(@ModelAttribute("rolePrivilege") RolePrivilegeEntity rolePrivilege, Model model) {
        return getViewPath("input");
    }

    @RequestMapping("/input/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        RolePrivilegeEntity rolePrivilege = rolePrivilegeService.getById(id);
        model.addAttribute("rolePrivilege", rolePrivilege);
        return getViewPath("input");
    }

    @RequestMapping("/save")
    public @ResponseBody
    JsonResult save(@ModelAttribute RolePrivilegeEntity rolePrivilege) {

        validateSave(rolePrivilege);
        if (rolePrivilege.isNew()) {
            rolePrivilegeService.save(rolePrivilege);
        } else {
            rolePrivilegeService.update(rolePrivilege);
        }
        return JsonResult.success("保存成功");
    }


    /**
     * 数据校验
     **/
    private void validateSave(RolePrivilegeEntity rolePrivilege) {
        //必填项校验
    }


    @RequestMapping("/delete/{id}")
    public @ResponseBody
    JsonResult delete(@PathVariable("id") Long id) {
        rolePrivilegeService.delete(id);
        return JsonResult.success("成功删除1条");
    }

    @RequestMapping("/delete")
    public @ResponseBody
    JsonResult delete(@RequestParam("ids") Long[] ids) {
        if (ids == null || ids.length == 0) {
            return JsonResult.error("没有选择删除记录");
        }
        for (Long id : ids) {
            rolePrivilegeService.delete(id);
        }
        return JsonResult.success("成功删除" + ids.length + "条");
    }


    //====================================设置权限开始===============================================
    @SecFunction(title = "设置权限", code = "allot")
    @RequestMapping("/allot/{roleId}")
    public String listpri(@PathVariable("roleId") Long roleId, Model model) {
        List<TreeNode> treeNodeList = rolePrivilegeService.listpri(roleId);
        model.addAttribute("roleId", roleId);
        model.addAttribute(treeNodeList);
        return getViewPath("allot");
    }


    /**
     * 添加权限
     *
     * @param roleId
     * @param priCode
     * @param functions 多个用|分开
     * @param model
     * @return
     */
    @RequestMapping("/add/{roleId}/{priCode}/{functions}")
    public @ResponseBody
    JsonResult addPri(@PathVariable("roleId") Long roleId,
                      @PathVariable("priCode") String priCode,
                      @PathVariable("functions") String functions,
                      Model model) {


        priCode = priCode.replace("menu_", "");
        QueryParameters param = new QueryParameters();
        param.addQuery("query.roleId@eq@l", roleId);
        param.addQuery("query.privilegeCode@eq@s", priCode);
        RolePrivilegeEntity rpe = rolePrivilegeService.getByParam(param);

        Privilege pri = PrivilegeService.getPrivilege(priCode);
        String priTitle = null;
        if (null != pri) {
            priTitle = PrivilegeService.getPrivilege(priCode).getTitle();
        }
        if (null == rpe) {
            rpe = new RolePrivilegeEntity();
            rpe.setRoleId(roleId);
            rpe.setPrivilegeCode(priCode);
            rpe.setFunctionList(functions);
            rpe.setPrivilegeTitle(priTitle);
            rolePrivilegeService.save(rpe);
        } else {
            String[] funcArr = functions.split("\\|");
            if (null != funcArr && funcArr.length > 0) {
                for (int i = 0; i < funcArr.length; i++) {
                    rpe.addFunction(funcArr[i]);
                }
                rpe.setPrivilegeTitle(priTitle);
            }
            rolePrivilegeService.update(rpe);
        }
        return JsonResult.success("为【" + priTitle + "】添加(" + functions + ")权限成功");
    }


    /**
     * 添加权限
     *
     * @param roleId
     * @param priCode
     * @param functions 多个用|分开
     * @param model
     * @return
     */
    @RequestMapping("/cancle/{roleId}/{priCode}/{functions}")
    public @ResponseBody
    JsonResult cancelPri(@PathVariable("roleId") Long roleId,
                         @PathVariable("priCode") String priCode,
                         @PathVariable("functions") String functions,
                         Model model) {
        priCode = priCode.replace("menu_", "");
        Privilege pri = PrivilegeService.getPrivilege(priCode);
        String priTitle = null;
        if (null != pri) {
            priTitle = PrivilegeService.getPrivilege(priCode).getTitle();
        }

        QueryParameters param = new QueryParameters();
        param.addQuery("query.roleId@eq@l", roleId);
        param.addQuery("query.privilegeCode@eq@s", priCode);
        RolePrivilegeEntity rpe = rolePrivilegeService.getByParam(param);

        if (null != rpe) {

            String[] funcArr = functions.split("\\|");
            if (null != funcArr && funcArr.length > 0) {
                for (int i = 0; i < funcArr.length; i++) {
                    rpe.removeFunction(funcArr[i]);
                }
            }

            if (StringUtils.isBlank(rpe.getFunctionList())) {
                rolePrivilegeService.delete(rpe);
            } else {
                rolePrivilegeService.update(rpe);
            }
        }
        return JsonResult.success("为【" + priTitle + "】取消(" + functions + ")权限成功");
    }


    public static void main(String[] args) {
        String functions = "edit|delete";
        String[] funcArr = functions.split("\\|");
        if (null != funcArr && funcArr.length > 0) {
            for (int i = 0; i < funcArr.length; i++) {
                System.out.println(funcArr[i]);
            }
        }

    }
}
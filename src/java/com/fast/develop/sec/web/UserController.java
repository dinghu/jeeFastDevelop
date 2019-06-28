package com.fast.develop.sec.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.develop.common.DictConstants;
import com.fast.develop.common.Constants;
import com.fast.develop.framework.exception.ValidationException;
import com.fast.develop.framework.json.JsonResult;
import com.fast.develop.framework.utils.Excel2007Util;
import com.fast.develop.framework.web.SpringControllerSupport;
import com.fast.develop.framework.web.support.Pagination;
import com.fast.develop.framework.web.support.QueryParameter;
import com.fast.develop.framework.web.support.QueryParameters;
import com.fast.develop.sys.dict.entity.DictItem;
import com.fast.develop.sys.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fast.develop.framework.security.annotations.SecCreate;
import com.fast.develop.framework.security.annotations.SecDelete;
import com.fast.develop.framework.security.annotations.SecEdit;
import com.fast.develop.framework.security.annotations.SecList;
import com.fast.develop.framework.security.annotations.SecPrivilege;
import com.fast.develop.sec.entity.User;
import com.fast.develop.sec.service.UserService;

@Controller
@RequestMapping("/sec/user")
public class UserController extends SpringControllerSupport {

    @Autowired
    private UserService userService;

    @Autowired
    private DictService dictService;

    @SecPrivilege(title = "用户管理")
    @RequestMapping(value = "/index")
    public String index(Model model) {
        String page = this.getViewPath("index");
        return page;
    }

    @SecList
    @RequestMapping(value = "/list")
    public String list(Model model,
                       @QueryParameter QueryParameters query,
                       @ModelAttribute("page") Pagination page
    ) {
        //只查询类型为管理员的用户
        query.addQuery("query.userType@eq@s", Constants.USER_TYPE_USER);
        query.addOrderByAsc("id");
        page = userService.findPage(query, page);
        return getViewPath("list");
    }

    @SecCreate
    @RequestMapping("/input")
    public String input(@ModelAttribute("user") User user,
                        @QueryParameter QueryParameters query,
                        Model model) {
        //查询省份
        query.addQuery("query.groupCode@eq@s", DictConstants.DICT_PROVINCE);
        query.addQuery("query.status@eq@s", Constants.COMMON_STATUS_VALID);
        query.addOrderByAsc("sn");
        List<DictItem> dictList = dictService.findList(query);
        model.addAttribute("dictList", dictList);
        return getViewPath("input");
    }

    /*
     * 修改用户
     */
    @SecEdit
    @RequestMapping("/input/{id}")
    public String edit(@PathVariable("id") Long id,
                       @QueryParameter QueryParameters query,
                       Model model) {

        //查询省份
        query.addQuery("query.groupCode@eq@s", DictConstants.DICT_PROVINCE);
        query.addQuery("query.status@eq@s", Constants.COMMON_STATUS_VALID);
        query.addOrderByAsc("sn");
        List<DictItem> dictList = dictService.findList(query);
        model.addAttribute("dictList", dictList);


        //查询用户
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return getViewPath("input");
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @SecCreate
    @SecEdit
    @RequestMapping("/save")
    public @ResponseBody
    JsonResult save(@ModelAttribute User user) {


        try {
            if (user.isNew()) {
                user.setCreateDate(new Date());
                user.setUserType(Constants.USER_TYPE_USER);
                userService.save(user);
            } else {
                userService.update(user);
            }
        } catch (Exception e) {
            if (e.getCause() == null) {
                return JsonResult.error("保存失败");
            }

            if (e.getCause().toString().contains("unique_login_name")) {
                return JsonResult.error("保存失败,账号" + user.getLoginName() + "已经被注册。");
            }
            if (e.getCause().toString().contains("unique_email")) {
                return JsonResult.error("保存失败,邮箱" + user.getEmail() + "已经被注册。");
            }

            if (e.getCause().toString().contains("unique_mobile")) {
                return JsonResult.error("保存失败,手机号" + user.getMobile() + "已经被注册。");
            }
            return JsonResult.error("保存失败");
        }

        return JsonResult.success("保存成功");
    }


    @RequestMapping(value = "/{userId}/detail", method = RequestMethod.POST)
    public String detail(@PathVariable("userId") Long userId, Model model) {
        if (userId == null) {
            return "redirect:/user/list";
        }
        User user = userService.getById(userId);
        if (user == null) {
            return "forward:/user/list";
        }
        model.addAttribute("user", user);
        return "detail";
    }

    @SecDelete
    @RequestMapping("/delete/{id}")
    public @ResponseBody
    JsonResult delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return JsonResult.success("成功删除1条");
    }

    @SecDelete
    @RequestMapping("/delete")
    public @ResponseBody
    JsonResult delete(@RequestParam("ids") Long[] ids) {
        if (ids == null || ids.length == 0) {
            return JsonResult.error("没有删除");
        }
        for (Long id : ids) {
            userService.delete(id);
        }
        return JsonResult.success("成功删除" + ids.length + "条");
    }

    /**
     * 导入用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importExcel(Model model) {

        return getViewPath("import");
    }

    /**
     * 导入用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/import/save", method = RequestMethod.POST)
    public @ResponseBody
    JsonResult importSave(Model model, @RequestParam(value = "fileId", required = false) Long fileId) {


        System.out.println("上传开始----");

        try {
            Map<String, Object> map = userService.saveImportUser(fileId);
            int allCnt = (Integer) map.get("allCnt");
            return JsonResult.success("导入成功：本次共计导入数据" + allCnt + "条");
        } catch (ValidationException e) {

            return JsonResult.error("导入失败：<br/>" + e.getMessage());
        }

    }


    /**
     * 导出用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/export/excel")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response) {

        List<User> userList = userService.findAll();

        LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
        columns.put("loginName", "账号");
        columns.put("userName", "用户名");
        columns.put("mobile", "手机");
        Excel2007Util.write(userList, columns, response, "user-export");

        return null;
    }


}

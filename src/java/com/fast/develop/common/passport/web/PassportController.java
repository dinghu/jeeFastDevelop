package com.fast.develop.common.passport.web;

import javax.servlet.http.HttpSession;

import com.fast.develop.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fast.develop.framework.exception.DataAccessException;
import com.fast.develop.framework.json.JsonResult;
import com.fast.develop.framework.json.JsonResult.MessageType;
import com.fast.develop.framework.utils.EncryptUtils;
import com.fast.develop.framework.utils.StringUtils;
import com.fast.develop.framework.web.ManagerInfo;
import com.fast.develop.framework.web.SpringControllerSupport;
import com.fast.develop.framework.web.support.QueryParameters;
import com.fast.develop.sec.entity.User;
import com.fast.develop.sec.service.UserService;

@Controller
@RequestMapping("/passport/manager")
public class PassportController extends SpringControllerSupport {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    public String managerLogin() {

        return "/passport/manager/login";
    }

    @RequestMapping(value = "/logout")
    public String managerLogout() {
        this.getRequest().getSession().invalidate();
        return "/passport/manager/login";
    }


    @RequestMapping(value = "/logon")
    public @ResponseBody
    JsonResult managerLogon(@RequestParam String loginName,
                            @RequestParam String password,
                            Model model) throws DataAccessException {

        String msg = validateLogin(loginName, password);
        model.addAttribute("loginName", loginName);
        if (msg != null) {
            return JsonResult.error(msg);
        }
        password = EncryptUtils.MD5Encode(password);//MD5密码加密


        //查询数据库
        QueryParameters param = new QueryParameters();
        param.addQuery("query.loginName@eq@s", loginName);
        param.addQuery("query.userType@eq@s", Constants.USER_TYPE_ADMIN);
        User user = userService.getByParam(param);

        JsonResult json = new JsonResult();
        json.setType(MessageType.error);
        if (null == user) {
            json.setMessage("账号不存在");
            json.setItem("loginName");
            return json;
        }

        if (Constants.COMMON_STATUS_INVALID.equals(user.getStatus())) {
            json.setMessage("账号已被禁用");
            json.setItem("loginName");
            return json;
        }

        if (!password.equals(user.getPassword())) {
            json.setMessage("用户密码不正确");
            json.setItem("password");
            return json;
        }

        ManagerInfo managerInfo = new ManagerInfo();
        managerInfo.setLoginName(user.getLoginName());
        managerInfo.setUserId(user.getId());
        managerInfo.setUserName(user.getUserName());
        managerInfo.setUserType(user.getUserType());

        this.getRequest().getSession().setAttribute(Constants.SESSION_MANAGER_INFO_KEY, managerInfo);
        json.setType(MessageType.success);
        return json;
    }

    /**
     * 验证登录信息
     *
     * @param base
     */
    private String validateLogin(String loginname, String password) {
        if (loginname == null || loginname.trim().length() == 0) {
            return "登录名不能为空";
        }
        if (password == null || password.trim().length() == 0) {
            return "密码不能为空";
        }
        return null;
    }


    /**
     * 进入用户密码修改的页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/mdypwd")
    public String password(Model model) {
        return this.getViewPath("mdypwd");
    }

    /**
     * 进入用户基本信息修改的页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/mdyinfo")
    public String infor(Model model) {
        ManagerInfo loginInfo = this.getManagerInfo();
        User user = userService.getById(loginInfo.getUserId());
        model.addAttribute("user", user);
        return this.getViewPath("mdyinfo");
    }

    /**
     * 密码修改
     *
     * @param oldpassword
     * @param newpassword
     * @param session
     * @return
     */
    @RequestMapping("/mdypwd/save")
    public @ResponseBody
    JsonResult passwordModify(@RequestParam("oldpassword") String oldpassword, @RequestParam("newpassword") String newpassword, HttpSession session) {
        if (StringUtils.isBlank(oldpassword)) return JsonResult.error("修改失败,请输入原密码!");
        if (StringUtils.isBlank(newpassword)) return JsonResult.error("修改失败,请输入新密码!");
        ManagerInfo loginInfo = this.getManagerInfo();
        User loginUser = userService.getById(loginInfo.getUserId());
        if (!loginUser.getPassword().equals(EncryptUtils.MD5Encode(oldpassword))) {
            return JsonResult.error("修改失败,您输入的原密码不正确!");
        } else {
            loginUser.setPassword(EncryptUtils.MD5Encode(newpassword));
            userService.update(loginUser);
            return JsonResult.success("密码修改成功!");
        }
    }

    /**
     * 登录用户基本信息修改
     *
     * @param baseUser
     * @return
     */
    @RequestMapping("/mdyinfo/save")
    public @ResponseBody
    JsonResult personalInfoModify(@ModelAttribute User baseUser) {
        User _baseUser = new User();
        _baseUser = userService.getById(baseUser.getId());
        _baseUser.setUserName(baseUser.getUserName());
        _baseUser.setBirthday(baseUser.getBirthday());
        _baseUser.setMobile(baseUser.getMobile());
        _baseUser.setEmail(baseUser.getEmail());
        _baseUser.setAddress(baseUser.getAddress());
        userService.update(_baseUser);
        return JsonResult.success("保存成功");
    }

}

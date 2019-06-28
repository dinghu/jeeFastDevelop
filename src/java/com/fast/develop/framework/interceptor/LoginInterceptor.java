package com.fast.develop.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.develop.common.Constants;
import com.fast.develop.framework.configuration.ConfigUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fast.develop.framework.web.ManagerInfo;

public class LoginInterceptor implements HandlerInterceptor {

    protected final Logger logger = Logger.getLogger(getClass());

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        ManagerInfo loginInfo = (ManagerInfo) request.getSession().getAttribute(Constants.SESSION_MANAGER_INFO_KEY);
        if (loginInfo == null && handler instanceof HandlerMethod) {
            String login_url = ConfigUtils.get(Constants.CONFIG_MANAGER_LOGIN_URL, request.getContextPath() + "/");
            response.sendRedirect(login_url);
            if (logger.isDebugEnabled()) {
                logger.debug("用户没有登录，重定向到网站根目录！");
            }
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
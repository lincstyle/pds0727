package com.ctdcn.pds.shiro;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.sys.service.ValidateWxMessageService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author zjblague
 *         2015-06-09 13:58.
 */
public class PdsFormAuthenticationFilter extends FormAuthenticationFilter {

    private static Logger log = LogManager.getLogger(PdsFormAuthenticationFilter.class.getName());


    @Autowired
    private ValidateWxMessageService validateWxMessageService;

    @Autowired
    @Qualifier("wxCpService")
    private WxCpServiceImpl wxCpService;

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //@TODO 这个提交并没有修改任何代码，只是为了测试Jenkins svn配置，之后的版本请删除
        //如果不是登录请求
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {

            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            /**
             * 如果启用了记住我，但又不是登录链接，还是执行登录
             */
            Subject subject = getSubject(request, response);
            if((!subject.isAuthenticated() && subject.isRemembered()) || request.getParameter("code") != null){
                return executeLogin(request,response);
            }


            if(isAjax(request, response)){
                return false;
            }
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }


    /**
     * 获取用户的信息创建 token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request,
                                              ServletResponse response) {

        Subject subject = getSubject(request, response);
        String code = request.getParameter("code");

        User user = null;
        String username = getUsername(request);
        String principal = subject.getPrincipal() == null ? "" : subject.getPrincipal().toString();

        log.debug(principal);
        log.debug(username);

        /**
         *
         * 请求中不携带用户名，但又记住密码。
         * 那么就使用密码登录
         * 若请求中携带用户名信息
         * 那么就
         */
        if(!subject.isAuthenticated() && subject.isRemembered() && (username == null || principal.equals(username))){
            user = getUserByRememberMe(principal);
        }else if (com.ctdcn.utils.WebUtils.isWeiXin((HttpServletRequest)request) && code != null){
            user = getUserByWeiXin(code);
        }

        if(user != null ){
            return super.createToken(user.getAccount(), user.getPwd(), true, getHost(request));
        }
        return super.createToken(request,response);

    }
    

    /**
     * 微信登录情况
     */
    User getUserByWeiXin(String code){

        String[] idDev = null;
        User user = null;
        try {
            idDev = wxCpService.oauth2getUserInfo(code);
            user = validateWxMessageService.ValidateWxUser(idDev[0], idDev[1]);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 记住我登录
     */
    User getUserByRememberMe(String account){
        User user = userService.queryUserByAccount(account);
        return user;
    }



    /**
     * 判断是否为ajax请求.
     * 如果是ajax返回http code 401
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    boolean isAjax(ServletRequest request, ServletResponse response) throws IOException{
        HttpServletRequest req = null;

        if(request instanceof HttpServletRequest){
            req = (HttpServletRequest) request;
        }
        if(req != null){
            String ajax = req.getHeader("X-Requested-With");
            if(ajax != null && !ajax.equals("") && ajax.equals("XMLHttpRequest")){
                HttpServletResponse res = null;

                if(response instanceof HttpServletResponse){
                    res = (HttpServletResponse) response;
                }
                if(res != null) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        redirectToSavedRequest(request, response, getSuccessUrl());
    }

    /**
     * Redirects the to the request url from a previously
     * 这里重写只是为了替换 “  # ” 号
     */
    public static void redirectToSavedRequest(ServletRequest request, ServletResponse response, String fallbackUrl)
            throws IOException {
        String successUrl = null;
        boolean contextRelative = true;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
            successUrl = savedRequest.getRequestUrl();
            successUrl = successUrl.replaceAll("\\%23","#");
            contextRelative = false;
        }

        Subject subject = SecurityUtils.getSubject();
        if((!subject.isAuthenticated() && subject.isRemembered()) || request.getParameter("code") != null){
            successUrl = ((HttpServletRequest)request).getRequestURI();
            successUrl = successUrl.replaceAll("\\%23","#");
            contextRelative = false;
        }

        if (successUrl == null) {
            successUrl = fallbackUrl;
        }

        if (successUrl == null) {
            throw new IllegalStateException("Success URL not available via saved request or via the " +
                    "successUrlFallback method parameter. One of these must be non-null for " +
                    "issueSuccessRedirect() to work.");
        }

        WebUtils.issueRedirect(request, response, successUrl, null, contextRelative);
    }
    
}

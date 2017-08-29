package com.ctdcn.pds.sys.controller;

import com.ctdcn.pds.shiro.PdsFormAuthenticationFilter;
import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.utils.MessageUtils;
import com.ctdcn.utils.SpringUtils;
import com.ctdcn.utils.WebUtils;
import com.ctdcn.utils.consts.Sys;
import com.google.common.base.Strings;

import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理未登录前 的一切操作.
 * @author 张靖
 *         2015-06-08 14:26.
 */
@Controller
public class LoginController {

    private static Logger logger = LogManager.getLogger(LoginController.class.getName());

	@Autowired
    @Qualifier("wxCpService")
	private WxCpServiceImpl wxCpService;

    @RequestMapping("/login")
    public String loginForm(HttpServletRequest request, ModelMap model) {

        Subject subject = SecurityUtils.getSubject();
        //如果已经登录，还是访问登录页面，则跳转到登录成功页面
        if(subject != null && subject.isAuthenticated() ){
            String successUrl = SpringUtils.getBean(PdsFormAuthenticationFilter.class).getSuccessUrl();
            return "redirect:"+successUrl;
        }

        /**
         * 如果没有有错误信息将会回写到页面
         */
        String error = String.valueOf(request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME));
        if (!Strings.isNullOrEmpty(error) && !error.equals("null")) {
            model.addAttribute("error", MessageUtils.message(error));
        }

        if(WebUtils.isMobile(request)){
            if(WebUtils.isWeiXin(request)  && request.getParameter("code") == null && request.getParameter("logout") == null){
                //默认使用配置的页面
                String successUrl = SysCodeVo.getCode(Sys.TYPE_CODE, Sys.WEIXIN_LOGIN_REDIRECT_URL).getValue();
                //但如果savedRequest的
                SavedRequest savedRequest = org.apache.shiro.web.util.WebUtils.getSavedRequest(request);
                if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
                    successUrl = SysCodeVo.getCode(Sys.TYPE_CODE, Sys.HOST).getValue() + savedRequest.getRequestUrl();
//                    大家测试微信客户端的时候,可以讲下面的hbctd.com:9000换成自己的地址
//                    successUrl = "http://www.hbctd.com:9000" + savedRequest.getRequestUrl();
                }else{
                    if(savedRequest != null){
                        logger.debug("上次未登录请求方法为"+savedRequest.getMethod());
                    }else{
                        logger.debug("saveRequest对象为空");
                    }
                }
                logger.debug("微信登录，跳转url为:  "+successUrl);

                String url = wxCpService.oauth2buildAuthorizationUrl(successUrl, null);
                return "redirect:"+url;
            }
            return "../mobile/login/index";
        }
        return "login";
    }
    
    @RequestMapping("/logout")
    public String logoutForm(HttpServletRequest request, ModelMap model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
        	subject.logout();
        }
        return "redirect:/login?logout=1";
    }
}

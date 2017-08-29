package com.ctdcn.utils;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.utils.boswer.ClientOsInfo;
import com.ctdcn.utils.boswer.HeaderUtil;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * Web工具
 * @author 张靖
 *         2015-07-27 11:48.
 */
public class WebUtils {

    //                                                     WEIXIN_PATTERN
    static final Pattern WEIXIN_PATTERN = Pattern.compile("micromessenger+",Pattern.CASE_INSENSITIVE);

    /**
     * 转HttpServletRequest.
     * 把继承HttpServletRequest的安全的转换成HttpServletRequest,如果传ll递的对象不能转换，则返回null
     * @param object
     * @return
     */
    public static HttpServletRequest toHttpRequest(Object object){
        if(object instanceof HttpServletRequest){
            return (HttpServletRequest) object;
        }
        return null;
    }

    /**
     * 转HttpServletResponse.
     * 把继承HttpServletResponse的安全的转换成HttpServletResponse,如果传ll递的对象不能转换，则返回null
     * @param object
     * @return
     */
    public static HttpServletResponse toHttpResponse(Object object){
        if(object instanceof HttpServletResponse){
            return (HttpServletResponse) object;
        }
        return null;
    }


    /**
     * 在shiro环境下，判断是手机
     * @param request
     * @return
     */
    public static boolean isMobileOnShiro(HttpServletRequest request){
        ClientOsInfo clientOsInfo = null;
        Object ob = SecurityUtils.getSubject().getSession().getAttribute(User.USER_CLIENT_INFO);
        if(ob != null){
            clientOsInfo = (ClientOsInfo) ob;
            return clientOsInfo.isMobile();
        }else{
            String userAgent = getUserAgent(request);
            clientOsInfo = HeaderUtil.getMobilOS(userAgent);
            SecurityUtils.getSubject().getSession().setAttribute(User.USER_CLIENT_INFO,clientOsInfo);
            return clientOsInfo.isMobile();
        }
    }

    /**
     * 在shiro环境下，判断是手机
     * @param request
     * @return
     */
    public static boolean isMobile(HttpServletRequest request){
        ClientOsInfo clientOsInfo = null;
        String userAgent = getUserAgent(request);
        clientOsInfo = HeaderUtil.getMobilOS(userAgent);
        SecurityUtils.getSubject().getSession().setAttribute(User.USER_CLIENT_INFO,clientOsInfo);
        return clientOsInfo.isMobile();
    }

    public static String getUserAgent(HttpServletRequest request){
        String userAgent=request.getHeader("user-agent");
        if(Strings.isNullOrEmpty(userAgent)){
            userAgent=request.getHeader("User-Agent");
        }
        return  userAgent;
    }
    
    /**
     * 判断是否为微信浏览器
     * @param request
     * @return
     */
    public static boolean isWeiXin(HttpServletRequest request){
    	String ua = getUserAgent(request);
        return  WEIXIN_PATTERN.matcher(ua.toLowerCase()).find();
    }
    


}

package com.ctdcn.utils.spring.web.method;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.utils.spring.web.bind.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 获取当前用户的解析器
 * @author 张靖
 *         2015-06-09 14:18.
 */
public class CurrentMethod implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        CurrentUser currentUserAnnotation = methodParameter.getParameterAnnotation(CurrentUser.class);
        return SecurityUtils.getSubject().getSession().getAttribute(User.USER_SESSIN_KEY);
    }
}

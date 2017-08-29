package com.ctdcn.utils.spring.web.bind;

import com.ctdcn.pds.organization.model.User;

import java.lang.annotation.*;

/**
 * 获取当前登录的用户
 * @author 张靖
 *         2015-06-09 14:15.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字 默认{@link User#USER_SESSIN_KEY}
     *
     * @return
     */
    String value() default User.USER_SESSIN_KEY;

}

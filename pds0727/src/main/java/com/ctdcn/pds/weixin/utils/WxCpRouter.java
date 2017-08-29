package com.ctdcn.pds.weixin.utils;

import me.chanjar.weixin.common.api.WxConsts;

import java.lang.annotation.*;

/**
 * 微信路由类注册
 * @author 张靖
 *         2015-07-14 9:16.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WxCpRouter {

    /**
     * 设置是否异步执行，默认是true
     * @return
     */
    boolean async() default false;

    String fromUser() default "";

    /**
     * 如果msgType等于某值
     * @return
     */
    String msgType() default WxConsts.XML_MSG_EVENT;

    /**
     * 如果event等于某值
     * @return
     */
    String event() default "";

    /**
     * 如果eventKey等于某值
     * @return
     */
    String eventKey() default "";

    /**
     * 如果content等于某值
     * @return
     */
    String content() default "";

    /**
     * 如果content匹配该正则表达式
     * @return
     */
    String rContent() default "";

    /**
     * 如果agentId匹配,默认为-1在转换时，要注意转成null
     * @return
     */
    int agentId() default -1;
    
}

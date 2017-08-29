package com.ctdcn.pds.weixin.utils;

import com.ctdcn.pds.weixin.service.router.AbstractWxCpMessageHandler;
import com.ctdcn.utils.SpringUtils;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpMessageRouterRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.util.Set;

/**
 * 扫描微信处理Router实现
 *
 * @author 张靖
 *         2015-07-14 9:13.
 */
public class WxCpRouterScanner {

    @javax.annotation.Resource
    WxCpMessageRouter wxCpMessageRouter;

    private static Logger logger = LogManager.getLogger(WxCpRouterScanner.class.getName());

    private String[] basePackages;

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public void init() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        if (basePackages != null) {
            for (String basePackage : basePackages) {
                basePackage = basePackage.replaceAll("\\.", "/");
                Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);
                for (BeanDefinition beanDefinition : beanDefinitions) {
                    Class clazz = Class.forName(beanDefinition.getBeanClassName());
                    if (clazz.getSuperclass() == AbstractWxCpMessageHandler.class){
                        AbstractWxCpMessageHandler handler = (AbstractWxCpMessageHandler) SpringUtils.getBean(clazz);
                        WxCpMessageRouterRule routerRule = wxCpMessageRouter.rule();

                        routerRule.setAsync(handler.isAsync());
                        if(handler.getFromUser()    != null){routerRule.setFromUser(handler.getFromUser());}
                        if(handler.getMsgType()     != null){routerRule.setMsgType( handler.getMsgType());}
                        if(handler.getEvent()       != null){routerRule.setEvent(   handler.getEvent());}
                        if(handler.getEventKey()    != null){routerRule.setEventKey(handler.getEventKey());}
                        if(handler.getContent()     != null){routerRule.setContent( handler.getContent());}
                        if(handler.getrContent()    != null){routerRule.setrContent(handler.getrContent());}
                        if(handler.getAgentId()     != null){routerRule.setAgentId( handler.getAgentId());}

                        routerRule.handler(handler);
                        logger.debug("加载微信消息路由{}", clazz.getName());
                    }
                }
            }
        }
    }


}

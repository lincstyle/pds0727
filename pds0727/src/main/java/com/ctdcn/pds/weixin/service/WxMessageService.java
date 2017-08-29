package com.ctdcn.pds.weixin.service;

import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.pds.weixin.utils.WxSendLogtaskExecutor;
import com.ctdcn.utils.StringUtils;
import com.ctdcn.utils.consts.Sys;
import com.ctdcn.utils.consts.WeixinMessage;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信操作消息类.
 * @author 张靖
 *         2015-06-17 9:26.
 */
@Service
public class WxMessageService {

    @Resource
    WxCpConfigStorage wxCpConfigStorage;

    @Resource
    WxCpServiceImpl wxCpService;

    /**
     * 发送text消息.
     * @param content 发送内容
     * @param departIds 发送部门
     * @param userIds 发送的用户
     * @throws WxErrorException
     */
    public void sendText(String content,Integer[] departIds,String[] userIds) throws WxErrorException {
        WxCpMessage message = new WxCpMessage();

        String toPart = "";
        if(departIds != null && departIds.length != 0){
        for(Integer departId : departIds){
            toPart += departId+"|";
        }
        toPart = toPart.substring(0,toPart.length()-1);
        }

        String toUser = "";
        if(userIds != null && userIds.length != 0){
        for(String userId : userIds){
            toUser += userId+"|";
        }
        toUser = toUser.substring(0,toUser.length()-1);
        }

        message.setToParty(toPart);
        message.setAgentId(wxCpConfigStorage.getAgentId());
        message.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
        message.setContent(content);
        message.setToUser(toUser);

        wxCpService.messageSend(message);
    }

    /**
     * 发送项目更新通知
     * @param projectLog 项目日志对象
     * @param departIds 部门ID集合 可为空
     * @param userIds   用户集合
     * @throws WxErrorException
     */
    public void sendProjectLog(ProjectLog projectLog,Integer[] departIds,String[] userIds) throws WxErrorException{

    	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
    	ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor)wac.getBean("taskExecutor"); 
    	
        WxCpMessage.WxArticle article = new WxCpMessage.WxArticle();
        String host = SysCodeVo.getCode(Sys.TYPE_CODE, Sys.HOST).getValue();
        String url = SysCodeVo.getCode(WeixinMessage.TYPE_CODE,WeixinMessage.PROJECT_LOG_DETAIL_URL).getValue();
        String title = SysCodeVo.getCode(WeixinMessage.TYPE_CODE,WeixinMessage.SEND_PROJECT_LOG_TITLE).getValue();
        String agentId = SysCodeVo.getCode(WeixinMessage.TYPE_CODE,WeixinMessage.PROJECT_AGENT_ID).getValue();
        String message = SysCodeVo.getCode(WeixinMessage.TYPE_CODE,WeixinMessage.SEND_PROJECT_LOG).getValue();

        title = String.format(title,projectLog.getPname(),projectLog.getPerson());
        url = String.format(url,projectLog.getLid());
        /**
         * %1$s %2$tF %3$tT 更新了项目：%4$s 更新内容：%5$s
         */
        message = String.format(message,projectLog.getPerson(),projectLog.getCdate(),projectLog.getCdate(),projectLog.getPname(),projectLog.getDetail());

        article.setUrl(host + url);
        article.setDescription(message);
        article.setTitle(title);

        WxCpMessage wxCpMessage = new WxCpMessage();

        wxCpMessage.setMsgType(WxConsts.CUSTOM_MSG_NEWS);
        List<WxCpMessage.WxArticle> list = new ArrayList<WxCpMessage.WxArticle>();
        list.add(article);
        wxCpMessage.setArticles(list);

        wxCpMessage.setAgentId(agentId);
        wxCpMessage.setToParty(StringUtils.concatStringBy("|", departIds));
        wxCpMessage.setToUser(StringUtils.concatStringBy("|", userIds));

        //wxCpService.messageSend(wxCpMessage);
        taskExecutor.execute(new WxSendLogtaskExecutor(wxCpMessage,projectLog));//采用非阻塞式线程池方式推送微信日志

    }


}

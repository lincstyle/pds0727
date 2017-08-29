package com.ctdcn.pds.weixin.service.router;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;

/**
 * @author 张靖
 *         2015-07-14 14:23.
 */
public abstract class AbstractWxCpMessageHandler implements WxCpMessageHandler {

    protected boolean async = true;

    protected String fromUser;

    protected String msgType = WxConsts.XML_MSG_EVENT;

    protected String event;

    protected String eventKey;

    protected String content;

    protected String rContent;

    protected Integer agentId;


    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

}

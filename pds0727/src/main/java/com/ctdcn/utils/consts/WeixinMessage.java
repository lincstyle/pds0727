package com.ctdcn.utils.consts;

/**
 * @author 张靖
 *         2015-07-30 14:20.
 */
public interface WeixinMessage {

    /**
     * 微信消息
     */
    String TYPE_CODE = "weixin_message";

    /**
     * 项目详细内容地址
     */
    int PROJECT_LOG_DETAIL_URL = 1;
    /**
     *  发送更新日报项目
     */
    int SEND_PROJECT_LOG = 2;

    /**
     *  发送更新日报项目标题
     */
    int SEND_PROJECT_LOG_TITLE = 3;

    /**
     *  发送更新日报的微信应用Id
     */
    int PROJECT_AGENT_ID = 4;

}

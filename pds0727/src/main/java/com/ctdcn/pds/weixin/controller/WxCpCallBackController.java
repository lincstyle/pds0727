package com.ctdcn.pds.weixin.controller;

import com.google.common.base.Strings;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;


/**
 * 微信回调入口
 * @author 张靖
 *         2015-07-13 15:36.
 */
@Controller
@RequestMapping("/wx")
public class WxCpCallBackController {

    @Resource
    WxCpConfigStorage wxCpConfigStorage;

    @Resource
    WxCpServiceImpl wxCpService;

    @Resource
    WxCpMessageRouter wxCpMessageRouter;

    @ResponseBody
    @RequestMapping("/callback")
    public String callBack(String msg_signature,String timestamp,String nonce,String echostr,HttpServletRequest request) throws IOException {

        /**
         * 判断echostr是否为空
         * 为空
         *      则说明是一般用户消息
         * 不为空则是验证签名
         */
        if(Strings.isNullOrEmpty(echostr)){
            InputStream in = request.getInputStream();
            if(in.read() == -1){
                return "非法请求";
            }
            WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(), wxCpConfigStorage, timestamp, nonce, msg_signature);
            WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);
            return outMessage.toEncryptedXml(wxCpConfigStorage);
        }else{
            if(!wxCpService.checkSignature(msg_signature,timestamp,nonce,echostr)){
                WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
                String plainText = cryptUtil.decrypt(echostr);
                return plainText;
            }
        }
        return "非法请求";
    }

}

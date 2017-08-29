package com.ctdcn.pds.weixin.service.router;

import com.ctdcn.pds.organization.service.UserService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 张靖
 *         2015-07-14 15:59.
 */
@Component
public class SubscribeRoute extends AbstractWxCpMessageHandler {

    @Resource
    UserService userService;

    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) throws WxErrorException {

        return null;
    }

    public SubscribeRoute() {
        this.event = WxConsts.EVT_SUBSCRIBE;
    }
}

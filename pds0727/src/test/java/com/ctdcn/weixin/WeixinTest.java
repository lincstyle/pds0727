package com.ctdcn.weixin;


import com.ctdcn.pds.sys.service.ValidateWxMessageService;
import com.ctdcn.pds.weixin.service.WxMessageService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * @author 张靖
 *         2015-06-19 16:04.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class WeixinTest {

    @Autowired
    private WxMessageService wxMessageService;
    @Autowired
    @Qualifier("wxCpService")
    private WxCpServiceImpl wxCpService;
    @Autowired
    private ValidateWxMessageService validateWxMessageService;

    public void sendProjectLogTest() throws WxErrorException
    {
        validateWxMessageService.ValidateWxMessageUser();
    }
}

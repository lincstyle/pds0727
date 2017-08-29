package com.ctdcn.pds.organization.controller;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.sys.model.JsonBean;
import com.ctdcn.pds.sys.service.ValidateWxMessageService;

@Controller
@RequestMapping("/syncUserDep")
public class SyncUserDepController {
	
	@Autowired
    private ValidateWxMessageService validateWxMessageService;

    @ResponseBody
    @RequestMapping("/userAndDeaprtment")
    public JsonBean validateWxMessage() {
        JsonBean bean = new JsonBean();
        try {
            validateWxMessageService.ValidateWxDepartMent();
            validateWxMessageService.ValidateWxMessageUser();
            bean.setSuccess(true);
            bean.setMsg("同步成功");
        } catch (WxErrorException e) {
            e.printStackTrace();
            bean.setSuccess(false);
            bean.setMsg(e.getMessage());
        } catch (Exception e){
        	e.printStackTrace();
        	bean.setSuccess(false);
            bean.setMsg(e.getMessage());
        }
        return  bean;
    }


}

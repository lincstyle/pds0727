package com.ctdcn.pds.Handler;

import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.utils.consts.WeixinError;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Triumphant on 2015/7/30.
 */
public class MyExceptionHandler implements HandlerExceptionResolver
{
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex)
    {

        Map<String, Object> model = new HashMap<String, Object>();
        // 根据不同错误转向不同页面
        if(ex instanceof WxErrorException) {
            WxErrorException e =(WxErrorException)ex;
            int errorCode = e.getError().getErrorCode();
            model.put("errorMsg", SysCodeVo.getCode(WeixinError.TYPE_CODE, errorCode).getValue());
            return new ModelAndView("weixinError", model);
        } else {
            ex.printStackTrace();
            model.put("errorMsg", ex.getMessage());
            return new ModelAndView("weixinError", model);
        }

    }
}

package com.ctdcn.pds.weixin.service;

import com.ctdcn.pds.organization.model.User;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 张靖
 *         2015-06-18 10:19.
 */
@Service
public class WxUserService {

    @Resource
    WxCpConfigStorage wxCpConfigStorage;

    @Resource
    WxCpServiceImpl wxCpService;

    /**
     * 添加用户.
     * @param user
     * @throws WxErrorException
     */
    public void addUser(User user) throws WxErrorException {
        wxCpService.userCreate(user(user));
    }

    /**
     * 根据登录帐号删除用户.
     * @param userAccount
     * @throws WxErrorException
     */
    public void deleteUser(String userAccount) throws WxErrorException {
        wxCpService.userDelete(userAccount);
    }

    /**
     * 批量根据用户帐号删除用户.
     * @param userAccounts
     * @throws WxErrorException
     */
    public  void batchDeleteUser(String[] userAccounts) throws WxErrorException {
        wxCpService.userDelete(userAccounts);
    }

    /**
     * 更改用户信息.
     * @param user
     * @throws WxErrorException
     */
    public void editUser(User user) throws WxErrorException {
        wxCpService.userUpdate(user(user));
    }


    /**
     * User转WxCpUser转换器
     * @param user
     * @return
     */
    public static  WxCpUser user(User user){
        WxCpUser wxCpUser = new WxCpUser();
        wxCpUser.setName(user.getName());
        wxCpUser.setDepartIds(new Integer[]{user.getDepartmentId()});
        wxCpUser.setEmail(user.getEmail());
        wxCpUser.setGender(String.valueOf(user.getGender()));
        wxCpUser.setMobile(user.getTel());
        wxCpUser.setWeiXinId(user.getWeixin());
        wxCpUser.setUserId(user.getAccount());
        return wxCpUser;
    }


    /**
     * WxCpUser转User转换器
     * @param
     * @return
     */


    public static  User user(WxCpUser  wxCpUser){

        User user =new User();
        user.setName(wxCpUser.getName());
        user.setDepartmentId(wxCpUser.getDepartIds()[0]);
        user.setEmail(wxCpUser.getEmail());

        if("男".equals(wxCpUser.getGender()))
        {
            user.setGender(1);
        }else
        {
            user.setGender(0);
        }
        user.setTel(wxCpUser.getMobile());
        user.setWeixin(wxCpUser.getWeiXinId());
        user.setAccount(wxCpUser.getUserId());
        user.setImgUrl(wxCpUser.getAvatar());
        return user;
    }



}

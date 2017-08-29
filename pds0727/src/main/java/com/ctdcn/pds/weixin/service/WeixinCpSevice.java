package com.ctdcn.pds.weixin.service;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpTag;
import me.chanjar.weixin.cp.bean.WxCpUser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 张靖
 *         2015-09-23 16:55.
 */
public class WeixinCpSevice implements WxCpService {
    public boolean checkSignature(String msgSignature, String timestamp, String nonce, String data) {
        return false;
    }

    public void userAuthenticated(String userId) throws WxErrorException {

    }

    public String getAccessToken() throws WxErrorException {
        return null;
    }

    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        return null;
    }

    public String getJsapiTicket() throws WxErrorException {
        return null;
    }

    public String getJsapiTicket(boolean forceRefresh) throws WxErrorException {
        return null;
    }

    public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException {
        return null;
    }

    public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException {
        return null;
    }

    public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
        return null;
    }

    public File mediaDownload(String media_id) throws WxErrorException {
        return null;
    }

    public void messageSend(WxCpMessage message) throws WxErrorException {

    }

    public void menuCreate(WxMenu menu) throws WxErrorException {

    }

    public void menuCreate(String agentId, WxMenu menu) throws WxErrorException {

    }

    public void menuDelete() throws WxErrorException {

    }

    public void menuDelete(String agentId) throws WxErrorException {

    }

    public WxMenu menuGet() throws WxErrorException {
        return null;
    }

    public WxMenu menuGet(String agentId) throws WxErrorException {
        return null;
    }

    public Integer departCreate(WxCpDepart depart) throws WxErrorException {
        return null;
    }

    public List<WxCpDepart> departGet() throws WxErrorException {
        return null;
    }

    public void departUpdate(WxCpDepart group) throws WxErrorException {

    }

    public void departDelete(Integer departId) throws WxErrorException {

    }

    public List<WxCpUser> userList(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
        return null;
    }

    public List<WxCpUser> departGetUsers(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
        return null;
    }

    public void userCreate(WxCpUser user) throws WxErrorException {

    }

    public void userUpdate(WxCpUser user) throws WxErrorException {

    }

    public void userDelete(String userid) throws WxErrorException {

    }

    public void userDelete(String[] userids) throws WxErrorException {

    }

    public WxCpUser userGet(String userid) throws WxErrorException {
        return null;
    }

    public String tagCreate(String tagName) throws WxErrorException {
        return null;
    }

    public void tagUpdate(String tagId, String tagName) throws WxErrorException {

    }

    public void tagDelete(String tagId) throws WxErrorException {

    }

    public List<WxCpTag> tagGet() throws WxErrorException {
        return null;
    }

    public List<WxCpUser> tagGetUsers(String tagId) throws WxErrorException {
        return null;
    }

    public void tagAddUsers(String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException {

    }

    public String oauth2buildAuthorizationUrl(String redirectUri, String state) {
        return null;
    }

    public String[] oauth2getUserInfo(String code) throws WxErrorException {
        return new String[0];
    }

    public String[] oauth2getUserInfo(String agentId, String code) throws WxErrorException {
        return new String[0];
    }

    public void tagRemoveUsers(String tagId, List<String> userIds) throws WxErrorException {

    }

    public int invite(String userId, String inviteTips) throws WxErrorException {
        return 0;
    }

    public String[] getCallbackIp() throws WxErrorException {
        return new String[0];
    }

    public String get(String url, String queryParam) throws WxErrorException {
        return null;
    }

    public String post(String url, String postData) throws WxErrorException {
        return null;
    }

    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        return null;
    }

    public void setWxCpConfigStorage(WxCpConfigStorage wxConfigProvider) {

    }

    public void setRetrySleepMillis(int retrySleepMillis) {

    }

    public void setMaxRetryTimes(int maxRetryTimes) {

    }

    public WxSession getSession(String id) {
        return null;
    }

    public WxSession getSession(String id, boolean create) {
        return null;
    }

    public void setSessionManager(WxSessionManager sessionManager) {

    }
}

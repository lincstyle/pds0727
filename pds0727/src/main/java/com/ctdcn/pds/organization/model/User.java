package com.ctdcn.pds.organization.model;

import com.ctdcn.pds.authority.model.Role;

import java.sql.Date;

/**
 * 用户类
 *
 * @author 张靖 on 2015-6-3 10:50:14
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 */
/**
 * @author Administrator
 *
 */

/**
 * @author Administrator
 *
 */
public class User {
    /**
     * session中存放用户的Key
     */
    public static final String USER_SESSIN_KEY = "currentUser";
    /**
     * session中存放用户的客户端信息
     */
    public static final String USER_CLIENT_INFO = "clientInfo";

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 账号
     */
    private String account;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别.
     * 1男，2女
     */
    private Integer gender;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 微信
     */
    private String weixin;
    /**
     * 部门ID
     */
    private Integer departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     *  角色ID
     */
    private Integer role;
    /**
     * 上一次登陆设备ID
     */
    private String deviceId;
    /**
     *  最后修改密码时间
     */
    private Date lastpwddate;
    /**
     * 最后登陆时间
     */
    private Date lastlogdate;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 是否使用
     */
    private Integer isUsed;
    /**
     * 个人图像
     */
    private String imgUrl;
    /**
     *名字全拼
     */
    private String quanpin;
    /**
     *名字简拼
     */
    private String jianpin;

    /**
     * ---------------------------- 以下是不与数据库相关的  -----------------------------------------------------------
     */
    private Role roleOb;
    private Integer pid;
    private Integer puid;
    private Integer isResponse;

    public Role getRoleOb() {
        return roleOb;
    }

    public void setRoleOb(Role roleOb) {
        this.roleOb = roleOb;
    }

    public Integer getPid(){
        return pid;
    }

    public void setPid(Integer pid){
        this.pid = pid;
    }

    public Integer getPuid(){ return puid; }

    public void setPuid(Integer puid) { this.puid = puid; }

    public String getQuanpin() {
        return quanpin;
    }

    public void setQuanpin(String quanpin) {
        this.quanpin = quanpin;
    }

    public String getJianpin() {
        return jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getLastpwddate() {
        return lastpwddate;
    }

    public void setLastpwddate(Date lastpwddate) {
        this.lastpwddate = lastpwddate;
    }

    public Date getLastlogdate() {
        return lastlogdate;
    }

    public void setLastlogdate(Date lastlogdate) {
        this.lastlogdate = lastlogdate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getIsResponse() {
        return isResponse;
    }

    public void setIsResponse(Integer isResponse) {
        this.isResponse = isResponse;
    }


}

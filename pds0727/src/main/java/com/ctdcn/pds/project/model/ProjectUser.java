package com.ctdcn.pds.project.model;

/**
 * Created by Triumphant on 2015/6/18.
 *
 * 项目人员关系表
 */
public class ProjectUser
{
    private Integer pid; //项目ｉｄ
    private Integer userid;//人员ｉｄ
    private Integer departmentid;
    private Integer isreceive; //是否接受消息　　０接受　１不接受
    private Integer puid;//关系表ｉｄ
    private  String pname;//项目名
    private  String username;//用户名
    private Integer isResponse;//是否为负责人
//-------------不与数据库字段一致-------------
    private Integer[] pidArr;//项目多个人的userid
    private Integer[] puidArr;//项目多个人的项目关系id
    private String account;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getIsreceive() {
        return isreceive;
    }

    public void setIsreceive(Integer isreceive) {
        this.isreceive = isreceive;
    }

    public Integer getPuid() {
        return puid;
    }

    public void setPuid(Integer puid) {
        this.puid = puid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIsResponse() {
        return isResponse;
    }

    public void setIsResponse(Integer isResponse) {    this.isResponse = isResponse;    }

    public Integer[] getPuidArr() {
        return puidArr;
    }

    public void setPuidArr(Integer[] puidArr) {
        this.puidArr = puidArr;
    }

    public Integer[] getPidArr() {
        return pidArr;
    }

    public void setPidArr(Integer[] pidArr) {
        this.pidArr = pidArr;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

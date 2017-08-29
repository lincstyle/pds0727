package com.ctdcn.pds.project.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Triumphant on 2015/6/10.
 */
public class Project
{
    private String pname;
    private Integer pflag;
    private String pintro;
    private String person;
    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    private Date pdate;
    private String lastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastDate;
    private String pstage; //项目阶段  编码
    private String selectSDate;//浏览器选择的项目开始时间
    private String selectEDate;//浏览器选择的项目终止时间

    private String lastSDate;//浏览器选择的 最后更新的  开始时间
    private String lastEDate;//浏览器选择的 最后更新的  终止时间
    private Integer userId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private  Date delDate;//删除项目时间
    private Integer pid;//项目id
    private Integer isdelete;//是否删除 0：正常 1：正常
 
	
	
	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPflag() {
        return pflag;
    }

    public void setPflag(Integer pflag) {
        this.pflag = pflag;
    }

    public String getPintro() {
        return pintro;
    }

    public void setPintro(String pintro) {
        this.pintro = pintro;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

	public String getLastPerson() {
        return lastPerson;
    }

    public void setLastPerson(String lastPerson) {
        this.lastPerson = lastPerson;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }


    public String getSelectSDate() {
        return selectSDate;
    }

    public void setSelectSDate(String selectSDate) {
        this.selectSDate = selectSDate;
    }

    public String getSelectEDate() {
        return selectEDate;
    }

    public void setSelectEDate(String selectEDate) {
        this.selectEDate = selectEDate;
    }

    public String getLastSDate() {
        return lastSDate;
    }

    public void setLastSDate(String lastSDate) {
        this.lastSDate = lastSDate;
    }

    public String getLastEDate() {
        return lastEDate;
    }

    public void setLastEDate(String lastEDate) {
        this.lastEDate = lastEDate;
    }

    public String getPstage() {
        return pstage;
    }

    public void setPstage(String pstage) {
        this.pstage = pstage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

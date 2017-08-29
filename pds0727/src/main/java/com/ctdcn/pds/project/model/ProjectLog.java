package com.ctdcn.pds.project.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * Created by Triumphant on 2015/6/10.
 */
public class ProjectLog
{
    private Integer lid;//日志记录ID
    private String pname; //项目名称
    private String person;  //更新人
    private String detail;  //内容
    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    private Date sdate;  //开始时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date cdate;  //提交时间
    private String pstage;//阶段
    private Integer pflag ;//阶段码
    private String  contents;//详细内容
    private Integer pid ;//项目ID
    private Integer userId;

    /**

     * 是否删除 0 正常 1 删除
     */
    private Integer isdelete;
    /**

     * 是否发送成功 0 失败 1 成功
     */
    private Integer isSuccess;
    private int[] updatePerIdArr;//浏览器选择更新人的ID
    private String selectSDate;//浏览器选择的时间
    private String selectEDate;//浏览器选择的终止时间

    public String getPname() {
        return pname;
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

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }


    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getPstage() {
        return pstage;
    }

    public void setPstage(String pstage) {
        this.pstage = pstage;
    }
    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Integer getPflag() {
        return pflag;
    }

    public void setPflag(Integer pflag) {
        this.pflag = pflag;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int[] getUpdatePerIdArr() {
        return updatePerIdArr;
    }

    public void setUpdatePerIdArr(int[] updatePerIdArr) {
        this.updatePerIdArr = updatePerIdArr;
    }

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
}

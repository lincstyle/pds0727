package com.ctdcn.pds.project.model;

/**
 * Created by Triumphant on 2015/6/15.
 */
public class StageCode
{
    private String type; //项目类型
    private String mc;  //编码名称
    private Integer typecode;  //编码
    private Integer cid;//编码ID
    private Integer pid;//项目ID

//----------------------下面的属性不与数据库相关-------------------------
    private Integer pflag;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getTypecode() {
        return typecode;
    }

    public void setTypecode(Integer typecode) {
        this.typecode = typecode;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPflag() {
        return pflag;
    }

    public void setPflag(Integer pflag) {
        this.pflag = pflag;
    }
}

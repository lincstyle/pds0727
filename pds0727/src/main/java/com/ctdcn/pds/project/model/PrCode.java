package com.ctdcn.pds.project.model;

/**
 * Created by Triumphant on 2015/6/30.
 */
public class PrCode
{
    private String mc;//名称
    private String type; //类型
    private Integer typecode; //编码
    private Integer pid; //项目id
    private Integer cid; //编码ID


    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getTypeCode() {
        return typecode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typecode = typeCode;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}

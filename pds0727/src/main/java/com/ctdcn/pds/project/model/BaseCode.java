package com.ctdcn.pds.project.model;

/**
 * Created by Triumphant on 2015/7/6.
 * 基本项目阶段编码
 */
public class BaseCode
{

    private String mc;


    private Integer typeCode;
    private Integer serial;//序列号

    public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }
}

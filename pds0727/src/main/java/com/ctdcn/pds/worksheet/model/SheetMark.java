package com.ctdcn.pds.worksheet.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SheetMark {
	private Integer id;	//备注ID
	private Integer sheetId;	//联络单ID
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date sdate;	//备注时间
	private Integer sendId;		//备注人ID
	private String sendName;		//备注人
	private String mark;		//备注内容
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSheetId() {
		return sheetId;
	}
	public void setSheetId(Integer sheetId) {
		this.sheetId = sheetId;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Integer getSendId() {
		return sendId;
	}
	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
}

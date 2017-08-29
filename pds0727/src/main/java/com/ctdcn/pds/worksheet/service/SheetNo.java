package com.ctdcn.pds.worksheet.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ctdcn.pds.worksheet.dao.SheetDao;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



public class SheetNo {
	//获得最大值的dao对象

	public SheetDao sheetDao;
	private  String no =null;
	private SheetNo(SheetDao sheetDao){
		//调用dao方法的获得最大编号
		String maxNo = sheetDao.maxNo();
		//赋值给计数器
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date);
		if(maxNo==null || maxNo.equals("")){
			maxNo = today+"000";
		}
		if(today.equals(maxNo.substring(0, 8))){
			no = maxNo;
		}else{
			no = today+"000";
		}
	}

	public  String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	/**
	 * 生成下一个编号
	 */
	public synchronized String nextNo(String no) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date);
		if(today.equals(no.substring(0, 8))==false){
			no = today+"000";
		}
		if(Integer.parseInt(no.substring(8, 11))<998){
			DecimalFormat df = new DecimalFormat("000");
			String nno = no.substring(0,8)+ df.format(1 + Integer.parseInt(no.substring(8, 11)));
			return nno;
		}else{
			return no.substring(0,8)+"999";
		}
	}


}

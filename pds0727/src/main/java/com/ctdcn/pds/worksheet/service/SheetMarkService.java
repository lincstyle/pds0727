package com.ctdcn.pds.worksheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctdcn.pds.worksheet.dao.SheetMarkDao;
import com.ctdcn.pds.worksheet.model.SheetMark;

@Service
public class SheetMarkService {
	@Autowired
	public SheetMarkDao sheetMarkDao;
	
	/**
	 * 查看联络单备注
	 * @param sheetId
	 * @return
	 */
	public List<SheetMark> queryMark(SheetMark sheetMark){
		return sheetMarkDao.queryMark(sheetMark);
	}
	
	/**
	 * 新增联络单备注
	 * @param sheetMark
	 */
	public void addMark(SheetMark sheetMark){
		sheetMarkDao.addMark(sheetMark);
	}
	
	/**
	 * 修改联络单备注
	 * @param sheetMark
	 */
	public void updateMark(SheetMark sheetMark){
		sheetMarkDao.updateMark(sheetMark);
	}
	
	public void deleteMark(SheetMark sheetMark){
		sheetMarkDao.deleteMark(sheetMark);
	}
}

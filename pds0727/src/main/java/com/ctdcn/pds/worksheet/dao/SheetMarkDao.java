package com.ctdcn.pds.worksheet.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ctdcn.pds.worksheet.model.SheetMark;

@Repository
public class SheetMarkDao {
	public static final String SHEET_MAPPER= "worksheet.mark";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 获取联络单备注
	 * @return
	 */
	public List<SheetMark> queryMark(SheetMark sheetMark){
		return sqlSessionTemplate.selectList(SHEET_MAPPER +".querymark",sheetMark);
	}
	
	/**
	 * 新增联络单备注
	 * @param sheetMark
	 */
	public  void addMark(SheetMark sheetMark){
		sqlSessionTemplate.insert(SHEET_MAPPER +".addmark", sheetMark);
	}
	
	/**
	 * 修改联络单备注
	 * @param sheetMark
	 */
	public  void updateMark(SheetMark sheetMark){
		sqlSessionTemplate.update(SHEET_MAPPER +".updatemark", sheetMark);
	}
	
	/**
	 * 删除联络单备注
	 * @param id
	 */
	public void deleteMark(SheetMark sheetMark){
		sqlSessionTemplate.delete(SHEET_MAPPER +".deletemark", sheetMark);
	}
}

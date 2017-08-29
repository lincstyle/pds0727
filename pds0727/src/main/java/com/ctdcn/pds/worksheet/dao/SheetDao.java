package com.ctdcn.pds.worksheet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ctdcn.pds.worksheet.model.Sheet;


@Repository
public class SheetDao {
 
	public static final String SHEET_MAPPER= "worksheet.manage";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 获取联络单列表
	 * @return
	 */
	public List<Sheet> sheetList(Map map,RowBounds rowBounds){
		return sqlSessionTemplate.selectList(SHEET_MAPPER+".sheetList",map,rowBounds);
	}
	
	/**
	 * 查询联络单详情
	 * @param id
	 * @return
	 */
	public Sheet querySheet(Integer id){
		return sqlSessionTemplate.selectOne(SHEET_MAPPER+".querySheet", id);
	}
	
	/**
	 * 根据查询条件查询联络单条数
	 * @return
	 */
	public List<Sheet> countSheet(Map map){
		return sqlSessionTemplate.selectList(SHEET_MAPPER+".sheetList",map);
	}
	
	/**
	 * 新增联络单
	 * @param sheet
	 */
	public void addSheet(Sheet sheet){
		sqlSessionTemplate.insert(SHEET_MAPPER+".addSheet", sheet);
	}
	
	/**
	 * 联络单审核、分发、处理
	 * @param sheet
	 */
	public void updateSheet(Sheet sheet){
		sqlSessionTemplate.update(SHEET_MAPPER+".updateSheet", sheet);
	}
	
	/**
	 * 删除联络单
	 * @param id
	 */
	public void deleteSheet(Integer id){
		sqlSessionTemplate.delete(SHEET_MAPPER+".deleteSheet", id);
	}
	
	/**
	 * 查询最大联络单号
	 * @return
	 */
	public String maxNo(){
		return sqlSessionTemplate.selectOne(SHEET_MAPPER+".maxNo");
	}


}
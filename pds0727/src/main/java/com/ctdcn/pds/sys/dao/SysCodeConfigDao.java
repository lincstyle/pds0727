package com.ctdcn.pds.sys.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ctdcn.pds.sys.model.SysCodeConfig;

@Repository
public class SysCodeConfigDao {

	public static final String NAME_SAPCE = "sys.syscode";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	//模糊查询、显示所有数据字典数据
	public List<SysCodeConfig> findSysCode(SysCodeConfig sysCodeConfig, RowBounds rowBounds){
		return sqlSessionTemplate.selectList(NAME_SAPCE + ".findSysCode", sysCodeConfig, rowBounds);
	}

	//得到所有的数据字典数据的数量
	public Integer getSysCodeTotal(SysCodeConfig sysCodeConfig) {
		return sqlSessionTemplate.selectOne(NAME_SAPCE + ".findSysCodeTotal", sysCodeConfig);
	}

	//删除数据字典数据
	public void delSysCode(String delSysCodeId) {
		sqlSessionTemplate.delete(NAME_SAPCE + ".delSysCode", delSysCodeId);
	}

	//添加数据字典数据
	public void addSysCode(SysCodeConfig sysCodeConfig) {
		sqlSessionTemplate.insert(NAME_SAPCE + ".addSysCode", sysCodeConfig);
	}
	
	//修改数据字典数据
	public void editSysCode(SysCodeConfig sysCodeConfig){
		sqlSessionTemplate.update(NAME_SAPCE + ".editSysCode", sysCodeConfig);
	}

	//根据ID得到对应的数据
	public SysCodeConfig getSysCodeById(int editCodeId) {
		return sqlSessionTemplate.selectOne(NAME_SAPCE + ".getSysCodeById", editCodeId);
	}
	
	//查询所有的数据，不分页
	public List<SysCodeConfig> getSysCodeList(){
		return sqlSessionTemplate.selectList(NAME_SAPCE + ".getSysCodeList");
	}
	
	//得到下一个sequence ID
	public Integer getSequenceId(){
		return sqlSessionTemplate.selectOne(NAME_SAPCE + ".getCodeSequenceId");
	}
	
}

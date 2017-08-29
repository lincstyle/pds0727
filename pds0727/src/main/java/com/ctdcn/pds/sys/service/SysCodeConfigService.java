package com.ctdcn.pds.sys.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctdcn.pds.sys.dao.SysCodeConfigDao;
import com.ctdcn.pds.sys.model.SysCodeConfig;
import com.ctdcn.pds.sys.model.SysCodeVo;

@Service
public class SysCodeConfigService {
	
	@Autowired
	private SysCodeConfigDao sysCodeConfigDao;

	/**
	 * 根据条件查询数据字典数据，并分页显示
	 * @param sysCodeConfig
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<SysCodeConfig> findSysCode(SysCodeConfig sysCodeConfig, int currentPage, int pageSize){
		int start = (currentPage-1)*pageSize;// 开始条数
		int limit = pageSize;//查询跨度  
		RowBounds rowBounds = new RowBounds(start,limit);
		return sysCodeConfigDao.findSysCode(sysCodeConfig, rowBounds);
	}

	/**
	 * 得到所有数据字典数据的总数量
	 * @param sysCodeConfig
	 * @return
	 */
	public int getSysCodeTotal(SysCodeConfig sysCodeConfig) {
		return sysCodeConfigDao.getSysCodeTotal(sysCodeConfig);
	}

	/**
	 * 删除数据字典数据
	 * @param delBm
	 * @param delTypecode
	 * @param delSysCodeId
	 */
	@Transactional
	public void delSysCode(String delSysCodeId, String delTypecode, String delBm) {
		sysCodeConfigDao.delSysCode(delSysCodeId);
	}

	/**
	 * 添加数据字典数据
	 * @param sysCodeConfig
	 */
	@Transactional
	public void addSysCode(SysCodeConfig sysCodeConfig) {
		int id = sysCodeConfigDao.getSequenceId();
		sysCodeConfig.setId(id);
		sysCodeConfigDao.addSysCode(sysCodeConfig);
		//把spring容器加载的syscode更新
		ConcurrentHashMap<String, ConcurrentHashMap<Integer, SysCodeConfig>> map_wai = SysCodeVo.getSysCodeConfigMap();
		if(map_wai.containsKey(sysCodeConfig.getTypecode())){
			ConcurrentHashMap<Integer, SysCodeConfig> sysCodeMap_lei = map_wai.get(sysCodeConfig.getTypecode());
			sysCodeMap_lei.put(sysCodeConfig.getBm(), sysCodeConfig);
		} else {
			ConcurrentHashMap<Integer, SysCodeConfig> sysCodeMap_lei = new ConcurrentHashMap<Integer, SysCodeConfig>();
			sysCodeMap_lei.put(sysCodeConfig.getBm(), sysCodeConfig);
			map_wai.put(sysCodeConfig.getTypecode(), sysCodeMap_lei);
		}
	}
	
	/**
	 * 修改数据字典数据
	 * @param sysCodeConfig
	 */
	@Transactional
	public void editSysCode(SysCodeConfig sysCodeConfig) {
		sysCodeConfigDao.editSysCode(sysCodeConfig);
		String typeCode= sysCodeConfig.getTypecode();
		Integer bm = sysCodeConfig.getBm();
		ConcurrentHashMap<String, ConcurrentHashMap<Integer, SysCodeConfig>> map_wai = SysCodeVo.getSysCodeConfigMap();
		if(map_wai.containsKey(typeCode) && map_wai.get(typeCode).containsKey(bm)){
			map_wai.get(typeCode).put(bm, sysCodeConfig);
		}
		
	}

	/**
	 * 根据id得到对应的数据
	 * @param editCodeId
	 * @return
	 */
	public SysCodeConfig getSysCodeById(int editCodeId) {
		return sysCodeConfigDao.getSysCodeById(editCodeId);
	}
	
}

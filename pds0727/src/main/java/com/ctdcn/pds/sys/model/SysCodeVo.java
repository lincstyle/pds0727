package com.ctdcn.pds.sys.model;

import com.ctdcn.pds.sys.dao.SysCodeConfigDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 用于封装syscodeconfig表的数据
 * 与错误状态码对比，返回信息提示到页面
 * @author Administrator
 *
 */
public class SysCodeVo {


	@Autowired
	private SysCodeConfigDao sysCodeConfigDao;
	
	private static final ConcurrentHashMap<String,ConcurrentHashMap<Integer, SysCodeConfig>> sysCodeConfigMap = new ConcurrentHashMap<String,ConcurrentHashMap<Integer, SysCodeConfig>>();

	public static ConcurrentHashMap<String, ConcurrentHashMap<Integer, SysCodeConfig>> getSysCodeConfigMap() {
		return sysCodeConfigMap;
	}

	/**
	 * 初始化系统字典
	 * @throws ClassNotFoundException
	 */
	public void init() throws ClassNotFoundException {
		//查询所有字典设置
		List<SysCodeConfig> sysCodeList = sysCodeConfigDao.getSysCodeList();

		for (int i = 0; i < sysCodeList.size(); i++) {
			SysCodeConfig sysCodeConfig = sysCodeList.get(i);
			String typeCode = sysCodeConfig.getTypecode();
			Integer bm = sysCodeConfig.getBm();
			//如果编码分组不存在，则创建
			if(!sysCodeConfigMap.containsKey(typeCode)){
				ConcurrentHashMap<Integer,SysCodeConfig> valueMap = new ConcurrentHashMap<Integer, SysCodeConfig>();
				sysCodeConfigMap.put(typeCode,valueMap);
			}
			try{
				sysCodeConfigMap.get(typeCode).put(bm,sysCodeConfig);
			}catch (Exception e){
				e.printStackTrace();
			}

		}
	}

	/**
	 * 获取字典
	 * @param typeCode 字典分组
	 * @param bm	字典编码
	 * @return
	 */
	public static SysCodeConfig getCode(String typeCode,Integer bm){
		return sysCodeConfigMap.get(typeCode).get(bm);
	}

}

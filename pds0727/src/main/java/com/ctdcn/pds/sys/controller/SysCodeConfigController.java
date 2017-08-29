package com.ctdcn.pds.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.authority.model.ReturnBean;
import com.ctdcn.pds.sys.model.SysCodeConfig;
import com.ctdcn.pds.sys.service.SysCodeConfigService;

@Controller	
@RequestMapping("/sysCodeConfigManager")
public class SysCodeConfigController {
	
	@Autowired
	private SysCodeConfigService sysCodeConfigService;
		
	/**
	 * 显示所有数据、模糊查询数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findSysCode")
	public Map<String, Object> findSysCode(HttpServletRequest request,SysCodeConfig sysCodeConfig){
		int currentPage = 0;
		int pageSize = 0;
		String currentPageStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("rows");
		if(currentPageStr != null && !"".equals(currentPageStr)){
			currentPage = Integer.parseInt(currentPageStr);
		}
		if(pageSizeStr != null && !"".equals(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		List<SysCodeConfig> sysCodeList = sysCodeConfigService.findSysCode(sysCodeConfig,currentPage,pageSize);
		int total = sysCodeConfigService.getSysCodeTotal(sysCodeConfig);
		Map<String, Object> sysCodeMap = new HashMap<String, Object>();
		sysCodeMap.put("rows", sysCodeList);
		sysCodeMap.put("total", total);
		return sysCodeMap;
	}
	
	/**
	 * 根据id得到对应的数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeById")
	public SysCodeConfig getSysCodeById(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		String editCodeId = request.getParameter("syscode_id");
		if(editCodeId != null && !"".equals(editCodeId)){
			SysCodeConfig sysCodeConfig = sysCodeConfigService.getSysCodeById(Integer.parseInt(editCodeId));
			return sysCodeConfig;
		} else {
			rb.setState("error");
			rb.setMessage("修改数据字典失败!选中的数据没有ID!");
			throw new RuntimeException("修改的数据  id 为空");
		}
	}

	/**
	 * 添加数据字典
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addSysCode")
	public ReturnBean addSysCode(HttpServletRequest request, SysCodeConfig sysCodeConfig){
		ReturnBean rb = new ReturnBean();
		try {
			sysCodeConfigService.addSysCode(sysCodeConfig);
			rb.setState("ok");
			rb.setMessage("添加数据字典成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("添加数据字典失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 删除数据字典
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/delSysCode")
	public ReturnBean delSysCode(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		String delId;
		try {
			delId = request.getParameter("delId");
			String delTypecode = request.getParameter("typecode");
			String delBm = request.getParameter("bm");
			sysCodeConfigService.delSysCode(delId,delTypecode,delBm);
			rb.setState("ok");
			rb.setMessage("删除数据字典成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("删除数据字典失败!选中的数据没有ID!");
		}
		return rb;
	}
	
	/**
	 * 修改数据字典
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editSysCode")
	public ReturnBean editSysCode(HttpServletRequest request, SysCodeConfig sysCodeConfig){
		ReturnBean rb = new ReturnBean();
		try {
			String codeId = request.getParameter("syscode_id");
			sysCodeConfig.setId(Integer.parseInt(codeId));
			sysCodeConfigService.editSysCode(sysCodeConfig);
			rb.setState("ok");
			rb.setMessage("修改数据字典成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("修改数据字典失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
}

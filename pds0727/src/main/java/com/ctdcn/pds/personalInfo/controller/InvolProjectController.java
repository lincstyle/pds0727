package com.ctdcn.pds.personalInfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctdcn.pds.authority.model.ReturnBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.personalInfo.service.InvolProjectService;
import com.ctdcn.pds.project.model.ProjectUser;
import com.ctdcn.pds.project.service.ProjectUserService;
import com.ctdcn.utils.spring.web.bind.CurrentUser;

@Controller
@RequestMapping("/personalInfoManager")
public class InvolProjectController {
	
	@Autowired
    private ProjectUserService projectUserService;
	
	@Autowired
    private InvolProjectService involProjectService;

	/**
	 * 查询当前用户所有参与的项目
	 * @param user
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/pcShowProject")
    public Map<String, Object> showProject(HttpServletRequest request,@CurrentUser User user) {
		Map<String, Object> involProMap = new HashMap<String, Object>();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		ProjectUser projectUser = new ProjectUser();
		projectUser.setUserid(user.getId());
		int total = projectUserService.countRecord(projectUser);
		List<ProjectUser> involProList = projectUserService.getProjectUser(projectUser, Integer.parseInt(page), Integer.parseInt(rows));
		involProMap.put("total", total);
		involProMap.put("rows", involProList);
		return  involProMap;
	}

	/**
	 * 查询当前用户所有参与的项目---移动端
	 * @param user
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/showProject")
    public List<ProjectUser> showProject(@CurrentUser User user) {
		ProjectUser projectUser = new ProjectUser();
        projectUser.setUserid(user.getId());
		return projectUserService.projectList(projectUser);
	}

	/**
	 * 修改项目通知
	 * @param request
	 * @param user
	 */
	@ResponseBody
	@RequestMapping("/editIsReceive")
	public ReturnBean editIsReceive(HttpServletRequest request, @CurrentUser User user){
		ReturnBean rb = new ReturnBean();
		String proId = request.getParameter("proId");
		String receive = request.getParameter("isReceive");
		int isReceive = Integer.parseInt(receive);
		ProjectUser proUser = new ProjectUser();
		if(proId == null || "".equals(proId)){
			throw new RuntimeException("未知错误,项目ID为空");
		}
		proUser.setPid(Integer.parseInt(proId));
		proUser.setUserid(user.getId());
		proUser.setIsreceive(isReceive);
		involProjectService.editIsReceive(proUser);

		rb.setState("ok");
		if(isReceive == 1){
			rb.setMessage("true");
		} else {
			rb.setMessage("false");
		}
		return rb;
	}


	@RequestMapping("/editIsResponse")
	public void editIsResponse(@RequestParam("puid[]") Integer[] puid, String response, @CurrentUser User user){
		int isResponse = 0;
		ProjectUser proUser = new ProjectUser();
		if(puid == null){
			throw new RuntimeException("未知错误,项目ID为空");
		}
		if(response.equals("true")){
			isResponse = 1;
		}
		proUser.setPuidArr(puid);
		proUser.setIsResponse(isResponse);
		involProjectService.editIsResponse(proUser);
	}
}

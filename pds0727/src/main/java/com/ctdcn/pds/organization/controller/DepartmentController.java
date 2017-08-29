package com.ctdcn.pds.organization.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.ctdcn.utils.consts.WeixinError;
import me.chanjar.weixin.common.exception.WxErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.authority.model.ReturnBean;
import com.ctdcn.pds.organization.model.Department;
import com.ctdcn.pds.organization.service.DepartmentService;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.sys.model.MenuNode;
import com.ctdcn.pds.sys.model.SysCodeConfig;
import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.utils.javascript.model.Node;

@Controller
@RequestMapping("/departmentManager")
public class DepartmentController {
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 得到指定的部门
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDepartment")
	public Department getDepartment(HttpServletRequest request){
		int departmentId = Integer.parseInt(request.getParameter("id"));
		Department d = departmentService.getDepartment(departmentId);
		return d;
	}
	

	/**
	 * 显示部门树
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDepartmentTree")
	public List<MenuNode> getDepartmentTree(HttpServletRequest request){
		String parent_id = request.getParameter("id");
		Integer parentId = 0;
		if(parent_id != null && !parent_id.equals("")){
			parentId = Integer.parseInt(parent_id);
		}
		List<Department> departmentList = departmentService.getDepartmentTree(parentId);
		List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
        for(Department depart : departmentList){
        	MenuNode menuNode = new MenuNode();
        	menuNode.setId(depart.getDepartmentId()+"");
        	menuNode.setText(depart.getDepartmentName());
        	menuNode.setParentId(depart.getParentId());
        	if(depart.getHasChildren() == 1){
        		menuNode.setState("closed");
			} else {
				menuNode.setState("open");
			}
            menuNodeList.add(menuNode);
        }
		return menuNodeList;
	}
	
	
	/**
	 * 显示部门下拉框
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDepartmentList")
	public List<Node> getDepartmentList(HttpServletRequest request){
		String selectId = request.getParameter("selectId");
		List<Department> departmentList = departmentService.getDepartmentList();
		List<Node> departCombobox = new ArrayList<Node>();
		for (Department department : departmentList) {
			Node n = new Node();
			String departId = department.getDepartmentId()+"";
			n.setId(Integer.parseInt(departId));
			n.setText(department.getDepartmentName());
			if(department.getParentId() == 0 && selectId.equals("null")){
				n.setSelected("true");
			}
			if(selectId.equals(departId)){
				n.setSelected("true");
			}
			departCombobox.add(n);
		}
		return departCombobox;
	}
	
	/**
	 * 添加部门
	 * @param request
	 * @param depart
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addDepartment")
	public ReturnBean addDepartment(HttpServletRequest request, Department depart){
		ReturnBean rb = new ReturnBean();
		int width = 1;
		int parentId = 1;
		int hasChildren = 0;
		try {
			String departmentName = depart.getDepartmentName();
/*			如果depart.getParentId() != null 说明 是页面正常传值
 * 			如果depart.getParentId() == 1  添加父级部门
 * 			如果添加父级部门    父ID默认 为1, 如果添加子级部门,父ID为选择的ID
 */
			if(depart.getParentId() != null){
				parentId = depart.getParentId();
			}
			if(parentId != 1){
				width = departmentService.getParentWidth(parentId)+1;
			}
			/*
			 * 根据级别  设置sort字段的值
			 * 如果为1级  说明是父级部门,则根据父级部门查询所有父级部门的sort的最大值
			 * 如果不为1级, 说明是子级部门,再根据父级部门的ID 得到此父级部门最大的sort值
			 */
			Integer sort = departmentService.getMaxSort(parentId)+1;
			Department department = new Department();
			department.setParentId(parentId);
			department.setDepartmentName(departmentName);
			department.setWidth(width);
			department.setHasChildren(hasChildren);
			department.setSort(sort);
			departmentService.addDepartment(department);
			rb.setState("ok");
			rb.setMessage("添加部门成功!");
		} catch (WxErrorException e) {
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("添加部门失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 修改部门
	 * @param request
	 * @param depart
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editDepartment")
	public ReturnBean editDepartment(HttpServletRequest request, Department depart){
		ReturnBean rb = new ReturnBean();
		try {
			int departmentId = depart.getDepartmentId();
			Department oldDepartment = departmentService.getDepartment(departmentId);
			int oldParentId = oldDepartment.getParentId();
			String newDepartName = depart.getDepartmentName();
			String oldDepartName = oldDepartment.getDepartmentName();
			
			Integer sort = depart.getSort();
			Integer width = 1;
			int parentId = depart.getParentId();
			//如果选择的父级部门ID不等于原来的父级部门ID 就说明他更改了所属部门和级别
			if(parentId != 0){
				if(parentId != oldParentId){
					width = departmentService.getParentWidth(parentId);
					if(width != null){
						width += 1;
					} else {
						width = 1;
					}
					sort = departmentService.getMaxSort(parentId);
					if(sort == null){
						sort = 0;
					}
					sort += 1;
				}
			}
			String departmentName = depart.getDepartmentName();
			int hasChildren = depart.getHasChildren();
			Department department = new Department();
			department.setDepartmentId(departmentId);
			department.setDepartmentName(departmentName);
			department.setParentId(parentId);
			department.setWidth(width);
			department.setHasChildren(hasChildren);
			department.setSort(sort);
			
			departmentService.editDepartment(department,oldParentId,newDepartName,oldDepartName);
			rb.setState("ok");
			rb.setMessage("修改部门成功!");
		} catch (WxErrorException e){	
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("修改部门失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 删除部门
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delDepartment")
	public ReturnBean delDepartment(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		int departId = 0;
		try {
			String departmentId = request.getParameter("id");
			String isleaf = request.getParameter("isleaf");
			String delDepartParentId = request.getParameter("parentId");
			if(departmentId == null || departmentId.equals("")){
				throw new RuntimeException();
			} else {
				departId = Integer.parseInt(departmentId);
			}
			//首先检查部门下是否还有员工,如果返回的是false,则不能删除
			int userCount = userService.getDepartmentUserTotal(departId);

			if(userCount > 0){
				rb.setState("error");
				rb.setMessage("此部门下还有员工,禁止删除!");
				return rb;
			}
			//删除部门
			departmentService.delDepartment(departId, isleaf, Integer.parseInt(delDepartParentId));
			rb.setState("ok");
			rb.setMessage("删除部门成功!");
		} catch (WxErrorException e){
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			rb.setState("no");
			rb.setMessage("删除部门失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
}

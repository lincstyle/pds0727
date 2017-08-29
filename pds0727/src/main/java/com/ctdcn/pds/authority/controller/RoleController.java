package com.ctdcn.pds.authority.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.authority.model.ReturnBean;
import com.ctdcn.pds.authority.model.Role;
import com.ctdcn.pds.authority.service.RoleService;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.utils.javascript.model.Node;

@Controller
@RequestMapping("/roleManager")
public class RoleController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 根据ID得到角色
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRoleById")
	public Role getRoleById(HttpServletRequest request){
		int roleId = Integer.parseInt(request.getParameter("role_id"));
		Role role = roleService.getRoleById(roleId);
		return role;
	}
	
	/**
	 * 显示所有的角色
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRoleList")
	public Map<String, Object> getRoleList(HttpServletRequest request){
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Map<String, Object> roleMap = new HashMap<String, Object>();
		List<Role> roleList = roleService.getRoleList(currentPage, pageSize);
		int total = roleService.getRoleTotal();
		roleMap.put("total", total);
		roleMap.put("rows", roleList);		
		return roleMap;
	}
	
	/**
	 * 添加角色
	 * @param request
	 * @param role
	 */
	@RequestMapping("/addRole")
	public ReturnBean addRole(HttpServletRequest request, Role role){
		ReturnBean rb = new ReturnBean();
		try {
			roleService.insertRole(role);
			rb.setState("ok");
			rb.setMessage("添加角色成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("添加角色失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 删除角色
	 * @param request
	 */
	@RequestMapping("/delRole")
	public ReturnBean delRole(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			int roleId = Integer.parseInt(request.getParameter("role_id"));
			User user = new User();
			user.setRole(roleId);
			int count = userService.getFindUserTotal(user);
			if(count > 0){
				rb.setState("error");
				rb.setMessage("此角色还有用户,禁止删除!");
				return rb;
			}
			roleService.delRole(roleId);
			rb.setState("ok");
			rb.setMessage("删除角色成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("删除角色失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 修改角色
	 * @param request
	 * @param role
	 */
	@RequestMapping("/updateRole")
	public ReturnBean updateRole(HttpServletRequest request, Role role){
		ReturnBean rb = new ReturnBean();
		try {
			roleService.updateRole(role);
			rb.setState("ok");
			rb.setMessage("修改角色成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("修改角色失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 角色下拉框
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/comboRoleList")
	public List<Node> comboRoleList(){
		List<Role> roleList = roleService.comboRoleList();
		List<Node> roleCombobox = new ArrayList<Node>();
		for (Role role : roleList) {
			Node node = new Node();
			node.setId(role.getRoleId());
			node.setText(role.getRoleName());
			roleCombobox.add(node);
		}
		return roleCombobox;
	}
	
	/**
	 * 添加和修改角色时,验证角色名称角色值是否已存在
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeRole")
	public ReturnBean changeRole(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		Map<String,String> map = new HashMap<String,String>();
		int flag = 0;
		try {
			String roleName = request.getParameter("roleName").trim();
			String role = request.getParameter("role").trim();
			String type = request.getParameter("type");
			String roleId = request.getParameter("roleId");
			
			if(roleName.equals("") || role.equals("")){
				rb.setState("error"); 
				rb.setMessage("角色名或角色值不能为空!");
				return rb;
			}
			
			map.put("type",type);
			map.put("roleName", roleName);
			map.put("role", role);
			if(type.equals("edit")){
				map.put("roleId", roleId);
			}
			
			flag = roleService.changeRole(map);
			if(flag == 1){
				rb.setState("error"); 
				rb.setMessage("角色名已存在!");
				return rb;
//				throw new RuntimeException();
			} 
			if(flag == 2){
				rb.setState("error"); 
				rb.setMessage("角色值已存在!");
				return rb;
//				throw new RuntimeException();
			}
			rb.setState("ok");
			rb.setMessage("编辑角色成功!");
		} catch (Exception e) {
			if(flag != 1 && flag != 2){
				rb.setState("error"); 
				rb.setMessage("编辑角色失败!");
			}
			e.printStackTrace();
		}
		return rb;
	}
}

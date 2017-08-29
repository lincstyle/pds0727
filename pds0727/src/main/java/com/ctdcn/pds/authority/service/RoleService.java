package com.ctdcn.pds.authority.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctdcn.pds.authority.dao.RoleDao;
import com.ctdcn.pds.authority.model.Role;

@Service
public class RoleService {
	
	@Autowired
	public RoleDao roleDao;
	
	public void insertRole(Role role){
		roleDao.insertRole(role);
	}
	
	public void delRole(int id){
		roleDao.delRole(id);
	}
	
	public void updateRole(Role role){
		roleDao.updateRole(role);
	}
	
	public Role getRoleById(int id){
		return roleDao.getRoleById(id);
	}
	
	/**
	 * 显示所有的角色
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Role> getRoleList(int currentPage, int pageSize){
		Map<String, Integer> map = new HashMap<String, Integer>();
		int start = (currentPage-1)*pageSize;// 开始条数
		int limit = pageSize;//查询跨度  
		RowBounds rowBounds = new RowBounds(start,limit);
		return roleDao.getRoleList(map,rowBounds);
	}

	/**
	 * 得到所有角色的总条数
	 * @return
	 */
	public int getRoleTotal() {
		return roleDao.getRoleTotal();
	}

	/**
	 * 角色下拉框
	 * @return
	 */
	public List<Role> comboRoleList() {
		return roleDao.comboRoleList();
	}

	/**
	 * 验证输入的角色名和角色值是否已经存在
	 * @param map
	 * @return 0 角色名和角色值都不存在,可以创建 ;  1 角色名已存在; 2 角色值已存在;
	 */
	public int changeRole(Map<String,String> map) {
		List<Role> roleList = roleDao.changeRole(map);
		if(!roleList.isEmpty()){
			for (Role role : roleList) {
				if(map.get("roleName").equals(role.getRoleName())){
					return 1;
				}
				if(map.get("role").equals(role.getRole())){
					return 2;
				}
			}
		}
		return 0;
	}

}

package com.ctdcn.pds.authority.service;

import java.util.ArrayList;
import java.util.List;

import com.ctdcn.utils.javascript.model.TreeNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctdcn.pds.authority.dao.RoleResourceDao;
import com.ctdcn.pds.authority.model.RoleResource;

@Service
public class RoleResourceService {
	
	@Autowired
	private RoleResourceDao roleResourceDao;
	
	/**
	 * 角色添加权限
	 * @param resource
	 */
	@Transactional
	public void addRoleResource(RoleResource []resource) {
		List<RoleResource> roleResourceList = new ArrayList<RoleResource>();
		for (int i = 0; i < resource.length; i++) {
			roleResourceList.add(resource[i]);
		}
		roleResourceDao.addRoleResource(roleResourceList);
	}

	/**
	 * 角色删除权限
	 * @param resource
	 */
	@Transactional
	public void delRoleResource(RoleResource []resource) {
		List<RoleResource> roleResourceList = new ArrayList<RoleResource>();
		for (int i = 0; i < resource.length; i++) {
			roleResourceList.add(resource[i]);
		}
		roleResourceDao.delRoleResource(roleResourceList);
	}

	/**
	 * 得到所有的资源
	 * @return
	 */
	public List<RoleResource> getRoleResourceList() {
		return roleResourceDao.getRoleResourceList();
	}

	/**
	 * 得到指定角色的资源
	 * @return
	 */
	public List<RoleResource> getRoleResourceById(int roleId) {
		return roleResourceDao.getRoleResourceById(roleId);
	}


	public List<TreeNode> getRoleResourceTree(Integer roleId,Integer parentId,Boolean checked){
		return  roleResourceDao.getRoleResourceTree( roleId, parentId,checked);
	}
}

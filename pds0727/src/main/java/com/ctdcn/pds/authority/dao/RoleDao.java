package com.ctdcn.pds.authority.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ctdcn.pds.authority.model.Role;

@Repository
public class RoleDao {

	public static final String ROLE_MAPPER = "sys.Role";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 添加角色
	 * @param role
	 */
	public void insertRole(Role role) {
		sqlSessionTemplate.insert(ROLE_MAPPER+".insertRole", role);
	}
	
	/**
	 * 根据ID删除角色和角色相关的资源
	 * @param id
	 */
	public void delRole(int id){
		sqlSessionTemplate.delete(ROLE_MAPPER+".delRole", id);
		sqlSessionTemplate.delete(ROLE_MAPPER+".delRoleResource", id);
	}
	
	/**
	 * 根据ID修改角色
	 * @param role
	 */
	public void updateRole(Role role){
		sqlSessionTemplate.update(ROLE_MAPPER+".updateRole", role);
	}
	
	/**
	 * 根据ID得到角色
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id){
		return sqlSessionTemplate.selectOne(ROLE_MAPPER+".getRoleById", id);
	}
	
	/**
	 * 得到角色的集合
	 * @return
	 */
	public List<Role> getRoleList(Map<String, Integer> map, RowBounds rowBounds){
		return sqlSessionTemplate.selectList(ROLE_MAPPER+".getRoleList", map, rowBounds);
	}

	/**
	 * 得到所有角色的总条数
	 * @return
	 */
	public Integer getRoleTotal() {
		return sqlSessionTemplate.selectOne(ROLE_MAPPER+".getRoleTotal");
	}

	/**
	 * 角色下拉框
	 * @return
	 */
	public List<Role> comboRoleList() {
		return sqlSessionTemplate.selectList(ROLE_MAPPER+".comboRoleList");
	}

	/**
	 * 验证输入角色名和角色值
	 * @param map
	 * @return
	 */
	public List<Role> changeRole(Map<String, String> map) {
		return sqlSessionTemplate.selectList(ROLE_MAPPER+".changeRole",map);
	}

}

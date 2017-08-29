package com.ctdcn.pds.sys.role;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ctdcn.pds.authority.model.Role;
import com.ctdcn.pds.authority.service.RoleService;

/**
 * @author 张靖.
 *         2015-06-04 16:20
 */
public class RoleDaoTest {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext.xml");
		RoleService roleService = ctx.getBean("roleService",RoleService.class);
//		Role role = new Role();
//		role.setRoleName("角色3");
//		role.setDescription("测试3");
//		role.setRole("333");
//		roleService.insertRole(role);
		
//		roleService.delRole(5);
		
//		Role role = roleService.getRoleById(6);
//		System.out.println(role.toString());
		
//		System.out.println(role.getRoleId());
//		role.setRoleName("测试456");
//		roleService.updateRole(role);
		
		
		List<Role> list = roleService.getRoleList(1,1);
		System.out.println(list.size());
		for (Role role : list) {
			System.out.println(role.toString());
		}
		
//		for (int i = 0; i < list.size(); i++) {
//			Map<String, Object> map = list.get(i);
//			System.out.println(map.keySet());
//			System.out.println(map.size());
//		}
		

	}
}

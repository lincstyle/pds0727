package com.ctdcn.pds.sys.role;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ctdcn.pds.authority.model.RoleResource;
import com.ctdcn.pds.authority.service.RoleResourceService;

public class RoleResourceTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext.xml");
		RoleResourceService roleResourceService = ctx.getBean("roleResourceService",RoleResourceService.class);

		RoleResource []roleResourceArr = {new RoleResource(1, 5)};
		
//		roleResourceService.addRoleResource(roleResourceArr);
		roleResourceService.delRoleResource(roleResourceArr);
		
	
	}

}

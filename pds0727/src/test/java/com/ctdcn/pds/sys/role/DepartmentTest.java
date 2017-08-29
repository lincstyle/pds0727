package com.ctdcn.pds.sys.role;




import com.ctdcn.pds.organization.model.Department;
import com.ctdcn.pds.organization.service.DepartmentService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Triumphant on 2015/6/9.
 */
public class DepartmentTest
{
    public static void  main(String[] args)
    {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext.xml");
        DepartmentService departmentService = ctx.getBean("departmentService",DepartmentService.class);

        Department department =new Department();
        department.setDepartmentId(1);


        Department updateDe =new Department();
        updateDe.setDepartmentName("武汉研发");
        updateDe.setParentId(0);
        updateDe.setWidth(1);
        updateDe.setDepartmentId(1);
//        departmentService.updateDepartment(updateDe,department);

        /**
         * 查
         */
//        List<Department> list =departmentService.queryDepartment(department);
//        System.out.println(list.toString());

      //  departmentService.deleteDepartment(1);
//        departmentService.addDepartment(updateDe);



    }
}

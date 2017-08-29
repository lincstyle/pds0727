package com.ctdcn.pds.sys.role.project;

import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.service.ProjectService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Triumphant on 2015/6/10.
 */
public class ProjectTest
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext.xml");

        ProjectService projectService = ctx.getBean("projectService", ProjectService.class);

        Project project =new Project();
        project.setPname("内蒙1");
        project.setPflag(1);
        project.setPerson("刘凯旋");
        java.util.Date date=new java.util.Date();
        project.setLastDate(new java.sql.Date(date.getTime()));
        project.setPdate(new java.sql.Date(date.getTime()));
        project.setIsdelete(0);
        project.setPintro("内蒙古社保卡管理系统");
        projectService.addProject(project);

//        Project updatePr =new Project();
//        updatePr.setPname("河南卡中心");
//        updatePr.setPerson("李四");
//        projectService.updateProject(updatePr,project);
//        Project project =new Project();
//        project.setPname("河南卡中心");
//        updatePr.setIsdelete(1);
//        projectService.updateProject(updatePr,project);

//        List<Project> list =projectService.queryProject(project);
//        System.out.println(list);



    }
}

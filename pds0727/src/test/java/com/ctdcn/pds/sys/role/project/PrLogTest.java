package com.ctdcn.pds.sys.role.project;

import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.service.ProjectLogService;
import com.ctdcn.pds.project.service.ProjectService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


/**
 * Created by Triumphant on 2015/6/10.
 */
public class PrLogTest
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext.xml");

        ProjectLogService projectLogService = ctx.getBean("projectLogService", ProjectLogService.class);

        ProjectLog projectLog =new ProjectLog();

        //增加
//        projectLog.setPname("河南卡中心");
//        projectLog.setPerson("刘凯旋");
//        projectLog.setDetail("最近无大事，正常");
//        projectLog.setIsdelete(0);
//        java.util.Date date =new java.util.Date();
//        projectLog.setCdate(new java.sql.Date(date.getTime()));
//        projectLog.setSdate(new java.sql.Date(date.getTime()));
//        projectLog.setEdate(new java.sql.Date(date.getTime()));
//        projectLogService.insertPrLog(projectLog);
        //查询
//        projectLog.setPname("河南卡中心");
//        List<ProjectLog> list =projectLogService.queryPrLog(projectLog);
//        System.out.println(list);







    }
}

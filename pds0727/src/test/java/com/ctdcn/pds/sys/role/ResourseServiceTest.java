package com.ctdcn.pds.sys.role;

import com.ctdcn.pds.authority.model.Resource;
import com.ctdcn.pds.authority.service.ResourceService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Trimup on 2015/6/4.
 */
public class ResourseServiceTest
{

    public static  void  main(String[] args)
    {
        Resource resource =new Resource();
        Resource testresource =new Resource();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext.xml");

        ResourceService resourceService = ctx.getBean("resourceService", ResourceService.class);


/**
 * 测试 查询
 */
//        int parent_id=0;
//        resource.setParentId(parent_id);
//        List<Resource> list=resourceService.queryResourceDao(resource);
//        for(Resource re : list)
//        {
//            System.out.println(re.toString());
//        }
/**
 * 测试 改
 */
//        testresource.setParentId(1);
//        testresource.setName("项目管理1");
//        testresource.setIdentity("哈哈哈");
//        testresource.setParentIds("0/0/0");
//        testresource.setUrl("......");
//        testresource.setIsShow(1);
//        resource.setId(1);
//        resourceService.updateResource(testresource,resource);
//        List<Resource> list=resourceService.queryResourceDao(resource);
//        for(Resource re : list)
//        {
//            System.out.println(re.toString());
//        }

 /**
 * 测试增加
*/
//        testresource.setParentId(1);
//        testresource.setName("增加测试");
//        testresource.setIdentity("哈哈哈");
//        testresource.setParentIds("0/0/0");
//        testresource.setUrl("......");
//        testresource.setIsShow(1);
//        testresource.setId(101);
//        resource.setId(101);
//        resourceService.addResource(testresource);
//        List<Resource> list=resourceService.queryResourceDao(resource);
//        for(Resource re : list)
//       {
//           System.out.println(re.toString());
//       }

        /**
         * 测试删除
         *
         */
//        resourceService.deleteResource(101);

        /**
         * 拼接权限测试
         */

        List<String> list=resourceService.querySpliceAuthority(1,3);
        System.out.println("-----------"+list.toString());
    }




}

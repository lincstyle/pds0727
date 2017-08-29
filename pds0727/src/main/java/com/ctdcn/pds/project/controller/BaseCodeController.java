package com.ctdcn.pds.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.project.model.BaseCode;
import com.ctdcn.pds.project.model.PrCode;
import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.service.BaseCodeService;
import com.ctdcn.pds.project.service.ProjectCodeService;
import com.ctdcn.pds.project.service.ProjectLogService;
import com.ctdcn.pds.project.service.ProjectService;
import com.ctdcn.pds.sys.model.JsonBean;

/**
 * Created by Triumphant on 2015/7/7.
 */

@Controller
@RequestMapping("/baseCodeManager")
public class BaseCodeController
{
    @Autowired
    private BaseCodeService baseCodeService;
    @Autowired
    private ProjectCodeService projectCodeService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectLogService projectLogService;

    @ResponseBody
    @RequestMapping("/queryBaseCode")
    public List<BaseCode>  queryBaseCode(HttpServletRequest request)
    {
        BaseCode baseCode =new BaseCode();
        List<BaseCode> baseCodeList= baseCodeService.queryBaseCode(baseCode);
        return baseCodeList;

    }

    @ResponseBody
    @RequestMapping("/moveBaseCode")
    public JsonBean  moveBaseCode(HttpServletRequest request)
    {
        //获取移动的节点参数

        JsonBean bean =new JsonBean();
        String originalMc = request.getParameter("original[mc]");
        Integer originalTypeCode = Integer.valueOf(request.getParameter("original[typeCode]"));
        //获取 移动目标节点参数
        //String targetMc = request.getParameter("target[mc]");
        Integer targetTypeCode = Integer.valueOf(request.getParameter("target[typeCode]"));



        try {
            //更新 基本项目编码表的 位置
            /**
             *  original <target
             *    就是节点要移动到其下方  将原节点 与目标节点之间的节点 的  排序 -1
             *  original >target
             *    就是节点要移动到其上方  将原节点 与目标节点之间的节点 的  排序 +1
             */
        baseCodeService.moveBaseCode(targetTypeCode,originalTypeCode);
            /**
             * 然后将原节点的位置排序位置换成目标节点的
             */
        baseCodeService.replaceBaseCode(targetTypeCode,originalTypeCode,originalMc);


//        //更新 项目编码表的  阶段编码 同  基本项目编码表 保持一致
        projectCodeService.updatePrCodeByMove();

            bean.setSuccess(true);
            bean.setMsg("移动成功");
        }catch (Exception e)
        {
            bean.setSuccess(false);
            bean.setMsg(e.getMessage());

        }
        return bean;

    }


    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/editBaseCode")
    public JsonBean edit(BaseCode upbaseCode)
    {


        JsonBean bean=new JsonBean();

                //获取被更新的节点 的 typecpde
        BaseCode baseCode =new BaseCode();
        baseCode.setTypeCode(upbaseCode.getTypeCode());


        try{
            //根据编辑的内容   更新基本编码表中的  编码名称
            baseCodeService.editBaseCode(baseCode, upbaseCode);

            //更新完基本编码表的 编码名称  之后 更新项目编码表的 名称
            projectCodeService.updateMcByEdit();
            bean.setSuccess(true);
            bean.setMsg("编辑成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            bean.setSuccess(false);
            bean.setMsg(e.getMessage());
        }


        return bean;
    }


    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/deleteBaseCode")
    public JsonBean deleteBaseCode(BaseCode baseCode)
    {


        JsonBean bean=new JsonBean();

        BaseCode deleteBaseCode =new BaseCode();

        PrCode prCode =new PrCode();
        //项目对象
        Project project = new Project();
        project.setPflag(baseCode.getTypeCode());
        //日志对象
        ProjectLog projectLog=new ProjectLog();
        projectLog.setPflag(baseCode.getTypeCode());
        //获得被删除的 节点 typecode
        deleteBaseCode.setTypeCode(baseCode.getTypeCode());
        prCode.setTypeCode(baseCode.getTypeCode());

        try{
        	//根据项目状态查找，看是不是在项目或日志中使用了的此项目状态
        	 Integer totalProject = projectService.countProject(project);
        	 Integer totalPflag = projectLogService.countPrlogPflag(projectLog);
        	 if(totalProject>0 || totalPflag>0){
        		 bean.setSuccess(false);
        		 bean.setMsg("删除失败,该项目阶段在项目或日志中已使用！");
        	  }else{      
            //根据选择的节点   删除相应的节点
            baseCodeService.deleteBaseCode(deleteBaseCode);
            //然后在更改删除后之后的节点 应该有的相应的排序
            baseCodeService.updateBaseCodeByAddBefore(3,baseCode.getSerial());
            //删除完基本编码表的 编码名称  之后 同时删除项目编码表的 记录
            projectCodeService.deleteByBaseCode(prCode);
            bean.setSuccess(true);
            bean.setMsg("编辑成功");
        	}
        }catch (Exception e)
        {
           // e.printStackTrace();
            bean.setSuccess(false);
            bean.setMsg(e.getMessage());
        }


        return bean;
    }


    /**
     * 添加基本项目编码
     * @param
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping("/addBaseCode/{flag}")
    public JsonBean add(BaseCode baseCode,@PathVariable(value="flag") Integer flag)
    {

        JsonBean bean=new JsonBean();
        try{

            //先更改新增之后 其他节点的顺序
            baseCodeService.updateBaseCodeByAddBefore(flag, baseCode.getSerial());
            BaseCode baseCodeSeq =new BaseCode();
            //在该节点之前 新增
            if(flag==1)
            {          	
            	baseCodeSeq.setSerial(baseCode.getSerial());        
              
            }else if(flag ==2)
                //在该节点之后 新增
            {
            	baseCodeSeq.setSerial(baseCode.getSerial()+1);               
            } 
            baseCodeSeq.setMc(baseCode.getMc());
            Integer maxTypecodeSeq=baseCodeService.queryTypeCodeSequcence();
            baseCodeSeq.setTypeCode(maxTypecodeSeq);
            baseCodeService.addNewBaseCode(baseCodeSeq);
            bean.setSuccess(true);
            bean.setMsg("编辑成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            bean.setSuccess(false);
            bean.setMsg(e.getMessage());
        }
        return bean;
    }








}

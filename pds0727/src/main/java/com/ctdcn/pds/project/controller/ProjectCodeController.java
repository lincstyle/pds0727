package com.ctdcn.pds.project.controller;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.project.model.PrCode;
import com.ctdcn.pds.project.model.ProjectUser;
import com.ctdcn.pds.project.service.ProjectCodeService;
import com.ctdcn.pds.sys.model.JsonBean;
import com.ctdcn.utils.spring.web.bind.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/7/1.
 */
@Controller
@RequestMapping("/codeManager")
public class ProjectCodeController
{

    @Autowired
    private ProjectCodeService projectCodeService;


    /**
     * 查询该项目所拥有的 阶段字典
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/querycodeStage")
    public Map queryCodeStage(HttpServletRequest request,HttpServletResponse response)
    {
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        String pid = request.getParameter("pid");
        PrCode prCode =new PrCode();
        if(pid !=null && !"".equals(pid))
        {
            prCode.setPid(Integer.parseInt(pid));
        }

        Integer total=projectCodeService.countRecord(prCode);
        List<PrCode> prCodeList=projectCodeService.queryCode(prCode,page,rows);
        Map prcodeMap =new HashMap<String,Object>();
        prcodeMap.put("total",total);
        prcodeMap.put("rows",prCodeList);
        return prcodeMap;

    }


    /**
     * 根据选中的  基本阶段字典 添加到项目阶段字典中
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addProjectCode")
    public JsonBean CreateBuProduct(HttpServletRequest request, HttpServletResponse response)
    {
        JsonBean bean =new JsonBean();
        String[] stageArr = request.getParameterValues("stage[]");
        String pid=request.getParameter("pid");
        StringBuffer buffer =new StringBuffer("");
        for(String s:stageArr)
        {
            buffer.append(s);
            buffer.append(",");
        }
        String inStage=buffer.substring(0, buffer.length() - 1);

        PrCode prCode =new PrCode();
        prCode.setPid(Integer.parseInt(pid));

        //设置返回浏览器成功失败参数
        try {
            //先删除之前的 编码
            projectCodeService.deleteByBaseCode(prCode);
            //再新增选中的编码
            projectCodeService.addCodeByBaseCode(inStage,Integer.parseInt(pid));
            bean.setMsg("添加成功");
            bean.setSuccess(true);
        }catch (Exception e)
        {

            e.printStackTrace();
            bean.setMsg("添加失败"+e.getMessage());
            bean.setSuccess(false);
        }
        return bean;
    }


    /**
     * 删除  项目阶段编码表中的某些基本编码
     * @param
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteprCode")
    public JsonBean deletePrUser(HttpServletRequest request,@RequestParam("cid_array[]") Integer[] cid_array)
    {
        JsonBean bean = new JsonBean();
        try {
            projectCodeService.deletePrCode(cid_array);
            bean.setMsg("删除成功");
            bean.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            bean.setMsg("删除失败"+e.getMessage());
            bean.setSuccess(false);
        }
        return bean;
    }


    /**
     * 项目阶段维护
     * @param request
     * @param stageAdd
     */
    @ResponseBody
    @RequestMapping("/updateProjectStage")
    public JsonBean updateProjectStage(HttpServletRequest request, @RequestParam("stageAdd[]") Integer[] stageAdd){
        JsonBean bean = new JsonBean();
        try {
            String pid = request.getParameter("pid");
            StringBuffer buffer =new StringBuffer("");
            for(Integer s:stageAdd) {
                buffer.append(s);
                buffer.append(",");
            }
            String inStage = buffer.substring(0, buffer.length() - 1);
            //先删除此项目原有的阶段
            PrCode prCode = new PrCode();
            prCode.setPid(Integer.parseInt(pid));
            projectCodeService.deleteByBaseCode(prCode);
            //2.再新增项目阶段
            if(!inStage.equals("")) {
                projectCodeService.addCodeByBaseCode(inStage, Integer.parseInt(pid));
            } else {
                throw new RuntimeException("没有选中任何要添加的阶段");
            }
            bean.setMsg("维护成功！");
            bean.setSuccess(true);
        } catch (Exception e) {
            bean.setMsg("维护失败！");
            bean.setSuccess(false);
        }
        return bean;
    }




}

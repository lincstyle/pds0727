package com.ctdcn.pds.project.controller;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.model.ProjectUser;
import com.ctdcn.pds.project.service.ProjectLogService;
import com.ctdcn.pds.project.service.ProjectService;
import com.ctdcn.pds.project.service.ProjectUserService;
import com.ctdcn.pds.sys.model.JsonBean;
import com.ctdcn.pds.weixin.service.WxMessageService;
import com.ctdcn.utils.spring.web.bind.CurrentUser;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/12.
 */
@Controller
@RequestMapping("/projectLog")
public class ProjectLogController
{
    @Autowired
    private ProjectLogService projectLogService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private WxMessageService wxMessageService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserService userService;

    /**
     * 查询项目日志
     */
    @ResponseBody
    @RequestMapping("/queryPrLog")
    public Map<String,Object> queryPrLog(HttpServletRequest request,@CurrentUser User user)
    {
        int currentPage = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("rows"));
        ProjectLog projectLog = new ProjectLog();
        Integer[] proRespIds = null;
        projectLog.setIsdelete(0);
        //获得查询条件的项目参数
        String pname = request.getParameter("pname");
        //获得查询条件的项目更新人
        String personId = request.getParameter("proRespId");
        /**
         * 浏览器选择的起止时间
         */
        String selectSDate = request.getParameter("selectSDate");
        String selectEDate  =request.getParameter("selectEDate");
        if(pname !=null && !"".equals(pname) ) {
            projectLog.setPname(pname);
        }
        if(selectSDate !=null && !"".equals(selectSDate)) {
            projectLog.setSelectSDate(selectSDate);
        }
        if(selectEDate !=null && !"".equals(selectEDate)) {
            projectLog.setSelectEDate(selectEDate);
        }
        if (!SecurityUtils.getSubject().isPermitted("project:Projectlog:queryAllPrLog")) {
            projectLog.setUpdatePerIdArr(new int[]{user.getId()});
        }

        if(personId != null && !personId.equals("") ) {
            String[] personIds = personId.split(",");
            proRespIds = new Integer[personIds.length];
            for(int i = 0; i < personIds.length; i++){
                proRespIds[i] = Integer.parseInt(personIds[i]);
            }
        }

        projectLog.setPid(Strings.isNullOrEmpty(request.getParameter("pid")) ? null : Integer.valueOf(request.getParameter("pid")));

        List<ProjectLog>  prLogList = projectLogService.queryPagingPrLog(projectLog, currentPage, pageSize, proRespIds);
        for (ProjectLog log : prLogList){
            String detail = log.getDetail();
            //解决页面上多个空格在页面上只显示一个空格的问题
            if(detail != null){
            	detail = detail.replace(" ","&nbsp;");
            }
            log.setDetail(detail);
        }
        Integer total = projectLogService.countPrlog(projectLog,proRespIds);
        Map prLogMap = new HashMap();
        prLogMap.put("total", total);
        prLogMap.put("rows", prLogList);
        return prLogMap;
    }

    /**
     * 编辑项目日志
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/editPrLog")
    public JsonBean editPrLog(HttpServletRequest request) {
        JsonBean bean=new JsonBean();
        ProjectLog projectLog = new ProjectLog();
        ProjectLog updatePrLog = new ProjectLog();
        try{

            //获取被编辑记录的 日志ID
            String lid = request.getParameter("lid");
            //获得选择的项目阶段
            String pflag = request.getParameter("pflag");
            //获得准备编辑的   详细内容
            String detail = request.getParameter("detail");
            //获得新编辑内容的 格式的全部内容
            String contents = request.getParameter("contents");

            if(lid !=null && !"".equals(lid) ) {
                projectLog.setLid(Integer.parseInt(lid));
            }
            if(pflag !=null && !"".equals(pflag) ) {
                updatePrLog.setPflag(Integer.parseInt(pflag));
            }
            if(detail !=null && !"".equals(detail)) {
                //解决多个空格变成问号的问题
                byte[] space = new byte[]{(byte)0xc2,(byte)0xa0};
                String UTFSpace = new String(space,"utf-8");
                detail = detail.replace(UTFSpace," ");
                if(detail.length()>50) {
                    updatePrLog.setDetail(detail.substring(0,50));
                }else{
                    updatePrLog.setDetail(detail);
                }
            }
            if(contents !=null && !"".equals(contents)){
                updatePrLog.setContents(contents);
            }
            //更新提交时间
            updatePrLog.setCdate(new java.util.Date());
            //更新内容
            projectLogService.updatePrLog(updatePrLog,projectLog);
            bean.setMsg("编辑成功");
            bean.setSuccess(true);
        }catch (Exception e){
        	e.printStackTrace();
        	//bean.setMsg("编辑失败");
            bean.setMsg(e.getMessage());
            bean.setSuccess(false);
        }
        return bean;
    }


    @ResponseBody
    @RequestMapping("/addprLog")
    public JsonBean addPrLog(HttpServletRequest request,@CurrentUser User user) {
        JsonBean bean = new JsonBean();
        ProjectLog projectLog = new ProjectLog();
        try{
            //解决多个空格变成问号的问题
            byte[] space = new byte[]{(byte)0xc2,(byte)0xa0};
            String UTFSpace = new String(space,"utf-8");
            String detail = request.getParameter("detail"); //日报简介
            detail = detail.replace(UTFSpace," ");

            String pname = request.getParameter("pname"); //项目名
//          String detail = request.getParameter("detail"); //日报简介
            String contents=request.getParameter("contents");//日报详细
            String sdate=request.getParameter("sdate"); //日报时间
            Integer pflag =Integer.parseInt(request.getParameter("pflag"));//项目阶段
            Integer pid =Integer.parseInt(request.getParameter("pid"));//项目id
            String person=user.getName(); //获得当前登录人的 姓名
            Project project =new Project();
            Project updateproject =new Project();

           //插入参数中设置pid
            projectLog.setPid(pid);
            /*if(pname !=null && !"".equals(pname)) {
                projectLog.setPname(pname);
                project.setPname(pname);
            }else{
                throw new RuntimeException("未选择项目名");
            }*/
            if(pid !=null && !pid.equals(-1)) {
                projectLog.setPname(pname);
                project.setPname(pname);
            }else{
                bean.setMsg("未选择项目名");
                bean.setSuccess(false);
                return bean;
            }

            /**
             * detail是简略显示,如果超出则需要使用
             */
            if(detail !=null && !"".equals(detail)) {
                if(detail.length()>50) {
                    projectLog.setDetail(detail.substring(0,50));
                } else {
                    projectLog.setDetail(detail);
                }
            }

            if(contents !=null && !"".equals(contents)) {
                projectLog.setContents(contents);
            }
            if(pflag !=null ) {
                projectLog.setPflag(pflag);
            }
            if(sdate !=null){
            	projectLog.setSdate(new java.util.Date());
            }

            projectLog.setCdate(new java.util.Date());
            projectLog.setPerson(person);
            projectLog.setIsdelete(0);
            projectLog.setUserId(user.getId());

            //设置 项目表中的最后更新人 和最后更新时间
            updateproject.setLastDate(new java.util.Date());
            updateproject.setLastPerson(person);

            Integer lid = projectLogService.queryLidSeq();
            projectLog.setLid(lid);
            projectLog.setIsSuccess(1);//默认设置为发送成功
            //插入更新日志到项目日志表中
            projectLogService.insertPrLog(projectLog);
            //更新项目表中的最后更新人和最后更新时间
            projectService.updateProject(updateproject, project);
            //向微信发送推送
            //获取 该项目  有联系的用户
            ProjectUser projectUser =new ProjectUser();
            projectUser.setPid(pid);
            projectUser.setIsreceive(1);
            List<ProjectUser> prUserList=projectUserService.queryAllProjectUser(projectUser);
            String[] userid_array=new String[prUserList.size()];
            for(int i=0;i<prUserList.size();i++) {
                Integer id = prUserList.get(i).getUserid();
                User user1 = userService.getUserById(id);
                userid_array[i] = user1.getAccount();
            }
            wxMessageService.sendProjectLog(projectLog,null,userid_array);


            bean.setMsg("添加成功");
            bean.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            bean.setMsg(e.getMessage());
            bean.setSuccess(false);
        }
        return bean;
    }


    @ResponseBody
    @RequestMapping("/deleteprLog")
    public JsonBean deleteProject(HttpServletRequest request) {
        JsonBean bean = new JsonBean();
        ProjectLog projectLog =new ProjectLog();
        ProjectLog updateLog=new ProjectLog();
        /**
         * 获取需要修改记录的  项目名
         */
        Integer lid = Integer.parseInt(request.getParameter("lid"));
        updateLog.setIsdelete(1);
        projectLog.setLid(lid);
        projectLogService.updatePrLog(updateLog,projectLog);
        bean.setMsg("删除成功!");
        bean.setSuccess(true);
        return bean;
    }


    /**
     * 查询日志详细信息
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/queryContent")
    public Map<String,Object> queryContent(HttpServletRequest request)
    {
        ProjectLog projectLog =new ProjectLog();
        projectLog.setIsdelete(0);
        //获得查询条件的项目参数
        //获得查询条件的项目更新人
        String lid =request.getParameter("lid");
        /**
         * 浏览器选择的起止时间
         */
        if(lid !=null && !"".equals(lid) )
        {
            projectLog.setLid(Integer.parseInt(lid));
        }
        String contents  = projectLogService.queryPrLogContent(projectLog).getContents();
        Map map =new HashMap();
        map.put("success", true);
        map.put("contents", contents);
        return  map;
    }

    /**
     *   根据lid 查出projectLog对象
     * @param lid
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryProjectLogByLid")
    public ProjectLog queryProjectLogByLid(Integer lid) {
        ProjectLog projectLog =new ProjectLog();
        projectLog.setIsdelete(0);
        if(lid !=null) {
            projectLog.setLid(lid);
        }
        ProjectLog resultProjectLog  = projectLogService.queryPrLogContent(projectLog);
        return resultProjectLog;
    }



}

package com.ctdcn.pds.project.controller;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.project.model.*;
import com.ctdcn.pds.project.service.ProjectLogService;
import com.ctdcn.pds.project.service.ProjectService;
import com.ctdcn.pds.project.service.ProjectUserService;
import com.ctdcn.pds.sys.model.JsonBean;
import com.ctdcn.pds.sys.model.SysCodeConfig;
import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.utils.spring.web.bind.CurrentUser;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;


import org.apache.poi.util.SystemOutLogger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/11.
 */
@Controller
@RequestMapping("/projectManage")
public class ProjectAintenanceController
{

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectLogService projectLogService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserService userService;

    /**
     * 查询项目
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryProject")
    public Map<String,Object> queryProject(HttpServletRequest request, @CurrentUser User user) {
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        Project project = new Project();
        project.setIsdelete(0);
        String pname = request.getParameter("pname");
        String person = request.getParameter("person");
        String pdate_start = request.getParameter("pdate_start");
        String pdate_end = request.getParameter("pdate_end"); 
        String lastDate_start= request.getParameter("lastDate_start");
        String lastDate_end = request.getParameter("lastDate_end");     

        //如果有查询所有项目权限，则不根据用户ID来进行查询
        if (!SecurityUtils.getSubject().isPermitted("project:ProjectAintenance:queryAllProject")) {
            project.setUserId(user.getId());
        }

        if(pname != null && !"".equals(pname) ) {
            project.setPname(pname);
        }
        if(person != null && !"".equals(person)) {
            project.setPerson(person);
        }
        if(pdate_start != null && !"".equals(pdate_start)) {
            project.setSelectSDate(pdate_start);
        }
        if(pdate_end !=null && !"".equals(pdate_end)) {
            project.setSelectEDate(pdate_end);
        }
        if(lastDate_start != null && !"".equals(lastDate_start)) {
            project.setLastSDate(lastDate_start);
        }
        if(lastDate_end !=null && !"".equals(lastDate_end)) {
            project.setLastEDate(lastDate_end);
        }
        //如果没有查询条件即查询出全部 状态为未删除的项目记录
        Integer total = projectService.countProject(project);

        List<Project> projectList = projectService.queryPagingProject(project, page, rows);

        Map projectMap =new HashMap<String,Object>();
        projectMap.put("total",total);
        projectMap.put("rows",projectList);
        return projectMap;
    }

    /**
     * 编辑项目
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/editproject")
    public JsonBean editProject(HttpServletRequest request, int pid, @RequestParam("fuzeId[]") Integer fuzeId[])
    {
        JsonBean bean = new JsonBean();
        //编辑项目表参数
        Project updatepr = new Project();
        Project project = new Project();
        Integer pflag = null;
        //编辑日志表 参数
        ProjectLog updateLog = new ProjectLog();
        ProjectLog projectLog = new ProjectLog();

        //从浏览器获得参数
        String pname = request.getParameter("pname");//新项目名
        String person = request.getParameter("person");//立项人

        String stage = request.getParameter("pflag");
        if(stage != null && !"".equals(stage)) {
            pflag = Integer.parseInt(stage);//项目阶段
        }
        //被编辑记录的  项目名
        String oldPname = request.getParameter("oldpname");//旧项目名
        String pintro = request.getParameter("pintro");//项目简介

        /**
         * 判定传入参数是否为空  不为空就传入到编辑 project 对象中
         */

        try {
            if(pname !=null && !"".equals(pname)){
                /**
                 *  设置  修改对应 日志表中的项目名 方法的参数
                 */
                updateLog.setPname(pname);
                projectLog.setPname(oldPname);
                updatepr.setPname(pname);
            }
            //如果person 是空的 就不设置person 进入更新工程的对象中去
            if(person !=null && !"".equals(person))  {
                updatepr.setPerson(person);
            }
            if(pflag !=null && pflag !=-1) {
                    updatepr.setPflag(pflag);
            }
            if(pintro !=null && !"".equals(pintro))  {
                updatepr.setPintro(pintro);
            }
            //如果 原记录项目名为空 说明没有选中记录
            if(oldPname != null)  {
                project.setPname(oldPname);
                project.setPid(pid);
            }else  {
                throw new RuntimeException("请选择一个项目");
            }
            projectService.updateProject(updatepr,project);
            //如果 新编辑的项目名不等于旧项目名
            if(!oldPname.equals(pname))  {
                //即更新日志表中的项目名
                projectLogService.updatePrLog(updateLog, projectLog);
            }
            //修改项目用户关系表中的负责人(页面方法需要)
            if(fuzeId[0] > -1) {
                projectUserService.editFuzeRen(fuzeId, pid, pname);
            }
            //修改项目用户关系表中的项目名称
            projectUserService.updateProName(pid, pname);
            bean.setMsg("编辑成功");
            bean.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            bean.setSuccess(false);
            bean.setMsg(e.getMessage());
        }
        return bean;
    }

    /**
     * 添加项目
     * @param request
     * @param user
     * @param fuzeId 负责人ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/addproject")
    public JsonBean addProject(HttpServletRequest request, @CurrentUser User user, @RequestParam("fuzeId[]") Integer[] fuzeId) {
        JsonBean bean = new JsonBean();
        Project project = new Project();
        try {
            String pname = request.getParameter("pname"); //项目名
            String pintro = request.getParameter("pintro"); //项目简介
            String person = user.getName(); //获得当前登录人的 姓名
            //获得 pid
            Integer pid=projectUserService.queryPrUserSequcence();
            project.setPid(pid);
            if(pname !=null && !"".equals(pname)) {
                project.setPname(pname);
            }
            if(pintro !=null && !"".equals(pintro)) {
                project.setPintro(pintro);
            }
            if(person !=null && !"".equals(person)) {
                project.setPerson(person);
            }
            //项目状态为新建
            project.setPflag(-1);
            //该项目记录设置为未删除
            project.setIsdelete(0);
            //获取当前系统时间
            project.setPdate(new java.util.Date());
            projectService.addProject(project);
            //将负责人 添加到  项目人员关系表中
            List<ProjectUser> projectUserList =new ArrayList<ProjectUser>();
            for(Integer userId : fuzeId) {
                ProjectUser projectUser = new ProjectUser();
                User fuzeUser = userService.getUserById(userId);
                projectUser.setUserid(userId);
                projectUser.setUsername(fuzeUser.getName());
                projectUser.setPname(pname);
                SysCodeConfig sysCodeConfig = SysCodeVo.getCode("project_user", 1);
                projectUser.setIsreceive(Integer.parseInt(sysCodeConfig.getValue()));
                projectUser.setDepartmentid(fuzeUser.getDepartmentId());
                projectUser.setPid(pid);
                sysCodeConfig = SysCodeVo.getCode("project_user", 2);
                projectUser.setIsResponse(Integer.parseInt(sysCodeConfig.getValue()));
                projectUserList.add(projectUser);
            }
            projectUserService.batchInsertProjectUser(projectUserList);
            bean.setMsg("添加成功");
            bean.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            if("DuplicateKeyException".equals(e.getClass().getSimpleName())){
                bean.setMsg("项目名重复");
            } else {
                bean.setMsg("未知错误");
            }
            bean.setSuccess(false);
        }
        return bean;
    }

    /**
     * 删除项目
     * 软删除。将 该项目记录的isdelete 状态改成 1
     * 0 未删除  1 已删除
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteproject")
    public JsonBean deleteProject(HttpServletRequest request) {
        JsonBean bean = new JsonBean();
        Project project = new Project();
        Project updatePr = new Project();
        /**
         * 获取需要修改记录的  项目名 
         */
        String pid = request.getParameter("pid");
        updatePr.setIsdelete(1);
        java.util.Date date2=new java.util.Date();
        updatePr.setDelDate(date2);
        project.setPid(Integer.parseInt(pid));
        projectService.updateProject(updatePr, project);
       
        bean.setMsg("删除成功");
        bean.setSuccess(true);
        return bean;
    }

    /**
     * 查询当前用户 部门所有项目 显示到撰写项目日志的    combox中
     */
    @ResponseBody
    @RequestMapping("/queryPnameByuser")
    public List queryPnameByuser(@CurrentUser User user) {
        return projectUserService.queryPnameByUserid(user.getId());
    }


    /**
     * 查询 项目人员关系记录
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryPrUser")
    public Map queryPrUser(HttpServletRequest request) {
        String pageStr = request.getParameter("page");
        String rowsStr = request.getParameter("rows");
        Integer page = 0;
        Integer rows = 0;
        if(pageStr != null && !"".equals(pageStr)){
            page = Integer.parseInt(pageStr);
        }
        if(rowsStr != null && !"".equals(rowsStr)){
            rows = Integer.parseInt(rowsStr);
        }
        ProjectUser projectUser =new ProjectUser();
        //获得前台传来的pid  ,userid
        String pid = request.getParameter("pid");
        String user = request.getParameter("user");

        if(pid != null  && !pid.equals("")) {
            projectUser.setPid(Integer.parseInt(pid));
        }
        if(user != null && !user.equals("")) {
            projectUser.setUsername(user);
        }
        //如果没有查询条件  即查所有
        List<ProjectUser> prUserList =projectUserService.queryProjectUser(projectUser,page,rows);
        Integer total =projectUserService.countRecord(projectUser);
        Map prUserMap =new HashMap();
        prUserMap.put("total",total);
        prUserMap.put("rows",prUserList);
        return prUserMap;
    }

    /**
     * 查询当前用户所参与的所有项目
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/projectList")
    public List<ProjectUser> projectList(@CurrentUser User user){
        ProjectUser projectUser = new ProjectUser();
        projectUser.setUserid(user.getId());
        return projectUserService.projectList(projectUser);
    }


    /**
     * 查询项目的参与人员
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryPMUsers")
    public List<ProjectUser> queryPMUsers(Integer pid){
        ProjectUser projectUser = new ProjectUser();
        projectUser.setPid(pid);
        projectUser.setIsResponse(1);
        return projectUserService.queryProjectUser(projectUser);
    }


    /**
     * 添加项目人员关系
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/addprUser")
    public JsonBean addPrUser(HttpServletRequest request,@CurrentUser User user)
    {
        JsonBean bean = new JsonBean();
        ProjectUser projectUser =new ProjectUser();
        String pid=request.getParameter("pid"); //项目名
        String userid=request.getParameter("userid"); //项目简介
        String username=request.getParameter("username");
        String pname =request.getParameter("pname");

        try {
            projectUser.setDepartmentid(user.getDepartmentId());
            projectUser.setIsreceive(1);
            if(pid !=null && !"".equals(pid))
            {
                projectUser.setPid(Integer.parseInt(pid));
            }
            if(userid !=null && !"".equals(userid))
            {
                projectUser.setUserid(Integer.parseInt(userid));
            }
            if(pname !=null && !"".equals(pname))
            {
                projectUser.setPname(pname);

            }
            if(username!=null && !"".equals(username))
            {
                projectUser.setUsername(username);

            }
            SysCodeConfig sysCodeConfig= SysCodeVo.getCode("project_user", 3);
            projectUser.setIsResponse(Integer.parseInt(sysCodeConfig.getValue()));
            projectUserService.insertProjectUser(projectUser);

            bean.setMsg("添加成功");
            bean.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setMsg("添加记录错误");
            bean.setSuccess(false);

        }
        return bean;
    }


    @ResponseBody
    @RequestMapping("/deleteprUser")
    public JsonBean deletePrUser(HttpServletRequest request,@CurrentUser User user,@RequestParam("puid_array[]") Integer[] puid_array) {
        JsonBean bean = new JsonBean();
        try {
            projectUserService.deleteProjectUser(puid_array);
            bean.setMsg("删除成功");
            bean.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setMsg("删除失败");
            bean.setSuccess(false);
        }
        return bean;
    }

    /**
     * 查询项目明细
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProjectDetail")
    public Project getProjectDetail(HttpServletRequest request){
        Project project = null;
        try {
            String pid = request.getParameter("pid");
            Integer intPid = null;
            if(pid != null && !"".equals(pid)){
                intPid = Integer.parseInt(pid);
            } else {
                throw new RuntimeException("id为空");
            }
            project = projectService.getProjectDetail(intPid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    /**
     * 根据项目ID查询此项目的负责人和所有参与人员
     */
    @ResponseBody
    @RequestMapping("/getProUserList")
    public List<ProjectUser> getProUserList(HttpServletRequest request){
        String pid = request.getParameter("pid");
        ProjectUser projectUser = new ProjectUser();
        projectUser.setPid(Integer.parseInt(pid));
        return projectUserService.queryAllProjectUser(projectUser);
    }


    /**
     * 修改项目参与人员
     */
//    @ResponseBody
//    @RequestMapping("/updateProjectUser")
    public JsonBean updateProjectUser(String pid, String pname, Integer[] update_del,  Integer[] update_add){
        JsonBean bean = new JsonBean();
        try {
            //1.删除关系表中的数据
            if(update_del[0] != -1){
                projectUserService.updateDelPro(update_del);
            }
            if (update_add[0] != -1){
                //2.得到新增人员信息
                List<User> userList = userService.getProUser(update_add);
                //3.要新增项目人员关系的数据集合
                List<ProjectUser> projectUserList = new ArrayList<ProjectUser>();
                for (User u : userList){
                    for(int i = 0; i < update_add.length; i++){
                        int uId = u.getId();
                        int upAddId = update_add[i];
                        if(uId == upAddId){
                            ProjectUser proUser = new ProjectUser();
                            proUser.setPid(Integer.parseInt(pid));
                            proUser.setUserid(u.getId());
                            proUser.setDepartmentid(u.getDepartmentId());
                            SysCodeConfig sysCodeConfig = SysCodeVo.getCode("project_user", 1);//设置为接受项目通知
                            proUser.setIsreceive(Integer.parseInt(sysCodeConfig.getValue()));
                            //                    proUser.setPuid(u.getPuid());
                            proUser.setPname(pname);
                            proUser.setUsername(u.getName());
                            sysCodeConfig = SysCodeVo.getCode("project_user", 3);//设置不为项目负责人
                            proUser.setIsResponse(Integer.parseInt(sysCodeConfig.getValue()));
                            projectUserList.add(proUser);
                            continue;
                        }
                    }
                }
                //再新增关系表中没有的数据
                projectUserService.batchInsertProjectUser(projectUserList);
            }
            bean.setMsg("修改成功！");
            bean.setSuccess(true);
        } catch (Exception e) {
            bean.setMsg("修改失败！");
            bean.setSuccess(false);
        }
        return bean;
    }


    /**
     * 修改项目负责人
     */
//    @ResponseBody
//    @RequestMapping("/updateAndDelProIsResponse")
    public JsonBean updateAndDelProIsResponse(String pid, String pname, Integer[] update_del, Integer[] update_add){
        /*
            两个参数（id数组） ，
            数组1：insert    未参与的直接新增成负责人，
                  update    参与的人改成项目负责人
            数组2：delete    项目负责人改成参与人
         */
        JsonBean bean = new JsonBean();
        try {
            //表示不为空   要把负责人改成参与人
            projectUserService.updateAndDelProIsResponse(pid, pname, update_del, update_add);
            bean.setMsg("修改成功");
            bean.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            bean.setSuccess(false);
            bean.setMsg("数据异常");
        }
        return bean;
    }

    /**
     * 检查 维护项目阶段时,1. 项目所处的阶段状态是否在去掉的阶段中
     *                   2. 项目日志的阶段是否有删除的阶段状态
     *                   只有两个参数: 项目ID、项目最终阶段的ID集合
     * @param pid
     */
    @ResponseBody
    @RequestMapping("/changeProjectPflag")
    public JsonBean changeProjectPflag(String pid, @RequestParam("stage[]") Integer[] stage){
        JsonBean bean = new JsonBean();
        StringBuffer buffer = new StringBuffer("");
        boolean change_pro_flag = false;//项目检查默认为修改失败
        boolean change_log_flag = false;//日志检查默认为修改失败

        // 1.查询项目当前的状态是否在 阶段数组里面, 如果项目当前状态不为-1 并在数组里面就可以添加
        Project project = projectService.changeProjectPflag(Integer.parseInt(pid));
        if(project.getPflag() != -1){
            for(Integer s : stage) {
                if(s == project.getPflag()){
                    change_pro_flag = true;
                    break;
                }
            }
        } else {
            change_pro_flag = true;
        }

        //项目检查通过后 才检查日志
        if(change_pro_flag) {
            // 2.查询所有日志的阶段在不在 阶段数组里面, 如果有一个日志的阶段不在, 就提示不能添加
            for (Integer s : stage) {//需要删除的阶段id
                buffer.append(s);
                buffer.append(",");
            }
            String inStage = buffer.substring(0, buffer.length() - 1);
            //如果返回值 > 0 说明有日志所处的阶段是不在 添加的阶段数组里面, 则提示不能添加
            int proLogCount = projectLogService.changeProjectLogPflag(Integer.parseInt(pid), inStage);
            if (proLogCount < 1) {
                change_log_flag = true;
            }
        }
        if(change_pro_flag && change_log_flag){
            bean.setMsg("修改成功");
            bean.setSuccess(true);
        } else {
            bean.setMsg("修改失败,此项目或日志在进行当中...");
            bean.setSuccess(false);
        }
        return bean;
    }


    /**
     * 微信端修改项目
     * @param request
     * @param pid
     * @param pname
     * @param pflag
     * @param pintro
     * @param person
     * @param fuzeId
     * @param noRespUpdateDel
     * @param noRespUpdateAdd
     * @param isRespUpdateDel
     * @param isRespUpdateAdd
     * @return
     */
    @ResponseBody
    @RequestMapping("/weixinEditproject")
    public JsonBean weixinEditproject(HttpServletRequest request, String pid,String oldpname, String pname, String pflag, String pintro, String person,
                                        @RequestParam("fuzeId[]") Integer[] fuzeId,
                                        @RequestParam("noRespUpdate_del[]") Integer[] noRespUpdateDel,
                                        @RequestParam("noRespUpdate_add[]") Integer[] noRespUpdateAdd,
                                        @RequestParam("isRespUpdate_del[]") Integer[] isRespUpdateDel,
                                        @RequestParam("isRespUpdate_add[]") Integer[] isRespUpdateAdd){
        JsonBean bean = new JsonBean();
        try {
            JsonBean bean1 = editProject(request, Integer.parseInt(pid), fuzeId);
            //页面点击的是项目参与人
            JsonBean bean2 = updateProjectUser(pid, pname, noRespUpdateDel,  noRespUpdateAdd);
            //页面点击的是项目负责人
            JsonBean bean3 = updateAndDelProIsResponse(pid, pname,isRespUpdateDel,isRespUpdateAdd);

            if(bean1.getSuccess() == true && bean2.getSuccess() == true && bean3.getSuccess() == true){
                bean.setMsg("修改项目成功");
                bean.setSuccess(true);
            } else {
                bean.setMsg("修改项目失败");
                bean.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            bean.setSuccess(false);
            bean.setMsg("修改项目失败!");
        }
        return bean;
    }




}

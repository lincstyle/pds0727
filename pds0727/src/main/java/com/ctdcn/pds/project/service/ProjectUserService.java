package com.ctdcn.pds.project.service;

import com.ctdcn.pds.organization.dao.UserDAO;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.project.dao.ProjectUserDao;
import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.model.ProjectUser;
import com.ctdcn.pds.sys.model.SysCodeConfig;
import com.ctdcn.pds.sys.model.SysCodeVo;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;

import java.util.*;

/**
 * Created by Triumphant on 2015/6/18.
 */

@Service
public class ProjectUserService
{

    @Autowired
    private ProjectUserDao projectUserDao;
//    private UserService userService;
    @Autowired
    private UserDAO userDao;


    //分页模糊查询 用户的全部(包括已删除项目)
    public List<ProjectUser> queryProjectUser(ProjectUser projectUser,int page,int rows)
    {
        int start = (page-1)*rows;// 开始条数
        int limit = rows;//查询跨度
        RowBounds rowBounds = new RowBounds(start,limit);
        Map map =new HashMap();
        map.put("pruser",projectUser);
       return  projectUserDao.mohuQueryProjectUser(map,rowBounds);
    }

    //分布查询用户的全部项目，不包括已删除项目
    public List<ProjectUser> getProjectUser(ProjectUser projectUser,int page,int rows){
        int start = (page-1)*rows;// 开始条数
        int limit = rows;//查询跨度
        RowBounds rowBounds = new RowBounds(start,limit);
        Map map = new HashMap();
        map.put("pruser",projectUser);
        return  projectUserDao.queryProjectUser(map,rowBounds);
    }

    public List<ProjectUser> queryProjectUser(ProjectUser projectUser)
    {
        Map map =new HashMap();
        map.put("pruser",projectUser);
        return  projectUserDao.queryProjectUser(map);
    }
    //查询 全体记录
    public List<ProjectUser> queryAllProjectUser(ProjectUser projectUser) {
        Map map =new HashMap();
        map.put("pruser",projectUser);
        return  projectUserDao.queryAllProjectUser(map);
    }

    //查询当前用户所有参与的项目（不包括已删除的）
    public List<ProjectUser> projectList(ProjectUser projectUser){
        return  projectUserDao.projectList(projectUser);
    }

    //查询记录条数
    public Integer countRecord(ProjectUser projectUser )
    {
        Map map =new HashMap();
        map.put("pruser", projectUser);
        return projectUserDao.countRecord(map);
    }

    //删除记录
    @Transactional
    public  void deleteProjectUser(Integer[] puid_array)
    {
        projectUserDao.deleteProjectUser(puid_array);
    }


    //添加记录
    @Transactional
    public void insertProjectUser(ProjectUser projectUser)
    {
        projectUserDao.addProjectUser(projectUser);
    }


    //批次添加记录
    @Transactional
    public void batchInsertProjectUser(List<ProjectUser> projectUserList) {
        projectUserDao.batchInsertProjectUser(projectUserList);
    }

    //根据userid去查询 拥有的项目记录
    //查询 当前登录人员所属部门的 项目
    public List queryPnameByUserid(Integer userid)
    {
        List comboxList =new ArrayList();
        Map map = new HashMap();
        map.put("id", "-1");
        map.put("text", "--请选择");
        comboxList.add(map);
        ProjectUser projectUser  =new ProjectUser();

//        System.out.println(SecurityUtils.getSubject().isPermitted("project:Projectlog:queryAllPrLog"));
        if (SecurityUtils.getSubject().isPermitted("project:Projectlog:queryAllPrLog")) {
        	projectUser.setUserid(userid);
        }
        Map puMap =new HashMap();
        puMap.put("pruser", projectUser);
        //查询当前登录用户的部门的 所有项目
        List<ProjectUser>  list  = projectUserDao.queryPnameByUser(puMap);

        //根据条件查询 显示对应的项目出来
        for( ProjectUser p : list) {
            Map prUserMap =new HashMap();
            prUserMap.put("id",p.getPid());
            prUserMap.put("text", p.getPname());
            comboxList.add(prUserMap);
        }
        return comboxList;
    }



    //查询出 PrUserSequcence  下一个值
    public Integer queryPrUserSequcence()
    {
        return  projectUserDao.queryPrUserSequcence();

    }

    /**
     * 编辑项目负责人
     */
    @Transactional
    public void editFuzeRen(Integer[] newFuzeId,Integer pid,String pname)
    {
        Map map =new HashMap();
        map.put("pid",pid);
        //将所有参与该项目人 全部设为不是项目负责人
        ProjectUser prUser =new ProjectUser();
        prUser.setIsResponse(0);
        prUser.setPid(pid);
        projectUserDao.editIsResponse(prUser);
        //查询出 所有参与该项目的人
        List<Integer> oldFuzeId=projectUserDao.queryFuzerenId(map);

        //所有参与该项目的人 同新的项目负责人相比较  新项目负责人不在参与该项目的人员 设为参与该项目
        Collection<Integer> subtractNew = CollectionUtils.subtract(Arrays.asList(newFuzeId),oldFuzeId );
        List<ProjectUser> projectUserList =new ArrayList<ProjectUser>();
        for(Integer userId : subtractNew)
        {
            User fuzeUser=userDao.getUserById(userId);
            ProjectUser projectUser =new ProjectUser();
            projectUser.setUserid(userId);
            projectUser.setUsername(fuzeUser.getName());
            projectUser.setPname(pname);
            SysCodeConfig sysCodeConfig= SysCodeVo.getCode("project_user", 1);
            projectUser.setIsreceive(Integer.parseInt(sysCodeConfig.getValue()));
            projectUser.setDepartmentid(fuzeUser.getDepartmentId());
            projectUser.setPid(pid);
            sysCodeConfig= SysCodeVo.getCode("project_user", 2);
            projectUser.setIsResponse(Integer.parseInt(sysCodeConfig.getValue()));
            projectUserList.add(projectUser);
        }
        batchInsertProjectUser(projectUserList);
        //再将所有提交的新的项目负责人 设置为项目负责人
        for(Integer userId : newFuzeId)
        {
            ProjectUser editNewFuZeRen=new ProjectUser();
            editNewFuZeRen.setPid(pid);
            editNewFuZeRen.setUserid(userId);
            editNewFuZeRen.setIsResponse(1);
            projectUserDao.editIsResponse(editNewFuZeRen);
        }

    }

    //在修改项目时，修改项目人员关系表的项目名
    @Transactional
    public void updateProName(Integer pid, String pname){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pid",pid);
        map.put("pname",pname);
        projectUserDao.updateProName(map);
    }

    //项目参与人员维护--删除人员
    @Transactional
    public void updateDelPro(Integer[] del_puid){
        projectUserDao.updateDelPro(del_puid);
    }

    @Transactional
    public void updateAndDelProIsResponse(String pidStr,String pname,Integer[] update_del,Integer[] update_add){
        /*
            两个参数（id数组） ，
            数组1：insert    未参与的直接新增成负责人，
                  update    参与的人改成项目负责人
            数组2：delete    项目负责人改成参与人
         */
        /*
            获得项目当前的参与人
            1.如果参与人在参数里面，就update成项目负责人
            2.如果参与人不参数里面，就insert成项目负责人
         */
        Integer pid = Integer.parseInt(pidStr);

        //1.参与的人改成项目负责人    2.未参与的直接新增成负责人
        if(update_add[0] != -1){
            List<ProjectUser> proUserUpdateList = new ArrayList();
            List<ProjectUser> proUserAddList = new ArrayList();

            //获得项目当前的参与人
            Map map = new HashMap();
            map.put("pid", pid);
            List<User> userList = userDao.getProjectIsResponse(map);

            //如果项目当前的参与人有需要修改人，就此人添加到要修改的参数list，
            for(User user : userList){
                if(user.getIsResponse() != null){
                    //1.参与的人改成项目负责人
                    if (user.getIsResponse() == 0) {
                        for(int i = 0; i < update_add.length; i++) {
                            int uId = user.getId();
                            int upAddId = update_add[i];
                            if(uId == upAddId){
                                ProjectUser projectUserUpdate = new ProjectUser();
                                projectUserUpdate.setUserid(user.getId());
                                projectUserUpdate.setPuid(pid);
                                SysCodeConfig sysCodeConfig = SysCodeVo.getCode("project_user", 2);
                                projectUserUpdate.setIsResponse(Integer.parseInt(sysCodeConfig.getValue()));
                                proUserUpdateList.add(projectUserUpdate);
                                continue;
                            }
                        }
                    }
                } else {
                    //未参与的直接新增成负责人
                    for(int i = 0; i < update_add.length; i++) {
                        int uId = user.getId();
                        int upAddId = update_add[i];
                        if(uId == upAddId){
                            ProjectUser projectUserAdd = new ProjectUser();
                            projectUserAdd.setUserid(user.getId());
                            projectUserAdd.setPid(pid);
                            projectUserAdd.setDepartmentid(user.getDepartmentId());
                            SysCodeConfig sysCodeConfig = SysCodeVo.getCode("project_user", 1);
                            projectUserAdd.setIsreceive(Integer.parseInt(sysCodeConfig.getValue()));
                            projectUserAdd.setPname(pname);
                            projectUserAdd.setUsername(user.getName());
                            sysCodeConfig = SysCodeVo.getCode("project_user", 2);
                            projectUserAdd.setIsResponse(Integer.parseInt(sysCodeConfig.getValue()));
                            proUserAddList.add(projectUserAdd);
                            continue;
                        }
                    }
                }
            }
            projectUserDao.batchUpdateProResponse(proUserUpdateList);//List<ProjectUser> projectUserList
            projectUserDao.batchInsertProjectUser(proUserAddList);//批量插入   项目负责人  List<ProjectUser> projectUserList
        }
        //项目负责人改成参与人
        if(update_del[0] != -1){
            ProjectUser projectUserDel = new ProjectUser();
            projectUserDel.setPid(pid);
            SysCodeConfig sysCodeConfig = SysCodeVo.getCode("project_user", 3);
            projectUserDel.setIsResponse(Integer.parseInt(sysCodeConfig.getValue()));
            projectUserDel.setPuidArr(update_del);
            projectUserDao.editIsResponse(projectUserDel);

        }

    }
    
    @Transactional
    public void updatePrUser(ProjectUser updatePrUser,ProjectUser prUser)
    {
        Map map =new HashMap();
        map.put("updatePrUser",updatePrUser);
        map.put("PrUser", prUser);
        List list =new ArrayList<Map>();
        list.add(map);
        projectUserDao.batchUpdateTProjectUser(list);
    }

}

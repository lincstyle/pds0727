package com.ctdcn.pds.sys.service;

import com.ctdcn.pds.organization.dao.DepartmentDao;
import com.ctdcn.pds.organization.dao.UserDAO;
import com.ctdcn.pds.organization.model.Department;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.sys.model.SysCodeConfig;
import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.pds.weixin.service.WxDepartmentService;
import com.ctdcn.pds.weixin.service.WxUserService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/8/5.
 */
@Service
public class ValidateWxMessageService
{

    @Autowired
    @Qualifier("wxCpService")
    private WxCpServiceImpl wxCpService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 验证微信用户同系统数据库用户的一致性 使两者保持一致
     * @throws WxErrorException
     */
    @Transactional
    public void ValidateWxMessageUser() throws WxErrorException
    {
        //获取微信所有的用户
        List<WxCpUser> wxCpUserList= wxCpService.userList(1, true, null);
        //存放将微信的用户转为User对象
        List<Map> userMapList=new ArrayList<Map>();
        //查询出所有数据库表中的用户
        List<User> userList=userDAO.queryAllUser(new User());
        Map<String,User> existUserMap = new HashMap<String, User>();
        for (User user : userList){
            existUserMap.put(user.getAccount(),user);
        }

        //存在添加系统数据库用户对象的List
        List<User> insertUserList=new ArrayList<User>();

        for(WxCpUser wxCpUser : wxCpUserList)
        {

            //将微信的用户转为User对象
            User user = WxUserService.user(wxCpUser);
            Map map =new HashMap();
            map.put("target",user);
            userMapList.add(map);
            if(!existUserMap.containsKey(user.getAccount()))
            {
                user.setPwd(SysCodeVo.getCode("user_sys", 2).getValue());
                user.setIsUsed(Integer.parseInt(SysCodeVo.getCode("user_sys",1).getValue()));
                user.setRole(Integer.parseInt(SysCodeVo.getCode("user_sys",3).getValue()));
                java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
                //date.toString().replaceAll("-", "/");
                user.setCreateDate(date);
                user.setId(userDAO.getUserSeqId());
                insertUserList.add(user);
            }
        }

        //批次更新系统数据库用户  同微信用户同步
        userDAO.batchUpdateUser(userMapList);
        //批量添加系统数据库不存在，微信用户存在的用户
        userDAO.batchInsertUser(insertUserList);
        //去更新系统数据库 sys_user表中的部门名
        userDAO.updateSysUserDepartmentName();
    }


    /**
     * 验证微信部门同系统数据库部门的一致性 使两者保持一致
     * @throws WxErrorException
     */
    @Transactional
    public void ValidateWxDepartMent() throws WxErrorException
    {
        //获取所有部门对象
        List<WxCpDepart> wxCpDepartList= wxCpService.departGet();
        //存放将微信部门对象转换为系统部门对象
        List<Map> departmentMapList=new ArrayList<Map>();
        //查询出系统数据库中所有的部门对象
        Map queryDeMap=new HashMap();
        queryDeMap.put("department",new Department());
        List<Department> sysDepartmentList=departmentDao.queryDepartment(queryDeMap);
        //存放添加系统数据库部门对象的List
        List<Department>  insertDepartment=new ArrayList<Department>();

        for(WxCpDepart wxCpDepart :wxCpDepartList)
        {
            Department updateDe = WxDepartmentService.toWx(wxCpDepart);
            boolean isNotExistDepartment =true;

            for(Department sysDepartment : sysDepartmentList)
            {
                if(sysDepartment.getDepartmentId().equals(wxCpDepart.getId()))
                {
                    isNotExistDepartment=false;
                }
            }
            //将微信部门对象转换成系统部门对象
            if(isNotExistDepartment)
            {
                insertDepartment.add(updateDe);
            }
            Department department=new Department();

            department.setDepartmentId(updateDe.getDepartmentId());
            Map map =new HashMap();

            map.put("updateDe",updateDe);
            map.put("department",department);
            departmentMapList.add(map);
        }

        //批量更新系统数据库部门  同微信用户部门
        departmentDao.batchUpdateDepartment(departmentMapList);
        //批量添加系统数据库不存在， 微信部门存在的部门
        departmentDao.batchInsertDepartment(insertDepartment);
        //去更新sys_department中的宽度
        departmentDao.updateWidth();
        //去更新sys_department表中是否有子节点
        departmentDao.updateDepartmentHasChildren();
    }

    @Transactional
    public User ValidateWxUser(String userId, String deviceId) throws WxErrorException{
    	User user = userService.queryUserByAccount(userId);
    	if(user!=null){
    		if(!deviceId.equals(user.getDeviceId())){
    			user.setDeviceId(deviceId);
    			userDAO.editUser_2(user);
    		}
    		return user;
    	}else{
    		return new User();
    	}
    }




}

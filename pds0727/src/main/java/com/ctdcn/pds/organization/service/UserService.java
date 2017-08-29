package com.ctdcn.pds.organization.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.model.ProjectUser;
import com.ctdcn.pds.project.service.ProjectLogService;
import com.ctdcn.pds.project.service.ProjectService;
import com.ctdcn.pds.project.service.ProjectUserService;
import com.ctdcn.pds.sys.model.SysCodeConfig;
import com.ctdcn.pds.sys.model.SysCodeVo;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctdcn.pds.organization.dao.DepartmentDao;
import com.ctdcn.pds.organization.dao.UserDAO;
import com.ctdcn.pds.organization.model.Department;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.weixin.service.WxUserService;

/**
 * 用户操作类.
 * @author 张靖.
 *         2015-06-04 9:24
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private WxUserService wxUserService;
	@Autowired
	private ProjectUserService projectUserService;
	@Autowired
    private ProjectService projectService;
	@Autowired
    private ProjectLogService projectLogService;

    /**
     * 查询用户列表.
     * @param user
     * @param orderCols
     * @param order
     * @param rowBounds
     * @return
     */
    public List<User> queryListOrder(User user,String[] orderCols,String order,RowBounds rowBounds){
        return userDAO.queryUserList(user,orderCols,order,rowBounds);
    }

    /**
     * 根据账户名查询用户对象
     * @param account
     * @return
     */
    public User queryUserByAccount(String account){
        User user = null;
        User userCond = new User();
        userCond.setAccount(account);
        List<User> userList = userDAO.queryUserList(userCond,null,null,null);
        if(userList != null && userList.size() == 1){
            user = userList.get(0);
        }
        return user;
    }

    /**
     * 显示部门下的所有员工(包括子部门)		
     * @param departmentId 部门ID
     */
	public List<User> getDepartmentUser(Integer departmentId, int currentPage, int pageSize) {
		int start = (currentPage-1)*pageSize;// 开始条数
		int limit = pageSize;//查询跨度  
		RowBounds rowBounds = new RowBounds(start,limit);
		StringBuffer departIds = new StringBuffer("");
		Department department = departmentDao.getDepartment(departmentId);
		departIds.append(department.getDepartmentId());
		if(department.getHasChildren() != 0){
			departIds = getChildrenId(departIds, departmentId);
		}
		return userDAO.getDepartmentUser(departIds.toString(),rowBounds);
	}

	/**
	 * 显示部门下的所有员工(包括子部门)的总人数
	 * @param departmentId
	 * @return
	 */
	public Integer getDepartmentUserTotal(Integer departmentId){
		StringBuffer departIds = new StringBuffer("");
		Department department = departmentDao.getDepartment(departmentId);
		departIds.append(department.getDepartmentId());
		if(department.getHasChildren() != 0){
			departIds = getChildrenId(departIds,departmentId);
		}
		return userDAO.getDepartmentUserTotal(departIds.toString());
	}
	
	/**
	 * 得到所有的子部门的id
	 * @param departIds
	 * @param departmentId
	 * @return
	 */
	public StringBuffer getChildrenId(StringBuffer departIds, int departmentId){
		List<Department> departmentList = departmentDao.getDepartmentList(departmentId);
		for (Department depart : departmentList) {
			departIds.append(","+depart.getDepartmentId());
			if(depart.getHasChildren() != 0){
				getChildrenId(departIds, depart.getDepartmentId());
			}
		}
		return departIds;
	}

	/**
	 * 删除用户
	 * @param
	 * @throws WxErrorException 
	 */
	@Transactional
	public void delUset(int userId,String account) throws WxErrorException {
		wxUserService.deleteUser(account);
		userDAO.delUser(userId);
	}

	/**
	 * 添加用户
	 * @param user
	 * @throws WxErrorException 
	 */
	@Transactional
	public void addUset(User user) throws WxErrorException {
		Integer seqId = userDAO.getUserSeqId();
	    user.setId(seqId);
	    wxUserService.addUser(user);
		userDAO.addUser(user);
	}

	/**
	 * 根据ID得到用户
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId) {
		return userDAO.getUserById(userId);
	}

	/**
	 * 修改用户
	 * @param user
	 * @throws WxErrorException 
	 */
	@Transactional
	public void editUser(User user) throws WxErrorException {
		User user_old = userDAO.getUserById(user.getId());
		wxUserService.editUser(user);
		Department department = departmentDao.getDepartment(user.getDepartmentId());
		user.setDepartmentName(department.getDepartmentName());
		userDAO.editUser_2(user);
		//如果用户名被修改，做以下同步
		if(!user_old.getName().equals(user.getName())){
			//同步t_project表person,lastperson
			for(int i=0;i<2;i++){
				Project project = new Project();
		        Project updatePr = new Project();
		        if(i == 0){
		        	project.setPerson(user_old.getName());
			        updatePr.setPerson(user.getName());
		        }else if(i == 1){
		        	project.setLastPerson(user_old.getName());
			        updatePr.setLastPerson(user.getName());
		        }
		        
				projectService.updateProject(updatePr, project);
			}
			//同步t_project_log表
			ProjectLog projectLog = new ProjectLog();
	        ProjectLog updatePrLog = new ProjectLog();
	        projectLog.setPerson(user_old.getName());
	        updatePrLog.setPerson(user.getName());
	        projectLogService.updatePrLog(updatePrLog,projectLog);
	        //同步t_project_user表
	        ProjectUser prUser = new ProjectUser();
	        ProjectUser updatePrUser = new ProjectUser();
	        prUser.setUserid(user_old.getId());
	        updatePrUser.setUsername(user.getName());
	        projectUserService.updatePrUser(updatePrUser, prUser);
		}
		
		
		
	}

	/**
	 * 查询用户(模糊查询)
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user, int currentPage, int pageSize){
		int start = (currentPage-1)*pageSize;// 开始条数
		int limit = pageSize;//查询跨度  
		RowBounds rowBounds = new RowBounds(start,limit);
		return userDAO.findUser(user, rowBounds);
	}
	
	//查询所有user 返回给combox
	public List queryAllUser(String parameter)
	{
		List comboxList =new ArrayList();

		Map map = new HashMap();
		map.put("id", "-1");
		map.put("text", "--请选择");

		comboxList.add(map);
		User user =new User();
		user.setIsUsed(1);
		//查询当前登录用户的部门的 所有项目
		List<User> list= new ArrayList<User>();
		//如果参数为0即表示去查询所有人员。不为0表示去查询还未参与该项目的人员
		if("0".equals(parameter))
		{
			list  = userDAO.queryAllUser(user);
		}else
		{
			Map parameterMap =new HashMap();
			parameterMap.put("pid",Integer.parseInt(parameter));
			list=userDAO.queryPrUser(parameterMap);
		}
		//根据条件查询 显示对应的项目出来
		for( User u : list)
		{
			Map userMap =new HashMap();
			userMap.put("id",u.getId());
			userMap.put("text",u.getName());
			comboxList.add(userMap);
		}
		return comboxList;
	}


	/**
	 * 查询所有用户 特别标注为该项目 项目负责人的
	 * @param parameter
	 * @return
	 */
	public List queryIsFuZeRen(String parameter)
	{
		List fuZeList =new ArrayList();
		ProjectUser projectUser=new ProjectUser();
		projectUser.setPid(Integer.parseInt(parameter));
		projectUser.setIsResponse(1);
		List<ProjectUser> projectUserlist =projectUserService.queryAllProjectUser(projectUser);
		for(ProjectUser prUser : projectUserlist)
		{
			fuZeList.add(prUser.getUserid().toString());
		}
		return fuZeList;
	}

	/**
	 * 模糊查询用户时需要的用户总数量
	 * @return
	 */
	public Integer getFindUserTotal(User user) {
		return userDAO.getFindUserTotal(user);
	}

	/**
	 * 保存用户图片的url
	 * @param
	 */
	@Transactional
	public void saveUploadImg(User user) {
		userDAO.saveUploadImg(user);
	}

	/**企业通讯录名单
	 * @param pid
	 * @return
	 */
	public List<User> getCommList(Integer pid, String inputParam, String type){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<User> userList = new ArrayList<User>();
		paramMap.put("pid", pid);
		paramMap.put("inputParam", inputParam);
		if(!type.equals("isResponse") && !type.equals("no_response")) {
			throw new RuntimeException("参数异常");
		}
		if (type.equals("isResponse")) {
			SysCodeConfig sysCodeConfig = SysCodeVo.getCode("project_user", 2);
			paramMap.put("isResponse", Integer.parseInt(sysCodeConfig.getValue()));
			userList = userDAO.getProjectIsResponse(paramMap);
		} else if (type.equals("no_response")) {
			SysCodeConfig sysCodeConfig = SysCodeVo.getCode("project_user", 3);
			paramMap.put("no_response", Integer.parseInt(sysCodeConfig.getValue()));
			userList = userDAO.getCommList(paramMap);
		}
		return userList;
	}

	/**
	 * 得到新增的项目参与人员信息
	 * @param userIds
	 * @return
	 */
	public List<User> getProUser(Integer[] userIds){
		return userDAO.getProUser(userIds);
	}


	//新建项目添加负责人
	public List<User> getProResponse(String inputParam){
		User user = new User();
		user.setName(inputParam);
		user.setIsUsed(1);
		return userDAO.getProResponse(user);
	}


}

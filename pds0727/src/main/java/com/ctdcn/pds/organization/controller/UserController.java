package com.ctdcn.pds.organization.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.exception.WxErrorException;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ctdcn.pds.authority.model.ReturnBean;
import com.ctdcn.pds.authority.model.Role;
import com.ctdcn.pds.authority.service.RoleService;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.model.UserConfig;
import com.ctdcn.pds.organization.service.DepartmentService;
import com.ctdcn.pds.organization.service.UserCacheService;
import com.ctdcn.pds.organization.service.UserService;
import com.ctdcn.pds.sys.model.JsonBean;
import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.utils.PinYinGenerator;
import com.ctdcn.utils.consts.WeixinError;
import com.ctdcn.utils.spring.web.bind.CurrentUser;

@Controller
@RequestMapping("/userManager")
public class UserController {
	
	@Autowired
	private UserConfig userConfig;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserCacheService userCacheService;


	
	@ResponseBody
	@RequestMapping("/getDepartmentUser")
	public Map<String, Object> getDepartmentUser(HttpServletRequest request){
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		
		String name = request.getParameter("name");
		String account = request.getParameter("account");
		String gender = request.getParameter("gender");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String weixin = request.getParameter("weixin");
		
		String depId = request.getParameter("id");
		Integer departmentId = 1;
		if(!depId.equals("null")){
			departmentId = Integer.parseInt(depId);
		}
		
		String find_departmentId = request.getParameter("departmentId");
//		String departmentName = request.getParameter("departmentName");
		String role = request.getParameter("role");
		
		User user = new User();
		Integer total = null;
		if(name != null && !"".equals(name.trim())){
			user.setName(name.trim());
		}
		if(account != null && !"".equals(account.trim())){
			user.setAccount(account.trim());
		}
		if(gender != null){
			user.setGender(Integer.parseInt(gender));
//		} else {
//			user.setGender(0);
		}
		if(tel != null && !"".equals(tel)){
			user.setTel(tel);
		}
		if(email != null && !"".equals(email)){
			user.setEmail(email);
		}
		if(weixin != null && !"".equals(weixin.trim())){
			user.setWeixin(weixin);
		}
		if(role != null && !role.equals("")){
			user.setRole(Integer.parseInt(role));
		}
		if(find_departmentId != null && !find_departmentId.equals("")){
			departmentId = Integer.parseInt(find_departmentId);
		}
		if(departmentId == 1){
			user.setDepartmentId(null);
		} else {
			user.setDepartmentId(departmentId);
		}
		total = userService.getFindUserTotal(user);

		List<User> userList = userService.findUser(user, currentPage, pageSize);
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("total", total);
		userMap.put("rows", userList);	
		return userMap;
	}
	
	/**
	 * 删除用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/delUser")
	public ReturnBean delUser(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			String user_id = request.getParameter("user_id");
			String account = request.getParameter("account");
			User user = userService.getUserById(Integer.parseInt(user_id));
			userService.delUset(Integer.parseInt(user_id),account);
			userCacheService.delUser(user);
			rb.setState("ok");
			rb.setMessage("删除用户成功!");
		} catch (WxErrorException e) {
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("删除用户失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 添加用户
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public ReturnBean addUser(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			String name = request.getParameter("name");
			String account = request.getParameter("account");
			String pwd = request.getParameter("pwd");
			String gender = request.getParameter("gender");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String weixin = request.getParameter("weixin");
			String departmentId = request.getParameter("departmentId");
			String departmentName = request.getParameter("departmentName");
			String role = request.getParameter("role");

			User user = new User();
			user.setName(name.trim());
			user.setPwd(pwd.trim());
			if(account != null && !"".equals(account.trim())){
				user.setAccount(account.trim());
			}
			if(gender != null){
				user.setGender(Integer.parseInt(gender));
			} else {
				user.setGender(0);
			}
			if(tel != null && !"".equals(tel.trim())){
				user.setTel(tel.trim());
			}
			if(email != null && !"".equals(email.trim())){
				user.setEmail(email.trim());
			}
			if(weixin != null && !"".equals(weixin.trim())){
				user.setWeixin(weixin.trim());
			}
			user.setDepartmentId(Integer.parseInt(departmentId));
			user.setDepartmentName(departmentName);
			user.setQuanpin(PinYinGenerator.formatToPinYin(user.getName()));//全拼
			user.setJianpin(PinYinGenerator.formatAbbrToPinYin(user.getName()));//简拼

//			if(departmentId != "0" && !"".equals(departmentId)){
//				int departId = Integer.parseInt(departmentId);
//				Department depart = departmentService.getDepartment(departId);
//				user.setDepartmentName(depart.getDepartmentName());
//			}
			user.setRole(Integer.parseInt(role));
			
			java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
			user.setCreateDate(date);
			Integer userState = Integer.parseInt(SysCodeVo.getCode("user_sys", 1).getValue());
			user.setIsUsed(userState);//0表示未激活,1表示已激活
			userService.addUset(user);
			userCacheService.putUser(user);
			rb.setState("ok");
			rb.setMessage("添加用户成功!");
		} catch (WxErrorException e) {
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("添加用户失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 根据ID得到用户信息
	 * @param request
	 * @return      @CurrentUser User user
	 */
	@ResponseBody
	@RequestMapping("/getUserById")
	public User getUserById(HttpServletRequest request){
		User user = new User();
		try {
			Integer userId = Integer.parseInt(request.getParameter("user_id"));
			user = userService.getUserById(userId);
			user.setRoleOb(roleService.getRoleById(user.getRole()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 修改用户
	 * @param request
	 * @return
	 */
	@ResponseBody    
	@RequestMapping("/editUser")
	public ReturnBean editUser(HttpServletRequest request, User user,@CurrentUser User currentUser){
		ReturnBean rb = new ReturnBean();
		try{
			String departmentId = request.getParameter("departmentId");
			if(departmentId != null && !"".equals(departmentId)){
				user.setDepartmentId(Integer.parseInt(departmentId));
			}
			user.setQuanpin(PinYinGenerator.formatToPinYin(user.getName()));//全拼
			user.setJianpin(PinYinGenerator.formatAbbrToPinYin(user.getName()));//简拼
			userService.editUser(user);
			//如果修改的是当前用户，则改变session里面的用户资料
			if(user.getId().equals(currentUser.getId())){
				currentUser.setId(user.getId());		//
				currentUser.setName(user.getName());	//
				currentUser.setAccount(user.getAccount());//
				currentUser.setGender(user.getGender());//
				currentUser.setTel(user.getTel());		//
				currentUser.setEmail(user.getEmail());	//
				currentUser.setWeixin(user.getWeixin());//
				currentUser.setDepartmentId(user.getDepartmentId());//
				currentUser.setRole(user.getRole());	//
				currentUser.setQuanpin(PinYinGenerator.formatToPinYin(user.getName()));//全拼
				currentUser.setJianpin(PinYinGenerator.formatAbbrToPinYin(user.getName()));//简拼
				SecurityUtils.getSubject().getSession().setAttribute(User.USER_SESSIN_KEY,currentUser);
			}
			//userCacheService.updateUser(user);
			userCacheService.delUser(user);
			userCacheService.putUser(user);
			rb.setState("ok");
			rb.setMessage("修改用户成功!");
		} catch (WxErrorException e) {
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("修改用户失败!");
			e.printStackTrace();
		}
		return rb;
	}

	@ResponseBody
	@RequestMapping("/queryAllUser")
	public List queryAllUser(String parameter)
	{

		return userService.queryAllUser(parameter);
	}


	@ResponseBody
	@RequestMapping("/queryIsFuZeRen")
	public JsonBean queryIsFuZeRen(String pid)
	{

		JsonBean bean =new JsonBean();
		try
		{
			List fuZeList =userService.queryIsFuZeRen(pid);
			bean.setSuccess(true);
			bean.setObj(fuZeList);

		}catch (Exception e)
		{
			e.printStackTrace();
			bean.setSuccess(false);
			bean.setMsg(e.getMessage());

		}



		return  bean;

	}




	/**
	 * 获取当前用户资料 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping("/getCurrentUser")
	public String getCurrentUser(HttpServletRequest request,@CurrentUser User user){
		try {
			Role role = roleService.getRoleById(user.getRole());
			request.setAttribute("id", user.getId());
			request.setAttribute("name",user.getName());
			request.setAttribute("account", user.getAccount());
			request.setAttribute("gender", user.getGender());
			request.setAttribute("tel", user.getTel());
			request.setAttribute("email", user.getEmail());
			request.setAttribute("weixin", user.getWeixin());
			request.setAttribute("departmentId", user.getDepartmentId());
			request.setAttribute("departmentName", user.getDepartmentName());
			request.setAttribute("roleId", role.getRoleId());
			request.setAttribute("role", role.getRoleName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "personalInfo/modifyMyData";
	}
	
	/**
	 * 修改个人资料
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/modifyMyData")
	public ReturnBean modifyMyData(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			String userId = request.getParameter("userId");
			String departmentId = request.getParameter("departmentId");
			String name = request.getParameter("username");
			String account = request.getParameter("account");
			String gender = request.getParameter("gender");
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String weixin = request.getParameter("weixin");
			String roleId = request.getParameter("roleId");
//			String departmentName = request.getParameter("departmentName");
			
			User user = new User();
			user.setId(Integer.parseInt(userId));
			user.setName(name);
			user.setAccount(account);
			user.setGender(Integer.parseInt(gender));
			user.setTel(tel);
			user.setEmail(email);
			user.setWeixin(weixin);
			user.setDepartmentId(Integer.parseInt(departmentId));
			user.setRole(Integer.parseInt(roleId));
			user.setQuanpin(PinYinGenerator.formatToPinYin(name));//全拼
			user.setJianpin(PinYinGenerator.formatAbbrToPinYin(name));//简拼

			userService.editUser(user);
//			把新的User对象设置到session中
			user.setRoleOb(roleService.getRoleById(user.getRole()));
			SecurityUtils.getSubject().getSession().setAttribute(User.USER_SESSIN_KEY,user);
			
			userCacheService.delUser(user);
			userCacheService.putUser(user);
			rb.setState("ok");
			rb.setMessage("修改用户成功!");
		} catch (WxErrorException e) {
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("修改用户失败!");
			e.printStackTrace();
		}
		
		return rb;
	}
	
	/**
	 * 得到原始密码
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeOldPassword")
	public ReturnBean changeOldPassword(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		String oldPassword = request.getParameter("password");
		String userId = request.getParameter("user_id");
		if(userId == null || userId.equals("")){
			throw new RuntimeException("用户ID为空");
		}
		User user = userService.getUserById(Integer.parseInt(userId));
		if(user.getPwd().equals(oldPassword)){
			rb.setState("yes");
		} else {
			rb.setState("no");
			rb.setMessage("输入的旧密码不正确");
		}
		return rb;
	}
	
	/**
	 * 修改密码
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/updatePassword")
	public ReturnBean updatePassword(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			String userId = request.getParameter("user_id");
			String newPassword = request.getParameter("new_password_1");
			User user = userService.getUserById(Integer.parseInt(userId));
			user.setPwd(newPassword);
			userService.editUser(user);
			rb.setState("ok");
			rb.setMessage("修改成功！");
		} catch (WxErrorException e) {
			int errorCode = e.getError().getErrorCode();
			rb.setState("error");
			rb.setMessage(SysCodeVo.getCode(WeixinError.TYPE_CODE,errorCode).getValue());
		} catch (Exception e) {
			e.printStackTrace();
			rb.setState("no");
			rb.setMessage("出现未知错误，修改失败！");
		}
		return rb;
	}
	
	/**
	 * 用户上传自己图片
	 * @param request
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/uploadImg")
	public ReturnBean uploadFile(HttpServletRequest request, @RequestParam("uploadfile") CommonsMultipartFile file) {
		ReturnBean rb = new ReturnBean();
		try {
			String userId = request.getParameter("upload_Id");
			if (!file.isEmpty()) {
				String upFileName = file.getOriginalFilename();//文件名
				// 取文件格式后缀名
				int index = upFileName.indexOf(".");
				String type = file.getOriginalFilename().substring(index);
			
				String fName = upFileName.substring(0, upFileName.lastIndexOf("."));
				// 原始文件名 + 当前时间戳作为文件名
				String fileName = fName + "_" + System.currentTimeMillis() + type;
				//userConfig是service.xml里面的bean,配置了上传图片的路径
				String imgUrl = userConfig.getImgUrl();
				String path = imgUrl + fileName;
				File destFile = new File(path);
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				
				//向数据库user表img_url字段插入值
				User user = new User();
				user.setId(Integer.parseInt(userId));
				user.setImgUrl(path);
				userService.saveUploadImg(user);
				
				rb.setState("ok");
				rb.setMessage("上传图片成功");
			}
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("上传图片失败,未知错误!");
			e.printStackTrace();
		}
		return rb;
	}

	/**
	 * 自动提示，联想用户名
	 * @param keywords
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/autoCompleteUser")
	public List<Map> autoCompleteUser(String keywords){
		return userCacheService.autoCompleteName(keywords);
	}

	/**
	 *
	 * 自动提示，联想用户名
	 * @param q
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/autoCompleteUserEasyui")
	public List<Map> autoCompleteUserEasyui(String q){
		return userCacheService.autoCompleteName(q);
	}


	/**
	 * 通讯录名单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCommList")
	public List<User> getCommList(HttpServletRequest request){
		String type = request.getParameter("type");
		String inputParam = request.getParameter("param");
		return userService.getCommList(Integer.parseInt(request.getParameter("pid")), inputParam,type);
	}

	/**
	 * 新增项目时添加项目负责人
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getProResponse")
	public List<User> getProResponse(HttpServletRequest request){
		String inputParam = request.getParameter("param");
		return userService.getProResponse(inputParam);
	}
}

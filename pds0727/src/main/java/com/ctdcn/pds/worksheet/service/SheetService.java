package com.ctdcn.pds.worksheet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctdcn.pds.organization.dao.UserDAO;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.worksheet.dao.SheetDao;
import com.ctdcn.pds.worksheet.model.Sheet;

@Service
public class SheetService {

	@Autowired
	public SheetDao sheetDao;
	
	@Autowired
	public UserDAO userDAO;
	//新增联络单
	public void addSheet(Sheet sheet){
		sheetDao.addSheet(sheet);
	}
	
	//联络单审核、分发、处理
	public void updateSheet(Sheet sheet){
		sheetDao.updateSheet(sheet);
	}
	
	//联络单详情
	public Sheet querySheet(Integer id){
		return sheetDao.querySheet(id);
	}
	
	//删除联络单
	public void deleteSheet(Integer id){
		sheetDao.deleteSheet(id);
	}
	
	//联络单跟踪列表
	public List<Sheet> sheetList(Sheet sheet,Integer userId,int page,int rows){
		int start = (page-1)*rows;// 开始条数
        int limit = rows;//查询跨度
        RowBounds rowBounds = new RowBounds(start,limit);
        Map map = new HashMap();
        map.put("sheet", sheet);
        map.put("userId", userId);
		return sheetDao.sheetList(map, rowBounds);
	}
	
	//查询当日最大联络单号
	public String maxNo(){
		return sheetDao.maxNo();
	}

//根据条件查询联络单条数
	public Integer countSheet(Sheet sheet, Integer userId){
		Map map = new HashMap();
        map.put("sheet", sheet);
        map.put("userId", userId);
		Integer count = sheetDao.countSheet(map).size();
        return  count;
	}


public List queryDeptUser(Integer departmentId)
{
	List comboxList =new ArrayList();

	Map map = new HashMap();
	map.put("id", "-1");
	map.put("text", "--请选择");

	comboxList.add(map);
	User user =new User();
	if(departmentId !=null){
		user.setDepartmentId(departmentId);
	}
	List<User> list= new ArrayList<User>();
	list  = userDAO.queryAllUser(user);
	for( User u : list)
	{
		Map userMap =new HashMap();
		userMap.put("id",u.getId());
		userMap.put("text",u.getName());
		comboxList.add(userMap);
	}
	return comboxList;
	}
}
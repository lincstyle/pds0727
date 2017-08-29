package com.ctdcn.pds.organization.service;

import com.ctdcn.pds.organization.model.Department;
import com.ctdcn.pds.organization.dao.DepartmentDao;
import com.ctdcn.pds.organization.dao.UserDAO;

import com.ctdcn.pds.weixin.service.WxDepartmentService;
import me.chanjar.weixin.common.exception.WxErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/9.
 */
@Service
public class DepartmentService
{
	@Autowired
	private WxDepartmentService wxDepartService;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private UserDAO userDao;


    //查询资源表
    public List<Department> queryDepartment(Department department)
    {

        Map departmentMap=new HashMap();
        //加入到map
        departmentMap.put("department",department);
        //查询出同一父节点的所有记录
        List<Department> querylist=departmentDao.queryDepartment(departmentMap);
        return querylist;

    }

    //删除资源表
    @Transactional
    public void deleteDepartment(int departmentId)
    {
        Department department = new Department();
        department.setDepartmentId(departmentId);
        Map departmentMap=new HashMap();
        departmentMap.put("department", department);
        //删除 节点为该id的记录
        departmentDao.deleteDepartment(departmentMap);
    }

    //编辑资源表
    @Transactional
    public void updateDepartment( Department updateDepartment,Department department)
    {
        Map departmentMap=new HashMap();
        //添加 包含修改的信息 资源对象到map
        departmentMap.put("updateDe",updateDepartment);
        //添加 原资源对象到map
        departmentMap.put("department",department);
        List updateList=new ArrayList<Map<String,Object>>();
        updateList.add(departmentMap);
        //更新 资源记录
        departmentDao.batchUpdateDepartment(updateList);
    }

    //添加部门
    @Transactional
    public void addDepartment(Department department) throws WxErrorException{
        Integer seqId = departmentDao.getSequenceId();
        department.setDepartmentId(seqId);
        Integer wxReturnId = wxDepartService.addDepart(department);
        department.setDepartmentId(wxReturnId);
    	departmentDao.insertDepartment(department);
    	
        //修改上级部门
        if(department.getWidth() != 1){
        	updateParentDepartment(department.getParentId(),1);
        }
    }

    /**
     * 部门管理主页面显示部门树
     * @param parentId
     * @return
     */
	public List<Department> getDepartmentTree(Integer parentId) {
		return departmentDao.getDepartmentTree(parentId);
	}

	/**
	 * 得到父部门的级别
	 * @param parentId
	 * @return
	 */
	public int getParentWidth(int parentId) {
		Department department = departmentDao.getDepartment(parentId);
		if(department == null){
			return 1;
		}
		if(department.getWidth() == null){
			return 0;
		}
		return department.getWidth();
	}

	
	/**
	 * 得到部门的下拉列表
	 * @return
	 */
	public List<Department> getDepartmentList() {
		return departmentDao.getDepartmentList(null);
	}

	
	/**
	 * 得到指定的部门
	 * @param id
	 * @return
	 */
	public Department getDepartment(int id){
		return departmentDao.getDepartment(id);
	}

	/**
	 * 修改部门
	 * @param department
	 * @throws WxErrorException 
	 */
	@Transactional
	public void editDepartment(Department department,int oldParentId, String newDepartName, String oldDepartName) throws WxErrorException{
//		Integer sort = null;
		wxDepartService.editDepart(department);
		//修改所选择的部门
		departmentDao.editDepartment(department);
		//如果新的部门名字不等于旧的部门名字,那么就要修改用户表里面的部门名字字段
		if(!newDepartName.equals(oldDepartName)){
			Map<String,String> departNameMap = new HashMap<String, String>();
			departNameMap.put("newDepartName", newDepartName);
			departNameMap.put("oldDepartName", oldDepartName);
			userDao.updateUserDepartmentName(departNameMap);
		}
		if(department.getParentId() != oldParentId){
			//修改所选择部门的新上级部门
			if(department.getParentId() != 0){
				updateParentDepartment(department.getParentId(),1);//123
			}
			//修改所选择部门的原上级部门
			int childrenCount = departmentDao.getChildrenCount(oldParentId);
			if(childrenCount == 0){//如果原上级部门没有子级了,就修改属性
				updateParentDepartment(oldParentId, 0);//162
			}
			//修改所选择部门的子级部门(修改级别属性)
			if(department.getHasChildren() == 1 ){
				updateChildrenDepartment(department);
			}
		}
	}
	
	/**
	 * 修改下级部门和下下级部门的级别
	 * @param department
	 */
	@Transactional
	public void updateChildrenDepartment(Department department){
		List<Department> childrenDepartmentList = departmentDao.getDepartmentList(department.getDepartmentId());
		if(!childrenDepartmentList.isEmpty()){
			int width = department.getWidth();
			for (Department depart : childrenDepartmentList) {
				depart.setWidth(width+1);
				departmentDao.editDepartment(depart);
				updateChildrenDepartment(depart);
			}
		}
//		updateChildrenDepartment(department);
	}
	
	/**
	 * 修改上级部门(是否有子级部门属性)
	 * @param id
	 * @param hasChildren
	 */
	@Transactional
	public void updateParentDepartment(int id,int hasChildren){
		Department parentDepartment = departmentDao.getDepartment(id);
		if(parentDepartment != null){
			parentDepartment.setHasChildren(hasChildren);
			departmentDao.editDepartment(parentDepartment);
			
		}
	}
	
	/**
	 * 删除部门
	 * @param departmentId
	 * @throws WxErrorException 
	 */
	@Transactional
	public void delDepartment(int departmentId, String isleaf, int delDepartParentId) throws WxErrorException{
		wxDepartService.removeDepart(departmentId);
		//删除所选部门
		departmentDao.delDepartment(departmentId);
		//如果有子部门就删除所有下级部门
		if(isleaf.equals("true")){
			departmentDao.delChildDepartment(departmentId);
		}
		//修改父级部门的属性   (如果上级部门没有子部门了要修改上级部门属性)
		if(delDepartParentId != 0){
			//查是否还有同级部门,如果有就不修改父级部门,如果没有就修改
			//得到同级部门
			List<Department> departmentList = departmentDao.getDepartmentList(delDepartParentId);
			if(departmentList.isEmpty()){
				//修改上级部门属性
				Department department = departmentDao.getDepartment(delDepartParentId);
				department.setHasChildren(0);
				departmentDao.editDepartment(department);
			}
		}
	}

	/**
	 * 根据父级ID 得到此父级下最大的sort
	 * @param parentId
	 * @return
	 */
	public Integer getMaxSort(int parentId) {
		Integer maxSort = departmentDao.getMaxSort(parentId);
		if(maxSort == null){
			return 0;
		}
		return maxSort;
	}

	
	
}

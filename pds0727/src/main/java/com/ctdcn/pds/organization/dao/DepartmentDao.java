package com.ctdcn.pds.organization.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ctdcn.pds.organization.model.Department;
import com.ctdcn.pds.organization.model.User;

/**
 * Created by Triumphant on 2015/6/9.
 */
@Repository
public class DepartmentDao
{
    public static final String DEPARTMENT_NAMESAPCE="sys.department";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    //查
    public List<Department> queryDepartment(Map departmentMap)    {
        return sqlSessionTemplate.selectList(DEPARTMENT_NAMESAPCE + ".querydepartment",departmentMap);
    }

    //新增部门
    public void insertDepartment(Department department){
    	//添加新增的部门
        sqlSessionTemplate.insert(DEPARTMENT_NAMESAPCE+".insertdepartment",department);
    }


    //批量新增
    public void batchInsertDepartment(List<Department> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Department department : list)
                sqlSession.insert(DEPARTMENT_NAMESAPCE+".insertdepartment",department);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }



    //删除
    public void deleteDepartment(Map map){
        sqlSessionTemplate.delete(DEPARTMENT_NAMESAPCE+".deletedepartment",map);
    }
    
    //更新
    public void batchUpdateDepartment(List<Map> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Map params : list)
                sqlSession.update(DEPARTMENT_NAMESAPCE+".updatedepartment",params);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }

    //得到部门树
	public List<Department> getDepartmentTree(Integer parentId) {
		return sqlSessionTemplate.selectList(DEPARTMENT_NAMESAPCE + ".getDepartmentTree",parentId);
	}

	//得到指定部门
	public Department getDepartment(int id) {
		return sqlSessionTemplate.selectOne(DEPARTMENT_NAMESAPCE + ".getDepartmentById", id);
	}

	//得到部门的下拉列表
	public List<Department> getDepartmentList(Integer id) {
		return sqlSessionTemplate.selectList(DEPARTMENT_NAMESAPCE + ".getDepartmentList", id);
	}

	//修改部门(单次操作)
	public void editDepartment(Department department){
        sqlSessionTemplate.update(DEPARTMENT_NAMESAPCE+".editDepartment", department);
	}

	//得到原上级部门的子级部门数量
	public Integer getChildrenCount(int oldParentId) {
		return sqlSessionTemplate.selectOne(DEPARTMENT_NAMESAPCE + ".getChildrenCount",oldParentId);
	}

	//删除部门
	public void delDepartment(int departmentId) {
		sqlSessionTemplate.delete(DEPARTMENT_NAMESAPCE + ".delDepartment",departmentId);
	}
	
	//删除子级部门
	public void delChildDepartment(int parentId) {
		sqlSessionTemplate.delete(DEPARTMENT_NAMESAPCE + ".delChildDepartment",parentId);
	}
	
	//得到下一个sequence ID
	public Integer getSequenceId(){
		return sqlSessionTemplate.selectOne(DEPARTMENT_NAMESAPCE + ".getSequenceId");
	}

	//得到最大的sort
	public Integer getMaxSort(int parentId) {
		return sqlSessionTemplate.selectOne(DEPARTMENT_NAMESAPCE + ".getMaxSort", parentId);
	}

    public void updateWidth()
    {
        sqlSessionTemplate.update(DEPARTMENT_NAMESAPCE + ".updateDepartmentWidth");
    }

    public void updateDepartmentHasChildren()
    {
        sqlSessionTemplate.update(DEPARTMENT_NAMESAPCE + ".updateDepartmentHasChildren");
    }


	
	
}

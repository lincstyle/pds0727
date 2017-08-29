package com.ctdcn.pds.organization.dao;

import com.ctdcn.pds.organization.model.User;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张靖.
 *         2015-06-03 17:18
 */
@Repository
public class UserDAO {

    public static final String NAME_SAPCE = "sys.user";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 分页查询user对象.
     * @param user 用户条件对象，如果属性有值，则拼接为等号（=）条件
     * @param orderCols 要排序的列，不需要排序时，则不传
     * @param order 排序方式desc,asc
     * @param rowBounds 分页对象，不需要分页就为空
     * @return
     */
    public List<User> queryUserList(User user,String[] orderCols,String order,RowBounds rowBounds){
        Map map = new HashMap();
        map.put("user",user);
        map.put("orderCols",orderCols);
        map.put("order",order);
        if(rowBounds != null){
            return sqlSessionTemplate.selectList(UserDAO.NAME_SAPCE+".query",map,rowBounds);
        }
        return sqlSessionTemplate.selectList(UserDAO.NAME_SAPCE + ".query", map);
    }
    
    

    /**
     * 添加用户.
     * @param user 要添加用户的对象
     * @return
     */
    public int addUser(User user){
        return sqlSessionTemplate.insert(UserDAO.NAME_SAPCE + ".add", user);
    }

    /**
     * 删除用户.
     * @param user
     * @return
     */
    public int removeUser(User user){
        return sqlSessionTemplate.delete(UserDAO.NAME_SAPCE + ".remove", user);
    }

    /**
     * 修改用户
     * @param target
     * @param cond
     * @return
     */
    public int editUser(User target,User cond){
        Map map = new HashMap();
        map.put("target",target);
        map.put("cond",cond);
        return sqlSessionTemplate.update(UserDAO.NAME_SAPCE + ".edit", map);
    }

    /**
     * 查询用户
     * @param user
     * @param rowBounds
     * @return
     */
    public List<User> findUser(User user, RowBounds rowBounds) {
    	return sqlSessionTemplate.selectList(UserDAO.NAME_SAPCE + ".findUser", user, rowBounds);
    }

    /**
     * 显示指定部门下的所有员工(包括子部门)
     * @param
     * @return
     */
    public List<User> getDepartmentUser(String departIds, RowBounds rowBounds) {
		return sqlSessionTemplate.selectList(UserDAO.NAME_SAPCE + ".getDepartmentUser", departIds, rowBounds);
	}

    /**
	 * 显示指定部门下的所有员工(包括子部门)的总人数
	 * @return
	 */
	public Integer getDepartmentUserTotal(String departIds) {
		return sqlSessionTemplate.selectOne(UserDAO.NAME_SAPCE + ".getDepartmentUserTotal", departIds);
	}

	/**
	 * 删除用户
	 * @param userId
	 */
	public void delUser(int userId) {
		sqlSessionTemplate.delete(UserDAO.NAME_SAPCE + ".delUser", userId);
	}

	/**
	 * 根据ID得到用户
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId) {
		return sqlSessionTemplate.selectOne(UserDAO.NAME_SAPCE + ".getUserById" ,userId);
	}
	
	/**
	 * 根据ID修改用户   
	 * @param user
	 */
	public void editUser_2(User user){
		sqlSessionTemplate.update(UserDAO.NAME_SAPCE+".editUser",user);
	}

	/**
	 * 得到部门seqID
	 * @return
	 */
	public Integer getDepSeqId() {
		return sqlSessionTemplate.selectOne(UserDAO.NAME_SAPCE + ".getDepSeqId");
	}

    /**
     * 得到seqID
     * @return
     */
    public Integer getUserSeqId() {
        return sqlSessionTemplate.selectOne(UserDAO.NAME_SAPCE + ".getUserSeqId");
    }


    /**
     * 查询所有user
     *
     */
    public List<User> queryAllUser(User user)
    {
        return sqlSessionTemplate.selectList(UserDAO.NAME_SAPCE + ".queryAllUser", user);
    }

    /**
     * 查询还未参与该项目的人员
     * @param
     * @return
     */
    public List<User> queryPrUser(Map map)
    {
        return sqlSessionTemplate.selectList(UserDAO.NAME_SAPCE + ".queryPrUser", map);
    }


    /**
     * 模糊查询用户时需要的总用户数量
     * @param user
     * @return
     */
	public Integer getFindUserTotal(User user) {
		return sqlSessionTemplate.selectOne(UserDAO.NAME_SAPCE + ".findUserTotal", user);
	}



	public void updateUserDepartmentName(Map<String,String> departNameMap) {
		sqlSessionTemplate.update(UserDAO.NAME_SAPCE + ".updateUserDepartmentName", departNameMap);
	}


	public void saveUploadImg(User user) {
		sqlSessionTemplate.update(UserDAO.NAME_SAPCE + ".saveUploadImg", user);
	}

    //更新
    public void batchUpdateUser (List<Map> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Map params : list)
                sqlSession.update(NAME_SAPCE+".updateAllUser",params);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }

    //批量增加
    public void batchInsertUser (List<User> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (User user : list)
                sqlSession.insert(NAME_SAPCE + ".add", user);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }

    //同步sys_user表中的项目名
    public void updateSysUserDepartmentName(){
        sqlSessionTemplate.update(NAME_SAPCE+".updateSysUserDepartmentName");
    }

    //企业通讯录名单
    public List<User> getCommList(Map<String, Object> paramMap){
        return sqlSessionTemplate.selectList(NAME_SAPCE + ".commList", paramMap);
    }

    //得到新增的项目参与人员信息
    public List<User> getProUser(Integer[] userIds){
        return sqlSessionTemplate.selectList(NAME_SAPCE + ".getProUser", userIds);
    }

    //新建项目的选择项目负责人(根据输入的条件 模糊查询用户)
    public List<User> getProResponse(User user){
        return sqlSessionTemplate.selectList(NAME_SAPCE + ".getProResponse", user);
    }

    public List<User> getProjectIsResponse(Map<String, Object> paramMap){
        return sqlSessionTemplate.selectList(NAME_SAPCE + ".getProjectIsResponse", paramMap);
    }
}

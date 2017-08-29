package com.ctdcn.pds.project.dao;

import com.ctdcn.pds.project.model.ProjectUser;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/18.
 * 项目人员关系表操作
 */

@Repository
public class ProjectUserDao
{
    private  static  final String  PRUSER_NAMESPACE="project.user";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    //分页查询所有符合条件的记录(不包括已删除)
    public List<ProjectUser> queryProjectUser(Map prUserMap,RowBounds rowBounds)
    {
        return sqlSessionTemplate.selectList(PRUSER_NAMESPACE + ".querypruser", prUserMap, rowBounds);

    }

    //查询所有符合条件的记录(不包括已删除)
    public List<ProjectUser> queryProjectUser(Map prUserMap) {
        return sqlSessionTemplate.selectList(PRUSER_NAMESPACE + ".querypruser", prUserMap);
    }

    //模糊分页查询所有符合条件的记录
    public List<ProjectUser> mohuQueryProjectUser(Map prUserMap,RowBounds rowBounds)
    {
        return sqlSessionTemplate.selectList(PRUSER_NAMESPACE + ".mohuQuerypruser", prUserMap, rowBounds);
    }

    //<!--根据人员查询  其有权限操作的项目-->  //排除项目阶段编码为空的 项目
    public List<ProjectUser> queryPnameByUser(Map prUserMap)
    {
        return sqlSessionTemplate.selectList(PRUSER_NAMESPACE + ".querypnameByUser", prUserMap);

    }

    //查询所有符合条件的记录
    public List<ProjectUser> queryAllProjectUser(Map prUserMap) {
        return sqlSessionTemplate.selectList(PRUSER_NAMESPACE + ".querypruser", prUserMap);
    }

    //查询　符合条件记录的　总数
    public  Integer countRecord(Map prUserMap)
    {

        return sqlSessionTemplate.selectOne(PRUSER_NAMESPACE + ".countrecord", prUserMap);
    }

    //添加 项目人员关系  记录
    public void addProjectUser(ProjectUser projectUser)
    {
        sqlSessionTemplate.insert(PRUSER_NAMESPACE+".insertproductuser",projectUser);
    }

    public void batchInsertProjectUser(List<ProjectUser> projectUserList)
    {
        SqlSession sqlSession =sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (ProjectUser projectUser : projectUserList)
                sqlSession.insert(PRUSER_NAMESPACE + ".insertproductuser", projectUser);
            sqlSession.commit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }

    //删除项目人员关系  记录
    public void  deleteProjectUser(Integer[] puid_Array)
    {
        sqlSessionTemplate.delete(PRUSER_NAMESPACE + ".deleteprojectuser", puid_Array);
    }

    //修改当前用户参与的项目是否接收消息
	public void editIsReceive(ProjectUser proUser) {
		sqlSessionTemplate.update(PRUSER_NAMESPACE + ".editIsReceive", proUser);
	}

    //修改当前用户是否为该项目负责人
    public void editIsResponse(ProjectUser proUser) {
        sqlSessionTemplate.update(PRUSER_NAMESPACE + ".editResponse", proUser);
    }

    //批量修改多个用户为项目负责人
    public void batchUpdateProResponse(List<ProjectUser> projectUserList) {
        SqlSession sqlSession =sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (ProjectUser projectUser : projectUserList) {
                sqlSession.insert(PRUSER_NAMESPACE + ".editResponse", projectUser);
            }
            sqlSession.commit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }

    //查询出 PrUserSequcence  下一个值
    public Integer queryPrUserSequcence()
    {
        return  sqlSessionTemplate.selectOne(PRUSER_NAMESPACE + ".queryPrUserSequcence");
    }

    //查询出 该项目的 负责人id
    public List<Integer> queryFuzerenId(Map map)
    {
        return sqlSessionTemplate.selectList(PRUSER_NAMESPACE + ".queryPrUserId", map);
    }

    //修改项目人员关系表的项目名
    public void updateProName(Map<String, Object> map) {
        sqlSessionTemplate.update(PRUSER_NAMESPACE + ".updateProName", map);
    }

    //项目参与人员维护 -- 删除 项目人员关系表 数据
    public void updateDelPro(Integer[] del_puid) {
        sqlSessionTemplate.update(PRUSER_NAMESPACE + ".updateDelPro", del_puid);
    }


    //查询当前用户所参与的项目
    public List<ProjectUser> projectList(ProjectUser projectUser) {
        return sqlSessionTemplate.selectList(PRUSER_NAMESPACE + ".projectList", projectUser);
    }
    
  //更新
    public void batchUpdateTProjectUser (List<Map> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Map params : list)
                sqlSession.update(PRUSER_NAMESPACE+".updateTProjectUser",params);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }
}

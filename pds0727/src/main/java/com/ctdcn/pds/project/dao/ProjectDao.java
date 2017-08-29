package com.ctdcn.pds.project.dao;

import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.StageCode;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/10.
 */
@Repository
public class ProjectDao
{
    public static final String PROJECT_NAMESAPCE="project.manage";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    //查
    public List<Project> queryProject(Map projectMap) {
        return sqlSessionTemplate.selectList(PROJECT_NAMESAPCE + ".queryproject",projectMap);
    }

    //分页查询项目
    public List<Project> queryPagingProject(Map projectMap,RowBounds rowBounds) {
        return sqlSessionTemplate.selectList(PROJECT_NAMESAPCE + ".queryproject",projectMap,rowBounds);
    }

    //根据查询条件查询项目条数
    public Integer countProject(Map projectMap)
    {
        return sqlSessionTemplate.selectOne(PROJECT_NAMESAPCE + ".countproject", projectMap);
    }

    //新增
    public void insertProject(Project project)
    {
        sqlSessionTemplate.insert(PROJECT_NAMESAPCE + ".insertproject", project);
    }

    //更新
    public void batchUpdateProject (List<Map> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Map params : list)
                sqlSession.update(PROJECT_NAMESAPCE+".updateproject",params);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }

    //根据项目ID得到项目明细
    public Project getProjectDetail(Integer pid) {
        return sqlSessionTemplate.selectOne(PROJECT_NAMESAPCE + ".getProjectDetail", pid);
    }

    //查询项目所处的阶段状态在不在修改的阶段数组里面
    public Project changeProjectPflag(Integer pid) {
        return sqlSessionTemplate.selectOne(PROJECT_NAMESAPCE + ".changeProjectPflag", pid);
    }
}

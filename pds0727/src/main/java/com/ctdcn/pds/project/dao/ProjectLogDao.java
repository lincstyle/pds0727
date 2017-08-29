package com.ctdcn.pds.project.dao;

import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.ProjectLog;
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
 * 项目管理
 */
@Repository
public class ProjectLogDao
{

    public static final String LOG_NAMESAPCE="project.log";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;




    //根据 lid  查询出大字段详细日志
    public ProjectLog queryPrLogContent(Map prLogMap)
    {
        return sqlSessionTemplate.selectOne(LOG_NAMESAPCE + ".queryprlogContents", prLogMap);
    }


    //查
    public List<ProjectLog> queryPrLog(Map prLogMap)
    {
        return sqlSessionTemplate.selectList(LOG_NAMESAPCE + ".queryprlog", prLogMap);
    }

    //查
    public List<ProjectLog> queryPagingPrLog(Map prLogMap,RowBounds rowBounds)
    {
        return sqlSessionTemplate.selectList(LOG_NAMESAPCE + ".queryprlog",prLogMap,rowBounds);
    }
    //查询符合条件的记录条数，如无条件即查询总条数
    public Integer countPrLog(Map prLogMap)
    {
        return sqlSessionTemplate.selectOne(LOG_NAMESAPCE + ".prlogcount", prLogMap);
    }
  //用日志状态查询符合条件的记录条数
    public Integer countPrLogPflag(Map prLogMap)
    {
        return sqlSessionTemplate.selectOne(LOG_NAMESAPCE + ".prlogcountPflag", prLogMap);
    }
   
    //新增
    public void insertPrLog(ProjectLog projectLog)
    {
        sqlSessionTemplate.insert(LOG_NAMESAPCE + ".insertprlog", projectLog);
    }

    //更新
    public void batchUpdateProject (List<Map> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Map params : list)
                sqlSession.update(LOG_NAMESAPCE+".updateprLog",params);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }

    //查询规定时间内有更新的 项目
    public List<ProjectLog> queryPnameByTime(Map prLogMap)
    {
        return sqlSessionTemplate.selectList(LOG_NAMESAPCE + ".queryPnameByTime",prLogMap);
    }
    //查询规定时间内有更新的项目id
    public List<ProjectLog> queryPidsByTime(Map searchMap)
    {
        return sqlSessionTemplate.selectList(LOG_NAMESAPCE + ".queryPidsByTime",searchMap);
    }


    //查询出 lid sequcence  下一个值
    public Integer queryLidSeq()
    {
        return  sqlSessionTemplate.selectOne(LOG_NAMESAPCE + ".querylidsequence");
    }

    //查询此项目的日志状态  在不在修改的项目阶段数组里面
    public List<ProjectLog> changeProjectLogPflag(Map<String, Object> map) {
        return sqlSessionTemplate.selectList(LOG_NAMESAPCE + ".changeProjectLogPflag", map);
    }
}

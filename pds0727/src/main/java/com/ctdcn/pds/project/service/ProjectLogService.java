package com.ctdcn.pds.project.service;

import com.ctdcn.pds.project.dao.ProjectLogDao;
import com.ctdcn.pds.project.model.ProjectLog;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/10.
 */

@Service
public class ProjectLogService
{
    @Autowired
   private  ProjectLogDao projectLogDao;

    @Transactional
    public void insertPrLog(ProjectLog projectLog) {
        projectLogDao.insertPrLog(projectLog);
    }

    @Transactional
    public void updatePrLog(ProjectLog updateLog,ProjectLog projectLog)
    {
        Map map =new HashMap();
        map.put("updateLog",updateLog);
        map.put("prLog", projectLog);
        List list =new ArrayList<Map>();
        list.add(map);
        projectLogDao.batchUpdateProject(list);
    }

    public List<ProjectLog> queryPrLog(ProjectLog projectLog)
    {
        Map map =new HashMap();
        map.put("prLog", projectLog);
        return projectLogDao.queryPrLog(map);
    }
    //根据条件查询单个项目的日志
    public List<ProjectLog> queryPrLog(Map pSearchMap)
    {
        return projectLogDao.queryPrLog(pSearchMap);
    }



    //根据 lid  查询出大字段详细日志
    public ProjectLog queryPrLogContent(ProjectLog projectLog)
    {
        Map map =new HashMap();
        map.put("prLog", projectLog);
        return projectLogDao.queryPrLogContent(map);
    }

    public List<ProjectLog> queryPagingPrLog(ProjectLog projectLog,int page,int rows, Integer[] updateIds)
    {
        int start = (page-1)*rows;// 开始条数
        int limit = rows;//查询跨度
        RowBounds rowBounds = new RowBounds(start,limit);
        Map map = new HashMap();
        map.put("prLog",projectLog);
        map.put("updateIds",updateIds);
        return projectLogDao.queryPagingPrLog(map, rowBounds);
    }

    public Integer countPrlog(ProjectLog projectLog, Integer[] updateIds)
    {
        Map map =new HashMap();
        map.put("prLog", projectLog);
        map.put("updateIds",updateIds);
        return projectLogDao.countPrLog(map);
    }
    public Integer countPrlogPflag(ProjectLog projectLog)
    {
        Map map =new HashMap();
        map.put("prLog", projectLog);
        return projectLogDao.countPrLogPflag(map);
    }
    public List<ProjectLog> queryPnameByTime(ProjectLog projectLog)
    {
        Map map =new HashMap();
        map.put("prLog", projectLog);
        return projectLogDao.queryPnameByTime(map);
    }
    //根据条件查询
    public List<ProjectLog> queryPidsByTime(Map searchMap)
    {        
        return projectLogDao.queryPidsByTime(searchMap);
    }


    //查询出 lid sequcence  下一个值
    public Integer queryLidSeq()
    {
        return  projectLogDao.queryLidSeq();
    }

    //查询此项目的日志状态  在不在修改的项目阶段数组里面
    public int changeProjectLogPflag(int pid, String inStage) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pid", pid);
        map.put("inStage", inStage);
        List<ProjectLog> proLogList = projectLogDao.changeProjectLogPflag(map);
        if(proLogList != null && !proLogList.isEmpty()){
            return proLogList.size();
        }
        return 0;
    }
}

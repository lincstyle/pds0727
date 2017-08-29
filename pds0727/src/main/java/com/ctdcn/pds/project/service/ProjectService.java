package com.ctdcn.pds.project.service;

import com.ctdcn.pds.project.dao.ProjectDao;
import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.StageCode;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Triumphant on 2015/6/10.
 */
@Service
public class ProjectService
{
    @Autowired
    private ProjectDao projectDao;

    @Transactional
    public void addProject(Project project)
    {
        projectDao.insertProject(project);

    }

    @Transactional
    public void updateProject(Project updatePr,Project project)
    {
        Map map =new HashMap();
        map.put("updatePr",updatePr);
        map.put("project",project);
        List<Map> list =new ArrayList<Map>();
        list.add(map);
        projectDao.batchUpdateProject(list);
    }

    public List<Project> queryProject(Project project)
    {
        Map map =new HashMap();
        map.put("project", project);
        return projectDao.queryProject(map);
    }


    //分页查询项目
    public List<Project> queryPagingProject(Project project,int page,int rows) {
        int start = (page-1)*rows;// 开始条数
        int limit = rows;//查询跨度
        RowBounds rowBounds = new RowBounds(start,limit);
        Map map = new HashMap();
        map.put("project", project);
        return projectDao.queryPagingProject(map, rowBounds);
    }

    //根据条件查询项目条数
    public Integer countProject(Project project)
    {
        Map map =new HashMap();
        map.put("project", project);
        return  projectDao.countProject(map);
    }

    //根据项目ID得到项目明细
    public Project getProjectDetail(Integer pid) {
        return projectDao.getProjectDetail(pid);
    }

    //查询项目所处的阶段状态在不在修改的阶段数组里面
    public Project changeProjectPflag(Integer pid) {
        return projectDao.changeProjectPflag(pid);
    }
}

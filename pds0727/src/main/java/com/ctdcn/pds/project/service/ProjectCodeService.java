package com.ctdcn.pds.project.service;

import com.ctdcn.pds.project.dao.ProjectCodeDao;
import com.ctdcn.pds.project.model.PrCode;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/30.
 */
@Service
public class ProjectCodeService
{
    @Autowired
    private ProjectCodeDao projectCodeDao;

    public List<PrCode> queryCode(PrCode prCode,int page,int rows)
    {
        int start = (page-1)*rows;// 开始条数
        int limit = rows;//查询跨度
        RowBounds rowBounds = new RowBounds(start,limit);
        Map map =new HashMap();
        map.put("prCode",prCode);
        return  projectCodeDao.queryPrCode(map,rowBounds);

    }

    public Integer countRecord(PrCode prCode)
    {
        Map map =new HashMap();
        map.put("prCode",prCode);
        return projectCodeDao.countRecord(map);

    }

    //增加编码
    @Transactional
    public  void addCodeByBaseCode(String inStage,Integer pid)
    {
        Map map =new HashMap();
        map.put("inStage",inStage);
        map.put("pid",pid);
        projectCodeDao.addCodeByBaseCode(map);
    }

    //    删除  项目阶段编码表中的某些基本编码
    @Transactional
    public void  deletePrCode(Integer[] cidArray)
   {
       projectCodeDao.deletePrCode(cidArray);
   }


    //在移动基本 阶段编码表的  同时也更新 项目阶段编码表
    @Transactional
    public void updatePrCodeByMove()
    {
        projectCodeDao.updatePrCodeBymove();

    }


    //在编辑 基本编码表 的编码名称时 同时 也更新 项目编码表的 编码名称
    @Transactional
    public void updateMcByEdit()
    {
        projectCodeDao.updateMcByEdit();

    }

    //    当基本表中的 阶段被删除时同时也删除项目表中的相应的记录
    @Transactional
    public void deleteByBaseCode(PrCode prCode)
    {
        Map map =new HashMap();
        map.put("prCode",prCode);

        projectCodeDao.deleteByBaseCode(map);
    }









}

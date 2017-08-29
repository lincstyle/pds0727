package com.ctdcn.pds.project.service;

import com.ctdcn.pds.project.dao.StageCodeDao;
import com.ctdcn.pds.project.model.PrCode;
import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.StageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/15.
 */
@Service
public class StageCodeService
{
    @Autowired
    private StageCodeDao stageCodeDao;


    //** 查询出项目字典 返回给前台
    public List queryStageByType(Integer  pid) {
        List comboxList = new ArrayList();
        Map map = new HashMap();
        map.put("id", -1);
        map.put("text", "--请选择");
        comboxList.add(map);
        PrCode prCode = new PrCode();
        prCode.setPid(pid);
        //查询当前登录用户的部门的 所有项目
        List<PrCode> list = queryStage(prCode);
        //根据条件查询 显示对应的项目出来
        for(PrCode s : list) {
            Map codeMap = new HashMap();
            codeMap.put("id", s.getTypeCode());
            codeMap.put("text",s.getMc());
//            codeMap.put("selected",s.getPid());
            comboxList.add(codeMap);
        }
        return comboxList;
    }

    public List<PrCode> queryStage(PrCode prCode) {
        return stageCodeDao.queryStageCode(prCode);
    }

    public Map queryAllStage(Integer pid)
    {
        //返回option的map
        Map map =new HashMap();
        //查询的map
        Map queryMap =new HashMap();
        StageCode stageCode =new StageCode();
        queryMap.put("stageCode",stageCode);
        queryMap.put("pid",pid);
        List<StageCode> stageCodeList= stageCodeDao.queryAllStage(queryMap);
        StringBuffer option =new StringBuffer("");
        //查询出来的参数  拼接 option
        for(StageCode s :stageCodeList)
        {
            option.append("<option value='"+s.getTypecode() + " '>" + s.getMc() + "</option>");
        }
        PrCode prCode =new PrCode();
        prCode.setPid(pid);
        List<PrCode> prCodeList=stageCodeDao.queryStageCode(prCode);
        for(PrCode p : prCodeList)
        {
            option.append("<option value='"+p.getTypeCode() + " ' selected='selected'>" + p.getMc() + "</option>");
        }
        if("".equals(option))
        {
            option =new StringBuffer("<option value='0'>无项目阶段状态存在，请维护</option>");
        }
        map.put("option", option.toString());
        return map;
    }

    //项目阶段维护
    public List<StageCode> projectStage(Integer pid){
        return stageCodeDao.projectStage(pid);
    }

    //得到指定项目的阶段
    public List<StageCode> getProStageById(Integer pid) {
        return stageCodeDao.getProStageById(pid);
    }
}

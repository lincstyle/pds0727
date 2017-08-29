package com.ctdcn.pds.project.dao;

import com.ctdcn.pds.project.model.PrCode;
import com.ctdcn.pds.project.model.Project;
import com.ctdcn.pds.project.model.StageCode;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/15.
 */
@Repository
public class StageCodeDao
{
    public static final String STAGE_NAMESAPCE="project.stage";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    //查
    public List<PrCode> queryStageCode(PrCode prCode)
    {
        return sqlSessionTemplate.selectList(STAGE_NAMESAPCE + ".queryStageCode",prCode);
    }


    public List<StageCode> queryAllStage(Map map)
    {
        return sqlSessionTemplate.selectList(STAGE_NAMESAPCE + ".queryallStage",map);
    }

    //项目阶段维护
    public List<StageCode> projectStage(Integer pid){
        return sqlSessionTemplate.selectList(STAGE_NAMESAPCE + ".projectStage", pid);
    }

    //得到指定项目的阶段
    public List<StageCode> getProStageById(Integer pid) {
        return sqlSessionTemplate.selectList(STAGE_NAMESAPCE + ".getProStageById", pid);
    }
}

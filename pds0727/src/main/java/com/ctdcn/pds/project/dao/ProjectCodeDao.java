package com.ctdcn.pds.project.dao;

import com.ctdcn.pds.project.model.PrCode;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/30.
 */

@Repository
public class ProjectCodeDao
{
    public static  final String CODE_NAMESPACE="project.code";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    public List<PrCode>  queryPrCode(Map map,RowBounds rowBounds)
    {

        return sqlSessionTemplate.selectList(CODE_NAMESPACE + ".queryprcode", map, rowBounds);
    }

    public Integer countRecord(Map map)
    {
        return  sqlSessionTemplate.selectOne(CODE_NAMESPACE + ".prCodeCount", map);
    }

    public void updatePrCode(Map map)
    {
        sqlSessionTemplate.update(CODE_NAMESPACE + ".updateprCode", map);
    }

    public void addPrCode(PrCode prCode)
    {
        sqlSessionTemplate.insert(CODE_NAMESPACE + ".insertprCode", prCode);

    }

    //把基本 阶段字典添加到 项目阶段字典  ---数据库中
    public void  addCodeByBaseCode(Map map)
    {
        sqlSessionTemplate.insert(CODE_NAMESPACE + ".addprCodeByBaseCode", map);
    }

    //删除 项目阶段字典 中的选择的记录
    public void deletePrCode(Integer[] cidArray)
    {
        sqlSessionTemplate.delete(CODE_NAMESPACE + ".deleteprojectcode", cidArray);
    }

//    当基本表中的 阶段被删除时同时也删除项目表中的相应的记录
    public void deleteByBaseCode(Map map)
    {
        sqlSessionTemplate.delete(CODE_NAMESPACE+".deleteprcodeByBaseCode",map);
    }

    //在移动基本 阶段编码表的  同时也更新 项目阶段编码表

    public void updatePrCodeBymove()
    {
        sqlSessionTemplate.update(CODE_NAMESPACE+".updatePrCodeByBaseCode");
    }

    //在编辑 基本编码表 的编码名称时 同时 也更新 项目编码表的 编码名称
    public void updateMcByEdit()
    {
        sqlSessionTemplate.update(CODE_NAMESPACE+".upMcByBaseCode");
    }




}

package com.ctdcn.pds.project.dao;

import com.ctdcn.pds.project.model.BaseCode;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/7/6.
 */
@Repository
public class BaseCodeDao
{
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private  static   final String  BASE_CODESPACE="project.baseCode";

    public List<BaseCode> queryBaseCode(Map map)
    {
        return sqlSessionTemplate.selectList(BASE_CODESPACE+".queryBaseCode",map);
    }


    public void addBaseCode(BaseCode baseCode)
    {
        sqlSessionTemplate.insert(BASE_CODESPACE+".insertBaseCode",baseCode);
    }

    public void updateBaseCode(Map map)
    {
        sqlSessionTemplate.update(BASE_CODESPACE + ".updateBaseCode", map);
    }

    public  void deleteBaseCode(Map map)
    {
        sqlSessionTemplate.delete(BASE_CODESPACE + ".deleteBaseCode", map);
    }
    public  Integer queryTypeCodeSequcence()
    {
         return sqlSessionTemplate.selectOne(BASE_CODESPACE + ".queryTypeCodeSequcence");
    }

    /**
     * 移动节点的位置 就改变排序顺序
     * @param map
     */
    public  void moveBaseCode(Map map )
    {
        sqlSessionTemplate.update(BASE_CODESPACE + ".moveBaseCode", map);
    }


    /**
     * 将原节点的位置换成目标节点的位置
     * @param map
     */
    public  void replaceBaseCode(Map map )
    {
        sqlSessionTemplate.update(BASE_CODESPACE+".ReplaceBaseCode",map);
    }

    /**
     * 加入新节点 同时更新其他节点的位置
     */

    public  void updateBaseCodeByAddBefore(Map map)
    {
        sqlSessionTemplate.update(BASE_CODESPACE+".updateBaseCodeByAddBefore",map);
    }








}

package com.ctdcn.pds.project.service;

import com.ctdcn.pds.project.dao.BaseCodeDao;
import com.ctdcn.pds.project.model.BaseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/7/7.
 */

@Service
public class BaseCodeService
{
    @Autowired
    private BaseCodeDao baseCodeDao;

    public List<BaseCode>  queryBaseCode(BaseCode baseCode)
    {
        Map map =new HashMap();
        map.put("BaseCode",baseCode);
        return  baseCodeDao.queryBaseCode(map);
    }

    @Transactional
    public void  moveBaseCode(Integer target,Integer original)
    {
        Map map = new HashMap();
        map.put("original",original);
        map.put("target",target);
        baseCodeDao.moveBaseCode(map);

    }

    @Transactional
    public void  replaceBaseCode(Integer target,Integer original,String originalMc)
    {
        Map map = new HashMap();
        map.put("original",original);
        map.put("target",target);
        map.put("originalMc",originalMc);
        baseCodeDao.replaceBaseCode(map);

    }

    /**
     * 编辑
     * @param baseCode
     * @param upBaseCode
     */
    @Transactional
    public void  editBaseCode(BaseCode baseCode,BaseCode upBaseCode)
    {

        Map map =new HashMap();
        map.put("upBaseCode",upBaseCode);
        map.put("BaseCode",baseCode);
        baseCodeDao.updateBaseCode(map);


    }
    /**
     * 删除
     */
    @Transactional
    public void deleteBaseCode(BaseCode baseCode)
    {
        Map map =new HashMap();
        map.put("BaseCode",baseCode);
        baseCodeDao.deleteBaseCode(map);
    }


    /**
     *加入新节点 同时更新其他节点的位置
     */
    @Transactional
    public void updateBaseCodeByAddBefore(Integer flag,Integer addTypeCode)
    {
        Map map =new HashMap();
        map.put("flag",flag);
        map.put("addTypecode",addTypeCode);
        baseCodeDao.updateBaseCodeByAddBefore(map);

    }
    /**
     *查新最大的typecode
     */
    public Integer queryTypeCodeSequcence()
    {
        return baseCodeDao.queryTypeCodeSequcence();

    }
    /**
     * 添加新节点
     */
    @Transactional
    public  void addNewBaseCode(BaseCode baseCode)
    {
        baseCodeDao.addBaseCode(baseCode);
    }





}

package com.ctdcn.pds.authority.service;

import com.ctdcn.pds.authority.dao.ResourceDao;
import com.ctdcn.pds.authority.model.Resource;
import com.ctdcn.pds.organization.model.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LIUKAIXUAN on 2015/6/4.
 */
@Service
public class ResourceService
{
    @Autowired
    private ResourceDao resourceDao;


    //查询资源表
    public List<Resource> queryResourceDao(Resource resource)
    {

        Map resourceMap=new HashMap();
        //加入到map
        resourceMap.put("resource", resource);
        //查询出同一父节点的所有记录
        List<Resource> querylist=resourceDao.queryResource(resourceMap);
        return querylist;

    }

    /**
     * 根据权限显示资源菜单.
     * roleId为空则显示所有权限的
     * ParentId为空则查询根节点
     * @param parentId
     * @return
     */
    public List<Resource> queryResourceWith(Integer parentId){
        List<Resource> resources = null;
        if(parentId == null){
            resources = queryResourceDao(new Resource());
        }else{
            resources  = resourceDao.queryResourceWitch(parentId);
        }

        return  resources;
    }

    //删除资源表
    @Transactional
    public void deleteResource(int id)
    {
        Resource resource =new Resource();
        resource.setId(id);
        //删除 节点为该id的记录
        resourceDao.deleteResource(resource);
    }

    @Transactional
    public void batchDelete(List<Resource> ids){
        List<Resource> list = new ArrayList<Resource>();
        for (Resource id : ids){
            Resource resource = new Resource();
            resource.setId(id.getId());
            list.add(resource);
        }
        resourceDao.batchDeleteResource(list);
    }

    /**
     * 递归查询所有子节点.
     * @param parentId
     * @return
     */
    public List<Resource> queryChildren(Integer parentId){
        return resourceDao.queryChildren(parentId);
    }



    //编辑资源表
    @Transactional
    public void updateResource( Resource updateResource,Resource resource)
    {
        Map resourceMap=new HashMap();
        //添加 包含修改的信息 资源对象到map
        resourceMap.put("updateRe",updateResource);
        //添加 原资源对象到map
        resourceMap.put("resource", resource);
        List updateList=new ArrayList<Map<String,Object>>();
        updateList.add(resourceMap);
        //更新 资源记录
        resourceDao.batchUpdateResource(updateList);
    }

    //插入新纪录
    @Transactional
    public void addResource(Resource resource)
    {
        //插入新记录
        resourceDao.insertResource(resource);
    }

    /**
     *  查询资源.
     * @param roleId
     * @param width
     *          1、width为空，则是查询菜单
     *          2、width为3，则是查询有哪些操作权限
     * @return
     */
    public List<String> querySpliceAuthority(int roleId,Integer width)
    {

        List<String> spliceList =new ArrayList<String>();

        Map map =new HashMap();
        map.put("roleId",roleId);
        map.put("width",width);
        List<Resource> listMap=resourceDao.queryByRoleId(map);

        Integer parent_id = null;
        String splice = "";
        for(Resource resource : listMap)
        {
            Integer id = resource.getId();
            String identity = resource.getIdentity();
            if (resource.getWidth().equals(width)){
                splice = identity;
            }else if (id.equals(parent_id)){
                splice = identity + ":" + splice;
            }
            parent_id = resource.getParentId();
            if (parent_id.equals(0)){
                spliceList.add(splice);
                splice = "";
            }
        }

        return spliceList;

    }

    /**
     * 得到所有的资源
     * @return
     */
	public List<Resource> showResourceTree() {
		return resourceDao.showResourceTree();
	}

    @Transactional
    public void move(Integer original,Integer target,Integer parentId){
        resourceDao.move(original,target,parentId);
    }

    public Integer getMax(Integer parentId){
        return resourceDao.getMax(parentId);
    }


}

package com.ctdcn.pds.authority.dao;

import com.ctdcn.pds.authority.model.Resource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.util.Strings;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Trimup on 2015/6/4.
 */
@Repository
public class ResourceDao
{
    public static final String RESOURCE_NAMESAPCE="sys.resource";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    //查
    public List<Resource> queryResource(Map resourceMap)
    {
        return sqlSessionTemplate.selectList(RESOURCE_NAMESAPCE + ".queryresource",resourceMap);
    }


    public List<Resource> queryResourceWitch(Integer parentId)
    {
        return sqlSessionTemplate.selectList(RESOURCE_NAMESAPCE + ".queryResourceWitch",parentId);
    }


    //新增
    public void insertResource(Resource resource)
    {
        sqlSessionTemplate.insert(RESOURCE_NAMESAPCE+".insertresource",resource);
    }

    //删除
    public void deleteResource(Resource map)

    {
        sqlSessionTemplate.delete(RESOURCE_NAMESAPCE + ".deleteresource", map);

    }

    //更新
    public void batchDeleteResource(List<Resource> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Resource resource : list)
                sqlSession.delete(RESOURCE_NAMESAPCE + ".deleteresource", resource);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }
    //更新
    public void batchUpdateResource(List<Map> list)
    {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (Map params : list)
                sqlSession.update(RESOURCE_NAMESAPCE + ".updateresource", params);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession != null){
                sqlSession.close();
            }
        }
    }


    /**
     * 查询用户所具备访问资源的权限
     * @param map
     * @return
     */
    public List<Resource> queryByRoleId(Map map)
    {
        if(map.containsKey("width") && !Strings.isBlank(map.get("width").toString())){
            return sqlSessionTemplate.selectList(RESOURCE_NAMESAPCE + ".queryResourceByRoleWithWidth",map);
        }
         return sqlSessionTemplate.selectList(RESOURCE_NAMESAPCE + ".queryResourceByRole",map);
    }

    public String querySplice(Map map)
    {
        return sqlSessionTemplate.selectOne(RESOURCE_NAMESAPCE + ".splice", map);
    }

    
	public List<Resource> showResourceTree() {
		return sqlSessionTemplate.selectList(RESOURCE_NAMESAPCE + ".showResourceTree");
	}

    public Integer  move(Integer original,Integer target,Integer parentId){
        Map map = new HashMap();
        map.put("original",original);
        map.put("target",target);
        map.put("parentId",parentId);
        return sqlSessionTemplate.update(RESOURCE_NAMESAPCE + ".move", map);
    }

    public Integer getMax(Integer parentId){
        return sqlSessionTemplate.selectOne(RESOURCE_NAMESAPCE + ".queryMax", parentId);
    }

    /**
     * 递归查询所有子节点.
     * @param parentId
     * @return
     */
    public List<Resource> queryChildren(Integer parentId){
        return sqlSessionTemplate.selectList(RESOURCE_NAMESAPCE + ".queryChildren", parentId);
    }
}

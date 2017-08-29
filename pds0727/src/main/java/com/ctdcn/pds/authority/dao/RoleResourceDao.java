package com.ctdcn.pds.authority.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctdcn.utils.javascript.model.TreeNode;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ctdcn.pds.authority.model.RoleResource;

@Repository
public class RoleResourceDao {
	
	public static final String ROLERESOURCE_MAPPER = "sys.RoleResource";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 角色批量授权权限
	 * @param roleResourceList
	 */
	public void addRoleResource(List<RoleResource> roleResourceList) {
		SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (RoleResource roleResource : roleResourceList){
                sqlSession.update(ROLERESOURCE_MAPPER+".addRoleResource",roleResource);
            }
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
	 * 角色批量删除权限
	 * @param roleResourceList
	 */
	public void delRoleResource(List<RoleResource> roleResourceList){
		SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try{
            for (RoleResource roleResource : roleResourceList){
                sqlSession.delete(ROLERESOURCE_MAPPER + ".delRoleResource", roleResource);
            }
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
	 * 得到所有的资源
	 * @return
	 */
	public List<RoleResource> getRoleResourceList() {
		return sqlSessionTemplate.selectList(ROLERESOURCE_MAPPER+".getRoleResourceList");
	}

	/**
	 * 得到指定角色的资源
	 * @param roleId
	 * @return
	 */
	public List<RoleResource> getRoleResourceById(int roleId) {
		return sqlSessionTemplate.selectList(ROLERESOURCE_MAPPER+".getRoleResourceById", roleId);
	}

    /**
     * 根据权限获取资源.
     * @param roleId    权限ID，可为空
     * @param parentId  父级ID,可为空
     * @param checked   是否有授权,可为空.
     * @return
     */
	public List<TreeNode> getRoleResourceTree(Integer roleId,Integer parentId,Boolean checked){
        Map map = new HashMap();
        map.put("parent_id",parentId);
        map.put("role_id",roleId);
        if(checked == null ){
            map.put("checked",checked);
        }else if(checked == Boolean.FALSE){
            map.put("checked"," null ");
        }else{
            map.put("checked"," not null ");
        }
        return sqlSessionTemplate.selectList(ROLERESOURCE_MAPPER+".getRoleResourceTree",map);
    }

}

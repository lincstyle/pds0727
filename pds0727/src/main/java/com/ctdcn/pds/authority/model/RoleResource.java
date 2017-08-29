package com.ctdcn.pds.authority.model;

/**
 * 角色资源关联表.
 * 主要是用来隔离sql语句
 * @author 张靖  on 2015-6-3 16:46:54
 */
public class RoleResource {
    private Integer id;
    private Integer roleId;
    private Integer resourceId;
    
    
    public RoleResource() {
	}

	public RoleResource(Integer roleId, Integer resourceId) {
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	public RoleResource(Integer id, Integer roleId, Integer resourceId) {
		this.id = id;
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }


    @Override
    public String toString() {
        return "RoleResource{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", resourceId=" + resourceId +
                '}';
    }
}

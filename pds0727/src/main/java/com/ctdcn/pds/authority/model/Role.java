package com.ctdcn.pds.authority.model;

/**
 * 角色.
 * @author 张靖 on 2015-6-3 10:50:31
 */
public class Role {
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 说明
     */
    private String description;
    /**
     * 角色的值
     */
    private String role;
    
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName
				+ ", description=" + description + ", role=" + role + "]";
	}
    
}

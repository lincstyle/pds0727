package com.ctdcn.pds.organization.model;

/**
 * Created by Triumphant on 2015/6/9.
 */
/**
 * @author Administrator
 *
 */
public class Department
{
    private Integer departmentId;  //部门id
    private String departmentName;  //部门名
    private Integer hasChildren;//是否有子节点
    private Integer width;  //级数
    private Integer parentId;  //父级id
    private Integer sort;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

	public Integer getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", departmentName="
				+ departmentName + ", hasChildren=" + hasChildren
				+ ", width=" + width + ", parentId="
				+ parentId + "]";
	}
    
}

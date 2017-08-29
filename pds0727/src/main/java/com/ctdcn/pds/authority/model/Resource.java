package com.ctdcn.pds.authority.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 资源表.
 * 菜单，按钮 都可以理解为资源
 *
 * @author 张靖 on 2015-6-3 10:50:49
 */
public class Resource {
    private Integer id;
    private Integer parentId;
    private String parentIds;
    private String name;
    private String url;
    private String identity;
    /**
     * 是否显示
     */
    @JSONField(name = "isShow")
    private Integer isShow;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 宽度
     */

    private Integer width;

    public Resource() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setParentId(Integer parentId) {

        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", parentIds='" + parentIds + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", identity='" + identity + '\'' +
                ", isShow=" + isShow +
                '}';
    }
}

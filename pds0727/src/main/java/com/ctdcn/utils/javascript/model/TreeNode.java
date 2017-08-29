package com.ctdcn.utils.javascript.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * easyui-tree数据模型.
 * @author 张靖
 *         2015-06-13 15:01.
 */
public class TreeNode extends Node {

    protected Integer parentId;

    protected String state;

    protected TreeNode[] children;

    protected Object attributes;

    protected String url;

    protected Boolean checked;

    @JSONField(name = "isParent")
    protected Boolean isParent;

    public Boolean isParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TreeNode[] getChildren() {
        return children;
    }

    public void setChildren(TreeNode[] children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }
}

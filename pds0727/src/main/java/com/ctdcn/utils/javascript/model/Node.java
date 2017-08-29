package com.ctdcn.utils.javascript.model;

/**
 * easyui-combo数据对象模型.
 * @author 张靖
 *         2015-06-13 14:58.
 */
public class Node {

    protected Integer id;

    protected String text;
    protected String selected;

    public Node() {
    }

    public Node(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Node(Integer id, String text, String selected) {
		this.id = id;
		this.text = text;
		this.selected = selected;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
    
    
}

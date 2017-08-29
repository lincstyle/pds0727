package com.ctdcn.pds.sys.model;

/**
 * Created by Triumphant on 2015/6/11.
 */
public class JsonBean
{

    private String msg;
    private boolean success;
    private Object obj;
    private Object rows;
    private int total;
    private Object footer;

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public Object getFooter() {
        return footer;
    }

    public void setFooter(Object footer) {
        this.footer = footer;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean getSuccess(){
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}

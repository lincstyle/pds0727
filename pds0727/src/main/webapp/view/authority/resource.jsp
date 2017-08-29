<%--
  User: zjblague
  Date: 2015/6/14
  Time: 21:52
  Description:
            资源管理
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="/static/js/jsp-js/authority/resource.js" ></script>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west'" style="width:300px;padding:20px">
        <div class="easyui-panel" title="" style="padding:20px">
            <span>
                资源列表
            </span>
            <ul id="resourceTree" class="ztree">
            </ul>
        </div>
    </div>
    <div data-options="region:'center'" style="padding:10px">
        <div class="easyui-panel" style="width:500px;height:500px">
        <form id="resourceForm" method="post">
            <%----%>
            <input type="hidden" name="id" />
            <input type="hidden" name="parentId" />
            <input type="hidden" name="width" />
            <input type="hidden" name="sort" />
            <%----%>
            <table class="table">
                <tr>
                    <td>资源名称:</td>
                    <td><input class="easyui-textbox" type="text" name="name" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td>URL:</td>
                    <td><input class="easyui-textbox" type="text" name="url" /></td>
                </tr>
                <tr>
                    <td>权限描述符:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="identity" data-options="required:true" />
                        <div id="resourceFormIdentity"></div>
                    </td>
                </tr>
                <tr>
                    <td>是否显示:</td>
                    <td>
                        <select class="easyui-combobox" name="isShow" >
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>
            </table>
            <div style="text-align:center;padding:5px">
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.authority_resource.submitEdit()" >修改</a>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.authority_resource.submitRemove()" >删除</a>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.authority_resource.submitAdd(0)" >新增同级</a>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.authority_resource.submitAdd(1)" >新增子节点</a>
                <button class="easyui-linkbutton" type="reset" >取消</button>
            </div>
        </form>
        </div>
    </div>
</div>
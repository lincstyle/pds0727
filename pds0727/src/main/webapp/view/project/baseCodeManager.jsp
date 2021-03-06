<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
            基本编码管理
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="/static/js/jsp-js/project/baseCodeManager.js" ></script>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west'" style="width:300px;padding:20px">
        <div class="easyui-panel" title="" style="padding:20px">
            <span>
                基本项目阶段列表
            </span>
            <ul id="baseCodeTree" class="ztree">
            </ul>
        </div>
    </div>
    <div data-options="region:'center'" style="padding:10px">
        <div class="easyui-panel" style="width:500px;height:500px">
            <form id="baseCodeForm" method="post">
                <%----%>
                <input type="hidden" name="typeCode" />
                <input type="hidden" name="serial"/>
                <%----%>
                <table class="table">
                    <tr>
                        <td>编码名称:</td>
                        <td><input class="easyui-textbox" type="text" name="mc" data-options="required:true,validType:'isBlank'" /></td>
                    </tr>
                </table>
                <div style="text-align:center;padding:5px">
                    <shiro:hasPermission name="project:baseCode:editBaseCode" >
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.basecode_mananger.submitEdit()" >修改</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="project:baseCode:deleteBaseCode" >
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.basecode_mananger.submitRemove()" >删除</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="project:baseCode:addBaseCode" >
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.basecode_mananger.submitAdd(1)" >往前新增</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="project:baseCode:addBaseCode" >
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="app.basecode_mananger.submitAdd(2)" >往后新增</a>
                    </shiro:hasPermission>
                    <button class="easyui-linkbutton" type="reset" >取消</button>
                </div>
            </form>
        </div>
    </div>
</div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>员工日报系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path;
    %>
    <%--jquery--%>
    <script type="text/javascript" src="<%=basePath%>/static/js/jquery/1.11.3/jquery.min.js" charset="utf-8"></script>

    <!--[if lt IE 9]>
    <script src="/static/js/html5shiv.js"></script>
    <script src="/static/js/respond.min.js"></script>
    <![endif]-->

    <%--bootstrap--%>
    <link rel="stylesheet" href="/static/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/static/font-awesome/4.3.0/css/font-awesome.min.css" />
    <script type="text/javascript" src="/static/bootstrap/3.3.4/js/bootstrap.min.js"></script>



    <%-- easyui 相关 --%>
    <script type="text/javascript" src="<%=basePath%>/static/js/easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/easyui/jquery.easyui.mask.js" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui/themes/metro/easyui.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/easyui/themes/icon.css" charset="utf-8"/>
    <script type="text/javascript" src="<%=basePath%>/static/js/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

    <%-- 工具类 --%>
    <script type="text/javascript" src="<%=basePath%>/static/js/easyui/plugins/extendvalidate.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/util/jutil.js" charset="utf-8"></script>
    <%-- ztree --%>
    <script type="text/javascript" src="<%=basePath%>/static/js/ztree/jquery.ztree.all-3.5.min.js" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/ztree/zTreeStyle.css" charset="utf-8"/>
    <%--按键插件--%>
    <script src="/static/js/wysiwyg/jquery.hotkeys.js" ></script>


    <%--首页--%>
    <script type="text/javascript" src="<%=basePath%>/static/js/jsp-js/index.js" charset="utf-8"></script>

    <script type="text/javascript">
        var ctx = "<%=path%>";
        //用与app域隔离每个JS文件中的变量,js文件规范请参照departManager.js
        var app = {};
    </script>
</head>
<body onload="loadMenu()" >
<div id="homeLayout" class="easyui-layout" fit='true'>
    <div data-options="region:'north'" border='false' style="height: 67px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td></td>
            </tr>
        </table>
    </div>
    <div data-options="region:'west',title:'系统菜单',split:true" style="width: 180px;">
        <div id="homeLayout_west" class="easyui-layout" fit='true'>
            <div data-options="region:'center'">
                <ul id="systemMenu"></ul>
            </div>
            <div data-options="region:'south',title:'系统用户信息'" style="height: 200px;">
                <p id="user_name">用户：${user.name}</p>

                <p>角色：${roleName }</p>
                <%-- 
                <button onclick="modifyMyData();">修改个人资料</button>
				--%>				 
                <p>&nbsp;</p>

                <p align="right">
                	<a href="/logout" style="text-decoration:none" target="_parent">退出系统</a>
                </p>
            </div>
        </div>
    </div>

    <div data-options="region:'center'" border='false'>
        <div id="center_tabs" class="easyui-tabs" fit="true">
            <div title="首页" data-options="closable:true">
                这里应该显示通知
            </div>
        </div>
    </div>
</div>
</body>
</html>
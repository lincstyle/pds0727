<%--
  Created by IntelliJ IDEA.
  User: 刘凯旋
  Date: 2015/6/9
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!--不加以上代码，中文将显示乱码-->
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<script type="text/javascript"
        src="<%=basePath%>/static/js/easyui/jquery.min.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>/static/js/easyui/jquery.easyui.min.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>/static/js/easyui/jquery.easyui.mask.js"
        charset="utf-8"></script>
<link rel="stylesheet" type="text/css"
      href="<%=basePath%>/static/js/easyui/themes/metro/easyui.css" charset="utf-8"/>
<link rel="stylesheet" type="text/css"
      href="<%=basePath%>/static/js/easyui/themes/icon.css" charset="utf-8"/>
<script type="text/javascript"
        src="<%=basePath%>/static/js/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>/static/js/easyui/plugins/extendvalidate.js" charset="utf-8"></script>
<script type="text/javascript"
        src="<%=basePath%>static/js/jsp-js/sys/resourceManage.js" charset="utf-8"></script>

<div data-options="region:'center'">
  <ul id="resourceMenu"></ul>
</div>






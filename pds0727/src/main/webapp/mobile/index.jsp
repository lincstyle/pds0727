<%@ page import="org.apache.shiro.web.mgt.CookieRememberMeManager" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="zhCN" ng-app="indexApp">
<head>
    <title>首页</title>
    <jsp:include page="/mobile/common/head.jsp" />

</head>
<body>
    <mobile-nav href="#" icon="fa fa-home fa-1x">首页</mobile-nav>
    <div class="container" >
        <div class="row text-center">
            <div class="col-xs-6">
                <a href="/mobile/projectManager/">
                    <div class="panel panel-default">
                        <i class="fa fa-tasks fa-4x"></i>
                        <p>项目管理</p>
                    </div>
                </a>
            </div>
            <div class="col-xs-6">
                <a href="/mobile/project/">
                    <div class="panel panel-default">
                        <i class="fa fa-pencil-square-o fa-4x"></i>
                        <p>日志管理</p>
                    </div>
                </a>
            </div>
            <div class="col-xs-6">
                <a href="/mobile/modifyMyData/#/modifyMyData">
                    <div class="panel panel-default">
                        <i class="fa fa-user fa-4x"></i>
                        <p>个人信息管理</p>
                    </div>
                </a>
            </div>
            <div class="col-xs-6">
                <a href="/mobile/involProject/#/involProject">
                    <div class="panel panel-default">
                        <i class="fa fa-comment fa-4x"></i>
                        <p>项目通知管理</p>
                    </div>
                </a>
            </div>
            <div class="col-xs-6">
                <a href="/logout" onclick='delCookie("<%=CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME %>")'>
                    <div class="panel panel-default">
                        <i class="fa fa-sign-out fa-4x"></i>
                        <p>退出系统</p>
                    </div>
                </a>
            </div>
        </div>

    </div>

    <jsp:include page="/mobile/common/foot.jsp" />

    <!-- build:js /static/js/ng-js/mobile/mobile.js -->
    <script src="/static/js/ng-js/mobile/nav/nav.js" ></script>
    <!-- endbuild -->
    <script>
        angular.module("indexApp",["mobile"]);

        function getCookie(name){
            var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
            if(arr != null) return unescape(arr[2]); return null;
        }

        function delCookie(name){
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval=getCookie(name);
            if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
        }

    </script>
</body>
</html>

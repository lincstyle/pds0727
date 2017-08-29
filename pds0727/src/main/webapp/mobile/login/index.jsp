<%@ page import="org.apache.shiro.web.mgt.CookieRememberMeManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!doctype html>
<html lang="zhCN">
<head>
    <title>登录</title>
    <meta charset="utf-8" />
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="stylesheet" href="/static/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/static/css/mobile/main.css" />

    <link rel="stylesheet" href="/static/font-awesome/4.3.0/css/font-awesome.min.css" />

</head>
<body>
    <div class="container" >
        <div class="panel panel-primary">
        	  <div class="panel-heading">
        			<h3 class="panel-title">登录系统</h3>
        	  </div>
        	  <div class="panel-body">
                  <c:if test="${not empty error}">
                      <div class="alert alert-danger" role="alert">
                          <span class="icon-remove-sign icon-large"></span>&nbsp;${error}
                      </div>
                  </c:if>
                  <form class="form-horizontal" method="post" action="/login">
                      <div class="form-group">
                          <label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
                          <div class="col-sm-10">
                              <input type="text" name="username" class="form-control" id="inputEmail3" placeholder="用户名">
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
                          <div class="col-sm-10">
                              <input type="password" name="password" class="form-control" id="inputPassword3" placeholder="密码">
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-sm-offset-2 col-sm-10">
                              <div class="checkbox">
                                  <label>
                                      <input type="checkbox" name="rememberMe" value="1"> 记住我
                                  </label>
                              </div>
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-xs-offset-4 col-xs-2">
                              <input type="submit" class="btn btn-success" value="登录" />
                          </div>
                      </div>
                  </form>
        	  </div>
        </div>
    </div>
</body>
</html>

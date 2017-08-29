<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>系统登录</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta http-equiv="cache-control" content="no-cache">
	<link rel="stylesheet" href="/static/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="/static/bootstrap/3.3.4/css/bootstrap-theme.min.css">

	<!--[if lt IE 9]>
	<script src="/static/js/html5shiv.js"></script>
	<script src="/static/js/respond.min.js"></script>
	<![endif]-->

	<script src="/static/js/jquery/1.11.3/jquery.min.js" ></script>
	<script src="/static/js/jquery.placeholder.min.js" ></script>
	<script>
		$(function (){
			$('input, textarea').placeholder();
		})
	</script>
</head>

<body background="/static/img/grey_wash_wall.png">
	<div class="container">
		<div class="row" style="margin-top: 50px;">
			<div  class="col-md-6 col-md-offset-3">
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title">登录</div>
					</div>
					<div style="padding-top:30px" class="panel-body">
						<form method="post" action="/login">
							<c:if test="${not empty error}">
								<div class="alert alert-danger" role="alert">
									<span class="icon-remove-sign icon-large"></span>&nbsp;${error}
								</div>
							</c:if>

							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								<input id="login-username" type="text" class="form-control" name="username" value=""
									   placeholder="用户名">
							</div>

							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input id="login-password" type="password" class="form-control" name="password"
									   placeholder="密码">
							</div>
							<div class="row">
									<div class="col-md-3 col-md-offset-3">
										<input style=" width: 16px; height: 16px; border: 1px solid; " type="checkbox" name="rememberMe" value="1" > 记住我
									</div>
									<div class="col-md-2">
										<input type="submit" class="btn btn-success" value="登录" />
									</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
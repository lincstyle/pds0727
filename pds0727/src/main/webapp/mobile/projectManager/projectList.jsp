<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<mobile-nav href="/admin/index">
    项目管理
    <a href="#/findProject">
        <i class="fa fa-search fa-1x pull-right" style="padding: 0 30px;font-size: 18px;" ></i>
    </a>
    <shiro:hasPermission name="project:ProjectAintenance:addProject" >
        <a href="#/plusProject/index">
            <i class="fa fa-plus fa-1x pull-right" style="font-size: 18px;" ></i>
        </a>
    </shiro:hasPermission>
</mobile-nav>

<div class="scroller">
    <ul ng-if="data != null && data.length != 0" >
        <li ng-repeat="project in data" >
            <div id="{{project.pid}}" class="container-fluid">
                <div class="row">
                    <label class="col-sm-2 control-label">
                        {{project.pname}}
                    </label>
                    <br>
                </div>

                <div class="panel-body" style="word-wrap: break-word; background-color:#E8E8E8;" ng-click="toDetail(project.pid)">
                    {{project.pintro}}
                </div>

                <div class="form-group" align="right" style="margin-top: 10px;">
                    <shiro:hasPermission name="project:ProjectAintenance:editProject" >
                        <a ng-click="editProject(project.pid,'list')">修改</a>
                    </shiro:hasPermission>
                    &nbsp;&nbsp;
                    <shiro:hasPermission name="project:ProjectAintenance:removeProject" >
                        <a ng-click="delProject(project.pid)" >删除</a>
                    </shiro:hasPermission>
                </div>
                <hr/>
            </div>
        </li>
    </ul>

    <ul ng-if="data.length < 1" >
        <li>
            <div class="alert alert-warning" align="center" >{{error}}</div>
        </li>
    </ul>
</div>

<button ng-if="data != null && data.length != 0" class="panel panel-default" ng-click="getData()"
        style="width: 100%;margin-top: -1px;" loading-button="" is-done="loadDone" is-show="hasMore" loading-msg="查询中..">
    更多
</button>
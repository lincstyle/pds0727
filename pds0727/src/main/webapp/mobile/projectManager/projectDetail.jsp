<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<mobile-nav href="#/projectList">项目明细</mobile-nav>

<div class="container-fluid" style="margin: 10px auto;" >
    <div class="page-header" style="margin: -10px 0px 10px 0px;" >
        <h1 align="center">
            {{project.pname}}<br>
        </h1>
        <small >{{project.pstage}}</small>
    </div>
    <dl>
        <dt>项目简介：</dt>
        <dd>
            <div class="well" data-ng-bind-html="project.pintro" style="word-wrap: break-word">
            </div>
        </dd>
    </dl>

    <dl>
        <dt>立项人：</dt>
        <dd>
            {{project.person}}
        </dd>
    </dl>

    <dl>
        <dt>立项时间：</dt>
        <dd>
            {{project.pdate}}
        </dd>
    </dl>

    <dl>
        <dt>项目负责人：</dt>
        <dd>
           {{isResponse}}
        </dd>
    </dl>

    <dl>
        <dt>项目参与人：</dt>
        <dd>
            {{userNames}}
        </dd>
    </dl>

    <dl>
        <dt>最后更新人：</dt>
        <dd>
            {{project.lastPerson}}
        </dd>
    </dl>

    <dl>
        <dt>最后更新时间：</dt>
        <dd>
            {{project.lastDate}}
        </dd>
    </dl>

    <div class="form-group" >
        <shiro:hasPermission name="project:ProjectAintenance:editProject" >
                <button class="btn btn-success  btn-block" ng-click="editProject(project.pid,'detail')" >修改项目</button>
        </shiro:hasPermission>
        <shiro:hasAnyRoles  name="projectleder,developer" >
            <button class="btn btn-success  btn-block" ng-click="createChat(project.pid)" >发起会话</button>
            <button class="btn btn-success  btn-block" ng-click="queryAllLog(project.pid)" >查看项目日志</button>
        </shiro:hasAnyRoles>
    </div>

</div>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<mobile-nav href="#/list">日志明细</mobile-nav>
<div class="container-fluid" style="margin-top: -20px;" >
    <div class="page-header">
        <h1>
            {{projectLog.pname}}<br>
            <small >{{projectLog.pstage}}</small>
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <dl>
                <dt>更&nbsp;新&nbsp;人：</dt>
                <dd>{{projectLog.person}}</dd>
            </dl>
        </div>
        <div class="col-xs-12">
            <dl>
                <dt>报告日期：</dt>
                <dd>{{projectLog.sdate}}</dd>
            </dl>
        </div>
        <div class="col-xs-12">
            <dl>
                <dt>提交时间：</dt>
                <dd>{{projectLog.cdate}}</dd>
            </dl>
        </div>
    </div>
    <hr/>
    <div class="well" data-ng-bind-html="projectLog.contents" style="word-wrap: break-word">
    </div>

    <div class="form-group" ng-if="userId == projectLog.userId ">
        <shiro:hasPermission name="project:Projectlog:editProjectLog" >
            <button class="btn btn-success btn-block" loading-button ng-click="editProLog(projectLog.lid,'detail')" >修改日志</button>
        </shiro:hasPermission>
    </div>
</div>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<mobile-nav href={{logBack}}>
    日志管理
    <a href="#/list-filter">
        <i class="fa fa-search fa-1x pull-right" style="padding: 0 30px;font-size: 18px;" ></i>
    </a>
    <shiro:hasPermission name="project:Projectlog:addProjectLog" >
        <a href="#/plusLog">
            <i class="fa fa-plus fa-1x pull-right" style="font-size: 18px;" ></i>
        </a>
    </shiro:hasPermission>
</mobile-nav>

<div class="scroller">
    <ul ng-if="data != null && data.length != 0" >
        <li id="{{projectLog.lid}}" ng-repeat="projectLog in data"  style="margin-top: 15px;">
            <div class="container-fluid">
                <div class="row">
                    <label class="col-sm-2">
                        {{projectLog.person}}
                    </label>
                    <label class="col-sm-2">
                        {{projectLog.pname}}
                    </label>
                </div>
                <div style="background-color: #E8E8E8;" >
                    <div class="panel-body touch" style="word-wrap: break-word;" ng-click="toDetail(projectLog.lid,'detail')">
                        {{projectLog.detail}}
                    </div>
                </div>
                <div style="color: #919191;font-size: 10px; margin-top: 10px;">
                    {{projectLog.sdate}}
                </div>
                <div style="height: 20px">
                    <div class="form-group" align="right" ng-if="projectLog.userId == userId">
                            <a  ng-click="editProLog(projectLog.lid,'list')">修改</a>
                        &nbsp;&nbsp;
                            <a ng-click="delProLog(projectLog.lid)" >删除</a>
                    </div>
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
        style="width: 100%;margin-top: -1px;" loading-button=""  is-done="loadDone" is-show="hasMore" loading-msg="查询中..">
    更多
</button>


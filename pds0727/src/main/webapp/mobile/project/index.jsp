<%@ page import="com.ctdcn.pds.organization.model.User" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!doctype html>
<html lang="zhCN" ng-app="projectLogApp">
<head>
    <title>项目汇报</title>
    <jsp:include page="/mobile/common/head.jsp" />
    <link rel="stylesheet" href="/static/js/textAngular-1.4.6/textAngular.css" />

</head>
<body >
<div ng-view ng-cloak class="container" >
</div>

<jsp:include page="/mobile/common/foot.jsp" />
<%-- 第三方--%>
<!-- build:js /static/js/textAngular-all.js -->
<script src='/static/js/textAngular-1.4.6/textAngular-rangy.min.js'></script>
<script src='/static/js/textAngular-1.4.6/textAngular-sanitize.min.js'></script>
<script src='/static/js/textAngular-1.4.6/textAngular.js'></script>
<script src='/static/js/textAngular-1.4.6/textAngularSetup.js'></script>
<!-- endbuild -->

<!-- build:js /static/js/ng-js/mobile/mobile.js -->
<script src="/static/js/ng-js/mobile/nav/nav.js"></script>
<script src="/static/js/ng-js/mobile/loading-button/loadingButton.js"></script>
<script src="/static/js/ng-js/mobile/confirmService.js"></script>
<!-- endbuild -->

<script>
    angular.module("projectLogApp",["mobile","ngRoute", "ngAnimate","ngTouch","textAngular"])
            .factory('listFilter',function() {
                //全局list变量
                return{}
            })
            .factory('plusProLogParam',function() {
                var plusProLogParam = new Object();
                return plusProLogParam;
            })
            .factory('editProLogParam',function() {
                var editProLogParam = new Object();
                return editProLogParam;
            })
            .service('logService',["$http",function ($http){
                var getData = function(param, url) {
                    return $http({
                        method: 'post',
                        url: url,
                        data: param
                    });
                }
                return {
                    proLogService: function(param, url) {
                        return getData(param, url);
                    }
                };
            }])
            .controller("listCtrl",["$scope","$http","listFilter","$location","$filter","confirmer","$window","$routeParams","logService",
                function($scope,$http,listFilter,$location,$filter,confirmer,$window,$routeParams,logService){
                    if($routeParams.pid){
                        listFilter.pid = $routeParams.pid;
                        $scope.logBack = "/mobile/projectManager/#/detail/" + listFilter.pid;
                    } else {
                        $scope.logBack = "/admin/index";
                    }
                    /**
                     * 初始化数据
                     * @type {{page: number, rows: number}}
                     */
                    $scope.pager ={
                        page:1,
                        rows:5
                    };
                    $scope.data = null;
                    $scope.loadDone = false;
                    $scope.hasMore = true;

                    $scope.userId = <%=((User)SecurityUtils.getSubject().getSession().getAttribute(User.USER_SESSIN_KEY)).getId()%>;

                    //获取数据
                    $scope.getData = function () {
                        $scope.loadDone = false;
                        var param =  angular.extend({}, $scope.pager, listFilter);

                        if(param.selectSDate){
                            param.selectSDate = $filter("date")(param.selectSDate,"yyyy-MM-dd");
                        }
                        if(param.selectEDate){
                            param.selectEDate = $filter("date")(param.selectEDate,"yyyy-MM-dd");
                        }

                        logService.proLogService(param,"/projectLog/queryPrLog")
                                .success(function (data){
                                    for(var i in data.rows){
                                        if(data.rows[i].detail){
                                            data.rows[i].detail = data.rows[i].detail.replace(/&nbsp;/g," ");  //数据库存的是nbsp
                                        }
//                                data.rows[i].detail = data.rows[i].detail.replace(" ","&nbsp;");   //数据库存的是“ ”
                                    }

                                    $scope.pager.total = data.total;
                                    $scope.pager.page++;

                                    if($scope.data == null){
                                        $scope.data = new Array();
                                        $scope.data = data.rows;
                                        $scope.error = "没有查询结果";
                                    } else {
                                        if (data.errorMsg) {
                                            $scope.error = data.errorMsg;
                                        } else {
                                            $scope.data = $scope.data.concat(data.rows);
                                        }
                                    }
                                    $scope.loadDone = true;
                                }).then(function(){
                                    if($scope.pager.total && ($scope.pager.page-1)*$scope.pager.rows >= $scope.pager.total){
                                        $scope.hasMore = false;
                                    }else{
                                        $scope.hasMore = true;
                                    }
                                });
                    }
                    //首次调用
                    $scope.getData();

                    //修改按钮，进入修改页面
                    $scope.editProLog = function(lid,type){
                        $location.path("/editProLog/"+lid+"/"+type);
                    }

                    //删除按钮
                    $scope.delProLog = function(lid){
                        confirmer.open(function (value){
                            if(value == 1){
                                logService.proLogService({lid:lid}, "/projectLog/deleteprLog")
                                        .success(function(data){
                                            if(data.success){
                                                alert("删除成功！");
                                                $window.location.reload(true);
                                            } else {
                                                alert("删除失败！");
                                            }
                                        });
                            }
                        });
                    }
                    //日志明细
                    $scope.toDetail = function(id){
                        $location.path("/detail/" + id);
                    }

                }])
            .controller("listFilterCtrl",["$scope","$http","listFilter","$location",
                function($scope,$http,listFilter,$location){
//                    for(var i in listFilter.userList){
//                        if(listFilter.userList[i].account){
//                            listFilter.userList[i].account = false;
//                        }
//                    }
                    /**
                     *   日志查询
                     */
                    $("input[type='date']").blur(function (e){
                        $(e.delegateTarget).change();
                    });
                    //回退
                    $scope.back = function(){
                        listFilter.pname = null;
                        listFilter.proRespId = null;
                        listFilter.proRespName = null;
                        for(var i in listFilter.userList){
                            if(listFilter.userList[i].account){
                                listFilter.userList[i].account = false;
                            }
                        }
                        $location.path("/list");
                    }
                    //选择更新人
                    $scope.updatePerson = function(){
                        listFilter = $.extend(true, listFilter, $scope.listFilter);
                        $location.path("/updatePer");
                    }
                    $scope.listFilter = $.extend(true, {}, listFilter);
                    //校验开始时间与结束时间的合法性,并查询
                    $scope.saveListFilter = function (){
                        var sdate = $("#selectSDate_prLog").val();
                        var edate = $("#selectEDate_prLog").val();
                        if(sdate && edate && (sdate > edate)){
                            return;
                        }
                        listFilter = $.extend(true, listFilter, $scope.listFilter);
                        $location.path("/list");
                    }

                    //清空表单
                    $scope.resetListFilter = function(){
                        $scope.listFilter.pname = "";
                        $scope.listFilter.selectSDate = "";
                        $scope.listFilter.selectEDate = "";
                        $scope.listFilter.proRespId = null;
                        $scope.listFilter.proRespName = null;
                        for(var i in $scope.listFilter.userList){
                            if($scope.listFilter.userList[i].account){
                                $scope.listFilter.userList[i].account = false;
                            }
                        }
                    }
                }
            ])
            .controller("updatePerCtrl",["$scope","$http","listFilter","$location","logService",
                function($scope,$http,listFilter,$location,logService){
                    /**
                     * 选择更新人页面
                     */
                    $scope.title = "选择更新人";
                    $scope.proResponseBack = "#/list-filter";
                    $scope.isDisabled = true;

                    //按更新人的条件查询
                    var oldUpdatePerson = listFilter.userList;//选中前
                    $scope.getUpdatePersonData = function(inputParam){
                        logService.proLogService(inputParam, "/userManager/getProResponse")
                                .success(function(data){
                                    $scope.search_input = "";
                                    oldUpdatePerson = $.extend(true, {}, data);
                                    $scope.userList = data;
                                });
                    }
                    //根据选择更新人,判断是否再次获取数据
                    if(oldUpdatePerson == undefined){
                        $scope.getUpdatePersonData();
                    } else {
                        $scope.userList = $.extend(true, {}, oldUpdatePerson);;
                        for(var i in $scope.userList){
                            if($scope.userList[i].account == true){
                                $scope.isDisabled = false;
                                break;
                            } else {
                                $scope.isDisabled = true;
                            }
                        }
                    }

                    //搜索的方法
                    $scope.search_user = function(inputParam){
                        $scope.getUpdatePersonData(inputParam);
                    }

                    //点击复选框的事件
                    $scope.selected = function() {
                        for(var i in $scope.userList){
                            if($scope.userList[i].account == true){
                                $scope.isDisabled = false;
                                break;
                            } else {
                                $scope.isDisabled = true;
                            }
                        }
                    }

                    //提交表单
                    $scope.submit_comm = function() {
                        //定义一个标记，默认为false，如果用户有选择的内容就为true，并操作数据
                        var flag = false;
                        for(var i in $scope.userList){
                            if($scope.userList[i].account == true){
                                flag = true;
                                break;
                            }
                        }

                        if(flag){
                            var proResp_name = "";
                            var proResp_id = new Array();
                            var newUpdatePerson = $scope.userList;
                            listFilter.userList = $scope.userList;
                            var j = 0;
                            for(var k in oldUpdatePerson){
                                for(var i in newUpdatePerson){
                                    if(oldUpdatePerson[k].id == newUpdatePerson[i].id){
                                        if(newUpdatePerson[i].account == true){
                                            proResp_name += "," + oldUpdatePerson[k].name;
                                            proResp_id += "," + oldUpdatePerson[k].id;
                                            j++;
                                        }
                                        break;
                                    }
                                }
                            }
                            listFilter.proRespId = proResp_id.substring(1, proResp_id.length);
                            listFilter.proRespName = proResp_name.substring(1, proResp_name.length);
                        } else {
                            listFilter.proRespId = null;
                            listFilter.proRespName = null;
                        }
                        $location.path("/list-filter");
                    }
                }
            ])
            .controller("plusLogCtrl",["$scope","$http","$location","$filter","plusProLogParam","logService",
                function($scope,$http,$location,$filter,plusProLogParam,logService){
                    //新增日志
                    $("input[type='date']").blur(function (e){
                        $(e.delegateTarget).change();
                    });
                    //表单提交
                    $scope.submitDone = true;
                    //初始化表单
                    if(plusProLogParam.sdate == null){
                        plusProLogParam.sdate = new Date();
                    }
                    $scope.proLog = plusProLogParam;

//                  首次进来，是没有值的，第二次进来，就判断新值和旧值是不是一样的。
                    if(plusProLogParam.oldPname != null && plusProLogParam.oldPname != $scope.proLog.pname){
                        $scope.proLog.pstage = "";
                        $scope.proLog.pflagId = null;
                        plusProLogParam.oldPname = $scope.proLog.pname;
                    }

                    //点击选择项目的input
                    $scope.select_pname = function (){
                        $location.path("/selectPname");
                    }

                    //点击选择项目阶段的input
                    $scope.select_pstage = function(){
                        if($scope.proLog.pname != null){
                            $location.path("/plusSelectPstage/"+$scope.proLog.pid);
                        } else {
                            alert("请先选择项目!");
                        }
                    }
                    //提交表单
                    $scope.submit = function () {
                        if (!$scope.projectLogForm.$valid) {
                            alert("请填写完成表单");
                            return;
                        }
                        var contents = $scope.proLog.contents;
                        var detail = $(contents).text();
                        if (!detail) {
                            alert("请填写日志内容");
                            return;
                        }
                        $scope.submitDone = false;

                        var param = {
                            pid: $scope.proLog.pid,
                            pname: $scope.proLog.pname,
                            pflag: $scope.proLog.pflagId,
                            sdate: $filter("date")($scope.proLog.sdate,"yyyy-MM-dd"),
                            detail: detail,
                            contents: contents
                        };
                        logService.proLogService(param, "/projectLog/addprLog")
                                .success(function(data){
                                    if(!data.success){
                                        alert("提交失败：数据异常！");
                                    }else{
                                        alert("提示信息：" + data.msg);
                                        plusProLogParam.oldPname = null;
                                        $location.path("/list");
                                    }
                                    $scope.submitDone = true;
                                });
                    }
                }
            ])
            .controller("selectPnameCtrl",["$scope","$http","$location","plusProLogParam","logService",
                function($scope,$http,$location,plusProLogParam,logService) {
                    $scope.isDisabled = true;
                    /**
                     *  查项目名列表
                     */
                    $scope.getProNameList = function (){
                        logService.proLogService(null, "/projectManage/projectList")
                                .success(function(data){
                                    if(data.length < 1){
                                        $scope.error = "您没有参加任何项目哦!";
                                    } else {
                                        $scope.pnameList = data;
                                        plusProLogParam.pnameList = $.extend(true,{},data);
                                    }
                                });
                    }
                    //获取项目名列表.
                    if(plusProLogParam.pname == null){
                        $scope.getProNameList();
                    } else {
                        $scope.pnameList = $.extend(true,{},plusProLogParam.pnameList);
                        for(var i in $scope.pnameList) {
                            if($scope.pnameList[i].pid == plusProLogParam.pid) {
                                $scope.pnameList[i].puid = plusProLogParam.pid;//用于标记页面上input元素的ID
                                $scope.isDisabled = false;
                                break;
                            }
                        }
                    }

                    //点击项目名的同时把所选择的项目名和id存在全局变量里
                    $scope.onRadioClick = function (pname, pid){
                        $scope.isDisabled = false;
                        $scope.selectPid = pid;
                        $scope.selectPname = pname;
                    }
                    //提交表单
                    $scope.submit_proPname = function(){
                        plusProLogParam.pid = $scope.selectPid;
                        plusProLogParam.pname = $scope.selectPname;
                        //如果是第一次选择，就把阶段名字 存为旧值
                        if(plusProLogParam.oldPname == null){
                            plusProLogParam.oldPname = $scope.selectPname;
                        }
                        $location.path("/plusLog");
                    }
                }
            ])
            .controller("plusSelectPstageCtrl",["$scope","$http","$routeParams","$location","plusProLogParam","logService",
                function($scope,$http,$routeParams,$location,plusProLogParam,logService) {
                    /**
                     *  新增项目时选择项目阶段页面
                     */
                    var pid = $routeParams.pid;
                    $scope.updateStage_isDisabled = true;
                    $scope.updateStage_title = "选择项目阶段";
                    $scope.backHref_type = "#/plusLog";

                    $scope.getPstageList = function () {
                        logService.proLogService({pid: pid}, "/stagecode/getProStageById")
                                .success(function(stageData){
                                    $scope.proStageList = stageData;
                                    if (stageData.length < 1) {
                                        $scope.error = "此项目还没有添加项目阶段哦!";
                                    } else {
                                        $scope.updateStage_isDisabled = false;
                                        for (var i in stageData) {
                                            if (plusProLogParam.pflagId == stageData[i].typecode) {
                                                $scope.u_selected = stageData[i].typecode;
                                                $scope.pflagMc = stageData[i].mc;
                                                break;
                                            }
                                        }
                                        plusProLogParam.proStageList = $.extend(true, {}, stageData);
                                    }
                                });
                    }
                    $scope.getPstageList();

                    //点击项目名触发的事件
                    $scope.onRadioClick = function(typecode,mc){
                        $scope.updateStage_isDisabled = false;
                        $scope.u_selected = typecode;
                        $scope.pflagMc = mc;
                    }
                    //提交表单
                    $scope.update_stage_submit = function(){
                        plusProLogParam.pflagId = $scope.u_selected;
                        plusProLogParam.pstage = $scope.pflagMc;
                        $location.path("/plusLog");
                    }
                }
            ])
            .controller("editLogCtrl",["$scope","$http","$routeParams","$location","editProLogParam","logService",
                function($scope,$http,$routeParams,$location,editProLogParam,logService){
                    /**
                     *   修改日志
                     */
                    $scope.submitDone = true;
                    var lid = $routeParams.lid;
                    var type = $routeParams.type;
                    $scope.href_type = type == 'list' ? '#/list' : '#/detail/'+lid;
                    $scope.prolog = editProLogParam;
                    //获取数据
                    $scope.getLogData = function() {
                        logService.proLogService({lid: lid}, "/projectLog/queryProjectLogByLid")
                                .success(function(logData){
                                    angular.extend(editProLogParam, logData);
                                });
                    }

                    //根据标记判断是否获取数据
                    if(editProLogParam.type != "stage"){
                        $scope.getLogData();
                    }
                    editProLogParam.type = type;

                    //选择项目阶段 , 并存储页面上输入的数据
                    $scope.select_stage = function(){
                        var contents = $scope.prolog.contents;
                        var detail = $(contents).text();
                        editProLogParam.contents = contents;
                        editProLogParam.detail = detail;
                        editProLogParam.lid = lid;
                        $location.path("/selectStage/" + $scope.prolog.pid + "/" +type);
                    }
                    //提交表单
                    $scope.submit = function (){
                        if(!$scope.editLogForm.$valid ){
                            alert("请填写完成表单");
                            return;
                        }
                        var contents = $scope.prolog.contents;
                        var detail = $(contents).text();
                        if(!detail){
                            alert("请填写日志内容");
                            return;
                        }
                        $scope.submitDone = false;
                        var param = {
                            lid: lid,
                            pflag: editProLogParam.pflag,
                            detail: detail,
                            contents: contents
                        };
                        logService.proLogService(param, "/projectLog/editPrLog")
                                .success(function(data){
                                    if(data.success){
                                        alert("提交成功");
                                        editProLogParam = null;
                                        $location.path("/list");
                                    }else{
                                        alert("错误信息:"+data.errorMsg);
                                    }
                                    $scope.submitDone = true;
                                });
                    }
                }
            ])
            .controller("detailCtrl",["$scope","$http","$routeParams","$sce","$location","logService",
                function($scope,$http,$routeParams,$sce,$location,logService){
                    /**
                     *  日志明细页面
                     */
                        //根据权限 判断是否显示修改的按钮
                    $scope.userId = <%=((User)SecurityUtils.getSubject().getSession().getAttribute(User.USER_SESSIN_KEY)).getId()%>;
                    var id = $routeParams.id;
                    logService.proLogService({lid:id}, "/projectLog/queryProjectLogByLid")
                            .success(function(data){
                                data.contents = $sce.trustAsHtml(data.contents);
                                $scope.projectLog = data;
                            });
                    //修改日志的按钮
                    $scope.editProLog = function(pid,type){
                        $location.path("/editProLog/"+pid+"/"+type);
                    }
                }])
            .controller("selectStageCtrl", ["$scope", "$http","$routeParams","$location","editProLogParam","logService",
                function ($scope, $http,$routeParams,$location,editProLogParam,logService) {
                    /**
                     *  选择项目阶段页面
                     */
                    var pid = $routeParams.pid;
                    var type = $routeParams.type;
//                    editProLogParam.url = "stage";
                    editProLogParam.type = "stage";
                    $scope.updateStage_title = "选择项目阶段";
                    $scope.backHref_type = '#/editProLog/' + pid + '/' + type;

                    //获取项目阶段列表
                    $scope.getProStageData = function() {
                        logService.proLogService({pid: pid}, "/stagecode/getProStageById")
                                .success(function(stageData){
                                    if(stageData.length < 1){
                                        $scope.error = "此项目还没有添加项目阶段哦!";
                                    } else {
                                        if (editProLogParam.pflag == null) {
                                            for (var i in stageData) {
                                                if (stageData[i].pflag == stageData[i].typecode) {
                                                    $scope.u_selected = stageData[i].typecode;
                                                    break;
                                                }
                                            }
                                        } else {
                                            $scope.u_selected = editProLogParam.pflag;
                                        }
                                    }
                                    $scope.proStageList = stageData;
                                });
                    }
                    $scope.getProStageData();

                    //点击项目阶段触发的事件(存储\传递值)
                    $scope.onRadioClick = function(typecode,mc){
                        $scope.u_selected = typecode;
                        $scope.pflagMc = mc;
                    }
                    //提交表单
                    $scope.update_stage_submit = function(){
                        editProLogParam.pflag = $scope.u_selected;
                        editProLogParam.pstage = $scope.pflagMc;
                        $location.path("/editProLog/" + editProLogParam.lid + "/" + type);
                    }
                }
            ])
            .config(['$routeProvider',
                function($routeProvider) {
                    $routeProvider.
                            when('/list', {
                                templateUrl: '/mobile/project/list.jsp',
                                controller: 'listCtrl'
                            }).
                            when('/list/pid/:pid', {
                                templateUrl: '/mobile/project/list.jsp',
                                controller: 'listCtrl'
                            }).
                            when('/updatePer', {
                                templateUrl: '/mobile/proResponse.html',
                                controller: 'updatePerCtrl'
                            }).
                            when('/plusLog', {
                                templateUrl: '/mobile/project/plusLog.html',
                                controller: 'plusLogCtrl'
                            }).
                            when('/editProLog/:lid/:type', {
                                templateUrl: '/mobile/project/editLog.html',
                                controller: 'editLogCtrl'
                            }).
                            when('/detail/:id', {
                                templateUrl: '/mobile/project/detail.jsp',
                                controller: 'detailCtrl'
                            }).
                            when('/selectStage/:pid/:type', {
                                templateUrl: '/mobile/updateStage.html',
                                controller: 'selectStageCtrl'
                            }).
                            when('/list-filter', {
                                templateUrl: '/mobile/project/list-filter.html',
                                controller: 'listFilterCtrl'
                            }).
                            when('/selectPname', {
                                templateUrl: '/mobile/pnameList.html',
                                controller: 'selectPnameCtrl'
                            }).
                            when('/plusSelectPstage/:pid', {
                                templateUrl: '/mobile/updateStage.html',
                                controller: 'plusSelectPstageCtrl'
                            }).
                            otherwise({
                                redirectTo: '/list'
                            });
                }]);
</script>
</body>
</html>

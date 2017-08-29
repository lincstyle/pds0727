<%@ page import="me.chanjar.weixin.cp.api.WxCpServiceImpl" %>
<%@ page import="com.ctdcn.utils.SpringUtils" %>
<%@ page import="me.chanjar.weixin.cp.api.WxCpConfigStorage" %>
<%@ page import="java.util.Date" %>
<%@ page import="me.chanjar.weixin.common.bean.WxJsapiSignature" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="zhCN" ng-app="projectManagerApp">
<head>
    <title>项目管理</title>
    <jsp:include page="/mobile/common/head.jsp"/>
    <link rel="stylesheet" href="/static/js/simditor-2.2.3/styles/simditor.css"/>
</head>
<body>
<div ng-view ng-cloak class="container">
</div>

<jsp:include page="/mobile/common/foot.jsp"/>

<!-- build:js /static/js/ng-js/mobile/mobile.js -->
<script src="/static/js/ng-js/mobile/nav/nav.js"></script>
<script src="/static/js/ng-js/mobile/loading-button/loadingButton.js"></script>
<script src="/static/js/ng-js/mobile/confirmService.js"></script>
<!-- endbuild -->

<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<%
    WxCpServiceImpl wxCpService = SpringUtils.getBean("wxCpChatService");
    WxCpConfigStorage wxCpConfigStorage = SpringUtils.getBean("wxCpChatConfigStorage");
    try{
    	WxJsapiSignature wxJsapiSignature = wxCpService.createJsapiSignature( request.getScheme()+"://"+ request.getServerName()+request.getRequestURI());
       // WxJsapiSignature wxJsapiSignature = wxCpService.createJsapiSignature(request.getHeader("Referer"));
%>
<script>
	//alert(location.href.split('#')[0]);
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: '<%=wxCpConfigStorage.getCorpId()%>', // 必填，企业号的唯一标识，此处填写企业号corpid
        timestamp: '<%=wxJsapiSignature.getTimestamp()%>', // 必填，生成签名的时间戳
        nonceStr: '<%=wxJsapiSignature.getNoncestr()%>', // 必填，生成签名的随机串
        signature: '<%=wxJsapiSignature.getSignature()%>',// 必填，签名，见附录1
        jsApiList: ['openEnterpriseChat'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    
</script>
<%
    }catch (Exception e){
    }
%>
<script>

    angular.module("projectManagerApp", ["mobile", "ngRoute", "ngAnimate", "ngTouch"])
            .factory('listFilter',function () {
                //全局projectlist变量
                return {}
            })
            .factory('editProjectParam',function () {
                var editProjectParam = new Object();
                return editProjectParam;
            })
            .factory('plusProjectParam',function (){
                var plusProjectParam = new Object();
                return plusProjectParam;
            })
            .service('projectService',["$http",function ($http){
                var getData = function(param, url) {
                    return $http({
                        method: 'post',
                        url: url,
                        data: param
                    });
                }
                return {
                    proService: function(param, url) {
                        return getData(param, url);
                    }
                };
            }])
            .controller("projectListCtrl", ["$scope", "$http", "listFilter", "$location", "$filter", "confirmer","$window","projectService",
                function ($scope, $http, listFilter, $location, $filter, confirmer, $window, projectService) {
                    /**
                     *  项目列表页面
                     **/
                    $scope.pager = {
                        page: 1,
                        rows: 5
                    };
                    $scope.data = null;
                    $scope.loadDone = false;
                    $scope.hasMore = true;

                    //获取项目列表数据
                    $scope.getData = function () {
                        $scope.loadDone = false;
                        var param = angular.extend({}, $scope.pager, listFilter);
                        if (param.pdate_start) {
                            param.pdate_start = $filter("date")(param.pdate_start, "yyyy-MM-dd");
                        }
                        if (param.pdate_end) {
                            param.pdate_end = $filter("date")(param.pdate_end, "yyyy-MM-dd");
                        }
                        if (param.lastDate_start) {
                            param.lastDate_start = $filter("date")(param.lastDate_start, "yyyy-MM-dd");
                        }
                        if (param.lastDate_end) {
                            param.lastDate_end = $filter("date")(param.lastDate_end, "yyyy-MM-dd");
                        }

                        projectService.proService(param, "/projectManage/queryProject")
                            .success(function (data) {
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
                            }).then(function (){
                                if ($scope.pager.total && ($scope.pager.page - 1) * $scope.pager.rows >= $scope.pager.total) {
                                    $scope.hasMore = false;
                                } else {
                                    $scope.hasMore = true;
                                }
                            });
                    }
                    $scope.getData();

                    //项目明细, 进行项目明细页面
                    $scope.toDetail = function (pid) {
                        $location.path("/detail/" + pid);
                    }
                    //修改项目，进入修改页面
                    $scope.editProject = function (pid, type) {
                        $location.path("/editProject/" + pid + "/" + type);
                    }

                    //删除项目
                    $scope.delProject = function (pid) {
                        //调用删除的service(弹出面板, 屏蔽其它的点击功能)
                        confirmer.open(function (value){
                            if(value == 1){
                                var param = {pid:pid};
                                projectService.proService(param, "/projectManage/deleteproject")
                                    .success(function(data){
                                        console.log(data);
                                        if(data.success){
                                            alert("删除成功！");
                                            $window.location.reload(true);
                                        } else {
                                            alert("删除失败！");
                                        }
                                    });
                            }
                        })
                    }
                }
            ])
            .controller("detailCtrl", ["$scope", "$http", "$routeParams", "$sce", "$location", "projectService",
                function ($scope, $http, $routeParams, $sce, $location, projectService) {
                    /**
                     *   项目明细页面
                     */
                    var pid = $routeParams.pid;
                    //获取项目信息
                    var param = {pid: pid};
                    projectService.proService(param,"/projectManage/getProjectDetail")
                        .success(function (data){
                            data.pintro = $sce.trustAsHtml(data.pintro);
                            $scope.project = data;
                            //根据项目ID 得到此项目的参与人员和负责人
                            projectService.proService(param,"/projectManage/getProUserList")
                                .success(function (proUserData){
                                    $scope.userList = proUserData;
                                    var isResponse = "";
                                    var userNames = "";
                                    for (var i in proUserData) {
                                        if (proUserData[i].isResponse == 1) {
                                            isResponse += "," + proUserData[i].username;
                                        }
                                        userNames += "," + proUserData[i].username;
                                    }
                                    $scope.isResponse = isResponse.substring(1, isResponse.length);
                                    $scope.userNames = userNames.substring(1, userNames.length);
                                });
                    });

                    //修改项目
                    $scope.editProject = function (pid, type) {
                        $location.path("/editProject/" + pid + "/" + type);
                    }
//发起会话
                    $scope.createChat = function (){
                        var users = new Array();
                        for(var i in $scope.userList){
                            users.push($scope.userList[i].account);
                        }

                        wx.openEnterpriseChat({
                            userIds: users.join(";"),
                            groupName: $scope.userList[0].pname,
                            success: function (res) {
                                // 回调
                            },
                            fail: function (res) {
                                if (res.errMsg.indexOf('function not exist') > 0) {
                                    alert('版本过低请升级')
                                }
                            }
                        });
                    }

                    $scope.queryAllLog = function (pid){
                        window.location.href = "/mobile/project/#/list/pid/" + pid;
//                        window.location.href = "/mobile/project/#/list/pid/"+$scope.userList[0].pid
                    }

                }])
            .controller("editProjectCtrl", ["$scope", "$http", "$routeParams", "$location", "editProjectParam", "projectService",
                function ($scope, $http, $routeParams, $location, editProjectParam, projectService) {
                    /**
                     *   修改项目
                     */
                    $scope.project = editProjectParam;
                    $scope.submitDone = true;
                    var pid = $routeParams.pid;
                    var type = $routeParams.type;
                    //根据不同的页面传入的参数来改变修改页面回退的url
                    $scope.href_type = type == 'list' ? '/projectList' : '/detail/'+pid;

                    $scope.getProData = function () {
                        var param = {pid: pid};
                        projectService.proService(param,"/projectManage/getProjectDetail")
                                .success(function (proData){
                                    editProjectParam.oldPname = proData.pname;
                                    angular.extend($scope.project, proData);
                                    projectService.proService(param,"/projectManage/getProUserList")
                                            .success(function (proUserData){
                                                var isResponse = "";
                                                var userNames = "";
                                                for (var i in proUserData) {
                                                    if (proUserData[i].isResponse == 1) {
                                                        isResponse += "," + proUserData[i].username;
                                                    }
                                                    userNames += "," + proUserData[i].username;
                                                }
                                                $scope.project.isResponse = isResponse.substring(1, isResponse.length);//项目负责人名字
                                                $scope.project.userNames = userNames.substring(1, userNames.length);//项目参与人名字

                                                //初始化项目负责人 和参与人  需要删除和新增的数组
                                                editProjectParam.noRespUpdateDel = new Array();
                                                editProjectParam.noRespUpdateAdd = new Array();
                                                editProjectParam.isRespUpdateDel = new Array();
                                                editProjectParam.isRespUpdateAdd = new Array();
                                                editProjectParam.baseCommUser = proUserData;
                                            });
                                });
                    }

                    if(!$scope.project.pname){
                        $scope.getProData();
                    }

                    //回退的方法,并清除service存储的数据.
                    $scope.editBack = function (){
                        editProjectParam.pname = null;
                        $location.path($scope.href_type);
                    }

                    //选择项目阶段, 跳转到项目已有的阶段页面
                    $scope.updateStage = function() {
                        $location.path("/updateStage/" + pid + "/" + type);
                    }

                    //选择项目负责人,跳转到所有人员页面
                    $scope.commList_isResponse = function(){
                        $location.path("/commList/" + pid + "/isResponse/" + type);
                    }

                    //选择项目参与人员,跳转到所有人员页面
                    $scope.commList = function() {
                        $location.path("/commList/" + pid + "/no_response/" + type);
                    }

                    $scope.submit = function () {
                        //难证表单
                        if (!$scope.editProjectForm.$valid) {
                            alert("请填写完成表单");
                            return;
                        }

                        if(editProjectParam.stageData != null) {
                            var index = $.inArray(editProjectParam.pflag, editProjectParam.stageData);
                            if (index < 0) {
                                alert("您修改的项目在阶段维护中已被删除, 请重新选择!");
                                return false;
                            }
                        }

                        if(editProjectParam.noRespUpdateDel.length < 1){
                            editProjectParam.noRespUpdateDel = [-1];
                        }
                        if(editProjectParam.noRespUpdateAdd.length < 1){
                            editProjectParam.noRespUpdateAdd = [-1];
                        }
                        if(editProjectParam.isRespUpdateDel.length < 1){
                            editProjectParam.isRespUpdateDel = [-1];
                        }
                        if(editProjectParam.isRespUpdateAdd.length < 1){
                            editProjectParam.isRespUpdateAdd = [-1];
                        }

                        $scope.submitDone = false;
                        //发送请求
                        var param = {
                            pid: pid,//项目ID
                            oldpname: editProjectParam.oldPname,
                            pname: $scope.project.pname,//项目名
                            pflag: $scope.project.pflag,//项目阶段ID
                            pintro: $scope.project.pintro,//项目简介
                            person: $scope.project.person,//项目立项人
                            fuzeId: [-1],//由于微信与页面共用一个方法, 但微信不需要此参数, 因此传-1
                            noRespUpdate_del: editProjectParam.noRespUpdateDel,//删除的参与人PUID
                            noRespUpdate_add: editProjectParam.noRespUpdateAdd,//新增的参与人ID
                            isRespUpdate_del: editProjectParam.isRespUpdateDel,//删除的负责人PUID
                            isRespUpdate_add: editProjectParam.isRespUpdateAdd//新增的负责人ID
                        };
                        projectService.proService(param,"/projectManage/weixinEditproject")
                                .success(function (data){
                                    if (data.errorMsg) {
                                        alert("错误信息:" + data.errorMsg);
                                    } else {
                                        alert("提交成功");
                                        editProjectParam.pname = null;
                                        $location.path($scope.href_type);
                                    }
                                    $scope.submitDone = true;
                                });
                    }

                    //项目阶段维护
                    $scope.projectStage = function() {
                        $location.path("/projectStage/" + pid + "/" + type);
                    }
                }
            ])
            .controller("findProjectCtrl", ["$http","$scope", "listFilter","$location",
                function ($http, $scope, listFilter,$location) {
                    /*
                     查询项目页面
                     */
                    $("input[type='date']").blur(function (e) {
                        $(e.delegateTarget).change();
                    });
                    $scope.listFilter = $.extend(true, {}, listFilter);
                    //把查询的条件存在全局变量里面
                    $scope.saveListFilter = function (){
                        listFilter = $.extend(true, listFilter, $scope.listFilter);
                        $location.path("/projectList");
                    }
                    $scope.resertData = function (){
                        $scope.listFilter.pname = "";
                        $scope.listFilter.pdate_start = "";
                        $scope.listFilter.pdate_end = "";
                        $scope.listFilter.lastDate_start = null;
                        $scope.listFilter.lastDate_end = null;
                    }
                }
            ])
            .controller("plusProjectCtrl", ["$scope","$http","$routeParams","$location","plusProjectParam", "projectService",
                function ($scope, $http,$routeParams,$location,plusProjectParam,projectService) {
                    /*
                     新增项目页面
                     */
                    $scope.title = "选择负责人";
                    $scope.submitDone = true;//表单提交
                    var flag = $routeParams.flag;

                    if(flag != "index"){
                        $scope.plusPro = plusProjectParam;
                    }

                    //存页面的数据, 并跳转到选择负责人的页面
                    $scope.response_select = function (){
                        if($scope.plusPro != null){
                            plusProjectParam.pname = $scope.plusPro.pname;
                            plusProjectParam.intro = $scope.plusPro.intro;
                        } else {
                            plusProjectParam = $scope.plusPro;
                        }
                        $location.path("/proResponse");
                    }
                    //提交表单
                    $scope.submit = function () {
                        if (!$scope.plusProForm.$valid) {
                            alert("请填写完成表单");
                            return;
                        }
                        $scope.submitDone = false;

                        var param = {
                            pname: $scope.plusPro.pname,
                            pintro: $scope.plusPro.intro,
                            fuzeId: plusProjectParam.proRespId
                        };

                        projectService.proService(param,"/projectManage/addproject")
                                .success(function (data){
                                    if (data.success) {
                                        alert("提示信息:" + data.msg);
                                        $location.path("/projectList");
                                    } else {
                                        alert("错误信息:" + data.msg);
                                    }
                                    $scope.submitDone = true;
                                });
                    }
                }
            ])
            .controller("proResponseCtrl", ["$scope", "$http","$location","plusProjectParam","projectService",
                function ($scope, $http, $location,plusProjectParam,projectService) {
                    /*
                     项目负责人列表(新增项目)
                     */
                    $scope.title = "选择项目负责人";
                    $scope.proResponseBack = "#/plusProject/proRespBack";
                    $scope.isDisabled = true;
                    $scope.userList = plusProjectParam.userList;

                    //获取项目负责人列表
                    $scope.getResponseData = function(inputParam){
                        var param = {param: inputParam};
                        projectService.proService(param,"/userManager/getProResponse")
                                .success(function (proResponseData){
                                    $scope.search_input = "";
                                    if($scope.userList == null){
                                        $scope.userList = $.extend(true, {}, proResponseData);
                                        plusProjectParam.oldProResponse = $.extend(true, {}, proResponseData);
                                    } else {
                                        $scope.userList = $.extend(true, {}, plusProjectParam.oldProResponse);
                                    }
                                    for(var i in $scope.userList){
                                        if($scope.userList[i].account == true){
                                            $scope.isDisabled = false;
                                            break;
                                        } else {
                                            $scope.isDisabled = true;
                                        }
                                    }
                                });
                    }
                    $scope.getResponseData();

                    //搜索的方法
                    $scope.search_user = function(inputParam){
                        $scope.getResponseData(inputParam);
                    }

                    $scope.selected = function(){
                        for(var i in $scope.userList){
                            if($scope.userList[i].account == true){
                                $scope.isDisabled = false;
                                break;
                            } else {
                                $scope.isDisabled = true;
                            }
                        }
                    }

                    //  提交表单
                    $scope.submit_comm = function() {
                        var proResp_name = "";
                        var proResp_id = new Array();
                        var newProResponse = $scope.userList;
                        var j = 0;
                        for(var k in plusProjectParam.oldProResponse){
                            for(var i in newProResponse){
                                if(plusProjectParam.oldProResponse[k].id == newProResponse[i].id){
                                    if(newProResponse[i].account == true){
                                        proResp_name += "," + plusProjectParam.oldProResponse[k].name;
                                        proResp_id[j] = plusProjectParam.oldProResponse[k].id;
                                        j++;
                                    }
                                    break;
                                }
                            }
                        }
                        proRespName = proResp_name.substring(1, proResp_name.length);
                        plusProjectParam.respName = proRespName;//项目负责人名字 -页面上显示
                        plusProjectParam.proRespId = proResp_id;//项目负责人ID
                        plusProjectParam.userList = newProResponse;//项目负责人列表
                        plusProjectParam.oldProResponse = $.extend(true, {}, newProResponse);
                        $location.path("/plusProject/proResp");
                    }
                }
            ])
            .controller("commListCtrl", ["$scope", "$http","$routeParams","$location","editProjectParam","projectService",
                function ($scope, $http, $routeParams,$location,editProjectParam,projectService) {
                    //项目负责人和参与人员列表(修改项目)
                    var pid = $routeParams.pid;
                    var response = $routeParams.response;
                    var type = $routeParams.type;
                    $scope.back_url = '#/editProject/' + pid + "/" + type;//通讯录页面回退的url，方便抽取后公用
                    //获取数据
                    $scope.getData = function(inputParam) {
                        var param = {
                            pid: pid,
                            param: inputParam,
                            type: response
                        };
                        projectService.proService(param,"/userManager/getCommList")
                                .success(function (data){
                                    $scope.search_input = "";
                                    for (var i in data) {
                                        if (data[i].pid != null && data[i].pid != "") {
                                            data[i].pid = true;
                                        } else {
                                            data[i].pid = false;
                                        }
                                    }

                                    editProjectParam.oldCommUser = $.extend(true, {}, data);
                                    if(response == "no_response"){
                                        //修改项目负责人后,  再修改项目参与人, 刚刚修改的负责人是未选中状态. 需要改成选中状态
                                        editProjectParam.noRespCommUser = data;
                                        if(editProjectParam.isRespCommUser != null){
                                            for(var i in editProjectParam.isRespCommUser){
                                                if(editProjectParam.isRespCommUser[i].pid == true && editProjectParam.noRespCommUser[i].pid == false){
                                                    editProjectParam.noRespCommUser[i].pid = true;
                                                }
                                            }
                                        }
                                    } else {
                                        for(var i in data){
                                            if(data[i].puid != null){
                                                var index = $.inArray(data[i].puid, editProjectParam.isRespUpdateDel);
                                                if(index > -1){
                                                    data[i].pid = false;
                                                }
                                            }
                                        }
                                        editProjectParam.isRespCommUser = data;
                                    }
                                    $scope.commUser = $.extend(true, {}, data);
                                });
                    }
                    //获取数据
                    if(response == "no_response") {
                        $scope.title = "项目参与人员";
                        if (!editProjectParam.noRespFlag) {
                            $scope.getData();
                        } else {
                            //第一次得到数据后存在editProjectParam.noRespCommUser里面, 然后赋值给页面的model
                            $scope.commUser = $.extend(true, {}, editProjectParam.noRespCommUser);
                        }
                    } else {
                        $scope.title = "项目负责人";
                        if (!editProjectParam.isRespFlag) {
                            $scope.getData();
                        } else {
                            for(var i in editProjectParam.isRespCommUser){
                                if(editProjectParam.isRespCommUser[i].puid != null){
                                    var index = $.inArray(editProjectParam.isRespCommUser[i].puid, editProjectParam.isRespUpdateDel);
                                    if(index > -1){
                                        editProjectParam.isRespCommUser[i].pid = false;
                                    }
                                }
                            }
                            $scope.commUser = $.extend(true, {}, editProjectParam.isRespCommUser);
                        }
                    }
                    //搜索的方法
                    $scope.search_comm = function(inputParam){
                        $scope.getData(inputParam);
                    }

                    //选择相关人员后，点击确定的方法
                    $scope.submit_comm = function(){
                        var proResp_id = new Array();//选中人员的名字
                        var proResp_name = "";
                        var j = 0;
                        //提取inpu里面显示的用户名字
                        for(var k in $scope.commUser) {
                            if($scope.commUser[k].pid){
                                proResp_name += "," + $scope.commUser[k].name;
                                proResp_id[j] = $scope.commUser[k].id;
                                j++;
                            }
                        }

                        var newCommUser = $scope.commUser;//列表页面旧数据
                        var update_del = new Array();//需要删除的数据(puid 人员项目关系id)
                        var update_add = new Array();//需要新增的数据(userid 人员id)
                        var update_del_name = new Array();//需要删除的人的名字
                        /*
                         *比较旧数据和新数据, 提取需要删除的项目人员id 和 需要新增的人员id, 并添加到数组
                         */
                        for(var o in editProjectParam.oldCommUser){
                            for(var n in newCommUser){
                                if(editProjectParam.oldCommUser[o].id == newCommUser[n].id){
                                    if(editProjectParam.oldCommUser[o].pid == true && newCommUser[n].pid == false){
                                        update_del.push(editProjectParam.oldCommUser[o].puid);
                                        update_del_name.push(editProjectParam.oldCommUser[o].name);
                                    }
                                    if(editProjectParam.oldCommUser[o].pid != true && newCommUser[n].pid == true){
                                        update_add.push(editProjectParam.oldCommUser[o].id);
                                    }
                                    break;
                                }
                            }
                        }
                        if(update_del.length == 0){
                            update_del = [-1];
                        }
                        if(update_add.length == 0){
                            update_add = [-1];
                        }

                        if(response == "no_response") {
                            //如果是修改项目参与人员
                            editProjectParam.noRespFlag = true;
                            var isResponseArr = new Array();
                            editProjectParam.noRespUpdateDel = update_del;//项目参与人  需要删除人
                            editProjectParam.noRespUpdateAdd = update_add;//项目参与人  需要新增的

                            editProjectParam.userNames = proResp_name.substring(1, proResp_name.length);//项目参与人名字
                            editProjectParam.noRespCommUser = $.extend(true, {}, $scope.commUser);//项目参与人列表
                            //项目负责人名字  页面上显示的
                            if(typeof editProjectParam.isResponse == "string"){
                                isResponseArr = editProjectParam.isResponse.split(",");
                            } else {
                                isResponseArr = editProjectParam.isResponse;
                            }
                            for(var i in update_del_name){
                                var index = $.inArray(update_del_name[i], isResponseArr);
                                if(index > -1){
                                    isResponseArr.splice(index,1);
                                }
                            }
                            editProjectParam.isResponse = isResponseArr;
                            /**修改项目参与人 id
                             * 删除
                             * 1 参与人不是负责人  负责人ID(editProjectParam.isRespUpdateDel)不做修改
                             * 2 参与人 是 负责人  负责人ID(editProjectParam.isRespUpdateDel)要修改  --去掉
                             * 新增
                             * 负责人ID (editProjectParam.isRespUpdateAdd) 不修改
                             */
                            for(var i in update_del){
                                for(var n in editProjectParam.noRespCommUser) {
                                    if(editProjectParam.noRespCommUser[n].isResponse == 1 && update_del[i] == editProjectParam.noRespCommUser[n].puid){
                                        if($.inArray(update_del[i], editProjectParam.isRespUpdateDel) < 0){
                                            editProjectParam.isRespUpdateDel.push(update_del[i]);
                                            break;
                                        }
                                    }
//                                    if (index > -1) {
//                                    editProjectParam.isRespUpdateDel = editProjectParam.isRespUpdateDel.concat(update_del[i]);
//                                    editProjectParam.isRespUpdateDel.splice(index,1);
//                                    } else {
//                                    if(editProjectParam.isRespUpdateDel == null){
//                                        editProjectParam.isRespUpdateDel = new Array();
//                                    }
//                                    editProjectParam.isRespUpdateDel = editProjectParam.isRespUpdateDel.concat(update_del[i]);
//                                    }
                                }
                            }
                            var index = $.inArray(-1, editProjectParam.isRespUpdateDel);//
                            if(index > -1){
                                editProjectParam.isRespUpdateDel.splice(index,1);
                            }
                        } else {
                            //如果是修改项目负责人
                            editProjectParam.isRespFlag = true;
                            //项目负责人名字
                            editProjectParam.isResponse = proResp_name.substring(1, proResp_name.length);
                            var userNamesArr = new Array()
                            var isResponseArr = editProjectParam.isResponse.split(",");
                            if(typeof editProjectParam.userNames == "string"){
                                userNamesArr = editProjectParam.userNames.split(",");
                            } else {
                                angular.extend(userNamesArr, editProjectParam.userNames);
                            }
                            editProjectParam.userNames = isResponseArr.concat(userNamesArr);//合并数组
                            $.unique(editProjectParam.userNames);//去重复

                            //项目负责人ID
                            editProjectParam.isRespCommUser = $.extend(true, {}, $scope.commUser);
                            editProjectParam.isRespUpdateDel = update_del;//项目负责人  需要删除人
                            editProjectParam.isRespUpdateAdd = update_add;//项目负责人  需要新增的

                            //如果新增了项目负责人,项目参与人也要加
//                            editProjectParam.noRespUpdateDel = update_del;//项目参与人  需要删除的
                            for(var i in update_add){
                                editProjectParam.noRespUpdateAdd.push(update_add[i]);
                            }
                            $.unique(editProjectParam.noRespUpdateAdd);//去重复
                            //负责人集合  要新增的负责人集合
                            for(var i in update_add) {
                                //参与人集合 所有
                                for(var b in editProjectParam.baseCommUser) {
                                    //如果新增的负责人id 在参与人列表里面,就从数组里面删除,最后 把数组赋值给参与人新增数组
                                    if(update_add[i] == editProjectParam.baseCommUser[b].userid){
                                        var index = $.inArray(editProjectParam.baseCommUser[b].userid, editProjectParam.noRespUpdateAdd);
                                        editProjectParam.noRespUpdateAdd.splice(index,1);
                                        break;
                                    }
                                }
                            }
                        }
                        $location.path("/editProject/"+pid+'/'+type);
                    }
                }
            ])
            .controller("projectStageCtrl", ["$scope", "$http","$routeParams","$location","editProjectParam","projectService",
                function ($scope, $http,$routeParams,$location,editProjectParam,projectService) {
                    /*
                     项目阶段维护
                     */
                    var pid = $routeParams.pid;
                    var type = $routeParams.type;
                    var oldProStage;

                    $scope.isDisabled = true;//没有选择时让提交按钮不可点击
                    $scope.back_url = '#/editProject/'+pid+"/"+type; //通讯录页面回退的url，方便抽取后公用
                    //获取数据
                    $scope.getProStageData = function() {
                        var param = {pid: pid};
                        projectService.proService(param,"/stagecode/projectStage")
                                .success(function (stageData){
                                    for (var i in stageData) {
                                        if (stageData[i].pid != null && stageData[i].pid != "") {
                                            stageData[i].pid = true;
                                            $scope.isDisabled = false;
                                        }
                                    }
                                    oldProStage = $.extend(true, {}, stageData);
                                    $scope.projectStageList = stageData;
                                });
                    }
                    $scope.getProStageData();

                    $scope.updateSelected = function(){
                        var isDisabled = $scope.projectStageList;
                        for(var i in isDisabled){
                            if(isDisabled[i].pid != null && isDisabled[i].pid == true){
                                $scope.isDisabled = false;
                                return;
                            } else {
                                $scope.isDisabled = true;
                            }
                        }
                    }

                    //提交表单
                    $scope.submit_stage = function(){
                        var newProStage = $scope.projectStageList;
                        var stage_add = new Array();//需要新增的数据
                        for(var i in newProStage){
                            if(newProStage[i].pid != null && newProStage[i].pid == true){
                                stage_add.push(newProStage[i].typecode);
                            }
                        }
                        editProjectParam.stageData = stage_add;

                        var param = {pid: pid,stage: stage_add};
                        projectService.proService(param,"/projectManage/changeProjectPflag")
                                .success(function (result){
                                    if(!result.success){
                                        alert('错误提示:修改失败,此项目或日志正在进行当中...');
                                        //让此项目当前的阶段还原到选中状态
                                        for(var i in newProStage){
                                            if(newProStage[i].typecode == editProjectParam.pflag){
                                                if(!newProStage[i].pid){
                                                    $("#proStage_"+newProStage[i].typecode).prop("checked",true);
                                                    newProStage[i].pid = true;
                                                    return false;
                                                }
                                            }
                                        }
                                    } else {
                                        var param1 = {pid: pid,stageAdd: stage_add}
                                        projectService.proService(param1,"/codeManager/updateProjectStage")
                                                .success(function (data){
                                                    if(data.success){
                                                        alert("提示信息:" + data.msg);
                                                        $location.path("/editProject/" + pid + "/" + type);
                                                    } else {
                                                        alert("错误信息:" + data.msg);
                                                    }
                                                });
                                    }
                                });
                    }
                }
            ])
            .controller("updateStageCtrl", ["$scope", "$http","$routeParams","$location","editProjectParam","projectService",
                function ($scope, $http,$routeParams,$location,editProjectParam,projectService) {
                    /*
                     修改项目阶段
                     */
                    var pid = $routeParams.pid;
                    var type = $routeParams.type;
                    $scope.updateStage_isDisabled = false;
                    $scope.updateStage_title = "修改项目阶段";
                    $scope.backHref_type = '#/editProject/' + pid + '/' + type;

                    //获取数据
                    $scope.getProStageData = function() {
                        projectService.proService({pid: pid},"/stagecode/getProStageById")
                                .success(function (stageData){
                                    $scope.proStageList = stageData;
                                    if(stageData.length < 1){
                                        $scope.error = "此项目还没有添加项目阶段哦!";
                                    } else {
                                        if (editProjectParam.pflag == undefined) {
                                            for (var i in stageData) {
                                                if (stageData[i].pflag == stageData[i].typecode) {
                                                    $scope.u_selected = stageData[i].typecode;
                                                    break;
                                                }
                                            }
                                        } else {
                                            if(editProjectParam.pflag < 1){
                                                $scope.updateStage_isDisabled = true;
                                            }
                                            $scope.u_selected = editProjectParam.pflag;
                                        }
                                    }
                                });
                    }
                    $scope.getProStageData();

                    //单击人员名字的时候触发
                    $scope.onRadioClick = function(typecode,mc){
                        $scope.updateStage_isDisabled = false;
                        $scope.u_selected = typecode;
                        $scope.pflagMc = mc;
                    }
                    //提交表单
                    $scope.update_stage_submit = function(){
                        editProjectParam.pflag = $scope.u_selected;//项目阶段ID
                        editProjectParam.pstage = $scope.pflagMc;//项目阶段名称
                        $location.path("/editProject/" + pid + "/" + type);
                    }
                }
            ])
            .config(['$routeProvider',
                function ($routeProvider) {
                    $routeProvider.
                            when('/projectList', {
                                templateUrl: '/mobile/projectManager/projectList.jsp',
                                controller: 'projectListCtrl'
                            }).
                            when('/detail/:pid', {
                                templateUrl: '/mobile/projectManager/projectDetail.jsp',
                                controller: 'detailCtrl'
                            }).
                            when('/editProject/:pid/:type', {
                                templateUrl: '/mobile/projectManager/editProject.html',
                                controller: 'editProjectCtrl'
                            }).
                            when('/findProject', {
                                templateUrl: '/mobile/projectManager/findProject.html',
                                controller: 'findProjectCtrl'
                            }).
                            when('/plusProject/:flag', {
                                templateUrl: '/mobile/projectManager/plusProject.html',
                                controller: 'plusProjectCtrl'
                            }).
                            when('/proResponse', {
                                templateUrl: '/mobile/proResponse.html',
                                controller: 'proResponseCtrl'
                            }).
                            when('/commList/:pid/:response/:type', {
                                templateUrl: '/mobile/commList.html',
                                controller: 'commListCtrl'
                            }).
                            when('/projectStage/:pid/:type', {
                                templateUrl: '/mobile/projectManager/projectStage.html',
                                controller: 'projectStageCtrl'
                            }).
                            when('/updateStage/:pid/:type', {
                                templateUrl: '/mobile/updateStage.html',
                                controller: 'updateStageCtrl'
                            }).
                            otherwise({
                                redirectTo: '/projectList'
                            });
                }
            ]);
</script>

</body>

</html>

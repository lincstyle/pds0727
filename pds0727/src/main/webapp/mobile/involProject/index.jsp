<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="zhCN" ng-app="involProjectApp">
<head>
    <title>项目通知管理</title>
    <jsp:include page="/mobile/common/head.jsp" />

    <style type="text/css">
        .onoffswitch {
            position: relative; width: 80px;
            -webkit-user-select:none; -moz-user-select:none; -ms-user-select: none;
        }
        .onoffswitch-checkbox {
            display: none;
        }
        .onoffswitch-label {
            display: block; overflow: hidden; cursor: pointer;
            border-radius: 3px;
        }
        .onoffswitch-inner {
            display: block; width: 200%; margin-left: -100%;
            -moz-transition: margin 0.2s;
            -webkit-transition: margin 0.2s;
            -o-transition: margin 0.2s;
        }
        .onoffswitch-inner:before, .onoffswitch-inner:after {
            display: block; float: left; width: 50%; height: 25px; padding: 0; line-height: 25px;
            font-size: 25px; color: white; font-family: Trebuchet, Arial, sans-serif; font-weight: bold;
            box-sizing: border-box;
        }
        .onoffswitch-inner:before {
            content: "";
            background-color: #68C74C; color: #FFFFFF;
        }
        .onoffswitch-inner:after {
            content: "";
            background-color: #999999; color: #000000;
            text-align: right;
        }
        .onoffswitch-switch {
            display: block; width: 40px; margin: 1px;
            background: #FFFFFF;
            position: absolute; top: 0; bottom: 0;
            right: 38px;
            border-radius: 3px;
            -moz-transition: all 0.2s;
            -webkit-transition: all 0.2s;
            -o-transition: all 0.2s;
        }
        .onoffswitch-checkbox:checked + .onoffswitch-label .onoffswitch-inner {
            margin-left: 0;
        }
        .onoffswitch-checkbox:checked + .onoffswitch-label .onoffswitch-switch {
            right: 0px;
        }

    </style>


</head>
<body >
    <div ng-view ng-cloak class="container" >
    </div>

    <jsp:include page="/mobile/common/foot.jsp" />
    <!-- build:js /static/js/ng-js/mobile/mobile.js -->
    <script src="/static/js/ng-js/mobile/nav/nav.js"></script>
    <script src="/static/js/ng-js/mobile/loading-button/loadingButton.js"></script>
    <!-- endbuild -->
    <script>
        angular.module("involProjectApp",["mobile","ngRoute", "ngAnimate","ngTouch"])
                .controller("involProjectCtrl",["$scope","$http","$timeout",
                    function($scope,$http,$timeout){
                        /**
                        *   项目通知页面
                         */
                        $scope.dataList = null;
                        //获取数据
                        $scope.getData = function (){
                            $http({
                                url:"/personalInfoManager/showProject",
                                method:"POST"
                            }).success(function(data) {
                                $scope.dataList = data;
                                if(data.length < 1){
                                    $scope.error = "您还没有参加任何项目哦!";
                                }
                            });
                        }
                        $scope.getData();
                        var promies;
                        //修改是否接收项目通知
                        $scope.isReceive = function(project,$event){
                            $event.stopPropagation();
                            var targetVar = project.isreceive == 0 ? 1 : 0;
                            if(promies){
                                $timeout.cancel(promies);
                            }
                            promies = $timeout(function () {
                                $http({
                                    url: "/personalInfoManager/editIsReceive",
                                    data: {
                                        proId: project.pid,
                                        isReceive: targetVar
                                    },
                                    method: "POST"
                                }).success(function (data) {
                                    if (data.state == "ok") {
                                        project.isreceive = targetVar;
                                    } else {
                                        alert("出现未知错误！");
                                    }
                                }).then(function () {
                                    promies = null;
                                })
                            },100);

                        }
                    }])
                .config(['$routeProvider',
                    function($routeProvider) {
                        $routeProvider.
                                when('/involProject', {
                                    templateUrl: '/mobile/involProject/involProject.html',
                                    controller: 'involProjectCtrl'
                                }).
                                otherwise({
                                    redirectTo: '/involProject'
                                });
                    }]);
    </script>
</body>
</html>

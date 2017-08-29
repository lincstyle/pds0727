<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="zhCN" ng-app="modifyMyDataApp">
<head>
    <title>个人信息管理</title>
    <jsp:include page="/mobile/common/head.jsp" />
</head>
<body >
    <div ng-view ng-cloak class="container" >
    </div>

    <jsp:include page="/mobile/common/foot.jsp" />
    <!-- build:js /static/js/ng-js/mobile/mobile.js -->
    <script src="/static/js/ng-js/mobile/nav/nav.js"></script>
    <script src="/static/js/ng-js/mobile/loading-button/loadingButton.js"></script>
    <!-- endbuild -->
    <script type="text/javascript">
        angular.module("modifyMyDataApp",["mobile","ngRoute", "ngAnimate","ngTouch"])
                .controller("modifyMyDataCtrl",["$scope","$http","$location","$window",
                    function($scope,$http,$location,$window){
                        /**
                         *  修改个人信息页面
                         */
                        $scope.submitDone = true;
                        //获取数据
                        $scope.getData = function (){
                            $http({
                                url:"/userManager/getUserById",
                                data:{
                                    user_id: ${sessionScope.currentUser.id}
                                },
                                method:"POST"
                            }).success(function(data) {
                                $scope.userInfo = data;
                                $scope.roleName = "${sessionScope.currentUser.roleOb.roleName}";
                            });
                        }
                        $scope.getData();
                        //提交表单
                        $scope.submit = function (){
                            var param = angular.extend({}, $scope.userInfo);
                            if(!$scope.modifyMyDataForm.$valid ){
                                alert("请填写完成表单!");
                                return false;
                            } else {
                                if(param.tel == "" && param.email == "" && param.weixin == ""){
                                    alert("身份验证信息用于绑定微信,不可同时为空!");
                                    return false;
                                }
                            }
                            $scope.submitDone = false;
                            $http({
                                url:"/userManager/modifyMyData",
                                data: {
                                    userId: param.id,
                                    username: param.name,
                                    account: param.account,
                                    gender: param.gender,
                                    tel: param.tel,
                                    email: param.email,
                                    weixin: param.weixin,
                                    departmentId: param.departmentId,
                                    roleId: param.role
                                },
                                method:"POST"
                            }).success(function(result) {//此处有改动，下面的数据对象的获取以及判断条件有问题

                                if(result.state != "ok"){
                                    alert("错误信息:"+result.message);
                                }else{
                                	//console.log(result.state);
                                    alert("修改成功");
                                    $window.location.href = "/admin/index";
                                }
                                $scope.submitDone = true;
                                return;
                            });
                        }
                        //修改密码按钮
                        $scope.editPassword = function(userId){
                            $location.path("/editPassword/"+userId);
                        }
                    }])
                .controller("editPasswordCtrl",["$scope","$http","$routeParams","$location",
                    function($scope,$http,$routeParams,$location){
                        /**
                         *  修改密码页面
                         */
                        $scope.submitDone = true;
                        var userId = $routeParams.userId;
                        //提交表单
                        $scope.submit = function () {
                            var oldPassword = $scope.oldPassword;
                            var newPassword_1 = $scope.newPassword_1;
                            var newPassword_2 = $scope.newPassword_2;

                            if(oldPassword == null){
                                $scope.oldError = "请输入原始密码！";
                                return ;
                            }
                            if(newPassword_1 == null){
                                $scope.newError_1 = "请输入新密码！";
                                return;
                            }
                            if(newPassword_2 == null){
                                $scope.newError_2 = "请输入确认密码！";
                                return;
                            }
                            if(newPassword_1 != newPassword_2){
                                $scope.newError_2 = "两次输入的密码不一致，请重新输入！";
                                alert("两次输入的密码不一致，请重新输入！");
                                return false;
                            }
                            $scope.submitDone = false;

                            //校验旧密码是否正确, 如果正确才允许修改密码, 不正确弹出提示
                            $http({
                                url: "/userManager/changeOldPassword",
                                data: {
                                    user_id: userId,
                                    password: oldPassword
                                },
                                method: "POST"
                            }).success(function (data) {
                                if(data.state == "no"){
                                    alert(data.message);
                                    return;
                                }
                                $http({
                                    url: "/userManager/updatePassword",
                                    data: {
                                        user_id: userId,
                                        new_password_1: newPassword_1
                                    },
                                    method: "POST"
                                }).then(function (edit_data) {
                                    if(edit_data.data.state == "ok"){
                                        alert(edit_data.data.message);
                                        $location.path("/modifyMyData");
                                    } else {
                                        alert(edit_data.data.message);
                                    }
                                    $scope.submitDone = true;
                                });
                            });
                        }
                    }
                ])
                .config(['$routeProvider',
                    function($routeProvider) {
                        $routeProvider.
                                when('/modifyMyData', {
                                    templateUrl: '/mobile/modifyMyData/modifyMyData.html',
                                    controller: 'modifyMyDataCtrl'
                                }).
                                when('/editPassword/:userId', {
                                    templateUrl: '/mobile/modifyMyData/editPassword.html',
                                    controller: 'editPasswordCtrl'
                                }).
                                otherwise({
                                    redirectTo: '/modifyMyData'
                                });
                    }]);
    </script>
</body>
</html>

/**
 * @author 张靖
 *          2015-08-07 8:45.
 */
'use strict';
angular.module('mobile').directive('loadingButton',function (){
    var defaults = {
        isDone : true,
        isShow : true
    }
    return {
        replace:true,
        scope:{
            isDone:"=",
            isShow:"=",
            loadingMsg:"@"
        },link: function($scope, element, attrs){
            $scope.oldVal || ($scope.oldVal = element.html());
            attrs.loadingMsg || (attrs.loadingMsg = '提交中..');

            attrs.isShow ? attrs.isShow : $scope.isShow = true;
            attrs.isDone ? attrs.isDone : $scope.isDone = true;

            attrs.loadingMsg = '<i class="fa fa-refresh fa-spin"></i>'+ attrs.loadingMsg;
            if(angular.uppercase(element[0].nodeName) != "BUTTON"){
                throw new Error("loadingButton指令，只能作用于button");
            }

            $scope.$watch("isDone",function (value){
                if(value){
                    element.html($scope.oldVal);
                    element.removeAttr("disabled");
                }else{
                    element.attr("disabled","disabled");
                    element.html($scope.loadingMsg);
                }
            });

            $scope.$watch("isShow",function (value){
                if(value){
                    element.show();
                }else{
                    element.hide();
                }
            });
        },
        restrict: 'A'
    }
});

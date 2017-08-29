/**
 * @author 张靖
 *          2015-07-17 9:02.
 */
'use strict';
angular.module('mobile').directive('mobileNav',function (){
    return {
        scope:{
            href:"@",
            icon:"@"
        },compile: function(element, attrs){
            if (!attrs.icon) { attrs.icon = 'fa fa-chevron-left fa-1x'; }
        },
        restrict: 'E',
        transclude: true,
        templateUrl:'/static/js/ng-js/mobile/nav/nav.html'
    }
});


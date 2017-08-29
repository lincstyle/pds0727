/**
 * @author 张靖
 *          2015-07-29 17:24.
 */
angular.module("mobile",['chieffancypants.loadingBar']).factory('globalHttpInterceptor', ['$q', '$window','cfpLoadingBar',function($q, $window,cfpLoadingBar) {
    return {
        'request': function(config) {
            cfpLoadingBar.start();
            return config;
        },
        'requestError': function(rejection) {
            cfpLoadingBar.complete();
            return $q.reject(rejection);
        },
        'response': function(response) {
            cfpLoadingBar.complete();
            return response;
        },
        'responseError': function(response) {
            cfpLoadingBar.complete();
            if (response.status == 401) {
                $window.location.href ="/login";
            }
            return $q.reject(response);
        }
    };
}]).config(["$httpProvider","$httpParamSerializerJQLikeProvider","cfpLoadingBarProvider",function($httpProvider,$httpParamSerializerJQLikeProvider,cfpLoadingBarProvider) {

    cfpLoadingBarProvider.includeSpinner = true;

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    $httpProvider.defaults.transformRequest = $httpParamSerializerJQLikeProvider.$get();
    $httpProvider.interceptors.push('globalHttpInterceptor');

}]);


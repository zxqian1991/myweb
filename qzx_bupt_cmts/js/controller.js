/**
 * Created by qianzhixiang on 15/7/6.
 */
var app = angular.module("bupt_app",[]);
app.controller("headbarController",function($scope){
    $scope.title = "cmts网络管理系统";
});
app.controller("navbarController",function($scope){
    $scope.goToCmts = function(){

    };
});
app.controller("cmtsContentHandler",function($scope,$http){
    $http.get("model/cmts_properties_list.json").success(function(response){
        $scope.lists = response;
    });
    $http.get("model/cmts_state_list.php").success(function(response){
        $scope.rows = response;
    });
});
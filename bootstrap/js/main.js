/**
 * Created by qianzhixiang on 15/7/5.
 */
var myapp = angular.module("myapp",[]);
myapp.controller("navbarController",function($scope){
    $scope.directTo = function () {
        var target = $(window.event.target);
        console.log(target.attr("id"));
    };
    $scope.func = "aadsa";
});
myapp.controller("cmtsTableController",function($scope){
    $scope.properties = ["aaaa","bbbb","cccc"];
    $scope.datas = ["assaasd","asdasdas","asdasdasdasd"];
});
myapp.controller("main_table",function($scope){
    $scope.properties = ["aaaa","bbbb","cccc"];
    $scope.aaa = "asasas";
});
//var abc = function() {
//
//};
//abc.prototype.xml = "abc";
//function derive(newObj,old) {
//    var temp = function () {};
//    temp.prototype = old.prototype;
//    newObj.prototype = new temp();
//};
//var newabc = function (){
//
//};
//derive(newabc,abc);
//abc.prototype.getText = function(){
//    this.xml = "alklkllkl";
//};
//abc.prototype.getText2 = function(){
//    this.xml = "alklkllkl22222222";
//};
//var zz = new newabc();
//zz.getText();
//zz.getText2();
//console.log(zz.xml);
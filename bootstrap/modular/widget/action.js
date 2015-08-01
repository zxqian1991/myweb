/**
 * Created by qianzhixiang on 15/7/5.
 */
define(function(){
    var action = function () {

    };
    action.prototype.enter = function() {
        var me = this;
        me.view.getText();
        me.initViewEvents();
        me.initBehavior();
    };
    action.prototype.initBehavior = function(){

    };
    action.prototype.dispose = function(){
        var me = this;
        me.view.dispose();
    };
    action.prototype.view = null;

    action.prototype.initViewEvents = function() {

    };
    return action;
});
/**
 * Created by qianzhixiang on 15/7/5.
 */
define(function(require){
    var util = require("util");
    var parent = require("action");
    var controller = require("controller");
    var action = function () {
        parent();
    };
    util.derive(action,parent);
    action.prototype.updateBar = null;
    action.prototype.enter = function() {
        var me = this;
        me.view.getText("k="+me.k);
        me.initViewEvents();
        me.initBehavior();
    };
    action.prototype.initBehavior = function () {
        var me = this;
        me.startUpdatePropertiesTimeByTime();
    };
    action.prototype.view = require("./view");
    action.prototype.initViewEvents = function() {

    };
    action.prototype.updateBar = "";
    action.prototype.startUpdatePropertiesTimeByTime = function () {
        var me = this;
        me.updateBar = setInterval(function(){
            me.view.getText("k="+me.view.k);
            console.log("lll");
        },1000);
    };
    action.prototype.dispose = function() {
        var me = this;
        clearInterval(me.updateBar);
        me.view.dispose();
    };
    action.prototype.stopUpdatePropertiesTimeByTime = function(){
        var me = this;
        clearInterval(me.updateBar);
    };
    return new action();
});
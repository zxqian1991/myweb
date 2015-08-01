/**
 * Created by qianzhixiang on 15/7/13.
 */
define(function(require){
    var util = require("util");
    var parent = require("action");
    var controller = require("controller");
    var action = function () {
        parent();
    };
    util.derive(action,parent);
    action.prototype.initBehavior = function () {
        var me = this;

    };
    action.prototype.view = require("./view");
    action.prototype.initViewEvents = function() {

    };
    return new action();
});
/**
 * Created by qianzhixiang on 15/7/7.
 */
define(function(require){
    var util = require("util");
    var parent = require("action");
    var action = function () {
        parent();
    };
    util.derive(action,parent);
    action.prototype.initBehavior = function () {

    };
    action.prototype.view = require("./view");
    action.prototype.initViewEvents = function() {

    };
    return new action();
});
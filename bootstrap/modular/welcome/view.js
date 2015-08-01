/**
 * Created by qianzhixiang on 15/7/7.
 */
define(function(require){
    var parent = require("view");
    var util = require("util");
    var view = function(){
        parent();
    };
    util.derive(view,parent);
    view.prototype.url = "modular/welcome/welcome.php";
    view.prototype.show = function () {
        var me = this;
        me.container.html("");
        me.container.append(me.text);
    };
    return new view();
});
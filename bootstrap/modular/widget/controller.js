/**
 * Created by qianzhixiang on 15/7/5.
 */
define(function(require){
    var controller = function (){

    };
    controller.prototype.actions = {};
    controller.prototype.tempPage = null;
    controller.prototype.tempMenuBar = $(".qzx-home");
    controller.prototype.islogged = false;
    controller.prototype.enter = function (path) {
         //加载对应的页面
        var me = this;
        require([path],function(action){
            if(me.tempPage){
                me.tempPage.dispose();
            }
            action.enter();
            me.tempPage = action;
        });
    };

    return new controller();
});
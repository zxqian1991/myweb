define(function(require){
    var headbar = function(){};
    headbar.prototype.controller = require("controller");
    headbar.prototype.main = $(".h-main");
    headbar.prototype.about = $(".h-about");
    headbar.prototype.join = $("h-join");
    headbar.prototype.enter = function(){
        var me = this;
        me.initBehavior();
    };

    headbar.prototype.initBehavior = function(){
        var me = this;
        me.initMainBtn();
        me.initAboutBtn();
        me.initJoinBtn();

    };
    headbar.prototype.initMainBtn = function(){
        var me = this;
        me.main.bind("click",function(){
            require(["home"],function(homepage){
               if(me.controller.tempPage !== homepage) {
                   me.controller.tempPage.dispose();
                   homepage.enter();
                   homepage.show();
                   me.controller.tempPage = homepage;
               }
            });
        });
    };
    headbar.prototype.initAboutBtn = function(){
        var me = this;
    };
    headbar.prototype.initJoinBtn = function() {
        var me = this;
    };
    return new headbar();
});
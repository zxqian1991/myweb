define(function(require){
    var home = function(){};
    home.prototype.html = null;
    home.prototype.url = "module/home/tpl.php";
    home.prototype.parentDom = null;
    home.prototype.selfDom = null;
    home.prototype.fullpage = null;
    home.prototype.enter = function(){
        var me = this;
        me.getHtml();
        me.addToDom();
        me.initBehavior();
    };
    home.prototype.getHtml = function(){
        var me = this;
        if(me.html == null) {
            me.html = $.ajax({
                url: me.url,
                async: false
            }).responseText;
        }
    };
    home.prototype.addToDom = function(){
        var me = this;
        me.parentDom = $("body");
        me.parentDom.append(me.html);
        me.selfDom = $("#h-homepage");
        me.fullpage = me.selfDom;
    };
    home.prototype.initBehavior = function(){
        var me = this;
        me.startFullPage();
    };

    home.prototype.show = function(){

    };
    home.prototype.hide = function(){

    };
    home.prototype.dispose = function(){
        me.removeDom();
        me.removeEvents();
    };
    home.prototype.removeDom = function(){
        var me = this;
        me.selfDom.remove();
    };
    home.prototype.startFullPage = function () {
        var me = this;
        me.fullpage.fullpage();
    };
    home.prototype.stopFullPage = function(){
        var me = this;
    };
    home.prototype.removeEvents = function(){
        var me = this;
        me.stopFullPage();
    };
    return new home();
});
/**
 * Created by qianzhixiang on 15/7/5.
 */
define(function(require){
    var parent = require("view");
    var util = require("util");
    var xml = require("xml");
    var view = function(){
        parent();
    };
    util.derive(view,parent);
    view.prototype.k = 0;
    view.prototype.pages = 0;
    view.prototype.url = "modular/main/main.php";
    view.prototype.show = function () {
        var me = this;
        me.container.html("");
        me.container.append(me.text);
    };
    view.prototype.initEvents = function(){
        var me = this;
        me.pages = $(".qzx-page li").length -2 ;
        $(".qzx-page").bind("click",function(e){
            var value = $(e.target).text();
            var typetest = value - 0;
            console.log(typetest);
            if(typetest) {
                me.k = typetest-1;
                me.getText("k="+me.k);
            } else {
                var direct =$(e.target).attr("qzx-btn");
                if(direct == "left") {
                    if(me.k!=0) {
                        me.k--;
                        me.getText("k="+me.k);
                    }
                } else {
                    if(me.k != me.pages-1)
                    me.k++;
                    me.getText("k="+me.k);
                }
            }
        });
    };
    view.prototype.getText = function (opt) {
        var me = this;
        var asy = new xml();
        asy.init({
            url: me.url,
            opt: opt
        },util.bind(me,me.onLoading));
    };
    view.prototype.dispose = function(){
        $(".qzx-page").unbind();
    };
    return new view();
});
/**
 * Created by qianzhixiang on 15/7/5.
 */
define(function(require){
    var xml = require("xml");
    var util = require("util");
    var view = function(){
        var me = this;
    };
    view.prototype.text = null;;
    view.prototype.url = "";
    view.prototype.getText = function (opt) {
        var me = this;
        if(!me.text){
            var asy = new xml();
            asy.init({
                url: me.url,
                opt: opt
            },util.bind(me,me.onLoading));
        } else {
            me.onLoading()
        }
    };
    view.prototype.container = $("#qzx-body");
    view.prototype.onLoading = function(xml) {
        var me = this;
        if(xml) {
            me.text = xml.responseText;
        }
        me.show();
        me.initEvents();
        me.onLoaded();
    };
    view.prototype.initEvents = function(){

    };
    view.prototype.dispose = function() {

    };
    view.prototype.onLoaded = function() {
        var me = this;
    };
    view.prototype.show = function () {

    };
    return view;
});
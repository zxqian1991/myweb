/**
 * Created by qianzhixiang on 15/7/3.
 */
define(function(require) {
    var util = require("./util");
    var xml = function () {

    };

    xml.prototype.type = "post";
    xml.prototype.async = true; // 默认是true,即异步
    xml.prototype.url = "modular/centercontroller/test/test1.php";
    xml.prototype.opt = null; // 附带的属性
    xml.prototype.ajax = null;
    xml.prototype.responseText = "";
    /*
     * 初始化设置
     */
    xml.prototype.init = function (opts,func) {
        var me = this;
        me.ajax = me.getXml();
        util.copyAllProperties(me,opts);
        me.ajax.open(me.type,me.url,me.async);
        if (me.type.toLowerCase() === 'post') {
            me.ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        }
        me.ajax.onreadystatechange = function () {
            if (me.ajax.readyState == 4 && me.ajax.status == 200) {
                // 代表成功,执行相应的函数
                me.responseText = me.ajax.responseText;
                func(me.ajax);
            }
        };
        me.ajax.send(me.opt);
    };
    /*
     * 获得一个新的AJAX
     */
    xml.prototype.getNewAjax = function () {
        var newObj = function () {};
        util.derive(newObj,xml);
        return new newObj();
    };

    /**
     *  获得XML实例
     * @returns {*}
     */
    xml.prototype.getXml = function () {
        var xml;
        if (window.XMLHttpRequest) {
            xml = new XMLHttpRequest();
        } else {
            xml = new ActiveXObject("Microsoft.XMLHTTP");
        }
        return xml;
    };
    xml.prototype.afterStateChange = function () {

    };
    return xml;
})
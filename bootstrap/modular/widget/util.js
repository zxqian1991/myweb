/**
 * Created by qianzhixiang on 15/7/3.
 */
/**
 * 这里面封装了常用的方法
 */
define(function(require) {
    var util = function () {

    };
    util.prototype.copyArray = function (newArr, oldArr) {
        var me = this;
        for (var i = 0; i < oldArr.length; i++) {
            if (oldArr[i] instanceof Object) {
                var tempObj = {};
                tempObj = me.copyObj(tempObj, oldArr[i]);
                newArr.push(tempObj);
            } else if (oldArr[i] instanceof Array) {
                var tempArr = [];
                tempArr = me.copyArray(tempArr, oldArr[i])
                newArr.push(tempArr);
            } else {
                newArr.push(oldArr[i]);
            }
        }
        return newArr;
    };
    util.prototype.copyObj = function (newObj,oldObj) {
        var me = this;
        for(var key in oldObj) {
            if (oldObj[key] instanceof Object) {
                var tempObj = {};
                tempObj = me.copyObj(tempObj, oldObj[key]);
                newObj[key] = tempObj;
            } else if (oldObj[key] instanceof Array) {
                var tempArr = [];
                tempArr = me.copyArray(tempArr, oldObj[key]);
                newObj[key] = tempArr;
            } else {
                newObj[key] = oldObj[key];
            }

        }
        return newObj;
    };
    util.prototype.derive = function (newObj,old) {
        var temp = function () {};
        temp.prototype = old.prototype;
        newObj.prototype = new temp();
    };
    /*
     * 其实就是将OPTS里面的拷贝到OBJ里面，原则如下:
     * 如果有相同的属性，覆盖
     * 如果没有相同的属性，加入
     * 为了防止相互间的影响，采用完全拷贝的方式
     */
    util.prototype.copyAllProperties = function (obj,opts) {
        var me = this;
        for(pro in opts) {
            //如果是对象
            if (opts[pro] instanceof Object) {
                obj[pro] = {};
                me.copyObj(obj[pro],opts[pro]);
            } else if (opts[pro] instanceof Array) { // 如果是数组
                obj[pro] = [];
                me.copyArray(obj[pro],opts[pro]);
            } else {
                obj[pro] = opts[pro];
            }
        }
    };
    util.prototype.copyLimitProperties = function (obj,opts) {
        var me = this;
        for(pro in opts) {
            //如果是对象
            if(pro in obj) {
                if (opts[pro] instanceof Object) {
                    obj[pro] = {};
                    me.copyObj(obj[pro],opts[pro]);
                } else if (opts[pro] instanceof Array) { // 如果是数组
                    obj[pro] = [];
                    me.copyArray(obj[pro],opts[pro]);
                } else {
                    obj[pro] = opts[pro];
                }
            }
        }
    };
    util.prototype.bind = function (obj,func) {
        return function() {
            return func.apply(obj, Array.prototype.slice.call(arguments));
        };
    };
    util.prototype.getCookie = function (c_name) {
        if (document.cookie.length>0) {
            var c_start;
            var c_end;
            c_start = document.cookie.indexOf(c_name + "=");
            if (c_start != -1) {
                c_start = c_start + c_name.length+1
                c_end = document.cookie.indexOf(";", c_start)
            }
            if (c_end ==-1) {
                c_end = document.cookie.length
            }
            return unescape(document.cookie.substring(c_start,c_end));
        }
        return '';
    };
    util.prototype.setCookie = function (c_name, value, expiredays) {
        var exdate = new Date()
        exdate.setDate(exdate.getDate() + expiredays);
        document.cookie = c_name + "=" + escape(value) +
            ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
    };
    util.prototype.islogged = function(){
        var me = this;
        var islogged = me.getCookie("islogged");
        if(islogged == "1") {
            return true;
        } else {
            return false;
        }
    };

    /**
     *
     * @param opts :
     * {
     *     url:
     *     type:
     *     async:
     *     success:
     *     data: 需要发送的数据，主要形式就是  a=1&b=2的形式
     * }
     */
    util.prototype.ajax = function(opts){
        var xml;
        if (window.XMLHttpRequest) {
            xml = new XMLHttpRequest();
        } else {
            xml = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var type = opts.type ? opts.type : "POST"; // 默认post
        var async = opts.async ? opts.async : true; // 默认true
        xml.open(type, opts.url, async);
        if (type.toLowerCase() === 'post') {
            xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        }
        xml.onreadystatechange = function () {
            if (xml.readyState == 4 && xml.status == 200) {
                // 代表成功,执行相应的函数
                opts.success(xml);
            }
        };
        xml.send(opts.data);
    };
    return new util();
});
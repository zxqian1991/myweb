/**
 * Created by qianzhixiang on 15/7/3.
 */
define(function (require) {
    var util = {};

    /*
     继承某个对象  对象是方法
     */
    util.derive = function (newObj, old) {
        var temp = function () {};
        temp.prototype = old.prototype;
        newObj.prototype = new temp();
    };

    /**
     * 通过统一的口令去调用同一个php页面，而后php会自动的读取响应的自己的缓存去调用对应的方法，或者直接从命名出发
     * @param command 传输的口令
     * @param async 代表同步还是异步，true是同步  false是异步
     * @param op 是要传输的参数，主要包括俩个  1.op.asy  主要用来表示是否异步，默认异步  2.op.opts 主要用来表示要传输的参数
     */
    util.getAjaxByCommand = function (command, func, op) {
        var me = this;
        // 考虑安全性，统一用post
        // 默认异步
        if (!op.asy) {
            op.asy = true;
        }
        var opts = {
            async: op.asy,
            url: me.baseCommandUrl,
            opt: "command=" + command + "&" + op.opts
        };
        me.getAjax("POST", func, opts);
    }

    /** ajax同步获取某个内容
     *  @opts 需要发送的参数  opts.async  true 异步  false 同步
     *  opts:{
     *      async: 同步还是异步 FALSE同步，true 异步
     *      url: ajax请求的地址
     *      data: 传输的POST的数据
     *      type: 请求的类型  POST OR GET
     *      handler: 请求到数据后的处理方法
     *  }
     */
    util.get = function (opt) {
        var me = this;
        var async = opt.async;
        var data = opt.data;
        var url = opt.url;
        var type = opt.type;
        var handler = opt.handler;
        var xml = me.getXml();
        xml.open(type,url,async);
        xml.setHeadRequest(tye)
    }

    util.getAjax = function (type, func, opts) {
        var me = this;
        var xml = me.getXml(); // 获得XML对象
        xml.onreadystatechange = function () {
            if (xml.readyState == 4 && xml.status == 200) {
                // 代表成功,执行相应的函数
                func(xml);
            }
        };
        xml.open(type, opts.url, opts.async);

        xml.send(opts.opt);
    };
    util.setHeadRequest = function (type,xml) {
        if (type.toLowerCase() === 'post') {
            xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        }
    };
    util.getXml = function () {
        var xml;
        if (window.XMLHttpRequest) {
            xml = new XMLHttpRequest();
        } else {
            xml = new ActiveXObject("Microsoft.XMLHTTP");
        }
        return xml;
    };


    util.embedScript = function () {

    };
    util.appendScript = function (tpl) {
        var head = $('head');
        head.append(tpl);
    };
    util.setCookie = function (c_name, value, expiredays) {
        var exdate = new Date()
        exdate.setDate(exdate.getDate() + expiredays);
        document.cookie = c_name + "=" + escape(value) +
            ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
    };

    util.sendLog = function (src, tgt, logtype) {
        var me = this;
        me.getAjax("POST", LogHandler, {
            url: 'html/main/ajax.php',
            async: true,
            opt: "option=Log"
            + "&userId=" + fcContext.userId
            + "&userName=" + fcContext.userName
            + "&userLevel=" + fcContext.userLevel
            + "&source=" + src
            + "&target=" + tgt
            + "&type=" + logtype
        });
        function LogHandler(xml) {
        }
    };


    util.copyArray = function (newArr, oldArr) {
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
    }
    util.copyObj = function (newObj,oldObj) {
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
    util.isLogged = function(){
        var me = this;
        if (me.getCookie("isLooged") == '1') {
            return true;
        } else {

            return false;
        }
    };
    return util;
});
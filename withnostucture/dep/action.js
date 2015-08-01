define(function (require) {
	var action = function () {};
	var util = require('util');
    var $ = require('jquery');
    action.prototype.tpl = '';
    action.prototype.tplName = '';
    // 默认是body
    action.prototype.container = $('body');

	// 主入口
	action.prototype.enter = function () {
        var me = this;
        me.render(me.tplName);
        me.initBehavior();
	};

	/**
	 * 程序的主入口
	 */
	action.prototype.initBehavior = function () {

	};

    // 渲染文件的参数
    action.prototype.renderArgs = {};


    /**
     * 1.获得html内容
     * 2.对符合相应规则的内容进行转义
     */
    action.prototype.render = function (name, opt) {
    	// 这个主要来获取tpl对应的模板的html
    	var me = this;
    	if (me.tpl == '') {
            util.getAjax(
            'GET',
            renderHandler,
            {
                async: false,
                url: name
            });
            function renderHandler(xml) {
                me.tpl = xml.responseText;
                for ( key in me.renderArgs) {
                    me.tpl = me.tpl.replace(
                        eval("/\{\{" + key +"\}\}/g"), me.renderArgs.key
                    );
                }
            }
        }

    };
    action.prototype.removeMyEvents = function () {
        var me = this;
        for (var i = 0; i < me.eventList[0].length; i++) {
            me.eventList[0][i].unbind(me.eventList[1][i]);
        }
    };
    // eventlist由三个数组组成
    // 第一个是对象 第二个是事件，第三个是方法
    action.prototype.startMyEvents = function () {
        var me = this;
        me.isOut = false;
        if (me.eventList[0].length != 0) {
            //不为零
            for (var i = 0; i < me.eventList[0].length; i++) {
                me.eventList[0][i].bind(me.eventList[1][i], function () {
                    me.eventList[2][i].apply(me);
                });
            }
        }
    };
    // 模板加载完成后的执行的函数
	action.prototype.onrendered = function () {
    
    };
    action.prototype.events = {};
    action.prototype.dispose = function () {
        var me = this;
        me.content.remove();
        me.isOut = true;
        me.removeMyEvents();
        me.ondisposed();
        delete me;
    };
    action.prototype.ondisposed = function () {
        
    };
    action.prototype.removeEvents = function () {
        var me = this;
        for (var key in me.events) {
            var obj = me.events.key.obj;
            for (var v in me.events.key.events) {
                obj.unbind(me.events.key.events[v]);
            }
        }
    };
    action.prototype.eventList = [[], [], []];
    action.prototype.isOut= false;

	return action;
});
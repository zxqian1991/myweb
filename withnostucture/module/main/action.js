/**
 * 前端不同于后端，前端更注重表现，因此，如果采用MVC分层模式会显得很是累赘
 * 因此我决定将所有的逻辑都整合在一个action之中
 */
define(function (require) {
	var util = require('util');
	var abAction = require('action');
	var action = function () {};
	util.derive(action, abAction);
    action.prototype.tplName = 'html/main/main.php';
    action.prototype.initBehavior = function () {
        var me = this;
        $('#qzx_root_content').append(me.tpl);
        me.content = $("#qzx_main_content");
        if (!me.isLogged()) {
            // 没有登录
            location.hash = 'login/action';
            return;
        }
        // me.arpSearch();
        
        util.sendLog("main/action", "enter the main page","info");
    };
    action.prototype.getCmtsList = function () {
        var me = this;
        util.getAjax("POST", getCmtsListHandler, {
            async: true,
            url: "html/main/ajax.php",
            opt: "option=getCmtsList"
        });
        function getCmtsListHandler(xml) {
            var text = xml.responseText;
  
        }
    }
    action.prototype.arpSearch = function () {
        var me = this;
        util.getAjax("POST",searchResultHandler,{
            async: true,
            url: "html/main/ajax.php",
            opt: "option=arpSearch"
        });
        function searchResultHandler(xml) {
            var text = xml.responseText;
            var result = eval("(" + text + ")");
            var item = 1;
            var wrapper = $(".item_wrapper");
            wrapper.html('');
            for (var key in result.ip) {
                
                var id = "main_list" + item;
                wrapper.append("<div class = 'main_list_item' id = '" + id + "'>" + result.ip[key] + "</div>");
                item++;
            }
            if (!me.isOut) {
                // setTimeout(function () {me.arpSearch();}, 1000);
            }
        }
    };
    action.prototype.table_name = {};
    action.prototype.drawTable = function () {

    }
    /**
     * 判断是否登录,用cookie来判断
     */
    action.prototype.isLogged = function () {
        var isLogged = util.getCookie('isLogged');
        if (isLogged == '1') {
            return true;
        } else {
            return false;
        }
    };

	return new action();
});
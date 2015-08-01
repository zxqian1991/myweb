define(function (require) {
    var util = require('util');
	var abAction = require('action');
	var action = function () {};
	util.derive(action, abAction);
	var TABLE_NAME = "cmtsdatanow";
	action.prototype.tplName = 'html/cmtsstate/main.php';
	action.prototype.initBehavior = function () {
		var me = this;
        me.container = $("body");
        me.container.append(me.tpl);
        me.content = $("#cmts_state");
        me.getData();
        me.actionController();
	}
	action.prototype.data = {};
	action.prototype.actionController = function () {

	};
	action.prototype.getData = function () {
		var me = this;
        util.getAjax("POST", getDataHandler, {
        	async: true,
        	url: "html/main/ajax.php",
            opt: "option=getCmtsNow&"
        });
        function getDataHandler(xml) {
			me.getMyDataHandler(xml);
			if (!me.isOut) {
				setTimeout(function () {me.getData();}, 1000);
			}
		};
	};
	action.prototype.getMyDataHandler = function (xml) {
		var me = this;
        var returnObj = eval('(' + xml.responseText + ')');
        var innerHtml = "";
        var fields_num = returnObj.fields_num;
        var rows_num = returnObj.rows_num;
        for (var i = 0; i< rows_num; i++) {
        	for (var j = 0; j< fields_num; j++) {
        		innerHtml = innerHtml + "<div class = 'show_item body_item_show '>"+ returnObj.data[i][j] + "</div>";
        	}
        	innerHtml = innerHtml + '<br/>';
        }
        $(".show_list_wrapper").html(innerHtml);
	};

	return new action();
});
define(function (require) {
	var util = require('util');
	var abAction = require('action');
	var action = function () {};
	util.derive(action, abAction);
    action.prototype.tplName = 'html/welcome/main.php';

    /**
     * 行为初始化
     * @override
     */
    action.prototype.initBehavior = function () {
        var me = this;
        $('body').append(me.tpl);
        me.content = $("#qzx_welcome_content");
        var start_btn = $('#qzx_start_btn');
        start_btn.bind('click', function () { location.hash = 'main/action'});
    };
    
    return new action();
});
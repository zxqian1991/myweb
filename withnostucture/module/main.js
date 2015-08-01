require.config({
	baseUrl: 'module',
	paths: {
		jquery: '../dep/jquery',
		util: '../dep/util',
		gmap: '../dep/gmap',
		less: '../dep/less',
		moment: '../dep/moment',
		underscore: '../dep/underscore',
		router: '../dep/router',
		test: 'test/main',
		action: '../dep/action',
        widgets: '../dep/widgets'
	},
	urlArgs: "bust=" +  (new Date()).getTime()
});

// less初始化设置
less = {
	env: 'development',
	async: false,
	fileAsync: false,
	poll: 1000,
	dumpLineNumbers: "comments",
	relativeUrls: false
};
// 进入主逻辑
require(['jquery', 'less', 'util', 'action', 'router'], function () {
    // $已经加载到了全局变量中了
    // 开启路由监听功能v
    var router = require('router');
    var util = require('util');
    router.init();
    if (location.href.split('#').length != 1) {
    	location.href = 'http://myweb:80';
    }
    $(document).ready(function () {
        require(['widgets']);
        initActions();
    });
    require(['welcome/action'], function (action) {
        action.startMyEvents();
        action.enter();
    });
    if (util.getCookie('isLogged') == '1') {
        $("#qzx_user_name").html(util.getCookie('username'));
        $("#qzx_user_operation").bind('click',function () {
            util.setCookie('isLogged', '0');
            $("#qzx_user_operation").html('登录');
            $("#qzx_user_name").html('guest');
            location.hash = 'login/action';
        });
    } else {
        $("#qzx_user_operation").bind('click',function () {
            location.hash = 'login/action';
        });
    }
    
});
function initActions () {
    var mainBtn = $("#qzx_id_mainBtn");
    mainBtn.bind('click', function () {
        location.hash = '';
    });
    var logout = $("#qzx_user_operation");
    var home = $("#qzx_home");
    home.bind('click', function () {
        location.hash = 'main/action';
    });
}

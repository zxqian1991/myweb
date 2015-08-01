define(function (require) {
    // 根函数
    var router = {};
    router.temphash = '';
    // 初始化函数
    router.init = function () {
    	//实时监测hash的变化
    	var me = this;
        window.fcContext = {};
        if ('onhashchange' in window) {
            window.onhashchange = function () {
                var hash = location.href.split('#')[1];
                if (hash === '') {
                    //跳转到main界面
                    initBehavior('welcome/action');
                } else {
                    initBehavior(hash);
                }
                if (me.temphash == '') {
                    require(['welcome/action'], function (myaction) {
                        myaction.dispose();
                    })
                } else {
                    require([me.temphash], function (action) {
                        action.dispose();
                    })
                }
                me.temphash = hash;          
            };
        } else {
            setInterval(function () {
                if (ifHashChanged) {
                    hashChangeHandler();
                }
            }, 200);
        }

    };
    
    // 判断hash是否变化
    router.ifHashChanged = function () {
    	var me = this;
        var hash = location.hash;
        if (hash !== this.temphash) {
        	return true;
        } else {
        	return false;
        }
    }

    // 具体的处理函数
    function initBehavior(hash) {
        require([hash], function (action) {
            action.startMyEvents();
            action.enter();
        });
    }
    return router;
});
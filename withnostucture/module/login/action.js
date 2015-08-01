define(function () {
	var util = require('util');
	var abAction = require('action');
	var action = function () {};
	util.derive(action, abAction);
    action.prototype.tplName = 'html/login/main.php';

    action.prototype.initBehavior = function () {
    	var me = this;
        $('#qzx_root_content').append(me.tpl);
        me.content = $("#qzx_login_content");
        if (me.isLogged()) {
            location.href = 'main/action';
            return;
        }
        me.initEvent();
    };

    /**
     * 初始化事件
     */
    action.prototype.initEvent = function () {
        var userInput = $("#qzx_user_input");
        userInput.bind('focus', function () {
            if (userInput.attr('inputstate') == '0') {
                userInput.val('');
                userInput.removeClass('qzx_bold');
                userInput.addClass('on_input');
            }
        });
        userInput.bind('blur', function () {
            if (userInput.val()) {
                userInput.attr('inputstate', '1');
            } else {
                userInput.removeClass('on_input');
                userInput.val('请输入用户名');
                userInput.attr('inputstate', '0');
            }            
        });
        var passwdInput = $("#qzx_password_input");
        passwdInput.bind('focus', function () {
            if (passwdInput.attr('inputstate') == '0') {
                passwdInput.val('');
                passwdInput.addClass('on_input');
                passwdInput.removeClass('qzx_bold');
                passwdInput.attr('type', 'password');
            }
        });
        passwdInput.bind('blur', function () {
            if (passwdInput.val()) {
                passwdInput.attr('inputstate', '1');
            } else {
                passwdInput.removeClass('on_input');
                passwdInput.val('请输密码');
                passwdInput.attr('inputstate', '0');
                passwdInput.attr('type', 'TEXT');
            }            
        });
        var submit_btn = $("#login_submit_btn");
        submit_btn.bind('click',function () {
            if (userInput.attr('inputstate') == '1' && passwdInput.attr('inputstate') == '1') {
                util.getAjax("POST", submitHandler, {
                    async: true,
                    url: 'html/main/ajax.php',
                    opt: 'option=verifyuser' +
                         '&username=' + userInput.val() +
                         '&password=' + passwdInput.val()
                });
            } else {
                userInput.attr('inputstate') == '0'
                    ? userInput.addClass('qzx_bold') 
                    : userInput.removeClass('qzx_bold');
                passwdInput.attr('inputstate') == '0'
                    ? passwdInput.addClass('qzx_bold')
                    : passwdInput.removeClass('qzx_bold');
            }
            function submitHandler(xml) {
                var text = xml.responseText;
                console.log("result is " + text);
                if (text == '1') {
                    //验证成功
                    util.setCookie('username', userInput.val(), 1);
                    util.setCookie('isLogged', '1', 1);
                    // 改一下名字
                    $("#qzx_user_name").html(userInput.val());
                    $("#qzx_user_operation").html('注销');
                    location.hash = 'main/action';
                } else {
                    $("#login_tips").text('用户名或者密码错误')
                }
            }
        });
    };

    /**
     * 判断是否存在islogged这个cookie
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
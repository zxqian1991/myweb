define(function (require) {
    var util = require('util');
	var abAction = require('action');
	var action = function () {};
	util.derive(action, abAction);
	action.prototype.tplName = 'html/config/main.php';
    
    /**
     * @override
     */
	action.prototype.initBehavior = function () {
        var me = this;
        me.container = $("body");
        me.container.append(me.tpl);
        me.content = $("#head_item_service_config");
        me.actionController();
	}
	action.prototype.actionController = function () {
        var add_btn = $("#cmts_add");
        var config_hosts_list = $("#add_wrapper");
        var tempHtml = '';
        var me = this;
        add_btn.bind("click", function () {
        	for (var i = 0; i<6; i++) {
                tempHtml = tempHtml + "<div class = 'table_item cmts_add_item'><input type = 'text'></div>";
        	}
        	tempHtml = tempHtml + '<br/>';
            config_hosts_list.append(tempHtml);
            tempHtml = '';
	        var cmts_add_item = $(".cmts_add_item input");
	        var length = cmts_add_item.length;
	        for(var i = 0; i< length; i = i + 6) {
	        	$(cmts_add_item[i]).unbind('blur');
	        	$(cmts_add_item[i]).bind('blur', function () {
	        		console.log('ok');
	        		if (this.value != '') {
	        			if ((this.value - this.value) != 0) {
	        				$(this).parent().addClass('error');

	        			} else {
	        				$(this).parent().removeClass('error');
	        			}
	        		} else {
	        			$(this).parent().removeClass('error');
	        		}
	        	});
	        }
        });

        var confirm_btn = $('#cmts_confirm_btn');
        confirm_btn.bind('click',function () {
        	if (!me.ifError()) {
        		var length = $(".cmts_add_item").length;
	            var row = length / 6;
	            var sendObj = {};
	            if (row != 0 ) {
	            	// 将每行的数据都存储起来并发送到服务器端处理
	            	// 服务器接收到请求后将数据进行存储
	            	for (var  j = 0; j < row; j++) {
	            		sendObj["row" + j] = [];
	            		var tempObj = sendObj["row" + j];
	            		for (var i = 0; i < 6; i++) {
	            			var num = j * 6 + i;
	            			var item_value = (i == 0) 
	            			    ? (-$('.cmts_add_item input')[num].value)
	            			    : $('.cmts_add_item input')[num].value;
	                        tempObj.push(item_value);
	            		}
	            	}
	            	var a = JSON.stringify(sendObj);
	                // 利用ajax发送数据
	                util.getAjax("POST", sendHandler, {
	                	async: true,
	                	url: "html/main/ajax.php",
	                	opt:"option=addCmts&" +
	                	    "obj=a"
	                });
	                function sendHandler(xml) {
	                	console.log("ok");
	                	console.log(xml.responseText);
	                }
	            }
        	} else {
                alert("存在格式错误，请重新填写");
        	}
        });
        var cancle_btn = $('#cmts_cancle_btn');
        cancle_btn.bind('click', function () {
            $("#add_wrapper").html('');
        });
	};
	action.prototype.ifError = function () {
		var cmts_add_item = $(".cmts_add_item input");
	    var length = cmts_add_item.length;
	    var result = 0;
	    for (var i = 0; i < length; i = i + 6) {
	    	var value = cmts_add_item[i].value
            if ((value - value) != 0 || value == '') {
            	result++;
            }
	    }
	    return result;
	};
	return new action();
});
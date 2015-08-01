/**
 * Created by qianzhixiang on 15/7/13.
 */
define(function(require){
    var parent = require("view");
    var util = require("util");
    var controller = require("controller");
    var view = function(){
        parent();
    };
    util.derive(view,parent);
    view.prototype.url = "modular/login/main.php";
    view.prototype.islogged = false ;
    view.prototype.show = function () {
        var me = this;
        me.container.empty();
        me.container.append(me.text);
    };
    view.prototype.initEvents = function(){
        var me = this;
        if(!qzx.controller.islogged) {
            var submitBtn = $("#qzx-login-btn");
            var cancelBtn = $("#qzx-cancel-btn");
            $('#myModal').on('hidden.bs.modal', function () {
                // 执行一些动作...
                if(me.islogged) {
                    $("#qzx-body").empty();
                    qzx.controller.enter("main/action");
                    $(".qzx-log").text("注销");
                    $(".qzx-log").unbind();
                    $(".qzx-log").bind("click",function(){
                        $(".qzx-log").text("login");
                        me.islogged = false;
                        qzx.controller.islogged = false;
                        console.log(qzx.controller.tempPage);
                        $(".qzx-log").unbind();
                        qzx.controller.enter("login/action");
                    });
                } else {
                    if(qzx.controller.tempMenuBar) {
                        qzx.controller.tempMenuBar.removeClass("active");;
                    }
                    $(".qzx-home").addClass("active");
                    qzx.controller.tempMenuBar = $(".qzx-home");
                    //qzx.controller.enter("login/action");
                }
            });
            $(".qzx-log").bind("click",function(){
                $('#myModal').modal('toggle');
            });
            submitBtn.bind("click",function(){
                var username = $("#username").val();
                var password = $("#password").val();
                console.log("i am in");
                util.ajax({
                    tyep: "post",
                    url: "modular/login/logincl.php",
                    asycn: false,
                    data: "username="+username+"&password="+password,
                    success: function(xml){
                        var text = xml.responseText;
                        if(text == "1") {
                            // 成功了
                            me.islogged = true;
                            $('#myModal').modal('hide');
                            qzx.controller.tempMenuBar.removeClass("active");
                            $(".qzx-cmts").addClass("active");
                            qzx.controller.islogged = true;
                            qzx.controller.tempMenuBar = $(".qzx-cmts");
                        } else {
                            // 失败了
                            me.islogged = false;
                            $("#qzx-warning-tip").removeClass("hide");
                        }
                    }
                })
            });
            cancelBtn.bind("click",function(){
                $('#myModal').modal('hide');
                me.islogged = false;
            });
        } else {
            $("#qzx-get-started").unbind();
            $("#qzx-get-started").attr({
                "data-target":null
            });;
            $("#qzx-get-started").bind("click",function(){
                qzx.controller.enter("main/action");
                qzx.controller.tempMenuBar.removeClass("active");;
                $(".qzx-cmts").addClass("active");
                qzx.controller.tempMenuBar = $(".qzx-cmts");
            });
        }
    };
    view.prototype.dispose = function(){
        var me = this;
        console.log("login disposed");
        $('#myModal').unbind();
        $("#qzx-login-btn").unbind();
        $("#qzx-cancel-btn").unbind();
    };

    return new view();
});
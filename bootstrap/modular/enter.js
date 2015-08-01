/**
 * Created by qianzhixiang on 15/7/5.
 */
require.config({
    baseUrl: 'modular',
    paths: {
        logger: "widget/logger",
        util: "widget/util",
        xml: 'widget/xml',
        controller: 'widget/controller',
        action: 'widget/action',
        view: "widget/view",
        headbar: "headbar/headbar"
    },
    urlArgs: "bust=" +  (new Date()).getTime()
//    "angular",
});
require(['util','xml','logger','action','headbar','controller'],function(){
    var util = require("util");
    window.qzx = {};
    qzx.util = require("util");
    qzx.logger = require("logger");
    qzx.controller = require("controller");
    $(".qzx-home").bind("click",function(){
        qzx.controller.enter("login/action");
        if(qzx.controller.tempMenuBar) {
            qzx.controller.tempMenuBar.removeClass("active");;
        }
        $(".qzx-home").addClass("active");
        qzx.controller.tempMenuBar = $(".qzx-home");
    });
    $(".qzx-cmts").bind("click",function(){
        if(qzx.controller.islogged) {
            qzx.controller.enter("main/action");
            if(qzx.controller.tempMenuBar) {
                qzx.controller.tempMenuBar.removeClass("active");;
            }
            $(".qzx-cmts").addClass("active");
            qzx.controller.tempMenuBar = $(".qzx-cmts");
        } else {
            $('#myModal').modal('toggle');
        }
    });
    if(util.islogged()) {
        qzx.controller.islogged = true;
        qzx.controller.enter("main/action");
   } else {
        $(".qzx-log").text("login");
        qzx.controller.islogged = false;
        qzx.controller.enter("login/action");
   }

});
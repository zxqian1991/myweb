/**
 * Created by qianzhixiang on 15/7/6.
 */
define(function(require){
    var headbar = {};
    var util = require("util");
    headbar.qzx_head_main = $("#qzx_head_main");
    headbar.qzx_head_cmts = $("#qzx_head_cmts");
    headbar.qzx_head_cm = $("#qzx_head_cm");
    headbar.qzx_head_cpe = $("#qzx_head_cpe");
    headbar.qzx_head_remote = $("#qzx_head_remote");
    headbar.qzx_head_cmts = $("#qzx_head_cmts");
    headbar.qzx_head_user_center = $("#qzx_head_user_center");
    headbar.qzx_head_username = $("#qzx_head_username");
    headbar.qzx_head_user_manage = $("#qzx_head_user_manage");
    headbar.qzx_head_logout = $("#qzx_head_logout");
    headbar.qzx_head_user_center.bind("click",function(){
        var me = $(this);
        var text = me.text();
        var islogged = util.getCookie("islogged");
        if(!islogged) {

        } else {

        }
    });

});
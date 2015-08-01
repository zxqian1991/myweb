/**
 * Created by qianzhixiang on 15/7/3.
 */
define(function(require) {
    var logger = {};
    var ajax = require("./xml");
    /*
     * opts :{
     * value:  内容
     * type:   类型  INFO WARN
     * }
     */
    logger.sendLog = function (type,value){
        var me = this;
        var temp = "type=" + type + "&value=" + value;
        var opts = {
            type: "post",
            url: me.url,
            async: true,
            opt: temp
        };
        me.xml.init(opts);
    };
    logger.xml = new ajax();
    logger.url = "modular/log/log.php";
    logger.info = function(value) {
        var me = this;
        me.sendLog("info",value);
    };
    logger.warn = function (value) {
        var me = this;
        me.sendLog("warn",value);
    };
    logger.debug = function (value) {
        var me = this;
        me.sendLog("debug",value);
    };
    logger.fatal = function (value) {
        var me = this;
        me.sendLog("fatal",value);
    };
    return logger;
});

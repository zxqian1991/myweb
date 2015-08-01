require.config({
    baseUrl: 'js',
    paths: {
        "jquery":'jquery/jquery-2-1-4-min',
        "easings":"fullpage/easings.min",
        "slimscroll": "fullpage/slimscroll-min",
        "fullpage": "fullpage/fullpage-2-6-6",
        "controller":"../module/controller",
        "headbar": "../module/headbar/headbar",
        "home": "../module/home/home"
    },
    urlArgs: "bust=" +  (new Date()).getTime()
//    "angular",
});
require(["easings","slimscroll","fullpage","controller","headbar"],function(){
    var controller = require("controller");
    var headbar = require("headbar");
    headbar.enter();
    require(['home'],function(home){
        home.enter();
        home.show();
        controller.tempPage = home;
    });
});
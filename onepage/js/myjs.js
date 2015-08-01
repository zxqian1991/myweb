/**
 * Created by qianzhixiang on 15/7/10.
 */
window.onload = function () {
    //var runPage;
    //runPage = new FullPage({
    //    id: 'pageContain',
    //    slideTime: 800,
    //    effect: {
    //        transform: {
    //            translate: 'X'    //垂直滚动，改为X则是水平滚动
    //        },
    //        opacity: [0, 1]
    //    },
    //    mode: 'wheel, touch, nav:navBar',
    //    easing: 'ease'
    //});
    var aa = $("#navBa");
    var ll = 0;
    $(document).bind("click",function(){
        console.log("ok");
        if(ll > 10) {
            $(this).unbind();
            return
        }
        ll++;
    });
    $(document).bind("mouseover",function(){
        console.log("mouseover");
    });
    var runPage, interval, autoPlay;

    autoPlay = function(to) {
        clearTimeout(interval);
        interval = setTimeout(function() {
            runPage.go(to);
        }, 1000);
    }
    runPage = new FullPage({
        id: 'pageContain',
        slideTime: 800,
        effect: {
            transform: {
                translate: 'X',
                scale: [0, 1],
                rotate: [270, 0]
            },
            opacity: [0, 1]
        },
        mode: 'wheel, touch, nav:navBar',
        easing: 'ease',
        callback: function(index, thisPage){
            index = index + 1 > 3 ? 0 : index + 1;
            autoPlay(index);
        }
    });
    interval = setTimeout(function() {
        runPage.go(runPage.thisPage() + 1);
    }, 1000);
}
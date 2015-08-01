/**
 * Created by qianzhixiang on 15/7/3.
 */
define(function (require) {
    var util = {};

    /*
     继承某个对象  对象是方法
     */
    util.derive = function (newObj, old) {
        var temp = function () {};
        temp.prototype = old.prototype;
        newObj.prototype = new temp();
    };

    util.copyArray = function (newArr, oldArr) {
        var me = this;
        for (var i = 0; i < oldArr.length; i++) {
            if (oldArr[i] instanceof Object) {
                var tempObj = {};
                tempObj = me.copyObj(tempObj, oldArr[i]);
                newArr.push(tempObj);
            } else if (oldArr[i] instanceof Array) {
                var tempArr = [];
                tempArr = me.copyArray(tempArr, oldArr[i]);
                newArr.push(tempArr);
            } else {
                newArr.push(oldArr[i]);
            }
        }
        return newArr;
    }
    util.copyObj = function (newObj,oldObj) {
        var me = this;
        for(var key in oldObj) {
            if (oldObj[key] instanceof Object) {
                var tempObj = {};
                tempObj = me.copyObj(tempObj, oldObj[key]);
                newObj[key] = tempObj;
            } else if (oldObj[key] instanceof Array) {
                var tempArr = [];
                tempArr = me.copyArray(tempArr, oldObj[key]);
                newObj[key] = tempArr;
            } else {
                newObj[key] = oldObj[key];
            }

        }
        return newObj;
    };
    return util;
});
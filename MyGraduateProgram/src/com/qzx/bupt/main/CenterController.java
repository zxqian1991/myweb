package com.qzx.bupt.main;

import org.apache.log4j.Logger;

/*
 * 1.中心控制器，控制所有的部件，每个部件在使用的时候得指定对应的控制器以便控制器调用
 * 2.每个部件都得要继承基类以获取公共属性
 */
public class CenterController {
     /*
      * 中心控制器，类似于一个人，它的主要属性就是他拥有的服务
      */
	DataCollector dCollector = null; // 数据收集处理中心
    DataCenter dCenter = null; // 数据中心
    DataSendor dSendor = null; // 数据发送中心
    DbController dbController = null; // 数据库操作中心，跑腿的，数据搬运工
    HostStateService hostStateService = null;
    Warnor warnor = null;
    Waiter waiter = null;
    MyLogger logger = null;
    public CenterController() {
    	logger = new MyLogger(this);
    	warnor = new Warnor(this);
    	dbController = new DbController(this);
    	dCenter = new DataCenter(this);
    	dCollector = new DataCollector(this);
    	dSendor = new DataSendor(this);
    	waiter = new Waiter(this);
    	hostStateService = new HostStateService(this);
    	this.start();
    }
    public void start() {
    	this.logger.start();
    	this.warnor.start(); // 初始化警告相关的操作，警告会产生日志，所以得放在logger后面
    	this.dbController.start(); // 数据库的初始化，这样获取数据存储就靠它了
    	// 数据中心的初始化，对各种数据的初始化，是核心，也是其他两类的灵魂，
    	// 数据采集,需要找到对应的oid等，数据发送也是如此
    	this.dCenter.start();
    	this.dCollector.start();
    	this.dSendor.start(); // 数据发送有很多的形式，有同步，有异步，有单个，由多个。
    	this.hostStateService.start();
    	this.waiter.start(); // socket开始服务
    }
    public void stop () {
    	// 反过来
    	 this.waiter.stop();
    	 this.dSendor.stop();
    	 this.dCollector.stop();
    	 this.dCenter.stop();
    	 this.dbController.stop();
    	 this.warnor.stop();
    	 this.logger.stop();
    }
    
    /*
     * 指定纪录者，就像文秘，有好几个，有的负责这个，有的负责那个，有的都负责
     * 目前的logger有一下几个，下面是他们对应的属性
     */
    public Logger getLogger(String name) {
    	return null;
    }
}

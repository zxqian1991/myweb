package com.qzx.bupt.main;

import java.lang.reflect.Field;

/*
 * 服务基类，主要定义了一些常见的公用的操作
 */
public abstract class BaseMaterial {
    CenterController center = null;
    private Boolean service_state = false;
    public BaseMaterial(CenterController center) {
    	this.center = center;
    }
    public void start() {
    	this.initBehavior();
    	// 放在程序的最后，保证状态为开启
    	this.service_state = true;
    }
    public void stop() {
        this.dispose();
    	this.service_state = false;
    }
    abstract public void initBehavior();
    abstract public void dispose();
    /*
     * 返回服务状态
     */
    public Boolean isServiceOn() {
    	return this.service_state;
    }
    public void updatePropertyByName(String name ,String value) {
    	Class temp = this.getClass();
    	try {
			Field field = temp.getDeclaredField(name);
			field.set(this, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

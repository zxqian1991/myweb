package com.qzx.bupt.main;

import org.apache.log4j.Logger;

public class MyLogger extends BaseMaterial{
    public Logger rootLogger = null;
    public Logger errorLogger = null;
    public Logger debugLogger = null;
    public Logger infoLogger = null;
    public Logger warnLogger = null;
    public String root_logger_name = "rootLogger";
    public String error_logger_name = "error";
    public String debug_logger_name = "debug";
    public String info_logger_name = "info";
    public String warn_logger_name = "warn";
	public MyLogger(CenterController center) {
		super(center);
		// TODO Auto-generated constructor stub
	}
    public String getRootLoggerName() {
    	return this.root_logger_name;
    }
    public void setRootLoggerName(String name) {
    	this.root_logger_name = name;
    }
    public String getErrorLoggerName() {
    	return this.error_logger_name;
    }
    public void setErrorLoggerName(String name) {
    	this.error_logger_name = name;
    }
    public String getDebugLoggerName() {
    	return this.debug_logger_name;
    }
    public void setDebugLoggerName(String name) {
    	this.debug_logger_name = name;
    }
    public String getInfoLoggerName() {
    	return this.info_logger_name;
    }
    public void setInfoLoggerName(String name) {
    	this.info_logger_name = name;
    }
    public String getWarnLoggerName() {
    	return this.info_logger_name;
    }
    public void setWarnLoggerName(String name) {
    	this.info_logger_name = name;
    }
	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		this.rootLogger = Logger.getLogger(this.root_logger_name);
		this.debugLogger = Logger.getLogger(this.debug_logger_name);
		this.infoLogger= Logger.getLogger(this.info_logger_name);
		this.errorLogger = Logger.getLogger(this.error_logger_name);
		this.warnLogger = Logger.getLogger(this.warn_logger_name);
	}
    public void debug(String value) {
    	this.debugLogger.debug(value);
    }
    public void info(String value) {
    	this.infoLogger.info(value);
    }
    public void error(String value) {
    	this.errorLogger.error(value);
    }
    public void warn(String value) {
    	this.warnLogger.warn(value);
    }
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}

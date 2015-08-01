package com.qzx.bupt.main;

import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;

/*
 * 数据收集器，主要接收snmp异步结果
 */
public class DataCollector extends BaseMaterial implements ResponseListener{
    public int port; // 这个数据收集器的端口号
	public DataCollector(CenterController center) {
		super(center);
	}
	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onResponse(ResponseEvent response) {
		// TODO Auto-generated method stub
		// 那儿不停的发送，这边不停的接受，并更新
		SnmpResponse res = new SnmpResponse(response);
//		System.out.println(res.resnodes.get(0).value);
        for(int i = 0;i < res.resnodes.size(); i++) {
        	this.center.dCenter.UpdateCmtsValueByResponseNode(res.resnodes.get(i));
        }
	}
}

package com.qzx.bupt.main;

import java.io.IOException;

import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;

public class HostStateService extends BaseMaterial {
    Boolean cmtsHostServiceState = false;
    String testoid = "1.3.6.1.2.1.1.1.0";
	public HostStateService(CenterController center) {
		super(center);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		this.initCmtsHostStateService();
	}
    public void initCmtsHostStateService() {
    	Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				cmtsHostServiceState = true;
				while(cmtsHostServiceState) {
					int len = center.dCenter.cmts_list_offline.size();
					CmtsNode[] nodes = center.dCenter.cmts_list_offline.toArray(new CmtsNode[0]);
					for(int i = 0; i < nodes.length; i++) {
						Package pkg = new Package();
						pkg.setTarget(nodes[i].ip + "/" + nodes[i].snmpport);
						pkg.addPdu(testoid);
						ResponseEvent event = null;
						try {
							event = center.dSendor.getNormalSnmp().send(pkg.pdu, pkg.target);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// 如果没有，怎不做任何动作
							continue; // 忽略后面的
						}
						// 不管返回结果是什么都不用解析,能直行道这儿说明通了
						center.dCenter.cmts_list.add(nodes[i]);
						center.dCenter.cmts_list_offline.remove(nodes[i]);
					}
				}
			}
		});
    	thread.start();
    }
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

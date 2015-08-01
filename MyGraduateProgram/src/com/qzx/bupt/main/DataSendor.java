package com.qzx.bupt.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import javax.management.loading.PrivateClassLoader;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import sun.tools.tree.ThisExpression;

import com.sun.jndi.url.ldaps.ldapsURLContextFactory;

/*
 * 数据发送器，和数据发送器相对应，她的主要目的是发送数据
 */
public class DataSendor extends BaseMaterial{
	int port = 9989;
	int time_wax = 1; // 发送一个pdu需要的时间
	float cpu_usage = 0;
	public Boolean time_wax_service = false;
	Boolean cmts_service_state = false;
	Boolean analysis_service_state = false;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	Snmp asendor = null;
	public DataSendor(CenterController center) {
		super(center);
	}
	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		this.initSnmp(); // 设置异步
		this.startTimeTestService();
		this.startLocalCpuService();
		this.startCmtsService();
		 
	}
	public void startTimeTestService() {
	     Thread time_test = new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					time_wax_service = true;
					while(time_wax_service) {
						time_wax = (int) Math.ceil(testTime(1000)/1000.0);
					}
					try {
						Thread.sleep(1000 * 60 * 2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
	}
	public void startLocalCpuService() {
		
	}
	public void startAnalysisService(HashSet<NameNode> nodes1, HashSet<NameNode> node2) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 不断的分析数据以改变其对应的类别
				
			}
		});
		thread.start();
	}
	public void startCmtsService() { // 更新数据后重新startService
		
		HashSet<NameNode> level_1_list = new HashSet<NameNode>(); // level = 0 的列表，对应着2-5s
		HashSet<NameNode> level_2_list = new HashSet<NameNode>(); // level ＝ 1的列表，对应着2-5min
		// 分两类
		NameNode[] nodes =  this.center.dCenter.cmts_biaoliang_list.toArray(new NameNode[0]);
		int size = nodes.length;
		for(int i = 0; i < size; i++) {
			if(nodes[i].level == 0) {
				level_1_list.add(nodes[i]);
			} else {
				level_2_list.add(nodes[i]);
			}
		}
		NameNode[] level_1_nodes = level_1_list.toArray(new NameNode[0]);
		NameNode[] level_2_nodes = level_2_list.toArray(new NameNode[0]);
		// 上面是两种不同级别的属性列表的初始化
		
		// 为了保证实时性，采用直接读取cmts列表的方式
		this.cmts_service_state = true;
		Thread level_1_thread = new Thread(new SendThread(this,	level_1_nodes,0));
		Thread level_2_thread = new Thread(new SendThread(this,	level_2_nodes,1));
		level_1_thread.start();
		level_2_thread.start();
		this.startAnalysisService(level_1_list, level_2_list);
	}
	public void stopCmtsService() {
		this.cmts_service_state = false;
	}
	public void initSnmp() {
		try {
			TransportMapping transport = new DefaultUdpTransportMapping(new UdpAddress(this.port));
			this.asendor = new Snmp(transport);
			transport.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Snmp getNormalSnmp() {
		try {
			TransportMapping transport = new DefaultUdpTransportMapping();
			Snmp snmp = new Snmp(transport);
			transport.listen();
			return snmp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public int getCmNums(String address) {
		
		// 开一个新的线程去完成
		return 0;
	}
	public Vector<ResponseNode> getValue(Package pg) {
		SnmpResponse response = null;
		try {
			response = new SnmpResponse(this.getNormalSnmp().send(pg.pdu, pg.target));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.resnodes;
	}
	public ResponseEvent send(Package pkg) {
		try {
			return this.getNormalSnmp().send(pkg.pdu, pkg.target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//	public String getValueByOid(String address,String oid, String type) {
//		Snmp snmp = this.getNormalSnmp();
//		PDU pdu = new PDU();
//		if (type == "get") {
//			pdu.setType(PDU.GET);
//		} else if (type == "getnext"){
//			pdu.setType(PDU.GETNEXT);
//		} else {
//			pdu.setType(PDU.GET);
//		}
//		pdu.add(new VariableBinding(new OID(oid)));
//		CommunityTarget target = new CommunityTarget();
//		target.setAddress(GenericAddress.parse("udp:" + address)); // "192.168.10.199/161"
//		target.setCommunity(new OctetString("public"));
//		target.setVersion(SnmpConstants.version2c);
//		target.setRetries(2);
//		target.setTimeout(1500);
//		try {
//			ResponseEvent response= snmp.send(pdu, target);
//			SnmpResponse result = new SnmpResponse(response);
//			if(result.resnodes.size() == 1) {
//				// 结果只有一个
//				ResponseNode node = result.resnodes.elementAt(0);
//				System.out.println(node.oid);
//				return node.value;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	public long testTime(int num) {
		// 测试发送pdu的速度
		PDU[] pdu = new PDU[num];
		for(int i = 0; i < pdu.length; i++) {
			pdu[i] = new PDU();
			pdu[i].setType(PDU.GET);
		}
		CommunityTarget target = new CommunityTarget();
		target.setAddress(GenericAddress.parse("udp:" + "127.0.0.1/161")); // "192.168.10.199/161"
		target.setCommunity(new OctetString("public"));
		target.setVersion(SnmpConstants.version2c);
		target.setRetries(2);
		target.setTimeout(1500);
	    ResponseListener listener = new ResponseListener() {
			
			@Override
			public void onResponse(ResponseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		Calendar cal = Calendar.getInstance();
	    long begin = cal.getTimeInMillis();
	    for(int i = 0; i < pdu.length; i++) {
	    	try {
				this.asendor.send(pdu[i], target, null, listener);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    cal = Calendar.getInstance();
	    long end = cal.getTimeInMillis();
		return (end - begin);
	}
	public Snmp getSnmp() {
		return this.asendor;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

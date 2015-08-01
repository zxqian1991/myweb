package com.qzx.bupt.main;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;

import com.sun.tools.internal.ws.wscompile.Options.Target;

public class SendThread implements  Runnable{
	DataSendor sendor;
	NameNode[] nodes = null;
	int level = 0;
	int limint_time = 0;
	int time_gip = 0;;
	public SendThread(DataSendor sendor,NameNode[] nodes, int level) {
		this.sendor = sendor;
		this.nodes = nodes;
		this.level = level;
		if(level == 0) {
			limint_time = 2; // 单位是s
		} else {
			limint_time = 120;// 单位是s  即2min
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(sendor.cmts_service_state) {
			// 遍历cmts列表
			int size = nodes.length;
			int cmts_nums = sendor.center.dCenter.cmts_list.size();
			int all_nums = size * cmts_nums;
			if (all_nums == 0) {
				break;
			}
			int all_mills =  all_nums * sendor.time_wax;
			int limit_mills = this.limint_time * 1000;
			// 有以下几种情况，第一低于这个值很多 可能所有的都发送完才只需要1s 那么可以适当的提高频率比如做到1s
			if (all_mills < (limit_mills)) { // 不到2秒，就按1.5s
				this.time_gip = (limint_time * 750 - all_mills) / (all_nums);
				this.send(size, cmts_nums);
			} else if (all_mills >= limint_time * 1000 && all_mills <= limint_time * 1000 * 2.5) {
				// 2-5s 或者 2-5min
				this.time_gip = 0;
				this.send(size, cmts_nums);
			}
		}
	}
	public void sleep(int mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void send(int size, int cmts_nums) {
		for(int i = 0; i < cmts_nums; i++) {
			CmtsNode cmts = sendor.center.dCenter.cmts_list.get(i);
			CommunityTarget target = this.getTarget(cmts);
			
			for(int j = 0; j < size; j++) {
				PDU pdu = new PDU();
				pdu.setType(PDU.GET);
				pdu.add(new VariableBinding(new OID(nodes[j].oid + ".0"))); // 由于是get,需要加.0
				try {
//					System.out.println(nodes[j].name);
					sendor.asendor.send(pdu, target, null, sendor.center.dCollector);
					
					this.sleep(this.time_gip); // 休息一会继续发
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("主机" + cmts.ip + "已经关闭了");
					cmts.state = 0;
					cmts.retries_times++;
					if(cmts.retries_times < 3) {
						sendor.center.dbController.setCmtsState(cmts, 0);
						cmts.retries_times = 0;  // 置零
						// 停止对这个cmts的包的发送，等待2min 继续发送
						sendor.center.dCenter.cmts_list.remove(cmts);
						sendor.center.dCenter.cmts_list_offline.add(cmts);
					}
					
				}
			}
		}
	}
    public CommunityTarget getTarget(CmtsNode node) {
    	CommunityTarget target = new CommunityTarget();
    	target.setAddress(GenericAddress.parse("udp:" + node.ip + "/" + node.snmpport)); // "192.168.10.199/161"
		target.setCommunity(new OctetString(node.community));
		target.setVersion(SnmpConstants.version2c);
		target.setRetries(2);
		target.setTimeout(1500);
		return target;
    }
}

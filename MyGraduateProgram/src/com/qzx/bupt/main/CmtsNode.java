package com.qzx.bupt.main;

import java.util.HashMap;
import java.util.HashSet;

import sun.tools.tree.ThisExpression;

public class CmtsNode extends BaseMaterial{
    public CmtsNode(CenterController center) {
		super(center);
		this.walker = new Walker(this.center);
		// TODO Auto-generated constructor stub
	}
    int retries_times = 0;
    String pro1 = "";
    String pro2 = "";
    String pro3 = "";
	String ip = "";
    int id = 0;
    String mac = "";
    String company = "default";
    String community = "public";
    int snmpport = 161;
    int version = 3;
    int ipdr = 0;
    int state = 0;
    String sysinfo = "";
    String sysname = "";
    String sysuptime = "";
    String cpuinfo = "";
    float cpuuseper = 0;
    String raminfo = "";
    int ramsize = 0;
    int raminusesize = 0;
    float ramuseper = 0;
    int cmnums = 0;
    int cpenums = 0;
    int cmonlinenums = 0;
    int cmofflinenums = 0;
    int interfacenums = 0;
    int channelnums = 0;
    int upchannelnums = 0;
    int upchannelinusenums = 0;
    float upstreamflows = 0;
    int downchannelnums = 0;
    int downchannelinusenums = 0;
    float downstreamflows = 0;
    HashSet<String> cm_suffix = new HashSet<String>();
    HashSet<String> upstream_suffix = new HashSet<String>();
    HashSet<String> downstream_suffix = new HashSet<String>(); // 每个里面都是存储的对应的suffix的值
    // 有一个单独的线程不断的去获取每个cmts的对应oid的值，以实时更新
    HashMap<String, CmNode> cm_list_ip = new HashMap<String, CmNode>();
    HashMap<String, CmNode> cm_list_suffix = new HashMap<String, CmNode>();
    Walker walker = null;
    public void goWalkCm() {
    	if(!(this.ip == "")) {
    		this.walker = new Walker(this.center);
    		walker.setTarget(this.ip + "/" + this.snmpport);
    		String oid = this.center.dCenter.cmts_name_oid_list.get("cmindex").toString();
    		this.walker.setOid(oid);
    		this.walker.walk();
    		this.cm_suffix = (HashSet<String>) walker.oid_suffix.clone();
    	} 
    }
    public CmNode getCmByIp(String ip) {
    	return this.cm_list_ip.get(ip);
    }
    public CmNode getCmByIndex(String suffix) {
    	return this.cm_list_suffix.get(suffix);
    } // 每一个cm有一个属于自己的后缀
    
    
	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

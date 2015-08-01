package com.qzx.bupt.main;

public class CmNode extends BaseMaterial{
    public CmNode(CenterController center) {
		super(center);
		// TODO Auto-generated constructor stub
	}
	int id = 0;
    int cmindex = 0;
    String suffix = "";
    String ip = "";
    int cmtsid = 0;
    String mac = "";
    int onstate = 0;
    int registstate = 0;
    int snmpport = 161;
    String community = "public";
    String cpuinfo = "";
    float cpuuseper = 0;
    String raminfo = "";
    int ramsize = 0;
    int raminusesize = 0;
    float ramuseper = 0;
    int cpenums = 0;
    String sysinfo = "";
    Walker walker = null;
	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

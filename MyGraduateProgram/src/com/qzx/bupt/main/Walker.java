package com.qzx.bupt.main;

import java.util.ArrayList;
import java.util.HashSet;

import javax.print.attribute.HashAttributeSet;

import org.snmp4j.PDU;

import sun.tools.tree.ThisExpression;

public class Walker extends BaseMaterial{
    public Walker(CenterController center) {
		super(center);
		this.pkg.pdu.setType(PDU.GETNEXT);
		// TODO Auto-generated constructor stub
	}
	Package pkg = new Package();
    String oid = "";
    HashSet<String>	oid_suffix = new HashSet<String>();
    public void resetOid() {
    	this.pkg.pdu.clear();
    	this.pkg.addPdu(this.oid);
    }
    public void resetOid(String oid) {
    	this.pkg.pdu.clear();
    	this.oid = oid;
    	this.pkg.addPdu(oid);
    }
    public int getSize() {
    	return this.oid_suffix.size();
    }
    public int getSizeByWalk() {
    	this.walk();
    	return this.oid_suffix.size();
    }
    public void setOid(String oid) {
    	this.pkg.pdu.clear();
    	this.oid = oid;
    	pkg.addPdu(oid);
    }
    public void setTarget(String address) {
    	this.pkg.setTarget(address);
    }
	public Boolean hasNext() {
		SnmpResponse response = new SnmpResponse(this.center.dSendor.send(pkg));
		ResponseNode node = response.resnodes.elementAt(0);
		int oid_len = this.oid.length();
		if(node.oid.substring(0, oid_len).equals(this.oid)) {
			// 说明还得一起遍历 
			System.out.println(node.oid + " : " + node.value);
			oid_suffix.add(node.oid); // 将返回值进行
			pkg.pdu.clear();
			pkg.addPdu(node.oid);
		} else {
			this.resetOid();
			return false;
		}
		return true;
	}
	/*
	 * 行走一遍，获得所有的节点
	 */
	public void walk() {
		this.resetOid();
		while(this.hasNext()) {
		}
	}
	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

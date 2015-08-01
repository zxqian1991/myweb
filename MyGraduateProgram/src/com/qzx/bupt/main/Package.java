package com.qzx.bupt.main;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;

public class Package {
    PDU pdu = new PDU();
    CommunityTarget target = new CommunityTarget();
    public Package() {
    	pdu.setType(PDU.GET);
    }
    public void addPdu(String oid) {
    	pdu.add(new VariableBinding(new OID(oid)));
    }
    public void addScalaPdu(String oid) {
    	oid = oid + ".0";
    	pdu.add(new VariableBinding(new OID(oid)));
    }
    public void setTarget(String address) {
    	target.setAddress(GenericAddress.parse("udp:" + address)); // "192.168.10.199/161"
		target.setCommunity(new OctetString("public"));
		target.setVersion(SnmpConstants.version2c);
		target.setRetries(2);
		target.setTimeout(1500);
    }
    public void setCommunity(String community) {
    	target.setCommunity(new OctetString(community));
    }
}

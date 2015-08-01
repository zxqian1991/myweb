package com.qzx.bupt.main;

import java.util.HashSet;
import java.util.Vector;

import org.apache.commons.httpclient.RedirectException;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.VariableBinding;

public class SnmpResponse {
    Vector<ResponseNode> resnodes = new Vector<ResponseNode>();
    public SnmpResponse(ResponseEvent response) {
    	if (response != null && response.getResponse() != null) {
    		Vector<VariableBinding> results = response.getResponse().getVariableBindings();
    	    int size = results.size();
    	    String ip = response.getPeerAddress().toString();
    	    int index = ip.indexOf('/');
    	    ip = ip.substring(0, index);
    	    for(int i = 0; i < size; i++) {
    	    	VariableBinding vb = results.elementAt(i);
    	    	ResponseNode node = new ResponseNode();
    	    	node.ip = ip;
    	    	node.oid = vb.getOid().toString();
    	    	node.value = vb.getVariable().toString();
    	    	this.resnodes.add(node);
    	    }
    	}
    }
    
}

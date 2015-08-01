package com.qzx.bupt.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.sun.corba.se.impl.orbutil.graph.NodeData;
import com.sun.org.apache.bcel.internal.generic.NEW;

/*
 * 数据中心，存储着所有的数据
 */
public class DataCenter extends BaseMaterial{
	/*
	 * company:{name : node(properties)}
	 */
	public String cmts_table_name = "cmts";
	public String cmts_properties_list_table_name = "cmts_properties_list";
	public String cm_properties_list_table_name =  "cm_properties_list";
	public String cpe_properties_list_table_name =  "cpe_properties_list";
	public HashSet<NameNode> cmts_biaoliang_list = new HashSet<NameNode>();
	public HashSet<NameNode> cm_biaoliang_list =  new HashSet<NameNode>();
	public HashSet<NameNode> cpe_biaoliang_list =  new HashSet<NameNode>();
	public HashMap<Integer, HashSet<NameNode>> cmts_level_properties_name_list = new HashMap<Integer, HashSet<NameNode>>();
	public HashMap<Integer, HashSet<NameNode>> cpe_level_properties_name_list = new HashMap<Integer, HashSet<NameNode>>();
	public HashMap<Integer, HashSet<NameNode>> cm_level_properties_name_list = new HashMap<Integer, HashSet<NameNode>>();
	public HashSet<NameNode> cmts_properties_name_list = new HashSet<NameNode>(); // 这是所有的属性的集合
	public HashSet<NameNode> cm_properties_name_list = new HashSet<NameNode>(); // 这是所有的属性的集合
	public HashSet<NameNode> cpe_properties_name_list = new HashSet<NameNode>(); // 这是所有的属性的集合
	
	public HashMap <String,HashMap<String, NameNode>> cmts_name_oid_list = new HashMap<String, HashMap<String,NameNode>>();
    public HashMap <String,HashMap<String, NameNode>> cm_name_oid_list = new HashMap<String, HashMap<String,NameNode>>();
    public HashMap <String,HashMap<String, NameNode>> cpe_name_oid_list = new HashMap<String, HashMap<String,NameNode>>();
    
    /*
     * 接下来就是获取已经有的保存了的cmts cm 的属性列表
     */
    public HashMap<String, CmtsNode> cmts_list_ip = new HashMap<String, CmtsNode>();
    public ArrayList<CmtsNode> cmts_list = new ArrayList<CmtsNode>();
    public ArrayList<CmtsNode> cmts_list_offline = new ArrayList<CmtsNode>();
    public HashMap<Integer, CmtsNode> cmts_list_id = new HashMap<Integer, CmtsNode>();
    public DataCenter(CenterController center) {
		super(center);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.qzx.bupt.main.BaseMaterial#initBehavior()
	 * 初始化数据中心
	 */
	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		this.initNameList("cmts");
		this.initNameList("cm");
		this.initNameList("cpe");
		this.initCmtsList();
		this.initCmList();
	}
    /*
     * (non-Javadoc)
     * @see com.qzx.bupt.main.BaseMaterial#dispose()
     * 删除数据
     */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	public CmtsNode getCmtsByIp(String ip) {
		return this.cmts_list_ip.get(ip);
	}
	public CmtsNode getCmtsById(int id) {
		return this.cmts_list_id.get(id);
	}
	
	public void initCmtsList() {
		HashMap<String, CmtsNode> temp_map = this.cmts_list_ip;
		HashMap<Integer, CmtsNode> temp_id_map = this.cmts_list_id;
		String tb_name = this.cmts_table_name;
		Connection connection = this.center.dbController.getConnection();
		try {
			/*
			 * 每个cmts都是有不同的ip和id的
			 */
			Statement statement = connection.createStatement();
			/*
			 * 开始逐一加载到内存
			 */
			String sql = "select * from " + tb_name;
		    ResultSet result = statement.executeQuery(sql);
		    CmtsNode node = null;
		    while(result.next()) {
                String ip = result.getString("ip");
                int id  = result.getInt("id");
                if (temp_map.get(ip) == null) {
					node = new CmtsNode(this.center);
					temp_map.put(ip, node);
					temp_id_map.put(id, node);
				} else {
					node = temp_map.get(ip);
				}
                node.ip = ip;
                node.id = id;
                node.channelnums = result.getInt("channelnums");
                node.cmnums = result.getInt("cmnums");
                node.cmofflinenums = result.getInt("cmofflinenums");
                node.cmonlinenums = result.getInt("cmonlinenums");
                node.community = result.getString("community");
                node.company = result.getString("company");
                node.cpenums = result.getInt("cpenums");
                node.cpuinfo = result.getString("cpuinfo");
                node.cpuuseper = result.getFloat("cpuuseper");
                node.downchannelinusenums = result.getInt("downchannelinusenums");
		        node.downchannelnums = result.getInt("downchannelnums");
                node.downstreamflows = result.getFloat("downstreamflows");
                node.interfacenums = result.getInt("interfacenums");
                node.ipdr = result.getInt("ipdr");
                node.mac = result.getString("mac");
                node.raminfo = result.getString("raminfo");
                node.raminusesize = result.getInt("raminusesize");
                node.ramsize = result.getInt("ramsize");
                node.ramuseper = result.getFloat("ramuseper");
                node.snmpport = result.getInt("snmpport");
                node.state = result.getInt("state");
                node.sysinfo = result.getString("sysinfo");
                node.sysname = result.getString("sysname");
                node.sysuptime = result.getString("sysuptime");
                node.upchannelinusenums = result.getInt("upchannelinusenums");
                node.upchannelnums = result.getInt("upchannelnums");
                node.upstreamflows = result.getFloat("upstreamflows");
                node.version = result.getInt("version");
                node.walker.setTarget(node.ip + "/" +node.snmpport);
                if (node.state == 1) {
					this.cmts_list.add(node);
				} else {
				   this.cmts_list_offline.add(node);	
				}
                this.initCmtsHistory(node);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void initCmList() {
		// 每个cm都有一个自己的cm存储表，里面存储着cm的信息
		Iterator iterator = this.cmts_list_id.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Integer, CmtsNode> entry = (Entry<Integer, CmtsNode>) iterator.next();
			int id = entry.getKey();
			CmtsNode node = entry.getValue();
			HashMap<String, CmNode> cm_list_ip = node.cm_list_ip;
			String cm_tb_name = "cm_list_" + id; // 这里存储着这个cmts所有的cm的信息
			Connection connection = this.center.dbController.getConnection();
			if(!this.center.dbController.ifExistsTable(cm_tb_name)) {
				// 不存在，新建一个表
				this.center.dbController.createCmTable(cm_tb_name); // 现在仅仅是建立表，在之后的数据采集不断的完善数据
			} else {
				// 如果存在，则初始化数据
				String sql = "select * from " + cm_tb_name;
				try {
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery(sql);
					CmNode cm_node = null;
					while(result.next()) {
						String cm_suffix = result.getString("suffix");
						int cm_id  = result.getInt("id");
						String cm_ip = result.getString("ip");
						if (cm_list_ip.get(cm_ip) == null) {
							// 不存在
							cm_node = new CmNode(this.center);
							cm_list_ip.put(cm_ip, cm_node);
							node.cm_list_suffix.put(cm_suffix, cm_node);
						} else {
							cm_node = cm_list_ip.get(cm_ip);
						}
						cm_node.id = result.getInt("id");
						cm_node.cmtsid = result.getInt("cmtsid");
						cm_node.cpuinfo = result.getString("cpuinfo");
						cm_node.cmindex = result.getInt("cmindex");
						cm_node.cpenums = result.getInt("cpenums");
						cm_node.cpuuseper = result.getFloat("cpuuser");
						cm_node.ip = result.getString("ip");
						cm_node.mac = result.getString("mac");
						cm_node.onstate = result.getInt("onstate");
						cm_node.raminfo = result.getString("raminfo");
						cm_node.raminusesize = result.getInt("raminusesize");
						cm_node.ramsize = result.getInt("ramsize");
						cm_node.ramuseper = result.getFloat("ramuseper");
						cm_node.registstate = result.getInt("registstate");
						cm_node.suffix = result.getString("suffix");
						cm_node.sysinfo = result.getString("sysinfo");
						cm_node.snmpport = result.getInt("snmpport");
						cm_node.community = result.getString("community");
						cm_node.walker.setTarget(cm_node.ip + "/" + cm_node.snmpport);
					}
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	public void createCmtsHistoryList(int i,CmtsNode cmts) {
		// i 代表的是级别
		HashSet<NameNode> name_list = this.cmts_level_properties_name_list.get(i);
		NameNode[] nodes = name_list.toArray(new NameNode[0]);
		String name = "cmts_" + i + "_" +cmts.id; // cmts_0_1代表id为1  level = 0 的cmts的属性历史列表
		Connection connection = this.center.dbController.getConnection();
		try {
			Statement statement = connection.createStatement();
			String content = "id int not null auto_increment primary key,";
			for(int j = 0;j < nodes.length;j++) {
				NameNode node = nodes[j];
				String pro_name = node.name; // 获得属性名称
				/**
				 * 属性类型就行统一
				 * 统一是 varchar(500) 
				 * 
				 */
				if (j == nodes.length -1) {
					content = content + pro_name +" varchar(500)  default null";
				} else {
					content = content + pro_name +" varchar(500)  default null,";
				}
			}
			String sql = "create table " + name + " (" + content + ")";
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// 修改历史列表的属性
	public void updateCmtsHistoryList(int i,CmtsNode cmts) {
		// 获取属性集合
		String tablename = "cmts_" + i + "_" + cmts.id;
		HashSet<NameNode> name_list = (HashSet<NameNode>) this.cmts_level_properties_name_list.get(i).clone();
		HashSet<String> name_list_str = new HashSet<String>();
		NameNode[] nodes = name_list.toArray(new NameNode[0]);
		for(int j = 0; j < nodes.length;j++) {
			name_list_str.add(nodes[j].name);
		}
		String name = "cmts_" + i + "_" +cmts.id; // cmts_0_1代表id为1  level = 0 的cmts的属性历史列表
		Connection connection = this.center.dbController.getConnection();
		try {
			Statement statement = connection.createStatement();
			String content = "show columns from " + name + "";
			ResultSet resultSet = statement.executeQuery(content);
			while(resultSet.next()) {
				String pro_name = resultSet.getString("Field");
				name_list_str.remove(pro_name);
			}
			String[] list = name_list_str.toArray(new String[0]);
			for(int j = 0;j < list.length;j++) {
				statement.execute("alter table " + tablename + " add " + list[j] + " varchar(500) default null");
			}
			statement.close();;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initCmtsHistory(CmtsNode cmts) {
		// 由于两种不同类型的属性的type不一样，所以分开
		if (!this.center.dbController.ifExistsTable("cmts_0_" + cmts.id)) {
			// 不存在，新建表
           if (this.cmts_level_properties_name_list.get(0) != null) {
   			   this.createCmtsHistoryList(0, cmts);
			}
		} else {
			// 可能有属性变化，更新属性
			this.updateCmtsHistoryList(0, cmts);
		}
		if (!this.center.dbController.ifExistsTable("cmts_1_" + cmts.id)){
			if (this.cmts_level_properties_name_list.get(1) != null) {
				this.createCmtsHistoryList(1, cmts);	
			}
		} else {
			this.updateCmtsHistoryList(1, cmts);
		}
		if (!this.center.dbController.ifExistsTable("cmts_2_" + cmts.id)) {
			if (this.cmts_level_properties_name_list.get(2) != null) {
				this.createCmtsHistoryList(2, cmts);
			}
		} else {
			this.updateCmtsHistoryList(2, cmts);
		}
	}
	public void initNameList(String list_name) {
		// 首先获取所有company 是default的条目
		HashMap <String,HashMap<String, NameNode>> temp_hash = null;
		String tb_name = this.cmts_properties_list_table_name;
		HashSet<NameNode> biaoliang_list = this.cmts_biaoliang_list;
		HashSet<NameNode> properties_name_list = this.cmts_properties_name_list;
		HashMap<Integer, HashSet<NameNode>> level_properties_name_list = this.cmts_level_properties_name_list;
		if (list_name == "cmts") {
			tb_name = this.cmts_properties_list_table_name;
			biaoliang_list = this.cmts_biaoliang_list;
			properties_name_list = this.cmts_properties_name_list;
			level_properties_name_list = this.cmts_level_properties_name_list;
			temp_hash = this.cmts_name_oid_list;
		} else if (list_name == "cm") {
			tb_name = this.cm_properties_list_table_name;
			biaoliang_list = this.cm_biaoliang_list;
			properties_name_list = this.cm_properties_name_list;
			level_properties_name_list = this.cm_level_properties_name_list;
			temp_hash = this.cm_name_oid_list;
		} else if (list_name == "cpe") {
			tb_name = this.cpe_properties_list_table_name;
			biaoliang_list = this.cpe_biaoliang_list;
			properties_name_list = this.cpe_properties_name_list;
			level_properties_name_list = this.cpe_level_properties_name_list;
			temp_hash = this.cpe_name_oid_list;
		}
    	Connection connection = this.center.dbController.getConnection();
    	try {
			Statement statement = connection.createStatement();
			String url = "select * from " + tb_name + " where company = 'default'";
			ResultSet result = statement.executeQuery(url);
			HashMap<String, NameNode> tempList = new HashMap<String, NameNode>();
			temp_hash.put("default", tempList);
			// 获得所有的属性 default
			while(result.next()) {
				NameNode node = new NameNode();
				String name = result.getString("name");
				String oid = result.getString("oid");
				int level = result.getInt("level");
				int type = result.getInt("type");
				tempList.put(name, node);
				node.level = level;
				node.type = type;
				node.oid = oid;
				node.name = name;
				int biaoliang = result.getInt("ifbiaoliang"); // 1 是标量
				if (biaoliang == 1) {
					// 是标量
					biaoliang_list.add(node);
				}
				properties_name_list.add(node);
				if (node.level == 0) { // level 是0的属性 优先级很高
					HashSet<NameNode> level_nodeHashSet;
					if(level_properties_name_list.get(0) == null) {
						level_nodeHashSet = new HashSet<NameNode>();
						level_nodeHashSet.add(node);
						level_properties_name_list.put(0, level_nodeHashSet);
					} else {
						level_nodeHashSet = level_properties_name_list.get(0);
					    level_nodeHashSet.add(node);
					}
				} else if(node.level == 1){ // level 是1的属性，优先级一般
					HashSet<NameNode> level_nodeHashSet;
					if(level_properties_name_list.get(1) == null) {
						level_nodeHashSet = new HashSet<NameNode>();
						level_nodeHashSet.add(node);
						level_properties_name_list.put(1, level_nodeHashSet);
					} else {
						level_nodeHashSet = level_properties_name_list.get(1);
					    level_nodeHashSet.add(node);
					}
				} else if(node.level == 2) { // level 是2的属性，这种属性几乎不变，系统打开一个小时自动收集一次以查看状态。
					HashSet<NameNode> level_nodeHashSet;
					if(level_properties_name_list.get(2) == null) {
						level_nodeHashSet = new HashSet<NameNode>();
						level_nodeHashSet.add(node);
						level_properties_name_list.put(2, level_nodeHashSet);
					} else {
						level_nodeHashSet = level_properties_name_list.get(2);
					    level_nodeHashSet.add(node);
					}
				}
			}
			url = "select * from " + tb_name + " where company != 'default'";
			result = statement.executeQuery(url);
			while(result.next()) {
				String company = result.getString("company");
				// 没有这个公司，先加入表内，将default复制给它
				if(temp_hash.get(company) == null) {
					tempList = (HashMap<String, NameNode>) temp_hash.get("default").clone();
					temp_hash.put(company, tempList);
				}
				NameNode node = new NameNode();
				String name = result.getString("name");
				String oid = result.getString("oid");
				int level = result.getInt("level");
				int type = result.getInt("type");
				tempList.put(name, node); // 有相同名字的直接覆盖
				node.level = level;
				node.type = type;
				node.oid = oid;
				node.name = name;
				int biaoliang = result.getInt("ifbiaoliang"); // 1 是标量
				if (biaoliang == 1) {
					// 是标量
					biaoliang_list.add(node);
				}
				properties_name_list.add(node); // 有些属性default并没有
				if (node.level == 0) { // level 是0的属性 优先级很高
					HashSet<NameNode> level_nodeHashSet;
					if(level_properties_name_list.get(0) == null) {
						level_nodeHashSet = new HashSet<NameNode>();
						level_nodeHashSet.add(node);
						level_properties_name_list.put(0, level_nodeHashSet);
					} else {
						level_nodeHashSet = level_properties_name_list.get(0);
					    level_nodeHashSet.add(node);
					}
				} else if(node.level == 1){ // level 是1的属性，优先级一般
					HashSet<NameNode> level_nodeHashSet;
					if(level_properties_name_list.get(1) == null) {
						level_nodeHashSet = new HashSet<NameNode>();
						level_nodeHashSet.add(node);
						level_properties_name_list.put(1, level_nodeHashSet);
					} else {
						level_nodeHashSet = level_properties_name_list.get(1);
					    level_nodeHashSet.add(node);
					}
				} else if(node.level == 2) { // level 是2的属性，这种属性几乎不变，系统打开一个小时自动收集一次以查看状态。
					HashSet<NameNode> level_nodeHashSet;
					if(level_properties_name_list.get(2) == null) {
						level_nodeHashSet = new HashSet<NameNode>();
						level_nodeHashSet.add(node);
						level_properties_name_list.put(2, level_nodeHashSet);
					} else {
						level_nodeHashSet = level_properties_name_list.get(2);
					    level_nodeHashSet.add(node);
					}
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	/*
	 * 通过返回值更新对应的属性，更新历史属性
	 */
	public void UpdateCmtsValueByResponseNode(ResponseNode response) {
		String ip = response.ip;
		if(ip != "") {
			// 继续
			CmtsNode cmts = this.cmts_list_ip.get(ip);
			String oid = response.oid;
			Connection connection = this.center.dbController.getConnection();
			int len = oid.length();
			oid = oid.substring(0, len-2);
			try {
				Statement statement = connection.createStatement();
				String sql = "select * from " + this.cmts_properties_list_table_name + " where oid = '" + oid + "'";
				ResultSet result = statement.executeQuery(sql);
				String name = "";
				int level = 0;
				if(result.next()) {
					name = result.getString("name");
					level = result.getInt("level");
					/*
					 * 有些属性可能并没有oid,他们的level一般是2 ，要注意，level＝1的是几乎不会变的属性 level = 2主要就是一些状态信息，无法用某个oid去衡量
					 */
				}
				if (name != "") {
					// 不为空，更新数据
					this.center.logger.info("The Host " + response.ip + " returns the value of " + response.oid + " who's name is " + name + " and it's value is " + response.value);
					cmts.updatePropertyByName(name,response.value);
//					System.out.println(response.value);
					// 更新cmts的数据库，里面存放的是实时数据
					sql = "update "+ this.cmts_table_name  + " set " + name + " = '" + response.value + "' where ip = '" + response.ip + "'" ;
					statement.execute(sql);
					// 更新对应的历史数据
					String history_name = "cmts_" + level + "_" + cmts.id;
					DbController.updateHistoryDataByTableName(history_name, name, response.value);
				}
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}

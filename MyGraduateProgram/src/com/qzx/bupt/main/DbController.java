package com.qzx.bupt.main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import net.sf.json.JSONObject;
import sun.tools.tree.ThisExpression;

public class DbController extends BaseMaterial{
    public static String user = "root";
    public static String password = "htcraider1991";
    public static String host = "127.0.0.1:3306";
    public static Connection connection = null;
    static String database_name = "cmts_list";
	public DbController(CenterController center) {
		super(center);
		// TODO Auto-generated constructor stub
	} 

	@Override
	public void initBehavior() {
		// TODO Auto-generated method stub
		this.initConnection();
		
		/*
		 * 获取数据库的顺序
		 * 1.首先获取所有的外在的，主要有以下几个
		 *    cmts cm的属性  oid 等对应表
		 */
	}
    public static void initConnection() {
    	String url = "jdbc:mysql://" + DbController.host +"/" + database_name + "?"
                + "user=" + DbController.user + "&password=" + DbController.password
                + "&useUnicode=true&characterEncoding=UTF8";
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			DbController.connection = DriverManager.getConnection(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static Connection getConnection() {
    	try {
			if(DbController.connection.isClosed()) {
				DbController.initConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return DbController.connection;
    }
    public Boolean ifExistsTable(String name) {
    	this.connection = this.getConnection();
    	DatabaseMetaData meta;
		try {
			meta = connection.getMetaData();
			ResultSet rsTables = meta.getTables(null , null, name, null);
			if(rsTables.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    public void createCmTable(String name) {
    	this.connection = this.getConnection();
    	String content = "id int not null auto_increment primary key," +//+  // 这个cm对应的id
    	                 "cmindex int not null default 0," +  // 在oid里面对应的index
    	                 "cmtsid int not null default 0," +
    	                 "suffix int not null default 0," +
    	                 "ip varchar(15) default null," +
    	                 "community varchar(20) not null default 'public'," +
    	                 "snmpport int not null default 161," +
    	                 "mac varchar(17) default null," +
    	                 "onstate tinyint(2) not null default 0," +
    	                 "registstate tinyint(2) not null default 0," +
    	                 "cpuinfo varchar(50) not null default 'it is a normal cpu without discription'," +
    	                 "cpuuseper float not null default 0," +
    	                 "raminfo varchar(50) not null default 'it is a normal ram without discription'," +
    	                 "ramsize int not null default 0," +
    	                 "raminusesize int not null default 0," +
    	                 "ramuseper float not null default 0," +
    	                 "sysinfo varchar(50) not null default 'it is a normal system without discription'," +
    	                 "cpenums int not null default 0";
    	String sql = "create table "+ name +"(" + content + ")";
    	try {
			Statement statement = this.connection.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    public void dropTable(String table) {
    	this.connection = DbController.getConnection();
    	String url = "drop table " + table;
    	try {
			Statement statement = this.connection.createStatement();
			statement.execute(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void setCmtsState(CmtsNode cmts, int state) {
    	String history_tbname = "cmts_2_" + cmts.id;
    	String cmts_tbname = this.center.dCenter.cmts_table_name;
    	// 改变cmts的总表
    	this.changeNumProperty(cmts_tbname, "state", state, cmts.id);
    	// 改变历史表
    	this.insertNumValue(history_tbname, "state", state);
    	
    }
    public void changeNumProperty(String table, String name,Object value, int id) { // ID 代表对应的编号
    	// 改变某个表的某个属性
    	String sql = "update " + table + " set " + name + " = " + value + " where id = " + id;
    	
    	Connection connection = DbController.getConnection();
    	try {
			Statement statement = connection.createStatement();
			if(!statement.execute(sql)) {
				System.out.println("修改数据表" + table + "的" + name + "成功,修改之后的值是:" + value);
			} else {
				System.out.println("修改数据表" + table + "的" + name + "失败");
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void changeStrProperty(String table, String name,Object value, int id) { // ID 代表对应的编号
    	// 改变某个表的某个属性
    	String sql = "update " + table + " set " + name + " = '" + value + "' where id = " + id;
    	Connection connection = DbController.getConnection();
    	try {
			Statement statement = connection.createStatement();
			if(!statement.execute(sql)) {
				System.out.println("修改数据表" + table + "的" + name + "成功,修改之后的值是:" + value);
			} else {
				System.out.println("修改数据表" + table + "的" + name + "失败");
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void insertNumValue(String table, String name,int value) {
    	// 插入一个新的值
    	//先看这个id是不是存在
    	String sql = "select id from " + table + " where " + name + " is null order by id asc limit 1";
    	Connection connection = DbController.getConnection();
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				// 说明存在,得update
				int id = resultSet.getInt("id");
				sql = "update " + table + " set " + name + " = " + value + " where id = " + id;
				if(!statement.execute(sql)) {
					System.out.println("插入新的数据到" + table + "的" + name + "成功,修改之后的值是:" + value);
				} else {
					System.out.println("插入新的数据到" + table + "的" + name + "失败");
				}
			} else {
				// 说明不存在，得insert,这里插入的是字符串
				sql = "insert into " + table + " ("+ name +") " + "values (" + value + ")";
				if(!statement.execute(sql)) {
					System.out.println("插入新的数据到" + table + "的" + name + "成功,修改之后的值是:" + value);
				} else {
					System.out.println("插入新的数据到" + table + "的" + name + "失败");
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    public  void insertStrValue(String table, String name,String value) {
    	// 插入一个新的值
    	//先看这个id是不是存在
    	String sql = "select id from " + table + " where " + name + " is null order by id asc limit 1";
    	Connection connection = DbController.getConnection();
    	try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				// 说明存在,得update
				int id = resultSet.getInt("id");
				sql = "update " + table + " set " + name + " = '" + value + "' where id = " + id;
				if(statement.execute(sql)) {
					System.out.println("插入新的数据到" + table + "的" + name + "成功");
				} else {
					System.out.println("插入新的数据到" + table + "的" + name + "失败");
				}
			} else {
				// 说明不存在，得insert,这里插入的是字符串
				sql = "insert into " + table + " ("+ name +") " + "values ('" + value + "')";
				if(statement.execute(sql)) {
					System.out.println("插入新的数据到" + table + "的" + name + "成功");
				} else {
					System.out.println("插入新的数据到" + table + "的" + name + "失败");
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    public void close() {
    	try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	public static  void updateHistoryDataByTableName(String table,String name,String value) {
        // 首先对value进行一个小的封装
		/*
		 * 存储格式:
		 * {
		 *     mills: int 存储毫秒
		 *     date: str 字符串形式
		 *     value: str 字符串形式
		 * }
		 */
		HashMap storeHashMap = new HashMap();
		Calendar calendar = Calendar.getInstance();
		Date time = calendar.getTime();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
		String timeStr = df.format(time);
		long timemills = calendar.getTimeInMillis();
		storeHashMap.put("mills", timemills);
		storeHashMap.put("date", timeStr);
		storeHashMap.put("value", value);
		JSONObject jsonObject = JSONObject.fromObject(storeHashMap);  
        String storeStr = jsonObject.toString();
        insertDataByRowName(table, name, storeStr);
	}
	public static void insertDataByRowName(String table,String colum,String value) {
		String sql = "";
		Connection connection = DbController.getConnection();
		try {
			Statement statement = connection.createStatement();
			sql = "select * from " + table + " where " + colum + " is " + "null order by id asc limit 1;"; // 先判断是否存在
			ResultSet result = statement.executeQuery(sql);
			if(result.next()) {
				// 存在
				sql  = "update " + table + " set " + colum + " = '" + value + "' where " + colum + " is null order by id asc limit 1;";
				statement.execute(sql);
			} else {
				// 不存在
				sql = "insert into " + table + "(" + colum + ") values ('" + value +"')";
				statement.execute(sql);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
}

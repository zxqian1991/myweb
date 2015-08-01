g<?php
   class util {
        static $database = "cmts";
        static $cmtstable = "cmts";
        static $cmtsdatanow = 'cmtsdatanow';
        static $cmtslist = 'cmtslist';
   	    static function my_connect ($domain = "localhost", $user = "root", $password = "htcraider1991") {
   	    	qu
   	    	if (!$con) {
		       die('Could not connect: ' . mysql_error());
		       return null;
		      } else {
		    	return $con;
		      }
   	    }
   	    static function judgesql ($tablename, $userlist, $user, $passwdlist, $passwd) {
            $result = mysql_query("select * from $tablename where $userlist = '$user' and $passwdlist = '$passwd'");
            $rownum = mysql_num_rows($result);
            if ($rownum != 1) {
            	return '0';
            } else {
            	return '1';
            }
   	    }
        static function isLogged() {
            if (!empty($_SESSION['islogged'])) {
                return $_SESSION['username'];
                exit;
            } else {
                return 'guest';
            }
        }
        /**
         * 开启snmp服务
         */
        static function startSnmpService() {

        }
        
        static function insertCmts() {
          
        }
        /**
         * 关闭snmp服务
         */
        static function stopSnmpService() {

        }
        /**
         * 存储扫描到的地址等信息
         *
         */
        static function storeHosts($arr) {

        }
        
        /**
         * 分离ip,并以数组形式返回
         */
        static function getIpByStr($str) {
          preg_match_all("/([0-9]{1,3}\.){3}[0-9]{1,3}/", $str, $matches);
          return $matches[0];
        }
        /**
         * 分离mac,并以数组形式返回
         */
        static function getMacByStr($str) {
          preg_match_all("/([0-9a-zA-Z]{1,2}\:){5}[0-9a-zA-Z]{1,2}/", $str, $matches);
          return $matches[0];
        }
          /**
           * 这个函数主要用来开机时发现现存的主机
           * @return 返回主机列表类的数组，每个类包含的属性有： 
           * 1.主机的名称 hostname
           * 2.主机的ip hostip
           * 3/ 
           */
          static function searchHosts() {

          }

          /**
           *  这个函数用来设置某个主机上的某个参数
           */
          static function setSnmpProperties() {

          }

          /**
           * 
           */
          static function getSnmpValue() {
            
          }

         /** 
           * 去数据库读取数据，包括数据库的地址，数据库的名称，表的名称，查询数据的名称，查询条件
           * @param ip 数据库的ip地址
           * @param database 数据库名称
           * @param table 表的名称
           * @param dataname 操作数据的名称  数据可以为数组
           * @param condition 数据加入的条件
           */
           
          static function getValuesss($ip, $database, $table, $dataname, $condition) {

          }

          /**
           * @param ip 数据库的ip地址
           * @param database 数据库
           * @param table 表的名称
           * @param dataname 需要添加的字段名称
           * @param value 需要设置的字段的值
           * @param condition 添加的条件
           */
          static function setValue($ip, $database, $table, $dataname, $value, $condition) {

          }

          /**
           *
           */


   }
   class MyLog {
        static function getLog($name = '') {
          include_once('/Users/qianzhixiang/myfile/myweb/快盘/myweb/withnostucture/log4php/Logger.php');
          Logger::configure("/Users/qianzhixiang/myfile/myweb/快盘/myweb/withnostucture/config.xml");
          $log = Logger::getLogger($name);
          return $log;
        }
   }
?>
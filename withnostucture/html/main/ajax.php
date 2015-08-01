<?php
    include('../dep/util.php');
    $option = $_POST['option'];
    switch ($option) {
    	case 'arpSearch':
    	    arpSearch();
    	    break;
    	case 'startOrStopSnmpService':
    	    startOrStopSnmpService();
    	    break;
        case 'Log':
            myLog();
            break;
        case 'addCmts':
            addCmts();
            break;
        case 'getCmtsNow':
            getCmtsNow();
            break;
        case 'verifyuser':
            verifyUser();
            break;
        case 'getCmtsListHandler':
            getCmtsListHandler();
            break;
        case 'get':
            get();
            break;
        case 'set':
            set();
            break;
    	default:
    	    echo $option;
    }

    function getCmtsListHandler() {
        $con = util::my_connect();
        mysql_select_db('cmts');
        
    }

    function get() {

    }
    function set() {
        
    }
    function verifyUser() {
        $con = util::my_connect();
        mysql_select_db('cmts');
        $name = $_POST['username'];
        $password = $_POST['password'];
        $result = mysql_query("select * from user where name = '$name' and password = '$password'");
        mysql_close($con);
        echo mysql_num_rows($result);
    }
    function getCmtsNow() {
        $con = util::my_connect();
        if ($con) {
            mysql_select_db(util::$database);
            $cmtsdatanow = util::$cmtsdatanow;
            $result = mysql_query("select * from $cmtsdatanow");
            $fields_num = mysql_num_fields($result);
            $rows_num = mysql_num_rows($result);
            $output = "[";
            $flags = 0;
            while($row = mysql_fetch_array($result)) {
                if ($flags != 0) {
                    $output = $output.",";
                }
                $output = $output."[";
                for ($j = 0; $j < $fields_num; $j++) {
                    ($j != ($fields_num-1)) ? $output = $output."'".$row[$j]."'," : $output = $output."'".$row[$j]."'";
                }
                $output = $output."]";
                $flags = 1;
            }
            $output = $output."]";
            $outObj = 
                "{".
                "rows_num: $rows_num,".
                "fields_num: $fields_num,".
                "data: $output".
                "}";
            echo $outObj;
        } else {
            echo "there must be an error in mysql connect";
        }
        mysql_close($con);
    }
    function addCmts() {
        
    }

    function myLog() {
        $type = strtolower($_POST['type']);
        $user = $_POST['userName'];
        $id = $_POST['userId'];
        $level = $_POST['userLevel'];
        $source = $_POST['source'];
        $target = $_POST['target'];
        switch ($type) {
            case 'info':
                $log = MyLog::getLog('info');
                $log->info(
                   "User: $user, Id: $id, Level: $level, Source: $source, Target: $target"
                );
                break;
            case 'debug':
                $log = MyLog::getLog('debug');
                $log->debug(
                    "User: $user, Id: $id, Level: $level, Source: $source, Target: $target"
                );
                break;
            case 'warn':
                $log = MyLog::getLog('warn');
                $log->warn(
                     "User: $user, Id: $id, Level: $level, Source: $source, Target: $target"
                );
                break;
            case 'error':
                $log = MyLog::getLog('error');
                $log->error(
                     "User: $user, Id: $id, Level: $level, Source: $source, Target: $target"
                );
                break;
            case 'fatal':
                $log = MyLog::getLog('fatal');
                $log->fatal(
                    "User: $user, Id: $id, Level: $level, Source: $source, Target: $target"
                );
                break;
            default:
                $log = MyLog::getLog('debug');
                $log->debug(
                     "User: $user, Id: $id, Level: $level, Source: $source, Target: $target"
                );
        }
    }
    function arpSearch() {
    	echo "{";
        $result = shell_exec("arp -a");
        $ips = util::getIpByStr($result);
        $length = count($ips);
        echo "ip:  {";
        for($i = 0; $i < $length; $i++) {
        	echo "ip".$i.":'".$ips[$i]."'";
        	if($i != $length - 1) {
                echo ",";
        	}
        }
        echo "},";
        $mac = util::getMacByStr($result);
        $length = count($mac);
        echo "mac: {";
        for($i = 0; $i < $length; $i++) {
        	echo "mac".$i.":'".$mac[$i]."'";
        	if($i != $length - 1) {
                echo ",";
        	}
        }
        echo "}";
        echo "}";
    }
    function startOrStopSnmpService(){
    	//查看当前状态而后根本不同的状态执行不同的操作
    	$con = util::my_connect();
    	if ($con) {
            mysql_select_db('test', $con);
            $result = mysql_query("select * from servicestate where servicename = 'snmpService'");
            $rownum = mysql_num_rows($result);
            if ($rownum == 1) {
                $row = mysql_fetch_array($result);
                if ($row['servicestate']) {
                	// 假如服务已经开启，则关闭服务
                	util::closeSnmpService();
                	echo "close";
                } else {
                	util::startSnmpService();
                	echo "start";
                }
            }

        }
        mysql_close($con);
    }
?>
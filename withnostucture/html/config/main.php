<div id = "head_item_service_config">
    <p>当前已有的cmts的主机</p>
    <div id = "head_item_service_config_hosts_list">
    	<div id = "head_item_service_config_hosts_list_main">
	        &nbsp;<div class = 'table_item'>编号</div><div class = 'table_item'>Ip地址</div><div class = 'table_item'>Mac地址</div><span></span><div class = 'table_item'>生产厂商</div><div class = 'table_item'>型号</div><div class = 'table_item'>操作</div>
	        <br/>
	        <?php
	            include("../dep/util.php");
	            $con = util::my_connect();
	            $database = util::$database;
	            $cmtstable = util::$cmtstable;
	            if ($con) {
		              mysql_select_db($database);
		              $result = mysql_query("select * from $cmtstable");
		              while($row = mysql_fetch_array($result)) {
		              	$ifconfigured = $row['ifconfigured'];
		              	$showImgPath = '';
		              	if ($ifconfigured) {
		              		$showImgPath = "imgs/ok.png";
		              	} else {
		              		$showImgPath = 'imgs/delete.png';
		              	}
		            	echo "<div class = 'table_item'>".$row['id']."</div>".
		            	     "<div class = 'table_item'>".$row['ip']."</div>".
		            	     "<div class = 'table_item'>".$row['mac']."</div>".
		            	     "<div class = 'table_item'>".$row['company']."</div>".
		            	     "<div class = 'table_item'>".$row['type']."</div>".
		            	     "<div class = 'table_item'>".
		            	     "<div class = 'little_click_icon'><img src = 'imgs/config.png'/></div>".
		            	     "<div class = 'little_click_icon'><img src = '$showImgPath'/></div>".
		            	     "<div class = 'little_click_icon'><img src = 'imgs/info.jpg'/></div>".
		            	     "</div>".
		            	     "<br/>";
		              }
		              echo "<div class = 'biaoti'>添加新的节点</div>".
		                   "<div class = 'little_icon' id = 'cmts_add'><img src = 'imgs/add.png'/></div><br/>".
		                   "<div id = 'add_wrapper'></div>";
	            }
	            
	            mysql_close($con)
	        ?>
        </div>
        <div>
        <input id = 'cmts_confirm_btn' type = "button" class = 'cmts_self' value = "确定添加"/><input id = 'cmts_cancle_btn' class = 'cmts_self' type = "button" value = "取消"/><br/>
        </div>
    </div>
</div>
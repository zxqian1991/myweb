<!DOCTYPE html>
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title> CMTS网络管理</title>
        <link rel = 'shortcut icon' href= 'imgs/logo.png'/>
		<script language = 'javascript' type = 'text/javascript' data-main= 'module/main.js' src = 'dep/require.js'></script>
		<link rel="stylesheet/less" type="text/css" href="style.less" />
    </head>
    <body>
    <headbar class = 'qzx_headbar'>
    	<item class = 'qzx_item qzx_self_main' id = 'qzx_id_mainBtn'>首页</item>
    	<div class = 'qzx_item_wrapper_right'>
    		<ul class = 'qzx_ul_drop_down'>
               <li id = 'qzx_home'>home</li>
               <li>服务
               	    <ul>
                        <li>snmp扫描服务</li>
                        <li>查看主机服务状态</li>

               	    </ul>
               	</li>
                <li>工具
                    <ul>
                        <li>ssh远程登录</li>
                        <li>telnet远程登录</li>
               	    </ul>
                </li>
                <li><span  id = 'qzx_user_name'>guest</span>
                    <ul>
                        <li id = 'qzx_user_operation'>
                            登录
                        </li>
               	    </ul>
                </li>
    		</ul>
    	</div>
    </headbar>
    <maincontent class = 'qzx_main_content' id = 'qzx_root_content'></maincontent>
    <tailbar class = 'qzx_tail_bar'></tailbar>
    </body>
</html>
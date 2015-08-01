<?php
    $pagein = $_POST['pagein'];
    $servername = "localhost";
    $username = "root";
    $password = "htcraider1991";
    $dbname = "cmts_list";
    $conn = new mysqli($servername, $username, $password, $dbname);
    $sql = "select * from cmts";
    $result = $conn->query($sql);
    $store = array(array());
    $rows = 0;
    if($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $store[$rows]['id'] = $row['id'];
            $store[$rows]['ip'] = $row['ip'];
            $store[$rows]['mac'] = $row['mac'];
            $store[$rows]['system'] = $row['system'];
            $store[$rows]['name'] = $row['pro2'];
            $store[$rows]['sysuptime'] = $row['pro3'];
            $store[$rows]['cpuuseper'] = $row['cpuuseper'];
            $store[$rows]['ramuseper'] = $row['ramuseper'];
            $store[$rows]['cmnums'] = $row['cmnums'];
            $store[$rows]['cpenums'] = $row['cpenums'];
            $store[$rows]['channelnums'] = $row['channelnums'];
            $store[$rows]['use_all_up'] = $row['upchannelnums']."/".$row['upchannelinusenums'];
            $store[$rows]['use_all_down'] = $row['downchannelnums']."/".$row['downchannelinusenums'];
            $rows++;
        }
    }
?>
<div id = "qzx-main-content" ng-controller="DemoController">
    <table id = "qzx_cmts_temp_value_list" class="table table-bordered table-hover qzx-hand">
        <h1>cmts列表</h1>
        <thead>
        <tr>
            <th>id</th>
            <th>ip</th>
            <th>mac</th>
            <th>company</th>
            <th>name</th>
            <th>系统启动时间</th>
            <th>CPU使用率</th>
            <th>内存使用率</th>
            <th>CM总数</th>
            <th>CPE总数</th>
            <th>信道总数</th>
            <th>上行已用/上行总数</th>
            <th>下行已用/下行总数</th>
        </tr>
        </thead>
        <tbody>
        <?php
           $pages = floor($rows/8);
           $page_last_num = $rows%8;
           $k = $_REQUEST["k"];
               $begin = $k * 8;
               if($k == $pages) {
                   for($i=0;$i<$page_last_num;$i++){
                       $tr = "";
                       if($i % 4 == 0){
                           $tr = '<tr class="active row_'.($begin + $i +1).'">';
                       } else if($i % 4 == 1) {
                           $tr = '<tr class="danger row_'.($begin + $i +1).'">';
                       } else if($i % 4 == 2) {
                          $tr = '<tr class="success row_'.($begin + $i +1).'">';
                       } else if($i % 4 == 3) {
                          $tr = '<tr class="warning row_'.($begin + $i +1).'">';
                       }
                       echo "$tr";
                       foreach($store[$begin + $i] as $key=>$value) {
                           echo "<td class = '".$key." row_".($begin + $i +1)."'>".$value."</td>";
                       };
                       echo "</tr>";
                   }
               } else {
                    for($i=0;$i<8;$i++){
                        $tr = "";
                        if($i % 4 == 0){
                            $tr = '<tr class="active row_'.($begin + $i +1).'">';
                        } else if($i % 4 == 1) {
                            $tr = '<tr class="danger row_'.($begin + $i +1).'">';
                        } else if($i % 4 == 2) {
                            $tr = '<tr class="success row_'.($begin + $i +1).'">';
                        } else if($i % 4 == 3) {
                            $tr = '<tr class="warning row_'.($begin + $i +1).'">';
                        }
                        echo $tr;
                        foreach($store[$begin + $i] as $key=>$value) {
                            echo "<td class = '".$key." row_".($begin + $i +1)."'>".$value."</td>";
                        };
                        echo "</tr>";
                    }
               }


        ?>
        </tbody>
    </table>
    <center>
        <ul class="pagination qzx-page">
        <li><a class="qzx-hand" qzx-btn="left">&laquo;</a></li>
            <?php
              for($i = 0; $i < ($pages+1);$i++) {
                  $id = "qzx_page_devide_".($i+1);
                  if($i == $k) {
                      echo "<li class= 'active'><a class='qzx-hand' id = $id >".($i+1)."</a></li>";
                  } else {
                      echo "<li><a class='qzx-hand' id = $id >".($i+1)."</a></li>";
                  }

              }
            ?>
        <li><a class="qzx-hand" qzx-btn="right">&raquo;</a></li>
       </ul>
    </center>
</div>
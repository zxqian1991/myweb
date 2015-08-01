<?php
    include("../phpcontrol/db.php");
    $user = $_REQUEST['username'];
    $password = $_REQUEST['password'];
    $sql = "select * from user where username='".$user."' and password = '".$password."'";
    $result = $conn->query($sql);
    $rows = $result->num_rows;
    if($rows == 1) {
        echo "1";
    } else {
        echo "0";
    }
?>
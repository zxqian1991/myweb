<?php 
    session_start();
    $username = $_POST['username'];
    $passwd = $_POST['passwd'];
    include('../dep/util.php');
    $con = util::my_connect();
    if ($con) {
    	mysql_select_db('test', $con);
    	$result = util::judgesql('user','username', $username, 'password', $passwd);
    	if ($result == '1') {
            $_SESSION['islogged'] = '1';
    	}

        $res = mysql_query("select * from user where username = '$username'");
        $row = mysql_fetch_array($res);
        $userName = $row['username'];
        $userId = $row['id'];
        $userLevel = $row['level'];
        $_SESSION['user'] = $username;
        $_SESSION['id'] = $userId;
        $_SESSION['level'] = $userLevel;
    	echo "{".
            "status: '1',".
            "userName: '$userName',".
            "userId: $userId,".
            "userLevel: $userLevel".
            "}";
    }
    mysql_close($con);
?>
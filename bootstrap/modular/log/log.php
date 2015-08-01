<?php
    include('log4php/Logger.php');
    Logger::configure('config.xml');
    $type = $_POST['type'];
    $value = $_POST['value'];
    echo $type.$value;
    $log = Logger::getLogger($type);
    switch ($type) {
        case 'info':
            $log->info($value);
            break;
        case 'debug':
            $log->debug($value);
            break;
        case 'warn':
            $log->debug($value);
            break;
        case 'error':
            $log->debug($value);
            break;
        case 'fatal':
            $log->debug($value);
            break;
    }
    echo "ok";
?>
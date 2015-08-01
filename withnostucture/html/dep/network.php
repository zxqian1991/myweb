<?php
    class network {
        
    	/**
    	 * 此函数用来扫描指定范围内的主机，加入范围没有制定，则扫描
    	 */
    	public static function scan() {

    	}

    	/**
    	 * 通过oid或者名称去查找对应的值
    	 * 这里要考虑到不同机器对于同一个值可能有不同的oid，这里得要进行智能判断
    	 */
    	public static function get(ip,name) {
    
    	}
        /**
         * 此函数用来扫描指定的ip范围内的主机是否存在
         */
        public static function ping(ip) {

        }
    	/**
    	 * 通过名称或者oid进行设置
    	 */
    	public static function set(ip,name) {

    	}

        /**
         * 此函数用来判断对应的ip的名字所正确指向的oid
         */
    	public static function judge(ip,name) {

    	}
    }
?>
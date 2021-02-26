package com.example.key;

/**
 * UrlConstants
 * <p>
 * Created by 火龙裸先生 on 2020/03/11.
 */

public class Key {


    public static String DOMAIN_XRP_LINE = "https://s1.ripple.com:51234";  //xrp测试域名
    public static String DOMAIN_XRP_TEST_NET = "https://s.altnet.rippletest.net:51234";
    public static String DOMAIN_XRP_DEV_NET = "https://s.devnet.rippletest.net:51234";
    public static String DOMAIN_XRP = "http://192.168.1.11:5005";  //xrp测试域名
    //public static  String DOMAIN_XRP = "https://s.altnet.rippletest.net:51234";  //xrp测试域名

    public static String DOMAIN_AITD_TEST2 = "http://192.168.1.11:5001";  //aitd测试域名
    public static String DOMAIN_AITD_TEST = "http://api.aitd.io:19391";  //aitd测试域名
    public static String DOMAIN_AITD_PRE = "http://172.31.10.48:19391";  //aitd开发环境
    public static String DOMAIN_AITD = DOMAIN_AITD_TEST;  //aitd测试域名
    /**
     * XRP瑞波币 域名
     * 正式环境
     * wss://s-east.ripple.com
     * http://s1.ripple.com:51234/
     * <p>
     * Testnet 测试环境
     * Websockets
     * wss://s.altnet.rippletest.net:51233
     * JSON-RPC
     * https://s.altnet.rippletest.net:51234
     * <p>
     * Devnet 开发环境
     * Websockets
     * wss://s.devnet.rippletest.net:51233
     * JSON-RPC
     * https://s.devnet.rippletest.net:51234
     */


    public static String testPrivateKey; //测试本地账号 （账号1）
    public static String testPublicKey;//要转账的账号地址（账号2）


    public static void checkTestAccount() {

    }


}

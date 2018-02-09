package com.example.yehongjiang.booklist.config;

/**
 * <copyright>Copyright (c) 2018 All Right Reserved</copyright>
 * <author>Van Ye</author>
 * <date>2018/1/23</date>
 * <summary>booklist</summary>
 */

public class GlobalSetting {
    public final static String REQUEST_URL = "http://10.0.2.2:8080/booklist/service.php";
    public final static String REQUEST_GET_URL = "https://api.douban.com/v2/book/search";

    //网络超时配置
    public final static int TIMEOUT = 5000;

    //错误返回码配置
    public final static int RESULT_OK = 1000;
    public final static int NO_CONTENT = 1001;
    public final static int NETWORK_ERROR = 1002;

    public final static int USER_NAME_PASSWORD_NULL = 0;
    public final static int JSON_EXCEPTION_ERROR = -1;
    public final static int UNKNOWN_ERROR = 999;
    public final static int AUTHORIZATION_ERROR = 2001;
    public final static int BOOK_ALL_BORROWED = 2002;
    public final static int BOOK_RETURNED = 2003;
    public final static int DATABASE_OPERATION_ERROR = 4001;
}

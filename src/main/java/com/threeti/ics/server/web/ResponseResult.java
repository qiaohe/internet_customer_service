package com.threeti.ics.server.web;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 24/09/12
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class ResponseResult {
    public static final String CUSTOMER_SERVICE_USER_NOT_EXISTS_CODE = "2";
    public static final String CUSTOMER_SERVICE_USER_PASSWORD_WRONG_CODE = "3";
    public static final String CHANGE_PASSWORD_SUCCESS_CODE = "1";
    public static final String SUCCESS_CODE = "001" ;
    public static final String SUCCESS = "success" ;
    public static final String SEARCH_RESULT_CODE = "full_text_search" ;

    private String code;
    private Object result;

    public ResponseResult() {
    }

    public ResponseResult(String code, Object result) {
        this.code = code;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

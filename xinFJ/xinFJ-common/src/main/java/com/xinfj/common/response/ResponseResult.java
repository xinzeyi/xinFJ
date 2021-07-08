package com.xinfj.common.response;

/**
 * @author PXIN
 * @create 2021-06-10 11:54
 */
public class ResponseResult<T> {

    private Integer code;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static<T> ResponseResult<T> createSuccess(Integer code,String msg,T data){
        return new ResponseResult<>(code, msg, data);
    }

    public static ResponseResult<Void> createSuccess(Integer code,String msg){
        return new ResponseResult<>(code, msg);
    }

    public static<T> ResponseResult<T> createSuccess(String msg,T data){
        return new ResponseResult<>(Const.SUCCESS, msg, data);
    }

    public static ResponseResult<Void> createSuccess(String msg){
        return new ResponseResult<>(Const.SUCCESS, msg);
    }

    public static<T> ResponseResult<T> createError(Integer code,String msg,T data){
        return new ResponseResult<>(code, msg, data);
    }

    public static  ResponseResult<Void> createError(Integer code,String msg){
        return new ResponseResult<>(code, msg);
    }

    public static<T> ResponseResult<T> createError(String msg,T data){
        return new ResponseResult<>(Const.ERROR,msg,data);
    }

    public static ResponseResult<Void> createError(String msg){
        return new ResponseResult<>(Const.ERROR,msg);
    }
}

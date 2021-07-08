package com.xinfj.common.exception;

/**
 * @author PXIN
 * @create 2021-06-16 15:56
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L ;

    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

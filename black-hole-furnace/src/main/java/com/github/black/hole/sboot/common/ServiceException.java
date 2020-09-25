package com.github.black.hole.sboot.common;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -3307563572487036333L;

    private String code;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        this(message);
        setCode(code);
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        setCode(code);
    }

    public ServiceException(String code, Throwable cause) {
        super(cause);
        setCode(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

package com.github.black.hole.sboot.common;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
public class ServerException extends Exception {

    private static final long serialVersionUID = -2394323922507446831L;

    private String code;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String code, String message) {
        this(message);
        setCode(code);
    }

    public ServerException(String code, String message, Throwable cause) {
        super(message, cause);
        setCode(code);
    }

    public ServerException(String code, Throwable cause) {
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

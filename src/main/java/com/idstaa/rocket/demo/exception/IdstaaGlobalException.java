package com.idstaa.rocket.demo.exception;


import com.idstaa.rocket.demo.result.AppResult;

/**
 * @author chenjie
 * @date 2021/4/13 23:14
 */
public class IdstaaGlobalException extends Throwable {

    private static final long serialVersionUID = -6543484989095940852L;
    private final int errorCode;
    private final String errorMsg;

        public IdstaaGlobalException(AppResult resultCode) {
        super(resultCode.getMessage());
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMessage();
    }

    public IdstaaGlobalException(AppResult resultCode, Throwable throwable) {
        super(resultCode.getMessage(), throwable);
        this.errorCode = resultCode.getCode();
        this.errorMsg = resultCode.getMessage();
    }

    public IdstaaGlobalException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public IdstaaGlobalException(int errorCode, String errorMsg, Throwable throwable) {
        super(errorMsg, throwable);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getResultCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
}
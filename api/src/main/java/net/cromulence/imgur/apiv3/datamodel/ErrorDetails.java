package net.cromulence.imgur.apiv3.datamodel;

import java.io.Serializable;
import java.util.Arrays;

public class ErrorDetails implements Serializable {

    /*
        "code":1002,
        "message":"File is over the size limit",
        "type":"ImgurException",
        "exception":[]

     */

    private int code;
    private String message;
    private String type;
    private String[] exception;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getException() {
        return exception;
    }

    public void setException(String[] exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", type='" + type + '\'' +
            ", exception=" + Arrays.toString(exception) +
            '}';
    }
}

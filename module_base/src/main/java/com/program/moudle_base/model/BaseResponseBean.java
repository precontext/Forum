package com.program.moudle_base.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponseBean implements Serializable {

    /**
     * success : true
     * code : 10000
     * message : 感谢点赞.
     * data : null
     */

    @SerializedName("success")
    private Boolean success;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Object data;

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseResponseBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

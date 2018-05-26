package com.husen.mall2.vo;

import java.io.Serializable;

/**
 * 返回Json数据格式的vo
 * @author husen
 */
public class JsonResult<T> implements Serializable {
    private Integer code;
    private String description;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }

    public JsonResult() {
        super();
    }

    public JsonResult(Integer code, String description, T data) {
        super();
        this.code = code;
        this.description = description;
        this.data = data;
    }

    public JsonResult(Integer code, String description) {
        super();
        this.code = code;
        this.description = description;
    }
}

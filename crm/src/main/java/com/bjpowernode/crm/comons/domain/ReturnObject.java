package com.bjpowernode.crm.comons.domain;

public class ReturnObject {

    //创建一个实体类来返回数据
    private String code;//处理成功获取失败的标志：1--成功，0--失败
    private String message;//提示信息

    private Object retData;//返回其他类

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}

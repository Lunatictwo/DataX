package com.alibaba.datax.plugin.writer.es5xwriter;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by zehui on 2017/8/18.
 */
public class DataAppsFlyerInfoEntity extends ESEntity {
    private String id;
    private Date create_time;
    private Integer type;
    private String uid;
    private String device_type;
    private String device_id;
    private String version_code;
    private String af_id;
    private JSONObject data_str;
    private String user_status;
    private Double limit_amount;

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public Double getLimit_amount() {
        return limit_amount;
    }

    public void setLimit_amount(Double limit_amount) {
        this.limit_amount = limit_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getAf_id() {
        return af_id;
    }

    public void setAf_id(String af_id) {
        this.af_id = af_id;
    }

    public JSONObject getData_str() {
        return data_str;
    }

    public void setData_str(JSONObject data_str) {
        this.data_str = data_str;
    }
}

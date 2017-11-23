package com.alibaba.datax.plugin.writer.es5xwriter;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created on 2017/8/18.
 * @author lunatictwo
 */
public class SimpleTestEntity extends EsEntity {
    private String id;
    private Date create_time;
    private String uid;
    private JSONObject data_str;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public JSONObject getData_str() {
        return data_str;
    }

    public void setData_str(JSONObject data_str) {
        this.data_str = data_str;
    }
}

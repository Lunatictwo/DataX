package com.alibaba.datax.plugin.writer.es5xwriter;

/**
 * Created  on 2017/8/14.
 * @author lunatictwo
 */
public class EsEntity {
    /**
     * 显式声明transient 说明该字段不需要进行序列化，
     * 否则在使用Gson的时候会和子类的id产生冲突 但是子类也需要该字段
     * 一些敏感数据字段也可以通过该方法禁止进行序列化
     */
    private transient String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
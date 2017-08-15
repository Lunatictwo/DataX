package com.alibaba.datax.plugin.writer.es5xwriter;

/**
 * Created by zehui on 2017/8/15.
 */
public class Student extends ESEntity {
    private String id;
    private String column1;
    private String column2;
    private Integer column3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public Integer getColumn3() {
        return column3;
    }

    public void setColumn3(Integer column3) {
        this.column3 = column3;
    }
}

package com.treefinance.payment.batch.processing.premium;

import java.io.Serializable;

/**
 * @author lxp
 * @date 2019/10/24 下午5:15
 * @Version 1.0
 */
public class TradeBatchDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String batch_no;

    private String source;

    private String biz_type;

    private String biz_value;

    private Integer status;

    private String exception_log;

    private String raw_data;

    private Integer userId;

    private Boolean is_deprecated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(String biz_type) {
        this.biz_type = biz_type;
    }

    public String getBiz_value() {
        return biz_value;
    }

    public void setBiz_value(String biz_value) {
        this.biz_value = biz_value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getException_log() {
        return exception_log;
    }

    public void setException_log(String exception_log) {
        this.exception_log = exception_log;
    }

    public String getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(String raw_data) {
        this.raw_data = raw_data;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIs_deprecated() {
        return is_deprecated;
    }

    public void setIs_deprecated(Boolean is_deprecated) {
        this.is_deprecated = is_deprecated;
    }
}

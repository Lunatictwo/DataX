package com.alibaba.datax.plugin.writer.es5xwriter;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zehui on 2017/8/15.
 */
public class DataLineItemEntity extends ESEntity {
    private String id;
    private String uid;
    private String type;
    private String sku_id;
    private Integer qty;
    private String vendor_id;
    private String po_id;
    private String so_id;
    private Integer periods;
    private BigDecimal item_down_payment;
    private BigDecimal monthly_installment_payment;
    private Integer sales_status;
    private String address_id;
    private Integer purchase_status;
    private BigDecimal price;
    private String act_id;
    private Date item_create_time;
    private Date item_update_time;
    private Integer payoff_status;
    private String item_name;
    private BigDecimal refund_amount;
    private BigDecimal supply_price;
    private Date item_cancel_time;
    private BigDecimal settlement_price;
    private String variation;
    private String coupon_id;
    private Date item_accept_time;
    private Integer user_role;
    private String country_id;
    private String first_name;
    private Date birth_date;
    private Integer gender;
    private String province;
    private String city;
    private String street;
    private Integer occupation;
    private Integer education_level;
    private String referrer;
    private Integer user_status;
    private Date user_create_time;
    private Date user_last_login_time;
    private Date user_active_time;
    private String user_channel;
    private Date user_application_time;
    private Integer user_call_status;
    private BigDecimal po_down_payment;
    private BigDecimal po_credit;
    private String pay_method;
    private String third_party_transaction_id;
    private Integer po_status;
    private Date po_create_time;
    private Date po_fail_time;
    private Date po_success_time;
    private String pay_method_id;
    private String device_id;
    private String device_model;
    private Double latitude;
    private Double longitude;
    private Double freight;
    private Integer coupon_type;
    private Double coupon_discount;
    private BigDecimal coupon_from_currency;
    private Integer coupon_action;
    private BigDecimal coupon_trigger_amount;
    private Date coupon_create_time;
    private Date coupon_update_time;
    private String coupon_status;
    private Boolean address_is_default;
    private Integer address_status;
    private Date address_create_time;
    private String address_area_id;
    private Date so_delivery_time;
    private Date so_create_time;
    private String so_tracking_num;
    private Date so_received_time;
    private String so_logistics;
    private Double so_fee;
    private String so_lc_id;
    private Integer item_sku_status;
    private String cr_tree_id;
    private String cr_parent_id;
    private Integer cr_level;
    private String category_name;
    private String category_type;
    private String cvs_name;
    private Integer daigou_shop;
    private String daigou_category;
    private String elevenia_id;
    private BigDecimal ele_price;
    private BigDecimal ele_freight;
    private BigDecimal ele_insurance;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getSo_id() {
        return so_id;
    }

    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public BigDecimal getItem_down_payment() {
        return item_down_payment;
    }

    public void setItem_down_payment(BigDecimal item_down_payment) {
        this.item_down_payment = item_down_payment;
    }

    public BigDecimal getMonthly_installment_payment() {
        return monthly_installment_payment;
    }

    public void setMonthly_installment_payment(BigDecimal monthly_installment_payment) {
        this.monthly_installment_payment = monthly_installment_payment;
    }

    public Integer getSales_status() {
        return sales_status;
    }

    public void setSales_status(Integer sales_status) {
        this.sales_status = sales_status;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public Integer getPurchase_status() {
        return purchase_status;
    }

    public void setPurchase_status(Integer purchase_status) {
        this.purchase_status = purchase_status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAct_id() {
        return act_id;
    }

    public void setAct_id(String act_id) {
        this.act_id = act_id;
    }

    public Date getItem_create_time() {
        return item_create_time;
    }

    public void setItem_create_time(Date item_create_time) {
        this.item_create_time = item_create_time;
    }

    public Date getItem_update_time() {
        return item_update_time;
    }

    public void setItem_update_time(Date item_update_time) {
        this.item_update_time = item_update_time;
    }

    public Integer getPayoff_status() {
        return payoff_status;
    }

    public void setPayoff_status(Integer payoff_status) {
        this.payoff_status = payoff_status;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public BigDecimal getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(BigDecimal refund_amount) {
        this.refund_amount = refund_amount;
    }

    public BigDecimal getSupply_price() {
        return supply_price;
    }

    public void setSupply_price(BigDecimal supply_price) {
        this.supply_price = supply_price;
    }

    public Date getItem_cancel_time() {
        return item_cancel_time;
    }

    public void setItem_cancel_time(Date item_cancel_time) {
        this.item_cancel_time = item_cancel_time;
    }

    public BigDecimal getSettlement_price() {
        return settlement_price;
    }

    public void setSettlement_price(BigDecimal settlement_price) {
        this.settlement_price = settlement_price;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Date getItem_accept_time() {
        return item_accept_time;
    }

    public void setItem_accept_time(Date item_accept_time) {
        this.item_accept_time = item_accept_time;
    }

    public Integer getUser_role() {
        return user_role;
    }

    public void setUser_role(Integer user_role) {
        this.user_role = user_role;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public Integer getEducation_level() {
        return education_level;
    }

    public void setEducation_level(Integer education_level) {
        this.education_level = education_level;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public Date getUser_create_time() {
        return user_create_time;
    }

    public void setUser_create_time(Date user_create_time) {
        this.user_create_time = user_create_time;
    }

    public Date getUser_last_login_time() {
        return user_last_login_time;
    }

    public void setUser_last_login_time(Date user_last_login_time) {
        this.user_last_login_time = user_last_login_time;
    }

    public Date getUser_active_time() {
        return user_active_time;
    }

    public void setUser_active_time(Date user_active_time) {
        this.user_active_time = user_active_time;
    }

    public String getUser_channel() {
        return user_channel;
    }

    public void setUser_channel(String user_channel) {
        this.user_channel = user_channel;
    }

    public Date getUser_application_time() {
        return user_application_time;
    }

    public void setUser_application_time(Date user_application_time) {
        this.user_application_time = user_application_time;
    }

    public Integer getUser_call_status() {
        return user_call_status;
    }

    public void setUser_call_status(Integer user_call_status) {
        this.user_call_status = user_call_status;
    }

    public BigDecimal getPo_down_payment() {
        return po_down_payment;
    }

    public void setPo_down_payment(BigDecimal po_down_payment) {
        this.po_down_payment = po_down_payment;
    }

    public BigDecimal getPo_credit() {
        return po_credit;
    }

    public void setPo_credit(BigDecimal po_credit) {
        this.po_credit = po_credit;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public String getThird_party_transaction_id() {
        return third_party_transaction_id;
    }

    public void setThird_party_transaction_id(String third_party_transaction_id) {
        this.third_party_transaction_id = third_party_transaction_id;
    }

    public Integer getPo_status() {
        return po_status;
    }

    public void setPo_status(Integer po_status) {
        this.po_status = po_status;
    }

    public Date getPo_create_time() {
        return po_create_time;
    }

    public void setPo_create_time(Date po_create_time) {
        this.po_create_time = po_create_time;
    }

    public Date getPo_fail_time() {
        return po_fail_time;
    }

    public void setPo_fail_time(Date po_fail_time) {
        this.po_fail_time = po_fail_time;
    }

    public Date getPo_success_time() {
        return po_success_time;
    }

    public void setPo_success_time(Date po_success_time) {
        this.po_success_time = po_success_time;
    }

    public String getPay_method_id() {
        return pay_method_id;
    }

    public void setPay_method_id(String pay_method_id) {
        this.pay_method_id = pay_method_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Integer getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(Integer coupon_type) {
        this.coupon_type = coupon_type;
    }

    public Double getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(Double coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public BigDecimal getCoupon_from_currency() {
        return coupon_from_currency;
    }

    public void setCoupon_from_currency(BigDecimal coupon_from_currency) {
        this.coupon_from_currency = coupon_from_currency;
    }

    public Integer getCoupon_action() {
        return coupon_action;
    }

    public void setCoupon_action(Integer coupon_action) {
        this.coupon_action = coupon_action;
    }

    public BigDecimal getCoupon_trigger_amount() {
        return coupon_trigger_amount;
    }

    public void setCoupon_trigger_amount(BigDecimal coupon_trigger_amount) {
        this.coupon_trigger_amount = coupon_trigger_amount;
    }

    public Date getCoupon_create_time() {
        return coupon_create_time;
    }

    public void setCoupon_create_time(Date coupon_create_time) {
        this.coupon_create_time = coupon_create_time;
    }

    public Date getCoupon_update_time() {
        return coupon_update_time;
    }

    public void setCoupon_update_time(Date coupon_update_time) {
        this.coupon_update_time = coupon_update_time;
    }

    public String getCoupon_status() {
        return coupon_status;
    }

    public void setCoupon_status(String coupon_status) {
        this.coupon_status = coupon_status;
    }

    public Boolean getAddress_is_default() {
        return address_is_default;
    }

    public void setAddress_is_default(Boolean address_is_default) {
        this.address_is_default = address_is_default;
    }

    public Integer getAddress_status() {
        return address_status;
    }

    public void setAddress_status(Integer address_status) {
        this.address_status = address_status;
    }

    public Date getAddress_create_time() {
        return address_create_time;
    }

    public void setAddress_create_time(Date address_create_time) {
        this.address_create_time = address_create_time;
    }

    public String getAddress_area_id() {
        return address_area_id;
    }

    public void setAddress_area_id(String address_area_id) {
        this.address_area_id = address_area_id;
    }

    public Date getSo_delivery_time() {
        return so_delivery_time;
    }

    public void setSo_delivery_time(Date so_delivery_time) {
        this.so_delivery_time = so_delivery_time;
    }

    public Date getSo_create_time() {
        return so_create_time;
    }

    public void setSo_create_time(Date so_create_time) {
        this.so_create_time = so_create_time;
    }

    public String getSo_tracking_num() {
        return so_tracking_num;
    }

    public void setSo_tracking_num(String so_tracking_num) {
        this.so_tracking_num = so_tracking_num;
    }

    public Date getSo_received_time() {
        return so_received_time;
    }

    public void setSo_received_time(Date so_received_time) {
        this.so_received_time = so_received_time;
    }

    public String getSo_logistics() {
        return so_logistics;
    }

    public void setSo_logistics(String so_logistics) {
        this.so_logistics = so_logistics;
    }

    public Double getSo_fee() {
        return so_fee;
    }

    public void setSo_fee(Double so_fee) {
        this.so_fee = so_fee;
    }

    public String getSo_lc_id() {
        return so_lc_id;
    }

    public void setSo_lc_id(String so_lc_id) {
        this.so_lc_id = so_lc_id;
    }

    public Integer getItem_sku_status() {
        return item_sku_status;
    }

    public void setItem_sku_status(Integer item_sku_status) {
        this.item_sku_status = item_sku_status;
    }

    public String getCr_tree_id() {
        return cr_tree_id;
    }

    public void setCr_tree_id(String cr_tree_id) {
        this.cr_tree_id = cr_tree_id;
    }

    public String getCr_parent_id() {
        return cr_parent_id;
    }

    public void setCr_parent_id(String cr_parent_id) {
        this.cr_parent_id = cr_parent_id;
    }

    public Integer getCr_level() {
        return cr_level;
    }

    public void setCr_level(Integer cr_level) {
        this.cr_level = cr_level;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public String getCvs_name() {
        return cvs_name;
    }

    public void setCvs_name(String cvs_name) {
        this.cvs_name = cvs_name;
    }

    public Integer getDaigou_shop() {
        return daigou_shop;
    }

    public void setDaigou_shop(Integer daigou_shop) {
        this.daigou_shop = daigou_shop;
    }

    public String getDaigou_category() {
        return daigou_category;
    }

    public void setDaigou_category(String daigou_category) {
        this.daigou_category = daigou_category;
    }

    public String getElevenia_id() {
        return elevenia_id;
    }

    public void setElevenia_id(String elevenia_id) {
        this.elevenia_id = elevenia_id;
    }

    public BigDecimal getEle_price() {
        return ele_price;
    }

    public void setEle_price(BigDecimal ele_price) {
        this.ele_price = ele_price;
    }

    public BigDecimal getEle_freight() {
        return ele_freight;
    }

    public void setEle_freight(BigDecimal ele_freight) {
        this.ele_freight = ele_freight;
    }

    public BigDecimal getEle_insurance() {
        return ele_insurance;
    }

    public void setEle_insurance(BigDecimal ele_insurance) {
        this.ele_insurance = ele_insurance;
    }
}

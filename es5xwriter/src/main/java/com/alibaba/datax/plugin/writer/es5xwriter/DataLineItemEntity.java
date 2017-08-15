package com.alibaba.datax.plugin.writer.es5xwriter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zehui on 2017/8/15.
 */
public class DataLineItemEntity extends ESEntity {
    private String id;
    private String uid;
    private String sku_id;
    private int qty;
    private String vendor_id;
    private String po_id;
    private String so_id;
    private int periods;
    private BigDecimal item_down_payment;
    private BigDecimal monthly_installment_payment;
    private int sales_status;
    private String address_id;
    private int purchase_status;
    private BigDecimal price;
    private String act_id;
    private Date item_create_time;
    private Date item_update_time;
    private int payoff_status;
    private String item_name;
    private BigDecimal refund_amount;
    private BigDecimal supply_price;
    private Date item_cancel_time;
    private BigDecimal settlement_price;
    private String variation;
    private String coupon_id;
    private Date item_accept_time;
    private int user_role;
    private String country_id;
    private String first_name;
    private Date birth_date;
    private int gender;
    private String province;
    private String city;
    private String street;
    private int occupation;
    private int education_level;
    private String referrer;
    private int user_status;
    private Date user_create_time;
    private Date user_last_login_time;
    private Date user_active_time;
    private String channel;
    private Date user_application_time;
    private int user_call_status;
    private BigDecimal po_down_payment;
    private BigDecimal po_credit;
    private String pay_method;
    private String third_party_transaction_id;
    private int po_status;
    private Date po_create_time;
    private Date po_fail_time;
    private Date po_success_time;
    private String pay_method_id;
    private String device_id;
    private String device_model;
    private double latitude;
    private double longitude;
    private double freight;
    private int coupon_type;
    private double coupon_discount;
    private BigDecimal coupon_from_currency;
    private int coupon_action;
    private BigDecimal coupon_trigger_amount;
    private int address_is_default;
    private int address_status;
    private Date address_create_time;
    private String address_area_id;
    private int so_status;
    private Date so_delivery_time;
    private Date so_create_time;
    private String so_tracking_num;
    private Date so_received_time;
    private String so_logistics;
    private double so_fee;
    private String so_lc_id;
    private int item_sku_status;
    private String cr_tree_id;
    private String cr_parent_id;
    private int cr_level;
    private String cvs_name;
    private int daigou_shop;
    private String daigou_category;
    private String elevenia_id;
    private BigDecimal ele_price;
    private BigDecimal ele_freight;
    private BigDecimal ele_insurance;



}

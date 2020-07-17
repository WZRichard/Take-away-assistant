package model;

import java.util.Date;

public class view_order {
    private int Rider_id;
    private int Consumer_id;
    private int Order_id;
    private String Consumer_name;
    private String Merchandise_name;
    private int Merchandise_Count;
    private String Order_state;
    private String Rider_name;
    private Date Order_platime;
    private Date Order_deltime;
    private int Merchant_id;
    private String Address;
    private String Address_province;
    private String Address_city;
    private String Address_region;
    private String Address_add;
    private String Address_linkman;
    private String Address_phonenum;
    private String Merchant_name;
    private String Comment_Rider;
    private String starttime;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public int getConsumer_id() {
        return Consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        Consumer_id = consumer_id;
    }

    public int getRider_id() {
        return Rider_id;
    }

    public void setRider_id(int rider_id) {
        Rider_id = rider_id;
    }


    public String getComment_Rider() {
        return Comment_Rider;
    }

    public void setComment_Rider(String comment_Rider) {
        Comment_Rider = comment_Rider;
    }

    public String getMerchant_name() {
        return Merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        Merchant_name = merchant_name;
    }

    public String getAddress_province() {
        return Address_province;
    }

    public void setAddress_province(String address_province) {
        Address_province = address_province;
    }

    public String getAddress_city() {
        return Address_city;
    }

    public void setAddress_city(String address_city) {
        Address_city = address_city;
    }

    public String getAddress_region() {
        return Address_region;
    }

    public void setAddress_region(String address_region) {
        Address_region = address_region;
    }

    public String getAddress_add() {
        return Address_add;
    }

    public void setAddress_add(String address_add) {
        Address_add = address_add;
    }

    public String getAddress_linkman() {
        return Address_linkman;
    }

    public void setAddress_linkman(String address_linkman) {
        Address_linkman = address_linkman;
    }

    public String getAddress_phonenum() {
        return Address_phonenum;
    }

    public void setAddress_phonenum(String address_phonenum) {
        Address_phonenum = address_phonenum;
    }

    public int getMerchant_id() {
        return Merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        Merchant_id = merchant_id;
    }

    public int getMerchandise_Count() {
        return Merchandise_Count;
    }

    public void setMerchandise_Count(int merchandise_Count) {
        Merchandise_Count = merchandise_Count;
    }

    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public String getConsumer_name() {
        return Consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        Consumer_name = consumer_name;
    }

    public String getMerchandise_name() {
        return Merchandise_name;
    }

    public void setMerchandise_name(String merchandise_name) {
        Merchandise_name = merchandise_name;
    }

    public String getOrder_state() {
        return Order_state;
    }

    public void setOrder_state(String order_state) {
        Order_state = order_state;
    }

    public String getRider_name() {
        return Rider_name;
    }

    public void setRider_name(String rider_name) {
        Rider_name = rider_name;
    }

    public Date getOrder_platime() {
        return Order_platime;
    }

    public void setOrder_platime(Date order_platime) {
        Order_platime = order_platime;
    }

    public Date getOrder_deltime() {
        return Order_deltime;
    }

    public void setOrder_deltime(Date order_deltime) {
        Order_deltime = order_deltime;
    }
}

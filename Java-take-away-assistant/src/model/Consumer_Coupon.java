package model;

import java.util.Date;

public class Consumer_Coupon {
    private int Consumer_id;
    private String Coupon_name;
    private Float Coupon_amount;
    private int Coupon_aim;
    private int Coupon_now;
    private Date Starttime;
    private Date Finishtime;
    private String Support_coupon;

    public int getConsumer_id() {
        return Consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        Consumer_id = consumer_id;
    }

    public String getSupport_coupon() {
        return Support_coupon;
    }

    public void setSupport_coupon(String support_coupon) {
        Support_coupon = support_coupon;
    }

    public Date getStarttime() {
        return Starttime;
    }

    public void setStarttime(Date starttime) {
        Starttime = starttime;
    }

    public Date getFinishtime() {
        return Finishtime;
    }

    public void setFinishtime(Date finishtime) {
        Finishtime = finishtime;
    }

    public Consumer_Coupon(){

    }

    public String getCoupon_name() {
        return Coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        Coupon_name = coupon_name;
    }

    public Float getCoupon_amount() {
        return Coupon_amount;
    }

    public void setCoupon_amount(Float coupon_amount) {
        Coupon_amount = coupon_amount;
    }

    public int getCoupon_aim() {
        return Coupon_aim;
    }

    public void setCoupon_aim(int coupon_aim) {
        Coupon_aim = coupon_aim;
    }

    public int getCoupon_now() {
        return Coupon_now;
    }

    public void setCoupon_now(int coupon_now) {
        Coupon_now = coupon_now;
    }
}

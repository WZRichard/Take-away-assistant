package model;

import java.util.Date;

public class Discount_coupon {
    private int Coupon_id;
    private int Merchant_id;
    private Float Discount_price;
    private int Order_count;
    private Date Starttime;
    private Date Finishtime;

    public Discount_coupon() {

    }

    public int getCoupon_id() {
        return Coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        Coupon_id = coupon_id;
    }

    public int getMerchant_id() {
        return Merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        Merchant_id = merchant_id;
    }

    public Float getDiscount_price() {
        return Discount_price;
    }

    public void setDiscount_price(Float discount_price) {
        Discount_price = discount_price;
    }

    public int getOrder_count() {
        return Order_count;
    }

    public void setOrder_count(int order_count) {
        Order_count = order_count;
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
}

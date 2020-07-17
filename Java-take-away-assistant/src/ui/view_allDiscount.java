package ui;

public class view_allDiscount {
    private int Consumer_id;
    private int Merchant_id;
    private Float Red_Amount;
    private Float Red_Aim;
    private Float Discount_price;
    private int Order_count;

    public int getOrder_count() {
        return Order_count;
    }

    public void setOrder_count(int order_count) {
        Order_count = order_count;
    }

    public int getConsumer_id() {
        return Consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        Consumer_id = consumer_id;
    }

    public int getMerchant_id() {
        return Merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        Merchant_id = merchant_id;
    }

    public Float getRed_Amount() {
        return Red_Amount;
    }

    public void setRed_Amount(Float red_Amount) {
        Red_Amount = red_Amount;
    }

    public Float getRed_Aim() {
        return Red_Aim;
    }

    public void setRed_Aim(Float red_Aim) {
        Red_Aim = red_Aim;
    }

    public Float getDiscount_price() {
        return Discount_price;
    }

    public void setDiscount_price(Float discount_price) {
        Discount_price = discount_price;
    }
}

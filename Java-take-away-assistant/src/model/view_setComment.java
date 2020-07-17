package model;

public class view_setComment {
    private String Merchant_name;
    private String Merchandise_name;
    private String starttime;
    private int Consumer_id;
    private int Order_id;
    private int Merchandise_id;

    public view_setComment() {

    }

    public int getMerchandise_id() {
        return Merchandise_id;
    }

    public void setMerchandise_id(int merchandise_id) {
        Merchandise_id = merchandise_id;
    }

    public String getMerchant_name() {
        return Merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        Merchant_name = merchant_name;
    }

    public int getConsumer_id() {
        return Consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        Consumer_id = consumer_id;
    }

    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public String getMerchandise_name() {
        return Merchandise_name;
    }

    public void setMerchandise_name(String merchandise_name) {
        Merchandise_name = merchandise_name;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
}

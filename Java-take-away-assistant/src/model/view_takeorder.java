package model;

public class view_takeorder {
    private int merchant_id;
    private String merchant_name;
    private int Merchant_level;
    private Float Consume_avgprice;
    private int Total_sales;
    private int merchandise_id;
    private String merchandise_name;
    private Float Merchandise_price;
    private Float Disconut_price;
    private String Sort_name;
    private int count;

    public view_takeorder() {
        this.count = 0;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public int getMerchant_level() {
        return Merchant_level;
    }

    public void setMerchant_level(int merchant_level) {
        Merchant_level = merchant_level;
    }

    public Float getConsume_avgprice() {
        return Consume_avgprice;
    }

    public void setConsume_avgprice(Float consume_avgprice) {
        Consume_avgprice = consume_avgprice;
    }

    public int getTotal_sales() {
        return Total_sales;
    }

    public void setTotal_sales(int total_sales) {
        Total_sales = total_sales;
    }

    public int getMerchandise_id() {
        return merchandise_id;
    }

    public void setMerchandise_id(int merchandise_id) {
        this.merchandise_id = merchandise_id;
    }

    public String getMerchandise_name() {
        return merchandise_name;
    }

    public void setMerchandise_name(String merchandise_name) {
        this.merchandise_name = merchandise_name;
    }

    public Float getMerchandise_price() {
        return Merchandise_price;
    }

    public void setMerchandise_price(Float merchandise_price) {
        Merchandise_price = merchandise_price;
    }

    public Float getDisconut_price() {
        return Disconut_price;
    }

    public void setDisconut_price(Float disconut_price) {
        Disconut_price = disconut_price;
    }

    public String getSort_name() {
        return Sort_name;
    }

    public void setSort_name(String sort_name) {
        Sort_name = sort_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

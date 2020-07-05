package model;

public class Merchant_information {
    private int Merchant_id;
    private String Merchant_pwd;
    private String Merchant_name;
    private int Merchant_level;
    private float Consume_avgprice;
    private int Total_sales;

    public int getMerchant_id() {
        return Merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        Merchant_id = merchant_id;
    }

    public String getMerchant_pwd() {
        return Merchant_pwd;
    }

    public void setMerchant_pwd(String merchant_pwd) {
        Merchant_pwd = merchant_pwd;
    }

    public String getMerchant_name() {
        return Merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        Merchant_name = merchant_name;
    }

    public int getMerchant_level() {
        return Merchant_level;
    }

    public void setMerchant_level(int merchant_level) {
        Merchant_level = merchant_level;
    }

    public float getConsume_avgprice() {
        return Consume_avgprice;
    }

    public void setConsume_avgprice(float consume_avgprice) {
        Consume_avgprice = consume_avgprice;
    }

    public int getTotal_sales() {
        return Total_sales;
    }

    public void setTotal_sales(int total_sales) {
        Total_sales = total_sales;
    }
}

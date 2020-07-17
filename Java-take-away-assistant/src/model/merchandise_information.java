package model;

public class merchandise_information {
    private int Merchandise_id;
    private int Merchant_id;
    private int Sort_id;
    private String Merchandise_name;
    private Float Merchandise_price;
    private Float Disconut_price;

    public int getMerchant_id() {
        return Merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        Merchant_id = merchant_id;
    }

    public int getMerchandise_id() {
        return Merchandise_id;
    }

    public void setMerchandise_id(int merchandise_id) {
        Merchandise_id = merchandise_id;
    }

    public int getSort_id() {
        return Sort_id;
    }

    public void setSort_id(int sort_id) {
        Sort_id = sort_id;
    }

    public String getMerchandise_name() {
        return Merchandise_name;
    }

    public void setMerchandise_name(String merchandise_name) {
        Merchandise_name = merchandise_name;
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
}

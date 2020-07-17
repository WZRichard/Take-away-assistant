package model;

public class delivery_address {
    private int Address_id;
    private int Consumer_id;
    private String Address_province;
    private String Address_city;
    private String Address_region;
    private String Address_add;
    private String Address_linkman;
    private String Address_phonenum;
    private Boolean Default_;

    public delivery_address() {

    }

    public int getAddress_id() {
        return Address_id;
    }

    public void setAddress_id(int address_id) {
        Address_id = address_id;
    }

    public int getConsumer_id() {
        return Consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        Consumer_id = consumer_id;
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

    public Boolean getDefault_() {
        return Default_;
    }

    public void setDefault_(Boolean default_) {
        Default_ = default_;
    }
}

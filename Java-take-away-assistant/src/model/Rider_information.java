package model;

import java.util.Date;

public class Rider_information {
    private int Rider_id;
    private String Rider_name;
    private String Rider_pwd;
    private Date Reg_date;
    private String Rider_sort;

    public Rider_information(){

    }

    public int getRider_id() {
        return Rider_id;
    }

    public void setRider_id(int rider_id) {
        Rider_id = rider_id;
    }

    public String getRider_name() {
        return Rider_name;
    }

    public void setRider_name(String rider_name) {
        Rider_name = rider_name;
    }

    public String getRider_pwd() {
        return Rider_pwd;
    }

    public void setRider_pwd(String rider_pwd) {
        Rider_pwd = rider_pwd;
    }

    public Date getEntry_date() {
        return Reg_date;
    }

    public void setEntry_date(Date entry_date) {
        Reg_date = entry_date;
    }

    public String getRider_sort() {
        return Rider_sort;
    }

    public void setRider_sort(String rider_sort) {
        Rider_sort = rider_sort;
    }
}

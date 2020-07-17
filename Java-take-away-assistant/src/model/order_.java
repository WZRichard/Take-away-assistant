package model;

public class order_ {
    private int Order_id;
    private int Address_id;
    private int Rider_id;
    private int Consumer_id;
    private String Order_state;
    private String Comment_Rider;
    private String Salary_Rider;

    public order_ () {

    }

    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public int getAddress_id() {
        return Address_id;
    }

    public void setAddress_id(int address_id) {
        Address_id = address_id;
    }

    public int getRider_id() {
        return Rider_id;
    }

    public void setRider_id(int rider_id) {
        Rider_id = rider_id;
    }

    public int getConsumer_id() {
        return Consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        Consumer_id = consumer_id;
    }

    public String getOrder_state() {
        return Order_state;
    }

    public void setOrder_state(String order_state) {
        Order_state = order_state;
    }

    public String getComment_Rider() {
        return Comment_Rider;
    }

    public void setComment_Rider(String comment_Rider) {
        Comment_Rider = comment_Rider;
    }

    public String getSalary_Rider() {
        return Salary_Rider;
    }

    public void setSalary_Rider(String salary_Rider) {
        Salary_Rider = salary_Rider;
    }
}

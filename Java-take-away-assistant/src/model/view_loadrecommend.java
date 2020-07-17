package model;

import javafx.scene.image.Image;

import java.util.Date;

public class view_loadrecommend {
    private Image Comment_picture;
    private int Comment_level;
    private String Comment_content;
    private Date Comment_Date;
    private int Order_id;
    private String Merchant_name;
    private String Consumer_name;
    private String Merchandise_name;

    public view_loadrecommend() {

    }
    public String getMerchandise_name() {
        return Merchandise_name;
    }

    public void setMerchandise_name(String merchandise_name) {
        Merchandise_name = merchandise_name;
    }

    public Image getComment_picture() {
        return Comment_picture;
    }

    public void setComment_picture(Image comment_picture) {
//        comment_picture.
        Comment_picture = comment_picture;
    }

    public int getComment_level() {
        return Comment_level;
    }

    public void setComment_level(int comment_level) {
        Comment_level = comment_level;
    }

    public String getComment_content() {
        return Comment_content;
    }

    public void setComment_content(String comment_content) {
        Comment_content = comment_content;
    }

    public Date getComment_Date() {
        return Comment_Date;
    }

    public void setComment_Date(Date comment_Date) {
        Comment_Date = comment_Date;
    }

    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public String getMerchant_name() {
        return Merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        Merchant_name = merchant_name;
    }

    public String getConsumer_name() {
        return Consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        Consumer_name = consumer_name;
    }
}

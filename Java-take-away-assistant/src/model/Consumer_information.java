package model;

import java.util.Date;

public class Consumer_information {
    private int Consumer_id;
    private String Consumer_name;
    private String Consumer_pwd;
    private String Consumer_gender; //1ÄÐÈË 2Å®ÈË
    private String Consumer_phonenum;
    private String Consumer_mail;
    private String Consumer_city;
    private Date Consumer_regdate;
    private Boolean Consumer_member;
    private Date Consumer_memberddl;

    public int getConsumer_id() {
        return Consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        Consumer_id = consumer_id;
    }

    public String getConsumer_name() {
        return Consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        Consumer_name = consumer_name;
    }

    public String getConsumer_pwd() {
        return Consumer_pwd;
    }

    public void setConsumer_pwd(String consumer_pwd) {
        Consumer_pwd = consumer_pwd;
    }

    public String getConsumer_gender() {
        return Consumer_gender;
    }

    public void setConsumer_gender(String consumer_gender) {
        Consumer_gender = consumer_gender;
    }

    public String getConsumer_phonenum() {
        return Consumer_phonenum;
    }

    public void setConsumer_phonenum(String consumer_phonenum) {
        Consumer_phonenum = consumer_phonenum;
    }

    public String getConsumer_mail() {
        return Consumer_mail;
    }

    public void setConsumer_mail(String consumer_mail) {
        Consumer_mail = consumer_mail;
    }

    public String getConsumer_city() {
        return Consumer_city;
    }

    public void setConsumer_city(String consumer_city) {
        Consumer_city = consumer_city;
    }

    public Date getConsumer_regdate() {
        return Consumer_regdate;
    }

    public void setConsumer_regdate(Date consumer_regdate) {
        Consumer_regdate = consumer_regdate;
    }

    public Boolean getConsumer_member() {
        return Consumer_member;
    }

    public void setConsumer_member(Boolean consumer_member) {
        Consumer_member = consumer_member;
    }

    public Date getConsumer_memberddl() {
        return Consumer_memberddl;
    }

    public void setConsumer_memberddl(Date consumer_memberddl) {
        Consumer_memberddl = consumer_memberddl;
    }
}

package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import ui.FrmMainLogin;
import ui.FrmMerchant.Mer_information;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Admin {

    public ObservableList<view_order> loadAllOrder() throws BaseException {
        ArrayList<view_order> result = new ArrayList<view_order>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select distinct Order_id, Consumer_name, Merchant_name, Rider_id, Order_state, Order_platime, Order_deltime, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Comment_Rider" +
                    " from view_order";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                view_order inf = new view_order();
                inf.setOrder_id(rs.getInt(1));
                inf.setConsumer_name(rs.getString(2));
                inf.setMerchant_name(rs.getString(3));
                inf.setRider_name(rs.getInt(4)==0 ? "未接单" : String.valueOf(rs.getInt(4)));
                inf.setOrder_state(rs.getString(5));
                inf.setOrder_platime(rs.getTimestamp(6));
                inf.setOrder_deltime(rs.getTimestamp(7));
                inf.setAddress(rs.getString(8)+"-"+rs.getString(9)+"-"+rs.getString(10));
                inf.setAddress_linkman(rs.getString(11));
                inf.setAddress_phonenum(rs.getString(12));
                inf.setComment_Rider(rs.getString(13));
                result.add(inf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return FXCollections.observableArrayList(result);
    }

    public view_order queryOrder (int Order_id) throws BaseException {
        Connection conn = null;
        view_order inf = new view_order();
        try {
            conn = DBUtil.getConnection();
            String sql = "select distinct Order_id, Consumer_name, Merchant_name, Rider_id, Order_state, Order_platime, Order_deltime, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Comment_Rider" +
                    " from view_order where Order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Order_id);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("未查找到该订单！");
            inf.setOrder_id(rs.getInt(1));
            inf.setConsumer_name(rs.getString(2));
            inf.setMerchant_name(rs.getString(3));
            inf.setRider_name(rs.getInt(4)==0 ? "未接单" : String.valueOf(rs.getInt(4)));
            inf.setOrder_state(rs.getString(5));
            inf.setOrder_platime(rs.getTimestamp(6));
            inf.setOrder_deltime(rs.getTimestamp(7));
            inf.setAddress(rs.getString(8)+"-"+rs.getString(9)+"-"+rs.getString(10));
            inf.setAddress_linkman(rs.getString(11));
            inf.setAddress_phonenum(rs.getString(12));
            inf.setComment_Rider(rs.getString(13));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return inf;
    }

    public void setmember(int id, String name, String date) throws BaseException {
        Connection conn = null;
        SimpleDateFormat formatime = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date datee = formatime.parse(date);
            conn = DBUtil.getConnection();
            String sql = "select * from consumer_information where Consumer_id = ? and Consumer_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, name);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("顾客编号或姓名输入有误！");

            sql = "update consumer_information set Consumer_member = true, Consumer_memberddl = ? where Consumer_id = ?";
            pst = conn.prepareStatement(sql);
            System.out.println(datee);
            pst.setDate(1, new java.sql.Date (datee.getTime()));
            pst.setInt(2, id);
            pst.executeUpdate();

            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "时间格式应为 yyyy-MM-dd", "错误",JOptionPane.ERROR_MESSAGE);
            return ;
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public ObservableList<Consumer_information> loadConinf() throws BaseException {
        ArrayList<Consumer_information> inf = new ArrayList<Consumer_information>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Consumer_id, Consumer_name, Consumer_gender, Consumer_pwd, Consumer_phonenum, Consumer_mail, Consumer_city, Consumer_regdate, Consumer_member, Consumer_memberddl " +
                    "from Consumer_information";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Consumer_information coninf = new Consumer_information();
                coninf.setConsumer_id(rs.getInt(1));
                coninf.setConsumer_name(rs.getString(2));
                coninf.setConsumer_gender(rs.getString(3));
                coninf.setConsumer_pwd(rs.getString(4));
                coninf.setConsumer_phonenum(rs.getString(5));
                coninf.setConsumer_mail(rs.getString(6));
                coninf.setConsumer_city(rs.getString(7));
                coninf.setConsumer_regdate(rs.getTimestamp(8));
                coninf.setConsumer_member(rs.getBoolean(9));
                coninf.setConsumer_memberddl(rs.getTimestamp(10));
                inf.add(coninf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return FXCollections.observableArrayList(inf);
    }

    public Consumer_information queryConsumerinf(int consid) throws BaseException {
        Connection conn = null;
        Consumer_information coninf = new Consumer_information();
        try {
            conn = DBUtil.getConnection();
            String sql = "select Consumer_id, Consumer_name, Consumer_gender, Consumer_pwd, Consumer_phonenum, Consumer_mail, Consumer_city, Consumer_regdate, Consumer_member, Consumer_memberddl " +
                    "from Consumer_information where Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, consid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("未查找到该顾客！");
            coninf.setConsumer_id(rs.getInt(1));
            coninf.setConsumer_name(rs.getString(2));
            coninf.setConsumer_gender(rs.getString(3));
            coninf.setConsumer_pwd(rs.getString(4));
            coninf.setConsumer_phonenum(rs.getString(5));
            coninf.setConsumer_mail(rs.getString(6));
            coninf.setConsumer_city(rs.getString(7));
            coninf.setConsumer_regdate(rs.getTimestamp(8));
            coninf.setConsumer_member(rs.getBoolean(9));
            coninf.setConsumer_memberddl(rs.getTimestamp(10));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return coninf;
    }

    public ObservableList<Mer_information> loadMerinf() throws BaseException {
        ArrayList<Mer_information> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Merchant_id, Merchant_name, Merchant_level, Consume_avgprice, Total_sales, Merchant_pwd, Merchant_id from merchant_information";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mer_information inf = new Mer_information();
                inf.setId(rs.getInt(1));
                inf.setName(rs.getString(2));
                if (rs.getInt(3)!=0) inf.setLevel(rs.getInt(3));
                if (rs.getFloat(4)!=0) inf.setAvgprice(rs.getFloat(4));
                inf.setTotal_sales(rs.getInt(5));
                inf.setPwd(rs.getString(6));
                inf.setId(rs.getInt(7));
                result.add(inf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return FXCollections.observableArrayList(result);
    }

    public Mer_information queryMerinf(int mersid) throws BaseException {
        Connection conn = null;
        Mer_information inf = new Mer_information();
        try {
            conn = DBUtil.getConnection();
            String sql = "select Merchant_id, Merchant_name, Merchant_level, Consume_avgprice, Total_sales, Merchant_pwd, Merchant_id " +
                    "from merchant_information where Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, mersid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("不存在该商家！");
            inf.setId(rs.getInt(1));
            inf.setName(rs.getString(2));
            if (rs.getInt(3)!=0) inf.setLevel(rs.getInt(3));
            if (rs.getFloat(4)!=0) inf.setAvgprice(rs.getFloat(4));
            inf.setTotal_sales(rs.getInt(5));
            inf.setPwd(rs.getString(6));
            inf.setId(rs.getInt(7));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return inf;
    }

    public ObservableList<Rider_information> loadRiderinf() throws BaseException {
        ArrayList<Rider_information> inf = new ArrayList<Rider_information>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Rider_id, Rider_name, Reg_date, Rider_sort, Rider_pwd from rider_information";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Rider_information rider_information = new Rider_information();
                rider_information.setRider_id(rs.getInt(1));
                rider_information.setRider_name(rs.getString(2));
                rider_information.setEntry_date(rs.getTimestamp(3));
                rider_information.setRider_sort(rs.getString(4));
                rider_information.setRider_pwd(rs.getString(5));
                inf.add(rider_information);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return FXCollections.observableArrayList(inf);
    }

    public Rider_information queryRiderinf(int Ridersid) throws BaseException {
        Connection conn = null;
        Rider_information rider_information = new Rider_information();
        try {
            conn = DBUtil.getConnection();
            String sql = "select Rider_id, Rider_name, Reg_date, Rider_sort, Rider_pwd from rider_information where Rider_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Ridersid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("不存在该骑手！");
            rider_information.setRider_id(rs.getInt(1));
            rider_information.setRider_name(rs.getString(2));
            rider_information.setEntry_date(rs.getTimestamp(3));
            rider_information.setRider_sort(rs.getString(4));
            rider_information.setRider_pwd(rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return rider_information;
    }

}

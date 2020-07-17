package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Rider_information;
import model.view_order;
import ui.FrmMainLogin;
import ui.FrmMerchant;
import util.BaseException;
import util.DBUtil;

public class Rider {

    public void updateriderinf(Rider_information change_row, int col) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update rider_information set "+getinfcol_name(col)+" = "+getinfchange_data(change_row, col)+" where Rider_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.executeUpdate();
            pst.close();
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
    }

    public ObservableList<Rider_information> loadriderinf() throws BaseException{
        ArrayList<Rider_information> inf = new ArrayList<Rider_information>();
        Connection conn = null;
        try {
            Rider_information rider_information = new Rider_information();
            conn = DBUtil.getConnection();
            String sql = "select * from rider_information where Rider_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            rs.next();
            rider_information.setRider_id(rs.getInt(1));
            rider_information.setRider_name(rs.getString(2));
            rider_information.setEntry_date(rs.getTimestamp(3));
            rider_information.setRider_sort(rs.getString(4));
            rider_information.setRider_pwd(rs.getString(5));
            inf.add(rider_information);
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

    public ObservableList<view_order> loadgetorder() throws BaseException {
        ArrayList<view_order> inf = new ArrayList<view_order>();
        Connection conn = null;
        try {
            view_order rider_information = new view_order();
            conn = DBUtil.getConnection();
            String sql = "select distinct order_id, Address_province, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Merchant_name " +
                    "from view_order where Rider_id is null and Order_state = '等待配送'";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                rider_information.setOrder_id(rs.getInt(1));
                rider_information.setAddress_province(rs.getString(2));
                rider_information.setAddress_city(rs.getString(3));
                rider_information.setAddress_region(rs.getString(4));
                rider_information.setAddress_add(rs.getString(5));
                rider_information.setAddress_linkman(rs.getString(6));
                rider_information.setAddress_phonenum(rs.getString(7));
                rider_information.setMerchant_name(rs.getString(8));
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

    public void getorder(view_order inf) throws BaseException {
        Connection conn = null;
        try {
            view_order rider_information = new view_order();
            conn = DBUtil.getConnection();
            String sql = "update order_ set Order_state = '配送中', Rider_id = ? where Order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.setInt(2, inf.getOrder_id());
            pst.executeUpdate();
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
    }

    public ObservableList<view_order> loadhavingorder() throws BaseException {
        ArrayList<view_order> inf = new ArrayList<view_order>();
        Connection conn = null;
        try {
            view_order rider_information = new view_order();
            conn = DBUtil.getConnection();
            String sql = "select distinct order_id, Address_province, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Merchant_name " +
                    "from view_order where Rider_id = ? and Order_state = '配送中'";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                rider_information.setOrder_id(rs.getInt(1));
                rider_information.setAddress_province(rs.getString(2));
                rider_information.setAddress_city(rs.getString(3));
                rider_information.setAddress_region(rs.getString(4));
                rider_information.setAddress_add(rs.getString(5));
                rider_information.setAddress_linkman(rs.getString(6));
                rider_information.setAddress_phonenum(rs.getString(7));
                rider_information.setMerchant_name(rs.getString(8));
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

    public void deliveryOrder(view_order inf) throws BaseException {
        Connection conn = null;
        try {
            view_order rider_information = new view_order();
            conn = DBUtil.getConnection();
            String sql = "update order_ set Order_state = '已送达' where Order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
            pst.executeUpdate();
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
    }

    public String salary() throws BaseException {
        Double salary = 0.0;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select count(Order_id) from order_ where Order_state = '已送达' and Comment_Rider = '未评价' and Rider_id = ?  " +
                    "and Order_deltime >= (SELECT DATE_FORMAT(NOW(), '%Y-%m')) and Order_deltime < (SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 1 MONTH), '%Y-%m'))";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int medium = rs.getInt(1);

            sql = "select count(Order_id) from order_ where Order_state = '已送达' and Comment_Rider = '好评' and Rider_id = ? " +
                    "and Order_deltime >= (SELECT DATE_FORMAT(NOW(), '%Y-%m')) and Order_deltime < (SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 1 MONTH), '%Y-%m'))";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            rs = pst.executeQuery();
            rs.next();
            int good = rs.getInt(1);

            sql = "select count(Order_id) from order_ where Order_state = '已送达' and Comment_Rider = '差评' and Rider_id = ? " +
                    "and Order_deltime >= (SELECT DATE_FORMAT(NOW(), '%Y-%m')) and Order_deltime < (SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 1 MONTH), '%Y-%m'))";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            rs = pst.executeQuery();
            rs.next();
            int bad = rs.getInt(1);

            sql = "select Rider_sort from rider_information where Rider_id = ? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            rs = pst.executeQuery();
            rs.next();
//            System.out.println(rs.getString(1));
            int newRider = rs.getString(1).equals("新手") ? 1 : 0;

            int sum = medium + bad + good;
            if (sum<100) {
                salary = sum*2.0;
            } else if (sum<300) {
                salary = 200 + (sum-100)*3.0;
            } else if (sum<450) {
                salary = 800 + (sum-300)*5.0;
            } else if (sum<550) {
                salary = 1400 + (sum-300)*6.0;
            } else if (sum<650) {
                salary = 2000 + (sum-300)*7.0;
            } else {
                salary = 2700 + (sum-300)*8.0;
            }
//            System.out.println(newRider);
            if (newRider==1) {
                salary += sum*0.5;
            }
            if (sum > 500) {
                salary += (salary-500)*0.5;
            }
            salary = salary + good*0.5 - bad*20;
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
        if (salary<0) {
            salary = 0.0;
        }
        return String.valueOf(salary);
    }

    private String getinfcol_name(int i) {
        switch (i) {
            case 2:return "Rider_name";
            case 5:return "Rider_pwd";
        }
        return null;
    }

    private String getinfchange_data(Rider_information inf, int col) {
        switch (col){
            case 2: return "'" + inf.getRider_id() + "'";
            case 5: return "'"+ inf.getRider_pwd() + "'";
        }
        return null;
    }

    public ObservableList<view_order> comment() throws BaseException {
        ArrayList<view_order> inf = new ArrayList<view_order>();
        Connection conn = null;
        try {
            view_order rider_information = new view_order();
            conn = DBUtil.getConnection();
            String sql = "select distinct Merchant_name, Consumer_name, Order_state, Comment_Rider, Order_id from view_order " +
                    " where Rider_id = ? and Order_state = '已送达'";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                rider_information.setMerchant_name(rs.getString(1));
                rider_information.setConsumer_name(rs.getString(2));
                rider_information.setOrder_state(rs.getString(3));
                rider_information.setComment_Rider(rs.getString(4));
                rider_information.setOrder_id(rs.getInt(5));
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

    public void check() throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select count(*) from order_ where Rider_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int order_count = rs.getInt(1);

            if (order_count>10) {
                sql = "update rider_information set Rider_sort = '正式员工' where Rider_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, FrmMainLogin.Currentuserid);
                pst.executeUpdate();
            } else {
                return ;
            }

            sql = "select count(Order_id) from order_ where Order_state = '已送达' and Rider_id = ? " +
                    "and Order_deltime >= (SELECT DATE_FORMAT(NOW(), '%Y-%m')) and Order_deltime < (SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 1 MONTH), '%Y-%m'))";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            rs = pst.executeQuery();
            rs.next();
            int order_month = rs.getInt(1);

            if (order_month>500) {
                sql = "update rider_information set Rider_sort = '单王' where Rider_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, FrmMainLogin.Currentuserid);
                pst.executeUpdate();
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
    }
}

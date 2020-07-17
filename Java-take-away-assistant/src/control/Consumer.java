package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import model.*;
import ui.FrmMainLogin;
import ui.FrmMerchant;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import model.view_takeorder;
import ui.FrmMerchant.Merchandise_information;

import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Consumer {

    public void insertcomment(view_setComment inf, int level, String comment, FileInputStream picture) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into comment_(merchandise_id, consumer_id, order_id, comment_content, comment_date, comment_level, comment_picture) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getMerchandise_id());
            pst.setInt(2, FrmMainLogin.Currentuserid);
            pst.setInt(3, inf.getOrder_id());
            pst.setString(4, comment);
            pst.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(6, level);
            pst.setBinaryStream(7, picture);
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

    public ObservableList<view_setComment> loadComment() throws BaseException {
        ArrayList<view_setComment> result = new ArrayList<view_setComment>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Merchant_name, Merchandise_name, Order_platime, Order_id, Merchandise_id from view_setComment where Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                view_setComment inf = new view_setComment();
                inf.setMerchant_name(rs.getString(1));
                inf.setMerchandise_name(rs.getString(2));
                inf.setStarttime(new Merchant().DatetoString(rs.getTimestamp(3)));
                inf.setOrder_id(rs.getInt(4));
                inf.setMerchandise_id(rs.getInt(5));
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

    public ObservableList<view_order> loadRidercomment() throws BaseException {
        ArrayList<view_order> result = new ArrayList<view_order>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select DISTINCT Merchant_name, Rider_name, Comment_Rider, order_.order_id " +
                    "from merchant_information, rider_information, order_, order_detail, merchandise_information " +
                    "where order_.Rider_id = rider_information.Rider_id " +
                    "and order_detail.Order_id = order_.Order_id " +
                    "and merchandise_information.Merchandise_id = order_detail.Merchandise_id " +
                    "and merchant_information.Merchant_id = merchandise_information.Merchant_id " +
                    "and Order_state='已送达' and Comment_Rider = '未评价' and Consumer_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                view_order inf = new view_order();
                inf.setMerchant_name(rs.getString(1));
                inf.setRider_name(rs.getString(2));
                inf.setOrder_state("已送达");
                inf.setComment_Rider(rs.getString(3));
                inf.setOrder_id(rs.getInt(4));
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

    public void updateRidercomment(view_order inf) throws BaseException {
        if (!(inf.getComment_Rider().equals("好评") || inf.getComment_Rider().equals("差评"))) {
            throw new BusinessException("骑手评价只能填“好评”或“差评”");
        }
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update order_ set Comment_Rider = ? where Order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, inf.getComment_Rider());
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

    public ObservableList<Consumer_Coupon> loadCoupon() throws BaseException {
        ArrayList<Consumer_Coupon> result = new ArrayList<Consumer_Coupon>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Consumer_id, Merchant_name, Discount_price, Order_count, Collect_now, Starttime, Finishtime from view_havingcoupon where Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Consumer_Coupon inf = new Consumer_Coupon();
                inf.setConsumer_id(rs.getInt(1));
                inf.setCoupon_name(rs.getString(2));
                inf.setCoupon_amount(rs.getFloat(3));
                inf.setCoupon_aim(rs.getInt(4));
                inf.setCoupon_now(rs.getInt(5));
                inf.setStarttime(rs.getTimestamp(6));
                inf.setFinishtime(rs.getTimestamp(7));
//                if (rs.getBoolean(8)) {
//                    inf.setSupport_coupon("支持");
//                } else {
//                    inf.setSupport_coupon("不支持");
//                }
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

    public ObservableList<delivery_address> loadAddress() throws BaseException {
        ArrayList<delivery_address> result = new ArrayList<delivery_address>();
        Connection conn = null;
        try {
            delivery_address inf = new delivery_address();
            conn = DBUtil.getConnection();
            String sql = "select Address_id, Consumer_id, Address_province, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Default_ from delivery_address where Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            rs.next();
            inf.setAddress_id(rs.getInt(1));
            inf.setConsumer_id(rs.getInt(2));
            inf.setAddress_province(rs.getString(3));
            inf.setAddress_city(rs.getString(4));
            inf.setAddress_region(rs.getString(5));
            inf.setAddress_add(rs.getString(6));
            inf.setAddress_linkman(rs.getString(7));
            inf.setAddress_phonenum(rs.getString(8));
            inf.setDefault_(rs.getBoolean(9));
            result.add(inf);
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

    public void delectAddress(delivery_address inf) throws BaseException {
        Connection conn = null;
        if (inf==null) throw new BusinessException("未选择现有地址！");
        try {
            conn = DBUtil.getConnection();
            String sql = "update delivery_address set Consumer_id = 0 where Address_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getAddress_id());
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

    public void updateAddress(delivery_address change_row, int col) throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update delivery_address set "+get_address(col)+" = "+get_addressdata(change_row, col)+
                    " where Address_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, change_row.getAddress_id());
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

    public delivery_address insertAddress(String province, String city, String region, String add, String linkman, String phonenum) throws BaseException {
        Connection conn = null;
        delivery_address result = new delivery_address();
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into delivery_address(Consumer_id, Address_province, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Default_) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, false )";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.setString(2, province);
            pst.setString(3, city);
            pst.setString(4, region);
            pst.setString(5, add);
            pst.setString(6, linkman);
            pst.setString(7, phonenum);
            pst.executeUpdate();
            sql = "select max(Address_id) from delivery_address";
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            result.setAddress_id(rs.getInt(1));
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
        result.setAddress_province(province);
        result.setAddress_city(city);
        result.setAddress_region(region);
        result.setAddress_add(add);
        result.setAddress_linkman(linkman);
        result.setAddress_phonenum(phonenum);
        result.setDefault_(false);
        return result;
    }

    public void defaultAddress(delivery_address inf) throws BaseException {
        if (inf==null) throw new BusinessException("未选择地址！");
        if (inf.getDefault_()) throw new BusinessException("已经是默认地址");
        Connection conn = null;
        System.out.println(inf.getDefault_());
        try {
            conn = DBUtil.getConnection();
            String sql = "update delivery_address set Default_ = false where Default_ = true and Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.executeUpdate();

            sql = "update delivery_address set Default_ = true where Address_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getAddress_id());
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

    public ObservableList<Consumer_information> loadConinf() throws BaseException {
        ArrayList<Consumer_information> inf = new ArrayList<Consumer_information>();
        Connection conn = null;
        try {
            Consumer_information coninf = new Consumer_information();
            conn = DBUtil.getConnection();
            String sql = "select Consumer_id, Consumer_name, Consumer_gender, Consumer_pwd, Consumer_phonenum, Consumer_mail, Consumer_city, Consumer_regdate, Consumer_member, Consumer_memberddl " +
                    "from Consumer_information where Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            rs.next();
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

    public ObservableList<view_takeorder> takeorder_Merchant()  throws BaseException {
        ArrayList<view_takeorder> inf = new ArrayList<view_takeorder>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select distinct Merchant_id, Merchant_name, Merchant_level, Consume_avgprice, Total_sales from view_takeorder";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("没有商家营业！");
            do {
                view_takeorder data = new view_takeorder();
                data.setMerchant_id(rs.getInt(1));
                data.setMerchant_name(rs.getString(2));
                data.setMerchant_level(rs.getInt(3));
                data.setConsume_avgprice(rs.getFloat(4));
                data.setTotal_sales(rs.getInt(5));
                inf.add(data);
            } while (rs.next());
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

    public ObservableList<view_takeorder> takeorder_Merchandise(view_takeorder select)  throws BaseException {
        ArrayList<view_takeorder> inf = new ArrayList<view_takeorder>();
        Connection conn = null;
        try {
            if(select == null) throw new BusinessException("未选择商家！");
            conn = DBUtil.getConnection();
            String sql = "select merchandise_id, merchandise_name, Merchandise_price, Disconut_price, Sort_name, merchant_id from view_takeorder where merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, select.getMerchant_id());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                view_takeorder data = new view_takeorder();
                data.setMerchandise_id(rs.getInt(1));
                data.setMerchandise_name(rs.getString(2));
                data.setMerchandise_price(rs.getFloat(3));
                data.setDisconut_price(rs.getFloat(4));
                data.setSort_name(rs.getString(5));
                data.setMerchant_id(rs.getInt(6));

                inf.add(data);
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

    public void takeorder(ObservableList<view_takeorder> inf, int Address_id, Float Order_orgamount, Float Order_setamount, int Reduction_id,
                          int Coupon_id, int Order_count, int Merchant_id) throws BaseException {
        Connection conn = null;
        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int order_id = 0;
        try {
            conn = DBUtil.getConnection();
            Calendar calendar = Calendar.getInstance();
            String sql = "insert into order_ (Address_id, Consumer_id, Reduction_id, Coupon_id, Order_orgamount, Order_setamount, Order_platime, Order_deltime, Order_state, Comment_Rider) " +
                    "VALUES (?, ?, ?, ?, ? ,?, ?, ? ,? , '未评价')";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Address_id);
            pst.setInt(2, FrmMainLogin.Currentuserid);
            if (Reduction_id==-1){
                pst.setNull(3, Types.INTEGER);
            } else {
                pst.setInt(3, Reduction_id);
            }
            if (Coupon_id == -1) {
                pst.setNull(4,Types.INTEGER);
            } else {
                pst.setInt(4, Coupon_id);
            }
            pst.setFloat(5, Order_orgamount);
            pst.setFloat(6, Order_setamount);
            pst.setTimestamp(7, new java.sql.Timestamp(calendar.getTimeInMillis()));
            calendar.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY+1);
            pst.setTimestamp(8, new java.sql.Timestamp(calendar.getTimeInMillis()));
            pst.setString(9, "烹饪中");
            pst.executeUpdate();

            sql = "select Order_id from order_ where Consumer_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                order_id = rs.getInt(1);
            }
            if (order_id==0) throw new BusinessException("发生错误！");

            for (int i=0; i<inf.size(); i++){
                sql = "insert into order_detail(order_id, merchandise_id, merchandise_count) VALUES (?, ?, ?)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, order_id);
                pst.setInt(2, inf.get(i).getMerchandise_id());
                pst.setInt(3, inf.get(i).getCount());
                pst.executeUpdate();
            }

            if (Coupon_id!=-1) {
                sql = "update collect_to_get set Collect_now = Collect_now-? where Coupon_id = ? and Merchant_id = ? and Consumer_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setFloat(1, Order_count);
                pst.setInt(2, Coupon_id);
                pst.setInt(3,Merchant_id);
                pst.setInt(4, FrmMainLogin.Currentuserid);
                pst.executeUpdate();
            }

            sql = "update merchant_information set Consume_avgprice =  (Consume_avgprice * Total_sales + ?)/(Total_sales+1) where Merchant_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setFloat(1, Order_setamount);
            pst.setInt(2, Merchant_id);
            pst.executeUpdate();

            sql = "update merchant_information set Total_sales =  Total_sales + 1 where Merchant_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Merchant_id);
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

    public ArrayList<Integer> loadcoupon(int Merchant_id) throws BaseException {
        Connection conn = null;
        ArrayList<Integer> result = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select Order_count, Discount_price, Coupon_id from discount_coupon where Merchant_id = ? " +
                    "and Starttime<(select NOW()) and  Finishtime>(select NOW())";
            PreparedStatement pst = conn.prepareStatement(sql);
//            System.out.println(Merchant_id);
            pst.setInt(1, Merchant_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(rs.getInt(1));
                result.add(rs.getInt(2));
                result.add(rs.getInt(3));
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
        return result;
    }

    public void updateConinf (Consumer_information change_row, int col) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update consumer_information set "+getinfchange_col(col)+" = "+getinfcol_name(change_row, col)+" where Consumer_id = ?";
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

    public void collect(String inf, ArrayList<Integer> coupon, int Merchant_id) throws BaseException {
        int size = coupon.size();
        String r1 = null, r2 = null, r3 = null, r4 = null, r5 = null;
        if (size==0) {
            return ;
        } if (size>=2){
            r1 = "集"+coupon.get(0)+"单，送"+coupon.get(1)+"元券";
        } if (size>=5) {
            r2 = "集"+coupon.get(3)+"单，送"+coupon.get(4)+"元券";
        } if (size>=8) {
            r3 = "集"+coupon.get(6)+"单，送"+coupon.get(7)+"元券";
        } if (size>=11) {
            r4 = "集"+coupon.get(9)+"单，送"+coupon.get(10)+"元券";
        } if (size>=14) {
            r5 = "集"+coupon.get(12)+"单，送"+coupon.get(13)+"元券";
        }
        int coupon_id = 0;

        if (inf.equals(r1)) {
            coupon_id = coupon.get(2);
        } else if (inf.equals(r2)) {
            coupon_id = coupon.get(5);
        } else if (inf.equals(r3)) {
            coupon_id = coupon.get(8);
        } else if (inf.equals(r4)) {
            coupon_id = coupon.get(11);
        } else if (inf.equals(r5)) {
            coupon_id = coupon.get(14);
        } else {
            throw new BusinessException("未选集券对象！");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from collect_to_get where Consumer_id = ? and Coupon_id = ? and Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.setInt(2, coupon_id);
            pst.setInt(3, Merchant_id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                sql = "update collect_to_get set Collect_now = Collect_now+1 where Consumer_id = ? and Coupon_id = ? and Merchant_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, FrmMainLogin.Currentuserid);
                pst.setInt(2, coupon_id);
                pst.setInt(3, Merchant_id);
                pst.executeUpdate();
            } else {
                sql = "insert into collect_to_get(Consumer_id, Coupon_id, Merchant_id, Collect_now) " +
                        "values (?, ?, ?, ?)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, FrmMainLogin.Currentuserid);
                pst.setInt(2, coupon_id);
                pst.setInt(3, Merchant_id);
                pst.setInt(4, 1);
                pst.executeUpdate();
            }
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

    private String getinfchange_col (int col){
        switch (col) {
            case 2: return "Consumer_name";
            case 3: return "Consumer_gender";
            case 4: return "Consumer_pwd";
            case 5: return "Consumer_phonenum";
            case 6: return "Consumer_mail";
            case 7: return "Consumer_city";
            case 9: return "Consumer_member";
        }
        return null;
    }

    private String getinfcol_name (Consumer_information inf, int col) {
        switch (col) {
            case 2: return "'" + inf.getConsumer_name()+ "'";
            case 3: return "'" + inf.getConsumer_gender()+ "'";
            case 4: return "'" + inf.getConsumer_pwd()+ "'";
            case 5: return "'" + inf.getConsumer_phonenum()+ "'";
            case 6: return "'" + inf.getConsumer_mail()+ "'";
            case 7: return "'" + inf.getConsumer_city()+ "'";
            case 9: return "'" + inf.getConsumer_member()+ "'";
        }
        return null;
    }

    private String get_address(int col){
        switch (col) {
            case 3: return "Address_province";
            case 4: return "Address_city";
            case 5: return "Address_region";
            case 6: return "Address_add";
            case 7: return "Address_linkman";
            case 8: return "Address_phonenum";
        }
        return null;
    }

    private String get_addressdata (delivery_address change_row, int col){
        switch (col) {
            case 3: return "'"+change_row.getAddress_province()+"'";
            case 4: return "'"+change_row.getAddress_city()+"'";
            case 5: return "'"+change_row.getAddress_region()+"'";
            case 6: return "'"+change_row.getAddress_add()+"'";
            case 7: return "'"+change_row.getAddress_linkman()+"'";
            case 8: return "'"+change_row.getAddress_phonenum()+"'";
        }
        return null;
    }

    public void delectOrder(view_order inf) throws BaseException {
        int Coupon_id, Merchant_id, Order_count;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Order_state from order_ where Order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
            ResultSet rs = pst.executeQuery();
            rs.next();
            if (rs.getString(1).equals("已送达")) throw new BusinessException("已送达不能取消！");

            sql = "delete from order_detail where Order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
            pst.executeUpdate();


            sql = "select discount_coupon.Coupon_id, Merchant_id, Order_count from order_, discount_coupon where order_.Coupon_id = discount_coupon.Coupon_id" +
                    " and Order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
            rs = pst.executeQuery();
            if (rs.next()) {
                Coupon_id = rs.getInt(1);
                Merchant_id = rs.getInt(2);
                Order_count = rs.getInt(3);
            } else {
                Coupon_id = 0;
                Merchant_id = 0;
                Order_count = 0;
            }

            sql = "update order_ set Order_state = '取消订单' where Order_id = ? and Order_state != '已送达'";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
            pst.executeUpdate();

            sql = "update merchant_order set Consume_avgprice = (Consume_avgprice*Total_sales - ?)/(Total_sales-1) where Order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
            pst.setInt(2, Merchant_id);
            pst.executeUpdate();

            sql = "update merchant_order set Total_sales = Total_sales - 1 where Order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
            pst.executeUpdate();

            if (Coupon_id!=0) {
                sql = "update collect_to_get set Collect_now = Collect_now + " + Order_count + " - 1 where Consumer_id = ? and Coupon_id = ? and Merchant_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, FrmMainLogin.Currentuserid);
                pst.setInt(2, Coupon_id);
                pst.setInt(3, Merchant_id);
                pst.executeUpdate();
            } else {
                sql = "update collect_to_get set Collect_now = Collect_now - 1 where Consumer_id = ? and Coupon_id = ? and Merchant_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, FrmMainLogin.Currentuserid);
                pst.setInt(2, Coupon_id);
                pst.setInt(3, Merchant_id);
                pst.executeUpdate();
            }

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

    public ObservableList<view_order> loadAllOrder() throws BaseException {
        ArrayList<view_order> result = new ArrayList<view_order>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select distinct Order_id, Consumer_name, Merchant_name, Rider_id, Order_state, Order_platime, Order_deltime, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Comment_Rider, Merchant_id  " +
                    " from view_order where Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
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
                inf.setMerchant_id(rs.getInt(14));
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
                    " from view_order where Order_id = ? and Consumer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Order_id);
            pst.setInt(2, FrmMainLogin.Currentuserid);
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
            inf.setMerchant_id(rs.getInt(14));
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

    public view_takeorder recommend(int merchant_id) throws BaseException {
        view_takeorder result = new view_takeorder();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select COUNT(Sort_id), Sort_id from view_recommend where Consumer_id = ? and Merchant_id = ? GROUP BY Sort_id desc";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.setInt(2, merchant_id);
            ResultSet rs = pst.executeQuery();
            int Sort_id;
            if (rs.next()) {
                Sort_id = rs.getInt(1);
            } else {
                Sort_id = 0;
            }

            sql = "select count(merchandise_id), merchandise_id, Merchandise_name, Merchandise_price, Disconut_price, Merchant_id, Sort_name from view_recommend where Sort_id = ? and Merchandise_id = ? GROUP BY merchandise_id desc";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Sort_id);
            pst.setInt(2, merchant_id);
            rs = pst.executeQuery();
            if (rs.next()) {
                result.setMerchandise_id(rs.getInt(2));
                result.setMerchandise_name(rs.getString(3));
                result.setMerchandise_price(rs.getFloat(4));
                result.setDisconut_price(rs.getFloat(5));
                result.setMerchant_id(rs.getInt(6));
                result.setSort_name(rs.getString(7));
                result.setCount(1);
            } else {
//                System.out.println(merchant_id);
                sql = "select merchandise_id, Merchandise_name, Merchandise_price, Disconut_price, Merchant_id, Sort_name from merchandise_information, merchandise_sort" +
                        " where Merchant_id = ? and merchandise_information.Sort_id = merchandise_sort.Sort_id";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, merchant_id);
                rs = pst.executeQuery();
                if (!rs.next()) throw new BusinessException("不存在任何菜品");
                result.setMerchandise_id(rs.getInt(1));
                result.setMerchandise_name(rs.getString(2));
                result.setMerchandise_price(rs.getFloat(3));
                result.setDisconut_price(rs.getFloat(4));
                result.setMerchant_id(rs.getInt(5));
                result.setSort_name(rs.getString(6));
                result.setCount(1);
            }
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
        return result;
    }


//    public ObservableList<view_takeorder> loadorder(ObservableList<view_takeorder> inf) throws BaseException {
//        ObservableList<view_takeorder> result = null;
//        for (int i=0; i<inf.size(); i++) {
//            if (inf.get(i).getCount()>0) {
//                result.add(inf.get(i));
//            }
//        }
//        return result;
//    }
}

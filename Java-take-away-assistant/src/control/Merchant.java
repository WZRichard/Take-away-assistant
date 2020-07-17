package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import model.Discount_coupon;
import model.view_loadrecommend;
import model.view_order;
import model.view_setComment;
import ui.FrmMainLogin;
import ui.FrmMerchant;
import ui.FrmMerchant.reduce_inf;
import ui.FrmMerchant.Merchandise_information;
import ui.FrmMerchant.Mer_information;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Merchant {

    public void check() throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update order_ set Order_state = '超时' where Order_deltime<(select NOW()) and Order_state!='已送达' " +
                    "and Order_state!='取消订单'";
            PreparedStatement pst = conn.prepareStatement(sql);
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

    public ObservableList<Mer_information> getMer_information() throws BaseException {
        Mer_information inf = new Mer_information();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchant_information where Merchant_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, FrmMainLogin.Currentusername);
            ResultSet rs = pst.executeQuery();
            rs.next();
            inf.setId(rs.getInt(1));
            inf.setName(rs.getString(2));
            if (rs.getInt(3)!=0) inf.setLevel(rs.getInt(3));
            else inf.setLevel(0);
            if (rs.getFloat(4)!=0) inf.setAvgprice(rs.getFloat(4));
            else inf.setAvgprice(0);
            inf.setTotal_sales(rs.getInt(5));
            inf.setPwd(rs.getString(6));
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

    public void updateMer_information(Mer_information change_row, int change_col) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update merchant_information set "+get_changecolname(change_col)+" = "+get_changecoldata(change_row, change_col)+" where Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, change_row.getId());
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

    public ObservableList<Merchandise_information> loadallMerchandise() throws BaseException {
        Connection conn = null;
        ArrayList<Merchandise_information> result = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select merchandise_information.Merchandise_id, Merchandise_name, Sort_name, Merchandise_price, Disconut_price " +
                    "from merchandise_information, merchandise_sort " +
                    "where merchandise_sort.Sort_id = merchandise_information.Sort_id and merchandise_information.Merchant_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Merchandise_information inf = new Merchandise_information();
                inf.setId(rs.getInt(1));
                inf.setMerchandise_name(rs.getString(2));
                inf.setSort_name(rs.getString(3));
                inf.setMerchandise_price(rs.getFloat(4));
                inf.setDisconut_price(rs.getFloat(5));
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

    public Merchandise_information insertMerchandise(String name, String sort, Float Merchandise_price, Float Disconut_price) throws BaseException {
        Merchandise_information result = new Merchandise_information();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Sort_id from merchandise_sort where Sort_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, sort);
            ResultSet rs = pst.executeQuery();
            int sort_id, Merchandise_amount;
            if (!rs.next()) throw new BaseException("不存在该商品种类！");
            else {
                sort_id = rs.getInt(1);
            }

            sql = "select * from merchandise_information where merchandise_name = ? and Merchant_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, FrmMainLogin.Currentuserid);
            rs = pst.executeQuery();
            if (rs.next()) throw new BaseException("该菜品已存在");

            sql = "insert into merchandise_information(sort_id, merchandise_name, merchandise_price, disconut_price, Merchant_id) values (?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, sort_id);
            pst.setString(2, name);
            pst.setFloat(3, Merchandise_price);
            pst.setFloat(4, Disconut_price);
            pst.setInt(5, FrmMainLogin.Currentuserid);
            pst.executeUpdate();

            pst.close();
            result.setId(sort_id);
            result.setMerchandise_name(name);
            result.setMerchandise_price(Merchandise_price);
            result.setDisconut_price(Disconut_price);
            result.setSort_name(sort);
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

    public void updateMerchandise(Merchandise_information change_row, int change_col) throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql;
            PreparedStatement pst = null;
            if (change_col==0) {
                sql = "select Sort_id from merchandise_sort where Sort_name = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, change_row.getSort_name());
                ResultSet rs = pst.executeQuery();
                if (!rs.next()) throw new BusinessException("不存在该分类！");
                int Sort_id = rs.getInt(1);

                sql = "update merchandise_information set Sort_id = ? where Merchandise_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, Sort_id);
                pst.setInt(2, change_row.getId());
            } else {
                sql = "update merchandise_information set "+get_merchandisename(change_col)+" = "+get_merchandisedata(change_row, change_col)+"" +
                        " where Merchandise_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, change_row.getId());
            }
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

    public void deletemerchandise(Merchandise_information inf) throws BaseException {
        Connection conn = null;
        try {
            if (inf==null) throw new BusinessException("未选中菜品!");
            conn = DBUtil.getConnection();
            String sql = "delete from belong_to where Merchandise_id = ? and Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getId());
            pst.setInt(2, FrmMainLogin.Currentuserid);
            pst.executeUpdate();
            sql = "delete from merchandise_information where Merchandise_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getId());
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

    public ObservableList<reduce_inf> loadallreduce() throws BaseException {
        Connection conn = null;
        ArrayList<reduce_inf> result = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from full_reduction where Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                reduce_inf inf = new reduce_inf();
                inf.setReduction_id(rs.getInt(1));
                inf.setMerchant_id(rs.getInt(2));
                inf.setRed_Amount(rs.getFloat(3));
                inf.setRed_Aim(rs.getFloat(4));
                inf.setSupport_coupon(rs.getBoolean(5));
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

    public reduce_inf insertreduce(Float Red_Amount, Float Red_Aim, Boolean Support_coupon) throws BaseException {
        reduce_inf result = new reduce_inf();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from full_reduction where Red_Aim = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setFloat(1, Red_Aim);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) throw new BaseException("直接在表格中修改即可！");

            sql = "insert into full_reduction(Merchant_id, Red_Amount, Red_Aim, Support_coupon) values (?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.setFloat(2, Red_Amount);
            pst.setFloat(3, Red_Aim);
            pst.setBoolean(4, Support_coupon);
            pst.executeUpdate();

            sql = "select Reduction_id from full_reduction where Red_Aim = ?";
            pst = conn.prepareStatement(sql);
            pst.setFloat(1, Red_Aim);
            rs = pst.executeQuery();
            rs.next();
            result.setReduction_id(rs.getInt(1));

            pst.close();
            result.setMerchant_id(FrmMainLogin.Currentuserid);
            result.setRed_Amount(Red_Amount);
            result.setRed_Aim(Red_Aim);
            result.setSupport_coupon(Support_coupon);
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

    public void updatereduce(reduce_inf change_row, int change_col) throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update full_reduction set "+get_reducecolname(change_col)+" = "+get_reducecoldata(change_row, change_col) + " where Reduction_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, change_row.getReduction_id());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delectreduce(reduce_inf inf) throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from full_reduction where Reduction_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getReduction_id());
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

    public ObservableList<Discount_coupon> loadallcoupon() throws BaseException {
        Connection conn = null;
        ArrayList<Discount_coupon> result = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from discount_coupon where Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Discount_coupon inf = new Discount_coupon();
                inf.setCoupon_id(rs.getInt(1));
                inf.setMerchant_id(FrmMainLogin.Currentuserid);
                inf.setDiscount_price(rs.getFloat(3));
                inf.setOrder_count(rs.getInt(4));
                inf.setStarttime(rs.getTimestamp(5));
                inf.setFinishtime(rs.getTimestamp(6));
                result.add(inf);
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
        return FXCollections.observableArrayList(result);
    }

    public Discount_coupon insertcoupon(Float Discount_price, int Order_count, Date Starttime, Date Finishtime) throws BaseException {
        Discount_coupon inf = new Discount_coupon();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into discount_coupon(Merchant_id, Discount_price, Order_count, Starttime, Finishtime) values (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            pst.setFloat(2, Discount_price);
            pst.setInt(3, Order_count);
            pst.setString(4, DatetoString(Starttime));
            pst.setString(5, DatetoString(Finishtime));
            pst.executeUpdate();

            sql = "select Coupon_id from discount_coupon where Merchant_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            rs.next();
            inf.setCoupon_id(rs.getInt(1));
            inf.setMerchant_id(FrmMainLogin.Currentuserid);
            inf.setDiscount_price(Discount_price);
            inf.setOrder_count(Order_count);
            inf.setStarttime(Starttime);
            inf.setFinishtime(Finishtime);
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
        return inf;
    }

    public void updatecoupon(Discount_coupon change_row, int change_col) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update discount_coupon set "+get_couponcolname(change_col)+" = '"+get_couponcoldata(change_row, change_col) + "' where Coupon_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, change_row.getCoupon_id());
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

    public void delectcoupon(Discount_coupon inf) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from discount_coupon where Coupon_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getCoupon_id());
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

    public ObservableList<view_order> loadallorder() throws BaseException {
        Connection conn = null;
        ArrayList<view_order> result = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select Order_id, Consumer_name, Merchandise_name, Merchandise_Count, Order_state, Rider_id, Order_platime, Order_deltime " +
                    "from view_order where Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                view_order inf = new view_order();
                inf.setOrder_id(rs.getInt(1));
                inf.setConsumer_name(rs.getString(2));
                inf.setMerchandise_name(rs.getString(3));
                inf.setMerchandise_Count(rs.getInt(4));
                inf.setOrder_state(rs.getString(5));
                inf.setRider_name(rs.getInt(6)==0 ? "暂无骑手接单" : String.valueOf(rs.getInt(6)));
                inf.setOrder_platime(rs.getTimestamp(7));
                inf.setOrder_deltime(rs.getTimestamp(8));
                result.add(inf);
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
        return FXCollections.observableArrayList(result);
    }

    public void doneorder(view_order inf) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update view_order set Order_state = '等待配送' where Order_id = ? " +
                    "and Order_state = '烹饪中'";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getOrder_id());
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

    public String DatetoString(Date date) throws BaseException {
        if (date==null) return null;
        SimpleDateFormat formatime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String st = formatime.format(date);
        return st;
    }

    private String get_couponcolname (int col) {
        switch (col) {
            case 3: return "Discount_price";
            case 4: return "Order_count";
            case 5: return "Starttime";
            case 6: return "Finishtime";
        }
        return null;
    }

    private String get_couponcoldata (Discount_coupon change_row, int col) throws BaseException {
        switch (col) {
            case 3: return String.valueOf(change_row.getDiscount_price());
            case 4: return String.valueOf(change_row.getOrder_count());
            case 5: return String.valueOf(DatetoString(change_row.getStarttime()));
            case 6: return String.valueOf(DatetoString(change_row.getFinishtime()));
        }
        return null;
    }

    private String get_reducecolname (int col) {
        switch (col) {
            case 3: return "Red_Amount";
            case 4: return "Red_Aim";
            case 5: return "Support_coupon";
        }
        return null;
    }

    private String get_reducecoldata (reduce_inf change_row, int col) {
        switch (col) {
            case 3: return String.valueOf(change_row.getRed_Amount());
            case 4: return String.valueOf(change_row.getRed_Aim());
            case 5: return String.valueOf(change_row.isSupport_coupon());
        }
        return null;
    }

    private String get_changecolname (int col) {
        switch (col) {
            case 2: return "Merchant_name";
            case 3: return "Merchant_level";
            case 4: return "Consume_avgprice";
            case 5: return "Total_sales";
            case 6: return "Merchant_pwd";
        }
        return null;
    }

    private String get_changecoldata (Mer_information change_row, int col) {
        switch (col) {
            case 2: return "'"+change_row.getName()+"'";
            case 3: return String.valueOf(change_row.getLevel());
            case 4: return String.valueOf(change_row.getAvgprice());
            case 5: return String.valueOf(change_row.getTotal_sales());
            case 6: return "'"+change_row.getPwd()+"'";
        }
        return null;
    }

    private String get_merchandisename (int col ){
        switch (col) {
            case 3: return "Merchandise_name";
            case 4: return "Merchandise_price";
            case 5: return "Disconut_price";
        }
        return null;
    }

    private String get_merchandisedata (Merchandise_information change_row, int col) {
        switch (col) {
            case 3: return "'"+change_row.getMerchandise_name()+"'";
            case 4: return String.valueOf(change_row.getMerchandise_price());
            case 5: return String.valueOf(change_row.getDisconut_price());
        }
        return null;
    }

    public ObservableList<view_loadrecommend> loadComment() throws BaseException {
        ArrayList<view_loadrecommend> result = new ArrayList<view_loadrecommend>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select comment_picture, comment_level, comment_content, comment_date, order_id, merchant_name, consumer_name, Merchandise_name" +
                    " from view_loadrecommend where Merchant_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                view_loadrecommend inf = new view_loadrecommend();
                inf.setComment_picture(SwingFXUtils.toFXImage(ImageIO.read(rs.getBinaryStream(1)), null));
                inf.setComment_level(rs.getInt(2));
                inf.setComment_content(rs.getString(3));
                inf.setComment_Date(rs.getTimestamp(4));
                inf.setOrder_id(rs.getInt(5));
                inf.setMerchant_name(rs.getString(6));
                inf.setConsumer_name(rs.getString(7));
                inf.setMerchandise_name(rs.getString(8));
                result.add(inf);
            }
        } catch (SQLException | IOException e) {
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
}

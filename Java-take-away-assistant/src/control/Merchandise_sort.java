package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.FrmMerchant.Merchandisesort_information;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Merchandise_sort {
    public void updateMerchandise_sort(Merchandisesort_information inf) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update merchandise_sort set Sort_name = ? where Sort_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, inf.getName());
            pst.setInt(2, inf.getId());
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

    public void delecechandisesort(Merchandisesort_information inf) throws BaseException {
        Connection conn = null;
        try {
            if (inf==null) throw new BusinessException("未选中菜品类别!");
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise_information where Sort_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, inf.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("无法删除!该菜品类别下存在菜品");
            sql = "delete from merchandise_sort where Sort_id = ?";
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

    public Merchandisesort_information insertMerchandise_sort(String name) throws BaseException {
        Connection conn = null;
        Merchandisesort_information inf = new Merchandisesort_information();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise_sort where Sort_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) throw new BaseException("分类已存在！");

            sql = "insert into merchandise_sort(Sort_name) values (?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.executeUpdate();

            sql = "select Sort_id from merchandise_sort where Sort_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            rs.next();

            inf.setId(rs.getInt(1));
            inf.setName(name);
            inf.setAmount(0);
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

    public ObservableList<Merchandisesort_information> loadallMerchandise_sort() throws BaseException {
        ArrayList<Merchandisesort_information> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise_sort";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                Merchandisesort_information inf = new Merchandisesort_information();
                inf.setId(rs.getInt(1));
                inf.setName(rs.getString(2));
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
}

package control;

import ui.FrmConsumer;
import ui.FrmMainLogin;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAndRegister {

    public void Register(String name, String pwd, String pwd2) throws BaseException  {
        Connection conn = null;
        try {
            if (!pwd.equals(pwd2)) throw new BusinessException("两次输入的密码不相同");
            conn = DBUtil.getConnection();
            String sql = "select "+getPwdAttributeName()+" from "+getTableName()+" where "+getNameAttributeName()+" = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) throw new BusinessException("该账号已注册");
            switch (FrmMainLogin.user_type) {
                case 1: {
                    sql = "insert into consumer_information(Consumer_name, Consumer_pwd, Consumer_regdate, Consumer_member) values (?, ?, ?, false)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    pst.setString(2, pwd);
                    pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                    pst.executeUpdate();
                    break;
                }
                case 2: {
                    sql = "insert into "+getTableName()+"("+getNameAttributeName()+", "+getPwdAttributeName()+", Total_sales, Consume_avgprice, Merchant_level) values (?, ?, 0, 1, 0)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    pst.setString(2, pwd);
                    pst.executeUpdate();
                    break;
                }
                case 3: {
                    sql = "insert into rider_information(Rider_name, Reg_date, Rider_sort, Rider_pwd) values (?, ?, '新手', ?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                    pst.setString(3, pwd);
                    pst.executeUpdate();
                    break;
                }
                case 4: {
                    sql = "insert into "+getTableName()+"("+getNameAttributeName()+", "+getPwdAttributeName()+") values (?, ?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    pst.setString(2, pwd);
                    pst.executeUpdate();
                    break;
                }
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

    public void Login(String name, String pwd) throws BaseException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select "+getPwdAttributeName()+", "+getidAttributeName()+" from "+getTableName()+" where "+getNameAttributeName()+" = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) throw new BusinessException("该账号未注册");
            else {
                if (rs.getString(1).equals(pwd)){
                    FrmMainLogin.Currentusername = name;
                    FrmMainLogin.Currentuserid = rs.getInt(2);
                } else throw new BusinessException("密码错误");
            }
            if (FrmMainLogin.user_type == 1) {
                sql = "select Consumer_member from consumer_information where Consumer_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, FrmMainLogin.Currentuserid);
                rs = pst.executeQuery();
                rs.next();
                FrmConsumer.member = rs.getBoolean(1);
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

    public String getTableName() {
        switch (FrmMainLogin.user_type) {
            case 1: return "consumer_information";
            case 2: return "merchant_information";
            case 3: return "rider_information";
            case 4: return "administrator";
        }
        return null;
    }

    public String getNameAttributeName() {
        switch (FrmMainLogin.user_type) {
            case 1: return "Consumer_name";
            case 2: return "Merchant_name";
            case 3: return "Rider_name";
            case 4: return "Administrator_name";
        }
        return null;
    }

    public String getidAttributeName() {
        switch (FrmMainLogin.user_type) {
            case 1: return "Consumer_id";
            case 2: return "Merchant_id";
            case 3: return "Rider_id";
            case 4: return "Administrator_id";
        }
        return null;
    }

    public String getPwdAttributeName() {
        switch (FrmMainLogin.user_type) {
            case 1: return "Consumer_pwd";
            case 2: return "Merchant_pwd";
            case 3: return "Rider_pwd";
            case 4: return "Administrator_pwd";
        }
        return null;
    }
}

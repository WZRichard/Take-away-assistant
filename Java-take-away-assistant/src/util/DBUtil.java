package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DBUtil {
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/Take-away assistant";
    private static final String dbUser = "root";
    private static final String dbPwd = "root";
    private static ComboPooledDataSource ds = null;
    private static Set<Connection> conns = new HashSet<>();
    private static int conncount = 0;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ds = new ComboPooledDataSource();
		ds.setInitialPoolSize(2);
        ds.setMaxPoolSize(15);
		ds.setMinPoolSize(1);
		ds.setMaxIdleTime(600);
        ds.setUser(dbUser);
        ds.setPassword(dbPwd);
        ds.setJdbcUrl(jdbcUrl);
    }

    public static synchronized Connection getConnection() throws java.sql.SQLException, BusinessException {
        try{
			return ds.getConnection();
		} catch (SQLException ex){
        	throw new BusinessException("无法连接数据库");
		}
    }
}

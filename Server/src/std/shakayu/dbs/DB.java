package std.shakayu.dbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private Connection connection = null;
    private Statement statement = null;
    private boolean bDebug = false;
    public DB (String sDBpath, boolean bDebug){
        this.bDebug = bDebug;
        String s;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            s = "jdbc:mysql:" + sDBpath;
            this.connection = DriverManager.getConnection(s);
            this.connection.setAutoCommit(true);
            this.statement = this.connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Can not load the DB driver.");
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

package std.shakayu.dbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private Connection connection = null;
    private Statement statement = null;
    private boolean bDebug = false;
    public DB (String sDBName, String sUsername, String sPwd, boolean bDebug){
        this.bDebug = bDebug;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/" + sDBName,sUsername,sPwd);
            if(this.bDebug) System.out.println("DB is connected!");
            this.connection.setAutoCommit(true);
            this.statement = this.connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Can not load the DB driver.");
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void Close(){
        try{
            if(this.statement!=null){
                this.statement.close();
                if(this.bDebug) System.out.println("Statement is closed.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(this.connection!=null) {
                this.connection.close();
                if(this.bDebug) System.out.println("Connection is closed.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }

}

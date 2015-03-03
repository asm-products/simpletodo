package std.shakayu.dbs;

import std.shakayu.STDAuth;
import std.shakayu.STDUtil;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private Connection connection = null;
    private Statement statement = null;
    private boolean bDebug = false;
    public DB (String sDBName, String sUsername, String sPwd, boolean bDebug){
        this.bDebug = bDebug;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + STDAuth.DBHOST + sDBName,sUsername,sPwd);
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
    
    public boolean IsTableExists(String sTableName){
        boolean bRes = false;
        if(this.connection == null){
            return bRes;
        }
        try {
            DatabaseMetaData dbm = this.connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, sTableName, null);
            bRes = tables.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return bRes;
        }
    }
    
    public void CreateTable(String sTableName, String[] tableinfo){
        if(this.statement == null){
            return;
        }
        if(!IsTableExists(sTableName)){
            String sSQL = "CREATE TABLE " + sTableName + " (";
            int nLength = tableinfo.length;
            for(int i = 0; i<nLength-1; i++){
                sSQL += tableinfo[i] + ",";
            }
            sSQL += tableinfo[nLength-1] + ");";
            try {
                this.statement.executeUpdate(sSQL);
                if(this.bDebug) System.out.println(sSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void DropTable(String sTableName){
        if(this.statement == null){
            return;
        }
        if(IsTableExists(sTableName)) {
            String sSQL = "DROP TABLE " + sTableName + ";";
            try {
                this.statement.executeUpdate(sSQL);
                if (this.bDebug) System.out.println(sSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void InsertRecords(String sTableName, String[] values){
        if(this.statement == null){
            return;
        }
        if(IsTableExists(sTableName)){
            String sSQL = "INSERT INTO " + sTableName + " VALUES (";
            int nLength = values.length;
            for(int i = 0; i<nLength-1; i++){
                sSQL += values[i] + ",";
            }
            sSQL += values[nLength-1] + ");";
            try {
                this.statement.executeUpdate(sSQL);
                if(this.bDebug) System.out.println(sSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void UpdateRecords(String sTableName, String sUpdateKey, String sUpdateValue, String sCondition){
        if(this.statement == null){
            return;
        }
        if(IsTableExists(sTableName)){
            String sWhere;
            if(sCondition == STDUtil.EMPTYSTRING){
                sWhere = "";
            }else{
                sWhere = " WHERE " + sCondition;
            }
            String sSQL = "Update " + sTableName + " set " + sUpdateKey +" = " +
                            sUpdateValue + sWhere + ";";
            try {
                this.statement.executeUpdate(sSQL);
                if(this.bDebug) System.out.println(sSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void UpdateRecords(String sTableName, String sUpdateKey, String sUpdateValue){
        UpdateRecords(sTableName,sUpdateKey,sUpdateValue,"");
    }
    
    public ArrayList SelectRecords(String sTableName, String sSelect, String sWhereCondition){
        ArrayList alRes = new ArrayList();
        if(this.statement == null){
            return alRes;
        }
        if(IsTableExists(sTableName)){
            String sWhere;
            if(sWhereCondition == STDUtil.EMPTYSTRING){
                sWhere = "";
            }else{
                sWhere = " WHERE " + sWhereCondition;
            }
            String sSQL = "SELECT " + sSelect + " FROM " + sTableName + sWhere + ";";
            try {
                ResultSet rs = this.statement.executeQuery(sSQL);
                while(rs.next()){
                    alRes.add(rs.getObject(sSelect));
                }
                rs.close();
                if(this.bDebug) System.out.println(sSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return alRes;
    }

    public ArrayList SelectRecords(String sTableName, String sSelect){
        return SelectRecords(sTableName,sSelect,"");
    }

    public void DeleteRecords(String sTableName, String sCondition){
        if(this.statement == null){
            return;
        }
        if(IsTableExists(sTableName)){
            String sSQL = "DELETE FROM " + sTableName + " WHERE " + sCondition + ";";
            try {
                this.statement.executeUpdate(sSQL);
                if(this.bDebug) System.out.println(sSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void ExecuteUpdate(String sTableName, String sSQL){
        if(this.statement == null){
            return;
        }
        if(IsTableExists(sTableName)){
            try {
                this.statement.executeUpdate(sSQL);
                if(this.bDebug) System.out.println(sSQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

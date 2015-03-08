package std.shakayu.dbs;

import std.shakayu.STDAuth;
import std.shakayu.STDUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBList extends DB {
    private static String   DBNAME = STDAuth.DBName;
    private static boolean  bDebug;
    public DBList(boolean bNeedDebug) {
        super(DBNAME, STDAuth.DBUSERNAME, STDAuth.DBPSW, bNeedDebug);
        bDebug = bNeedDebug;
    }

    protected HashMap GetItemInfo(String sTablename, String sItemID){
        HashMap<String, Object> iteminfo = new HashMap();
        ArrayList<String> sSchema = DescribeTable(sTablename);
        String sSQL = "select * from " + sTablename + " where ITEMID='" + sItemID + "'";
        String sField = "";
        try {
            ResultSet rs = this.statement.executeQuery(sSQL);
            while(rs.next()){
                for(int i = 0; i<sSchema.size();i++){
                    sField = sSchema.get(i);
                    iteminfo.put(sField,rs.getObject(sField));
                }
            }
            rs.close();
            if(this.bDebug) System.out.println(sSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iteminfo;
    }

}

package std.shakayu.dbs;

import std.shakayu.STDAuth;
import std.shakayu.STDUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBList extends DB {
    private static String DBNAME = STDAuth.DBName;

    public DBList(boolean bDebug) {
        super(DBNAME, STDAuth.DBUSERNAME, STDAuth.DBPSW, bDebug);
    }
    


}

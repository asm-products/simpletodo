package std.shakayu.dbs;

import std.shakayu.STDAuth;

public class DBUser extends DB {
    private static String DBNAME = STDAuth.DBName;

    public DBUser(boolean bDebug) {
        super(DBNAME, STDAuth.DBUSERNAME, STDAuth.DBPSW, bDebug);
    }
    
    
}

package std.shakayu.dbs;

import std.shakayu.STDAuth;

public class DBUser extends DB {
    private static String DBNAME = "STDUSERDB";

    public DBUser(boolean bDebug) {
        super(DBNAME, STDAuth.DBUSERNAME, STDAuth.DBPSW, bDebug);
    }
    
    
}

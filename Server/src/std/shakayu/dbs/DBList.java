package std.shakayu.dbs;

import std.shakayu.STDAuth;

public class DBList extends DB {
    private static String DBNAME = STDAuth.DBName;

    public DBList(boolean bDebug) {
        super(DBNAME, STDAuth.DBUSERNAME, STDAuth.DBPSW, bDebug);
    }

}

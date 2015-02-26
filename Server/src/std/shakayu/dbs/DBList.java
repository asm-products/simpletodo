package std.shakayu.dbs;

import std.shakayu.STDAuth;

public class DBList extends DB {
    private static String DBNAME = "STDLISTDB";

    public DBList(boolean bDebug) {
        super(DBNAME, STDAuth.DBUSERNAME, STDAuth.DBPSW, bDebug);
    }

}

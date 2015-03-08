package std.shakayu.dbs;

import std.shakayu.STDUtil;

import java.util.ArrayList;

public class TListInfo {
    private static String   ID                 = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
    private static String   ITEMID             = "ITEMID VARCHAR(32) NOT NULL";
    private static String   DO                 = "DO VARCHAR(20) NOT NULL";
    private static String   DESCRIPTION        = "DESCRIPTION TEXT NOT NULL";
    private static String   DEADLINE           = "DEADLINE VARCHAR(20) NOT NULL";
    private static String   STARTTIME          = "STARTTIME VARCHAR(20) NOT NULL";
    private static String   RECORDTIME         = "RECORDTIME VARCHAR(20) NOT NULL";
    private static String   STATUS             = "STATUS INT NOT NULL";
    private static String   TAG                = "TAG VARCHAR(32) NOT NULL";
    private static String   NEEDALARM          = "NEEDALARM INT NOT NULL";
    private static String   REPEATING          = "REPEATING INT NOT NULL";
    private static String   LOCATION           = "LOCATION TEXT";
    private static String   PRIORITY           = "PRIORITY INT NOT NULL";

    private DBList          db;
    private boolean         bDebug             = false;

    public TListInfo(String sTablename, boolean bDebug){
        db = new DBList(bDebug);
        if(!db.IsTableExists(sTablename)){
            String[] listinfotable = {  ID,
                                        ITEMID,
                                        DO,
                                        DESCRIPTION,
                                        DEADLINE,
                                        STARTTIME,
                                        RECORDTIME,
                                        STATUS,
                                        TAG,
                                        NEEDALARM,
                                        REPEATING,
                                        LOCATION,
                                        PRIORITY    };
            db.CreateTable(sTablename, listinfotable);
        }
    }

    private String[] GenItemInfoArray(String sItemID, String sDo, String sDescription,
                    String sDeadline,String sStarttime,String sStatus, String sTag,
                    String sNeedalarm,String sRepeating,String sLoaction, String sPriority){
        String[] tablerow = new String[13];
        tablerow[0] = "NULL";                                               //0: ID
        tablerow[1] = STDUtil.InDoubleQuote(sItemID);                       //1: ITEMID
        tablerow[2] = STDUtil.InDoubleQuote(sDo);                           //2: DO
        tablerow[3] = STDUtil.InDoubleQuote(sDescription);                  //3: DESCRIPTION
        tablerow[4] = STDUtil.InDoubleQuote(sDeadline);                     //4: DEADLINE
        tablerow[5] = STDUtil.InDoubleQuote(sStarttime);                    //5: STARTTIME
        tablerow[6] = STDUtil.InDoubleQuote(STDUtil.CurrentTimeMillis());   //6: RECORDTIME
        tablerow[7] = sStatus;                                              //7: STATUS
        tablerow[8] = STDUtil.InDoubleQuote(sTag);                          //8: TAG
        tablerow[9] = sNeedalarm;                                           //9: NEEDALARM
        tablerow[10] = sRepeating;                                          //10: REPEATING
        tablerow[11] = STDUtil.InDoubleQuote(sLoaction);                    //11: LOCATION
        tablerow[12] = sPriority;                                           //12: PRIORITY
        return tablerow;
    }

    public void InsertItem(String sTablename, String sItemID, String sDo, String sDescription,
                                 String sDeadline,String sStarttime,String sStatus, String sTag,
                                 String sNeedalarm,String sRepeating,String sLoaction, String sPriority){
        db.InsertRecords(sTablename,
                GenItemInfoArray(sItemID, sDo, sDescription, sDeadline, sStarttime, sStatus, sTag,
                        sNeedalarm, sRepeating, sLoaction, sPriority));
    }

    public String GetDo(String sTablename, String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sTablename, "DO","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateDo(String sTablename, String sItemID,String sDo){
        db.UpdateRecords(sTablename,"DO",
                STDUtil.InDoubleQuote(sDo),
                "ITEMID='"+sItemID+"'");

    }
    
    public String GetDescription(String sTablename, String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sTablename,
                        "DESCRIPTION","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateDescription(String sTablename, String sItemID,String sDescription){
        db.UpdateRecords(sTablename,"DESCRIPTION",
                STDUtil.InDoubleQuote(sDescription),
                "ITEMID='"+sItemID+"'");

    }

    public String GetDeadline(String sTablename, String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sTablename,
                "DEADLINE","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateDeadline(String sTablename, String sItemID,String sDeadline){
        db.UpdateRecords(sTablename,"DEADLINE",
                STDUtil.InDoubleQuote(sDeadline),
                "ITEMID='"+sItemID+"'");

    }

    public String GetStarttime(String sTablename, String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sTablename,
                "STARTTIME","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateStarttime(String sTablename, String sItemID,String sStarttime){
        db.UpdateRecords(sTablename,"STARTTIME",
                STDUtil.InDoubleQuote(sStarttime),
                "ITEMID='"+sItemID+"'");

    }

    public String GetRecordtime(String sTablename, String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sTablename,
                "RECORDTIME","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateRecordtime(String sTablename, String sItemID,String sRecordtime){
        db.UpdateRecords(sTablename,"RECORDTIME",
                STDUtil.InDoubleQuote(sRecordtime),
                "ITEMID='"+sItemID+"'");

    }

    public int GetStatus(String sTablename, String sItemID){
        // return 0: delete
        // return 1: will do
        // return 2: doing
        // return 3: done
        ArrayList<Integer> sRes = db.SelectRecords(sTablename,
                "STATUS","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdateStatus(String sTablename, String sItemID,String sStatus){
        db.UpdateRecords(sTablename,"STATUS",
                sStatus,
                "ITEMID='"+sItemID+"'");

    }

    public String GetTag(String sTablename, String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sTablename,
                "TAG","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateTag(String sTablename, String sItemID,String sTag){
        db.UpdateRecords(sTablename,"TAG",
                STDUtil.InDoubleQuote(sTag),
                "ITEMID='"+sItemID+"'");

    }

    public int GetNeedAlarm(String sTablename, String sItemID){
        // return 0: don't alarm
        // return 1: alarm start time
        // return 2: alarm deadline
        // return 3: alarm both start time and deadline
        ArrayList<Integer> sRes = db.SelectRecords(sTablename,
                "NEEDALARM","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdateNeedAlarm(String sTablename, String sItemID,String sNeedAlarm){
        db.UpdateRecords(sTablename,"NEEDALARM",
                sNeedAlarm,
                "ITEMID='"+sItemID+"'");

    }

    public int GetRepeating(String sTablename, String sItemID){
        // return 0: don't repeat
        // return 1: repeat daily
        ArrayList<Integer> sRes = db.SelectRecords(sTablename,
                "REPEATING","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdateRepeating(String sTablename,String sItemID,String sRepeating){
        db.UpdateRecords(sTablename,"REPEATING",
                sRepeating,
                "ITEMID='"+sItemID+"'");

    }

    public String GetLocation(String sTablename, String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sTablename,
                "LOCATION","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateLocation(String sTablename, String sItemID,String sLocation){
        db.UpdateRecords(sTablename,"LOCATION",
                STDUtil.InDoubleQuote(sLocation),
                "ITEMID='"+sItemID+"'");

    }

    public int GetPriority(String sTablename, String sItemID){
        // return 0: normal
        // return 1: low
        // return 2: high
        ArrayList<Integer> sRes = db.SelectRecords(sTablename,
                "PRIORITY","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdatePriority(String sTablename, String sItemID,String sPriority){
        db.UpdateRecords(sTablename,"PRIORITY",
                sPriority,
                "ITEMID='"+sItemID+"'");

    }
    
    public ArrayList<String> GetItemIDList(String sTablename){
        return db.SelectRecords(sTablename,"ITEMID");
    }

    protected ArrayList GetTableSchema(String sTablename){
        return db.DescribeTable(sTablename);
    }
    
    public void Close(){
        if(db != null){
            db.Close();
        }
    }

}

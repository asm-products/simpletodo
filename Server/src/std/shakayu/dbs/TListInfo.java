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
    private static String   TABLENAMEPREFIX    = "LT";

    private DBList          db;
    private String          sWholeTablename;
    private boolean         bDebug             = false;

    public TListInfo(String sTablename, boolean bDebug){
        db = new DBList(bDebug);
        sWholeTablename = SetTablename(sTablename);
        if(!db.IsTableExists(sWholeTablename)){
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
            db.CreateTable(sWholeTablename, listinfotable);
        }
    }
    
    private String SetTablename(String sTablename){
        return TABLENAMEPREFIX+sTablename;
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

    public void InsertItem(String sItemID, String sDo, String sDescription,
                                 String sDeadline,String sStarttime,String sStatus, String sTag,
                                 String sNeedalarm,String sRepeating,String sLoaction, String sPriority){
        db.InsertRecords(sWholeTablename,
                GenItemInfoArray(sItemID, sDo, sDescription, sDeadline, sStarttime, sStatus, sTag,
                        sNeedalarm, sRepeating, sLoaction, sPriority));
    }

    public String GetDo(String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sWholeTablename, "DO","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateDo(String sItemID,String sDo){
        db.UpdateRecords(sWholeTablename,"DO",
                STDUtil.InDoubleQuote(sDo),
                "ITEMID='"+sItemID+"'");

    }
    
    public String GetDescription(String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sWholeTablename, 
                        "DESCRIPTION","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateDescription(String sItemID,String sDescription){
        db.UpdateRecords(sWholeTablename,"DESCRIPTION",
                STDUtil.InDoubleQuote(sDescription),
                "ITEMID='"+sItemID+"'");

    }

    public String GetDeadline(String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sWholeTablename,
                "DEADLINE","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateDeadline(String sItemID,String sDeadline){
        db.UpdateRecords(sWholeTablename,"DEADLINE",
                STDUtil.InDoubleQuote(sDeadline),
                "ITEMID='"+sItemID+"'");

    }

    public String GetStarttime(String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sWholeTablename,
                "STARTTIME","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateStarttime(String sItemID,String sStarttime){
        db.UpdateRecords(sWholeTablename,"STARTTIME",
                STDUtil.InDoubleQuote(sStarttime),
                "ITEMID='"+sItemID+"'");

    }

    public String GetRecordtime(String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sWholeTablename,
                "RECORDTIME","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateRecordtime(String sItemID,String sRecordtime){
        db.UpdateRecords(sWholeTablename,"RECORDTIME",
                STDUtil.InDoubleQuote(sRecordtime),
                "ITEMID='"+sItemID+"'");

    }

    public int GetStatus(String sItemID){
        // return 0: delete
        // return 1: will do
        // return 2: doing
        // return 3: done
        ArrayList<Integer> sRes = db.SelectRecords(sWholeTablename,
                "STATUS","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdateStatus(String sItemID,String sStatus){
        db.UpdateRecords(sWholeTablename,"STATUS",
                sStatus,
                "ITEMID='"+sItemID+"'");

    }

    public String GetTag(String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sWholeTablename,
                "TAG","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateTag(String sItemID,String sTag){
        db.UpdateRecords(sWholeTablename,"TAG",
                STDUtil.InDoubleQuote(sTag),
                "ITEMID='"+sItemID+"'");

    }

    public int GetNeedAlarm(String sItemID){
        // return 0: don't alarm
        // return 1: alarm start time
        // return 2: alarm deadline
        // return 3: alarm both start time and deadline
        ArrayList<Integer> sRes = db.SelectRecords(sWholeTablename,
                "NEEDALARM","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdateNeedAlarm(String sItemID,String sNeedAlarm){
        db.UpdateRecords(sWholeTablename,"NEEDALARM",
                sNeedAlarm,
                "ITEMID='"+sItemID+"'");

    }

    public int GetRepeating(String sItemID){
        // return 0: don't repeat
        // return 1: repeat daily
        ArrayList<Integer> sRes = db.SelectRecords(sWholeTablename,
                "REPEATING","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdateRepeating(String sItemID,String sRepeating){
        db.UpdateRecords(sWholeTablename,"REPEATING",
                sRepeating,
                "ITEMID='"+sItemID+"'");

    }

    public String GetLocation(String sItemID){
        ArrayList<String> sRes = db.SelectRecords(sWholeTablename,
                "LOCATION","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public void UpdateLocation(String sItemID,String sLocation){
        db.UpdateRecords(sWholeTablename,"LOCATION",
                STDUtil.InDoubleQuote(sLocation),
                "ITEMID='"+sItemID+"'");

    }

    public int GetPriority(String sItemID){
        // return 0: normal
        // return 1: low
        // return 2: high
        ArrayList<Integer> sRes = db.SelectRecords(sWholeTablename,
                "PRIORITY","ITEMID='"+sItemID+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    public void UpdatePriority(String sItemID,String sPriority){
        db.UpdateRecords(sWholeTablename,"PRIORITY",
                sPriority,
                "ITEMID='"+sItemID+"'");

    }
    
    public ArrayList<String> GetItemIDList(){
        return db.SelectRecords(sWholeTablename,"ITEMID");
    }

    public void Close(){
        if(db != null){
            db.Close();
        }
    }

}

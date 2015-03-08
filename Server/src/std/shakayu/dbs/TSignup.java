package std.shakayu.dbs;

import std.shakayu.STDUtil;

import java.util.ArrayList;

public class TSignup {
    private static String   ID          = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
    private static String   UID         = "UID VARCHAR(32) NOT NULL";
    private static String   USERMAIL    = "USERMAIL VARCHAR(50) NOT NULL";
    private static String   PSW         = "PSW VARCHAR(32) NOT NULL";
    private static String   USERNAME    = "USERNAME VARCHAR(50) NOT NULL";
    private static String   SALT        = "SALT VARCHAR(50) NOT NULL";
    private static String   SOURCE      = "SOURCE INT NOT NULL";
    private static String   TABLENAME   = "SIGNUP";
    
    private DBUser          db;
    private boolean         bDebug      = false;
    
    public TSignup(boolean bDebug){
        db = new DBUser(bDebug);
        if(!db.IsTableExists(TABLENAME)){
            String[] signuptable = {ID,UID,USERMAIL,PSW,USERNAME,SALT,SOURCE};
            db.CreateTable(TABLENAME, signuptable);
        }
    }

    private String GenUID(String sUsermail){
        return STDUtil.MD5Pro(sUsermail, GenSalt());
    }

    private String GenPSW(String sPSW, String sSalt){
        return STDUtil.MD5Pro(sPSW,sSalt);
    }
    
    private String GenSalt(){
        return STDUtil.CurrentTimeMillis();
        
    }
    
    private String[] GenSignupInfoArray(String sUsermail, String sPSW, String sUsername, String sSource){
        String[] tablerow = new String[7];
        String sSalt = GenSalt();
        tablerow[0] = "NULL";                                       //0: ID
        tablerow[1] = STDUtil.InDoubleQuote(GenUID(sUsermail));     //1: UID
        tablerow[2] = STDUtil.InDoubleQuote(sUsermail);             //2: USERMAIL
        tablerow[3] = STDUtil.InDoubleQuote(GenPSW(sPSW, sSalt));   //3: PSW
        tablerow[4] = STDUtil.InDoubleQuote(sUsername);             //4: USERNAME
        tablerow[5] = STDUtil.InDoubleQuote(sSalt);                 //5: SALT
        tablerow[6] = sSource;                                      //6: SOURCE
        return tablerow;
    }
    
    protected void InsertSignupInfo(String sUsermail, String sPSW, String sUsername, String sSource){
        db.InsertRecords(TABLENAME,GenSignupInfoArray(sUsermail,sPSW,sUsername,sSource));
    }

    protected String GetUID(String sUsermail){
        ArrayList<String> sRes = db.SelectRecords(TABLENAME, "UID","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    protected String GetUsername(String sUsermail){
        ArrayList<String> sRes = db.SelectRecords(TABLENAME, "USERNAME","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    protected void UpdateUsername(String sUsermail,String sNewUsername){
        db.UpdateRecords(TABLENAME,"USERNAME",
                STDUtil.InDoubleQuote(sNewUsername),
                "USERMAIL='"+sUsermail+"'");
        
    }

    protected void UpdatePSW(String sUsermail,String sNewPSW){
        String sSalt = GetSalt(sUsermail);
        db.UpdateRecords(TABLENAME,"PSW",
                STDUtil.InDoubleQuote(GenPSW(sNewPSW, sSalt)),
                "USERMAIL='"+sUsermail+"'");

    }

    protected String GetSalt(String sUsermail){
        ArrayList<String> sRes = db.SelectRecords(TABLENAME, "SALT","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    protected int GetSource(String sUsermail){
        ArrayList<Integer> sRes = db.SelectRecords(TABLENAME, "SOURCE","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return -1;
        }
    }

    private String GetPSW(String sUsermail){
        ArrayList<String> sRes = db.SelectRecords(TABLENAME, "PSW","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    } 
    
    protected int CheckPSW(String sUsermail,String sPSW){
        String sSalt = GetSalt(sUsermail);
        String sDBPSW = GetPSW(sUsermail);
        String sInputPSW = GenPSW(sPSW,sSalt);
        if(sInputPSW.equals(sDBPSW)){
            return 1;
        }else{
            return -1;
        }
    }

    public void Close(){
        if(db != null){
            db.Close();
        }
    }
    
}

package std.shakayu.dbs;

import com.sun.tools.javac.jvm.Gen;
import std.shakayu.STDUtil;

import java.util.ArrayList;

public class TSignup {
    private static String    ID          = "ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
    private static String    UID         = "UID VARCHAR(32) NOT NULL";
    private static String    USERMAIL    = "USERMAIL VARCHAR(50) NOT NULL";
    private static String    PSW         = "PSW VARCHAR(32) NOT NULL";
    private static String    USERNAME    = "USERNAME VARCHAR(50) NOT NULL";
    private static String    SALT        = "SALT VARCHAR(50) NOT NULL";
    private static String    SOURCE      = "SOURCE INT NOT NULL";
    private static String    TABLENAME   = "SIGNUP";
    
    private DB              db;
    private boolean         bDebug = false;
    
    public TSignup(boolean bDebug){
        db = new DB(bDebug);
        if(!db.IsTableExists(TABLENAME)){
            String[] signuptable = {ID,UID,USERMAIL,PSW,USERNAME,SALT,SOURCE};
            db.CreateTable(TABLENAME,signuptable);
        }
    }

    private String GenUID(String sUsermail){
        return STDUtil.MD5Pro(sUsermail, GenSalt());
    }

    private String GenPSW(String sPSW, String sSalt){
        return STDUtil.MD5Pro(sPSW,sSalt);
    }
    
    private String GenSalt(){
        return Long.toString(System.currentTimeMillis());
        
    }
    
    private String[] GenSignupInfoArray(String sUsermail, String sPSW, String sUsername, String sSource){
        String[] tablerow = new String[7];
        String sSalt = GenSalt();
        tablerow[0] = "NULL";//ID
        tablerow[1] = STDUtil.DOUBLEQUOTE + GenUID(sUsermail) + STDUtil.DOUBLEQUOTE;//UID
        tablerow[2] = STDUtil.DOUBLEQUOTE + sUsermail + STDUtil.DOUBLEQUOTE;//USERMAIL
        tablerow[3] = STDUtil.DOUBLEQUOTE + GenPSW(sPSW, sSalt) + STDUtil.DOUBLEQUOTE;//PSW
        tablerow[4] = STDUtil.DOUBLEQUOTE + sUsername + STDUtil.DOUBLEQUOTE;//USERNAME
        tablerow[5] = STDUtil.DOUBLEQUOTE + sSalt + STDUtil.DOUBLEQUOTE;//SALT
        tablerow[6] = sSource;//SOURCE
        return tablerow;
    }
    
    public void InsertSignupInfo(String sUsermail, String sPSW, String sUsername, String sSource){
        db.InsertRecords(TABLENAME,GenSignupInfoArray(sUsermail,sPSW,sUsername,sSource));
    }
    
    public String GetUID(String sUsermail){
        ArrayList<String> sRes = db.SelectRecords(TABLENAME, "UID","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public String GetUsername(String sUsermail){
        ArrayList<String> sRes = db.SelectRecords(TABLENAME, "USERNAME","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    protected String GetSalt(String sUsermail){
        ArrayList<String> sRes = db.SelectRecords(TABLENAME, "SALT","USERMAIL='"+sUsermail+"'");
        if(sRes.size()!= 0){
            return sRes.get(0);
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }

    public int GetSource(String sUsermail){
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
    
    public int CheckPSW(String sUsermail,String sPSW){
        String sSalt = GetSalt(sUsermail);
        String sDBPSW = GetPSW(sUsermail);
        String sInputPSW = GenPSW(sPSW,sSalt);
        if(sInputPSW.equals(sDBPSW)){
            return 1;
        }else{
            return -1;
        }
    }
    
    public boolean IsUserExists(String sUsermail){
        String sUID = GetUID(sUsermail);
        return STDUtil.IsStringAvaliable(sUID,false);
    }
    
    public void Close(){
        if(db != null){
            db.Close();
        }
    }
    
}

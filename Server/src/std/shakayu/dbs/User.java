package std.shakayu.dbs;

import std.shakayu.STDUtil;

import java.util.ArrayList;
import java.util.List;

public class User extends TSignup {
    protected boolean bDebug        =   false;
    public User(boolean bNeedDebug) {
        super(bNeedDebug);
        bDebug = bNeedDebug;
    }
    public boolean IsUserExists(String sUsermail){
        String sUID = GetUID(sUsermail);
        return STDUtil.IsStringAvaliable(sUID, false);
    }
    
    public void Signup(String sUserEmail, String sPSW, String sUsername, String sSource){
        InsertSignupInfo(sUserEmail,sPSW,sUsername,sSource);
    }
    
    public int CanUserLogin(String sUserEmail, String sPSW){
        int nRes = 0;
        nRes = CheckPSW(sUserEmail,sPSW);
        return nRes;//0: User not exists; -1:Wrong PSW; 1: OK
    }
    
    public String GetListTableName(String sUserEmail){
        String sUID = GetUID(sUserEmail);
        if(STDUtil.IsStringAvaliable(sUID,false)){
            return STDUtil.TABLENAMEPREFIX+sUID;
        }else{
            return STDUtil.EMPTYSTRING;
        }
    }
    
    public void AddItem(String sUserEmail, String sItemID, String sDo, String sDescription, 
                        String sDeadline,String sStarttime,String sStatus, String sTag, 
                        String sNeedalarm,String sRepeating,String sLoaction, String sPriority){
        
        String sTable = GetListTableName(sUserEmail);
        TListInfo list = new TListInfo(sTable,bDebug);
        list.InsertItem(sTable,sItemID, sDo, sDescription, sDeadline, sStarttime, sStatus, sTag,
                sNeedalarm, sRepeating, sLoaction, sPriority);
        list.Close();
    }
    
    public ArrayList GetItemListField(String sUserEmail){
        ArrayList<String> sSchema;
        String sTable = GetListTableName(sUserEmail);
        TListInfo list = new TListInfo(sTable,bDebug);
        sSchema = list.GetTableSchema(sTable);
        list.Close();
        return sSchema;
    }
}

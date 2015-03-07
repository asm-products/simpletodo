package std.shakayu.dbs;

import std.shakayu.STDUtil;

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
        if(IsUserExists(sUserEmail)){
            nRes = CheckPSW(sUserEmail,sPSW);
        }
        return nRes;//0: User not exists; -1:Wrong PSW; 1: OK
    }
}

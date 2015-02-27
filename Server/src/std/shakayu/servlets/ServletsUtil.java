package std.shakayu.servlets;

import std.shakayu.STDUtil;
import std.shakayu.dbs.TSignup;

import javax.servlet.http.HttpServletRequest;

public class ServletsUtil {
    protected static boolean bDebug = false;
    
    protected static int CheckAuth(HttpServletRequest request){
        int nRes = 1;
        String sSession = request.getParameter("session");
        String sUsername = request.getParameter("username");
        String sPassword = request.getParameter("password");
        return nRes;
    }

    protected static int SignUp(HttpServletRequest request){
        int nRes = 0;
        String sEmail = request.getParameter("email");
        String sPSW = request.getParameter("password");
        String sUsername = request.getParameter("username");
        String sSource = request.getParameter("source");
        if(!STDUtil.IsStringAvaliable(sEmail,false)){
            nRes = -1;
        }else if(!STDUtil.IsStringAvaliable(sPSW,false)){
            nRes = -2;
        }else if(!STDUtil.IsStringAvaliable(sUsername,false)){
            nRes = -3;
        }else if(!STDUtil.IsStringAvaliable(sSource,false)){
            nRes = -4;
        }else{
            TSignup t = new TSignup(bDebug);
            t.InsertSignupInfo(sEmail,sPSW,sUsername,sSource);
            t.Close();
            nRes = 1;
        }
        return nRes;
    }
    
    protected static int AddItem(HttpServletRequest request){
        int nRes = 0;
        String sTag = request.getParameter("tag");
        String sText = request.getParameter("text");
        String sTime = request.getParameter("time");
        
        
        return nRes;
    }
}

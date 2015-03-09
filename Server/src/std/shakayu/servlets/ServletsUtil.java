package std.shakayu.servlets;

import org.json.JSONException;
import org.json.JSONObject;
import std.shakayu.STDUtil;
import std.shakayu.dbs.TListInfo;
import std.shakayu.dbs.TSignup;
import std.shakayu.dbs.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

public class ServletsUtil {
    protected static boolean    bDebug          = true;
    protected static int        EMAIL           = 0;
    protected static int        PSW             = 1;
    protected static int        USERNAME        = 2;
    protected static int        SOURCE          = 3;
    protected static int        ITEMID          = 0;
    protected static int        DO              = 1;
    protected static int        DESCRIPTION     = 2;
    protected static int        DEADLINE        = 3;
    protected static int        STARTTIME       = 4;
    protected static int        RECORDTIME      = 5;
    protected static int        STATUS          = 6;
    protected static int        TAG             = 7;
    protected static int        NEEDALARM       = 8;
    protected static int        REPEATING       = 9;
    protected static int        LOCATION        = 10;
    protected static int        PRIORITY        = 11;

    protected static boolean CheckSession(HttpServletRequest request) {
        HttpSession httpsession = request.getSession(false);
        boolean bRes = false;
        if(httpsession == null){
            return bRes;
        }
        String sUserEmail = (String)httpsession.getAttribute("useremail");
        if(STDUtil.IsStringAvaliable(sUserEmail,false)){
            User user = new User(bDebug);
            bRes = user.IsUserExists(sUserEmail);
        }
        return bRes;
    }

    private static String[] GetSignupData(HttpServletRequest request){
        String[] data = new String[SOURCE +1];
        data[EMAIL] = request.getParameter("email");
        data[PSW] = request.getParameter("password");
        data[USERNAME] = request.getParameter("username");
        data[SOURCE] = request.getParameter("source");
        return data;
    }
    
    protected static int SignUp(HttpServletRequest request){
        String[] data = GetSignupData(request);
        int nRes = STDUtil.CheckStringArrayAvaliable(data,false);
        STDUtil.PrintDebug("ServletsUtil.Signup.nRes = ",(Integer)nRes,bDebug);
        if(nRes == 1){
            User user = new User(bDebug);
            if(!user.IsUserExists(data[EMAIL])) {
                user.Signup(data[EMAIL], data[PSW], data[USERNAME], data[SOURCE]);
            }else{
                nRes = 2;
                STDUtil.PrintDebug("User exists.","",bDebug);
            }
            user.Close();
        }
        return nRes;
    }

    private static String[] GetLoginData(HttpServletRequest request){
        String[] data = new String[PSW +1];
        data[EMAIL] = request.getParameter("email");
        data[PSW] = request.getParameter("password");
        return data;
    }
    
    protected static int Login(HttpServletRequest request){
        String[] data = GetLoginData(request);
        int nRes = STDUtil.CheckStringArrayAvaliable(data,false);
        if(nRes == 1){
            User user = new User(bDebug);
            nRes = user.CanUserLogin(data[EMAIL],data[PSW]);
            user.Close();
            if(nRes == 1){
                HttpSession httpsession = request.getSession(true);
                httpsession.setAttribute("useremail", data[EMAIL]);
            }
        }
        return nRes;
    }
    
    protected static void Logout(HttpServletRequest request){
        HttpSession httpsession = request.getSession(true);
        httpsession.invalidate();
    }
    
    private static String[] GetItemData(HttpServletRequest request){
        String[] data = new String[PRIORITY+1];
        data[ITEMID] = STDUtil.MD5(STDUtil.CurrentTimeMillis());
        data[DO] = request.getParameter("do");
        data[DESCRIPTION] = request.getParameter("description");
        data[DEADLINE] = request.getParameter("deadline");
        data[STARTTIME] = request.getParameter("starttime");
        data[RECORDTIME] = request.getParameter("recordtime");
        data[STATUS] = request.getParameter("status");
        data[TAG] = request.getParameter("tag");
        data[NEEDALARM] = request.getParameter("needalarm");
        data[REPEATING] = request.getParameter("repeating");
        data[LOCATION] = request.getParameter("location");
        data[PRIORITY] = request.getParameter("priority");
        return data;
    }
    
    protected static int AddItem(HttpServletRequest request){
        String[] data = GetItemData(request);
        String sUseremail = request.getParameter("email");
        int nRes = 0;
        if(!STDUtil.IsStringAvaliable(sUseremail,false)){
            STDUtil.PrintDebug("ServletsUtil.AddItem.nRes = ",(Integer)nRes,bDebug);
            return nRes;
        }
        
        nRes = STDUtil.CheckStringArrayAvaliable(data,false);
        if(nRes == 1){
            User user = new User(bDebug);
            user.AddItem(sUseremail, data[ITEMID], data[DO], data[DESCRIPTION], data[DEADLINE],
                    data[STARTTIME], data[STATUS], data[TAG], data[NEEDALARM], data[REPEATING],
                    data[LOCATION], data[PRIORITY]);
            user.Close();
        }
        STDUtil.PrintDebug("ServletsUtil.AddItem.nRes = ",(Integer)nRes,bDebug);
        return nRes;
    }

    protected static HashMap GetItemInfo(HttpServletRequest request){
        String sUseremail = request.getParameter("email");
        String sItemID = request.getParameter("itemid");
        return GetItemInfo(sUseremail, sItemID);
    }

    protected static HashMap GetItemInfo(String sUseremail, String sItemID){
        HashMap<String, String> iteminfo = new HashMap<>();
        if(STDUtil.IsStringAvaliable(sUseremail,false) && STDUtil.IsStringAvaliable(sItemID, false)){
            User user = new User(bDebug);
            iteminfo = user.GetItemInfo(sUseremail,sItemID);
            user.Close();
        }
        return iteminfo;
    }

    protected static ArrayList ListItemInfo(String sUserEmail){
        ArrayList<HashMap> infolist = new ArrayList<>();
        User user = new User(bDebug);
        ArrayList<String> idlist = user.GetItemIDList(sUserEmail);
        String sItemID = "";
        for(int i = 0; i<idlist.size();i++){
            sItemID = idlist.get(i);
            HashMap info = user.GetItemInfo(sUserEmail, sItemID);
            infolist.add(info);
        }
        user.Close();
        return infolist;
    }

    private static String[] GetItemData(HashMap<String, String> iteminfo){
        String[] data = new String[PRIORITY+1];
        data[ITEMID] = iteminfo.get("ITEMID");
        data[DO] = iteminfo.get("DO");
        data[DESCRIPTION] = iteminfo.get("DESCRIPTION");
        data[DEADLINE] = iteminfo.get("DEADLINE");
        data[STARTTIME] = iteminfo.get("STARTTIME");
        data[RECORDTIME] = iteminfo.get("RECORDTIME");
        data[STATUS] = iteminfo.get("STATUS");
        data[TAG] = iteminfo.get("TAB");
        data[NEEDALARM] = iteminfo.get("NEEDALARM");
        data[REPEATING] = iteminfo.get("REPEATING");
        data[LOCATION] = iteminfo.get("LOCATION");
        data[PRIORITY] = iteminfo.get("PRIORITY");
        return data;
    }
    
    protected static JSONObject GetToDoItems(HttpServletRequest request){
        String sUserEmail = request.getParameter("email");
        if(!STDUtil.IsStringAvaliable(sUserEmail,false)){
            return null;
        }
        ArrayList<HashMap> items = ListItemInfo(sUserEmail);
        if(items!=null && items.size()!=0){
            try {
                JSONObject jItems = new JSONObject();
                for(int i = 0; i<items.size(); i++){
                    HashMap<String, String> iteminfo = items.get(i);
                    String[] data = GetItemData(iteminfo);
                    JSONObject jItem = new JSONObject();
                    jItem.put("ITEMID",data[ITEMID]);
                    jItem.put("DO",data[DO]);
                    jItem.put("DESCRIPTION",data[DESCRIPTION]);
                    jItem.put("DEADLINE",data[DEADLINE]);
                    jItem.put("STARTTIME",data[STARTTIME]);
                    jItem.put("RECORDTIME",data[RECORDTIME]);
                    jItem.put("STATUS",data[STATUS]);
                    jItem.put("TAG",data[TAG]);
                    jItem.put("NEEDALARM",data[NEEDALARM]);
                    jItem.put("REPEATING",data[REPEATING]);
                    jItem.put("LOCATION",data[LOCATION]);
                    jItem.put("PRIORITY",data[PRIORITY]);
                    
                    jItems.put(data[ITEMID],jItem);
                }
                return jItems;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            
        }else{
            return null;
        }
    }
}

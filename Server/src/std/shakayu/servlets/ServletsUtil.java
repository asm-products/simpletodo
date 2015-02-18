package std.shakayu.servlets;

import javax.servlet.http.HttpServletRequest;

public class ServletsUtil {
    public static int CheckAuth(HttpServletRequest request){
        int nRes = 0;
        String sSession = request.getParameter("session");
        String sUsername = request.getParameter("username");
        String sPassword = request.getParameter("password");
        return nRes;
    }

    public static int AddItem(HttpServletRequest request){
        int nRes = 0;
        String sTag = request.getParameter("tag");
        String sText = request.getParameter("text");
        String sTime = request.getParameter("time");
        
        
        return nRes;
    }
}

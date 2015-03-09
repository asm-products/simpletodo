package std.shakayu.servlets;

import org.json.JSONObject;
import std.shakayu.STDUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class ServletsMgr {
    protected static boolean bDebug = true;
    
    public static class Welcome extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            if(ServletsUtil.CheckSession(request)){
                PrintWriter out = response.getWriter();
                out.println("<html><body><h1><center>Welcome to SimpleToDo!<br></center></h1></body></html>");
                STDUtil.PrintDebug("ServletsMgr.Welcome.doGet: ", "visited", bDebug);
            }else{
                response.sendRedirect("/");
            }
            
        }
    }
    public static class Door extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1><center>Need Login<br></center></h1></body></html>");
            STDUtil.PrintDebug("ServletsMgr.Door.doGet: ", "visited", bDebug);
        }
    }

    public static class SignUp extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.SignUp.doGet: ", "visited", bDebug);
            response.sendRedirect("/welcome");
        }
        
        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException{
            if(ServletsUtil.CheckSession(request)){
                response.sendRedirect("/welcome");
            }else{
                ServletsUtil.SignUp(request);
            }
        }
    }

    public static class Login extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.Login.doGet: ", "visited", bDebug);
            response.sendRedirect("/welcome");
        }

        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.Login.doPost: ", "visited", bDebug);
            int nRes = ServletsUtil.Login(request);
            STDUtil.PrintDebug("ServletsMgr.Login: login res code: ", nRes, bDebug);
        }
    }

    public static class Logout extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            ServletsUtil.Logout(request);
            STDUtil.PrintDebug("ServletsMgr.Logout","", bDebug);
        }
    }
    
    public static class AddItem extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.AddItem.doGet: ", "visited", bDebug);
            response.sendRedirect("/welcome");
        }
        
        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException{
            if(ServletsUtil.CheckSession(request)){
                ServletsUtil.AddItem(request);
            }else{
                response.sendRedirect("/");
            }
        }
    }

    public static class ListItemInfo extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.GetItemInfo.doGet: ", "visited", bDebug);
            response.sendRedirect("/welcome");
        }

        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException{
            if(ServletsUtil.CheckSession(request)){
                JSONObject jItems = ServletsUtil.GetToDoItems(request);
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=utf-8");
                response.setCharacterEncoding("utf-8");
                out.print(jItems);
                out.close();
            }else{
                response.sendRedirect("/");
            }
        }
    }
}
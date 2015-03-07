package std.shakayu.servlets;

import std.shakayu.STDUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletsMgr {
    protected static boolean bDebug = true;
    
    public static class Welcome extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1><center>Welcome to SimpleToDo!<br></center></h1></body></html>");
            STDUtil.PrintDebug("ServletsMgr.Welcome.doGet: ", "visited", bDebug);
        }
    }

    public static class SignUp extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.SignUp.doGet: ", "visited", bDebug);
            response.sendRedirect("/");
        }
        
        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.SignUp.doPost: ", "visited", bDebug);
            int nAuth = ServletsUtil.CheckAuth(request);
            switch (nAuth){
                case 1:
                    ServletsUtil.SignUp(request);
                    break;
                case -1:
                    //response.sendRedirect("/login?ercode=-1");
                    break;
                default:
                    //response.sendRedirect("/login?ercode=0");
                    break;
            }
        }
    }

    public static class Login extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.Login.doGet: ", (String)"visited", bDebug);
            response.sendRedirect("/");
        }

        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.Login.doPost: ", (String)"visited", bDebug);
            int nRes = ServletsUtil.Login(request);
            if(bDebug){
                STDUtil.PrintDebug("ServletsMgr.Login: login res code: ", nRes, bDebug);
            }
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
            STDUtil.PrintDebug("ServletsMgr.AddItem.doGet: ", (String)"visited", bDebug);
            response.sendRedirect("/");
        }
        
        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException{
            STDUtil.PrintDebug("ServletsMgr.AddItem.doPost: ", (String)"visited", bDebug);
            int nAuth = ServletsUtil.CheckAuth(request);
            switch (nAuth){
                case 1:
                    ServletsUtil.AddItem(request);
                    break;
                case -1:
                    //response.sendRedirect("/login?ercode=-1");
                    break;
                default:
                    //response.sendRedirect("/login?ercode=0");
                    break;
            }
        }
    }
}
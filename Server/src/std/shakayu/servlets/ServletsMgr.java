package std.shakayu.servlets;


import std.shakayu.dbs.DB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletsMgr {
    public static class Welcome extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response)
                throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            out.println("<html><body><h1><center>Welcome to SimpleToDo!<br></center></h1></body></html>");
            DB db = new DB("STD","simpletodo","simpletodo",false);
            db.Close();
        }
    }

    public static class AddItem extends HttpServlet {
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response)
                throws ServletException, IOException {
            //response.sendRedirect("/");
        }
        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response)
                throws ServletException, IOException {
            int nAuth = ServletsUtil.CheckAuth(request);
            switch (nAuth){
                case 1:
                    ServletsUtil.AddItem(request);
                    break;
                case -1:
                    response.sendRedirect("/login?ercode=-1");
                    break;
                default:
                    response.sendRedirect("/login?ercode=0");
                    break;
            }
        }
    }
}
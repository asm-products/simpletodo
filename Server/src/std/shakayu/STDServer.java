package std.shakayu;


import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import std.shakayu.servlets.ServletsMgr;

public class STDServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[] { connector });

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHandler servlet = new ServletHandler();
        servlet.addServletWithMapping(ServletsMgr.Welcome.class, "/");
        context.setHandler(servlet);

        server.setHandler(context);
        server.start();
        server.join();
    }
}

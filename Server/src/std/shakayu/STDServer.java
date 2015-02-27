package std.shakayu;


import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import std.shakayu.servlets.ServletsMgr;

public class STDServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(STDUtil.HTTPPORT);
        
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("Key/std");
        sslContextFactory.setKeyStorePassword(STDAuth.DBUSERNAME);
        sslContextFactory.setKeyManagerPassword(STDAuth.DBUSERNAME);
        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(STDUtil.HTTPSPORT);

        server.setConnectors(new Connector[] { connector, sslConnector });

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHandler servlet = new ServletHandler();
        servlet.addServletWithMapping(ServletsMgr.Welcome.class, "/");
        servlet.addServletWithMapping(ServletsMgr.SignUp.class, "/signup");
        servlet.addServletWithMapping(ServletsMgr.AddItem.class, "/additem");
        context.setHandler(servlet);

        server.setHandler(context);
        server.start();
        server.join();
    }
}

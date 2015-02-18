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
/*
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("Key/webmail.jks");
        sslContextFactory.setKeyStorePassword("webmail");
        sslContextFactory.setKeyManagerPassword("webmail");

        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(8443);
*/
        server.setConnectors(new Connector[] { connector });

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHandler servlet = new ServletHandler();

        servlet.addServletWithMapping(ServletsMgr.Welcome.class, "/");

        context.setHandler(servlet);
/*
        NCSARequestLog RequestLog = new NCSARequestLog();
        RequestLog.setFilename(XMailUtil.LOGPATH + "yyyy_mm_dd.jettyrequest.log");
        RequestLog.setFilenameDateFormat("yyyy_MM_dd");
        RequestLog.setRetainDays(90);
        RequestLog.setAppend(true);
        RequestLog.setExtended(true);
        RequestLog.setLogCookies(false);
        RequestLog.setLogTimeZone("GMT");

        RequestLogHandler RequestLogHandler = new RequestLogHandler();
        RequestLogHandler.setRequestLog(RequestLog);

        RequestLogHandler.setHandler(context);
        server.setHandler(RequestLogHandler);
        */
        server.setHandler(context);

        ////////////////////////////////////////////////////
        //The handler chain looks like this:
        //Server
        //  |-RequestLogHandler
        //      |- ServletContextHandler
        //          |- ServletHandler
        //              |- (servlet)
        //              |- (servlet)
        ////////////////////////////////////////////////////

        server.start();
        server.join();
    }
}

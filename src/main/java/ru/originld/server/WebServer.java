package ru.originld.server;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.originld.utils.JettyPropertyReader;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URL;

/**
 * Created by redin on 5/3/17.
 */
public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String CONTEXT_PATH ="/";
    private static final String PROJECT_RELATIVE_PATH_TO_WEBAPP ="src/main/webapp";
    private static final String TEMP_DIRECTORY ="/src/%s";

    private Server server;

    private String pathToLog;
    private int retainDays;
    private int port;

    public WebServer() throws Exception {
        loadConfig();
        this.server = new Server(port);
    }

    public void start() throws Exception {
        setupServer();

        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void setupServer() {
        WebAppContext ctx = createWebAppContext();
        HandlerCollection handlerCollection = createHeandlerCollection(ctx);
        server.setHandler(handlerCollection);
        server.setStopAtShutdown(true);
    }

    private HandlerCollection createHeandlerCollection(WebAppContext ctx) {
        ctx.setSessionHandler(createSessionHandler());

        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(createRequestLog());

        HandlerCollection result = new HandlerCollection();
        result.setHandlers(new Handler[]{ctx,requestLogHandler});

        return result;
    }

    private RequestLog createRequestLog() {
        NCSARequestLog requestLog = new NCSARequestLog();

        File logPath = new File(pathToLog);
        logPath.getParentFile().mkdirs();

        requestLog.setFilename(logPath.getPath());
        requestLog.setRetainDays(retainDays);
        requestLog.setExtended(false);
        requestLog.setAppend(true);
        requestLog.setLogTimeZone("GMT");
        requestLog.setLogLatency(true);
        return requestLog;
    }

    private SessionHandler createSessionHandler() {
        HashSessionManager sessionManager = new HashSessionManager();
        sessionManager.setSessionCookie(SessionManager.__DefaultSessionCookie +"_"+port);
        sessionManager.setSessionIdPathParameterName(
                SessionManager.__DefaultSessionIdPathParameterName + "_" + port);

        return new SessionHandler(sessionManager);

    }



    private WebAppContext createWebAppContext() {
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath(CONTEXT_PATH);
        URL location = App.class.getProtectionDomain().getCodeSource().getLocation();

        if(isRunningInJar()) {
            ctx.setWar(location.toExternalForm());
            File dir = new File(String.format(TEMP_DIRECTORY, port));
            if (dir.exists() || dir.mkdir()) {
                ctx.setTempDirectory(dir);
            }
        }
            else{
                ctx.setWar(App.class.getClassLoader().getResource(".").toExternalForm());
                ctx.setBaseResource(new ResourceCollection(new String[]{PROJECT_RELATIVE_PATH_TO_WEBAPP,location.toExternalForm()}));
                log.info("Running in debug environment");
            }

        return ctx;
    }

    private boolean isRunningInJar() {
        File runningFile = new File(WebServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        return runningFile.isFile();
    }

    private void loadConfig() throws Exception {
        JettyPropertyReader reader = new JettyPropertyReader();
        port = reader.getPort();
        retainDays = reader.getRetainsDays();
        pathToLog = reader.getLogPath();
    }

}


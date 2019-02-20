package com.nanoit.bkg.web;

import com.nanoit.bkg.web.api.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author cho_jeong_ha
 * @project DREAMLINE-BKG-AGENT
 * @update 2018-05-09
 **/
public class JettyWebServer implements Runnable {
    @Override
    public void run() {
        try {
            Server server = new Server(8080);

            URL webRootLocation = this.getClass().getResource("/monitor/monitor/monitor.html");
            if (webRootLocation == null) {
                throw new IllegalStateException("Unable to determine webroot URL location");
            }

            URI webRootUri = URI.create(webRootLocation.toURI().toASCIIString().replaceFirst("/monitor.html$", "/"));
            System.err.printf("Web Root URI: %s%n", webRootUri);

            ServletContextHandler context = new ServletContextHandler();
            context.setContextPath("/");
            context.setBaseResource(Resource.newResource(webRootUri));
            context.setWelcomeFiles(new String[]{"monitor.html"});
            context.getMimeTypes().addMimeMapping("txt", "text/plain;charset=utf-8");

            server.setHandler(context);

            // Add WebSocket endpoints
            //ServerContainer wsContainer = WebSocketServerContainerInitializer.configureContext(context);
            //wsContainer.addEndpoint(TimeSocket.class);

            // Add Servlet endpoints
            context.addServlet(Time.class, "/time/");
            context.addServlet(DefaultServlet.class, "/");

            // ADDDD
            context.addServlet(ThreadStatus.class, "/status/");
            context.addServlet(DeliveryToday.class, "/today/");
            context.addServlet(Memory.class,"/memory/");
            context.addServlet(Total.class, "/total/");

            // Start Server
            server.start();
            server.join();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

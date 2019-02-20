package com.nanoit.bkg.web.old;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The type One sevlet context.
 *
 * @author cho_jeong_ha
 * @project DREAMLINE -BKG-AGENT
 * @update 2018 -05-08
 */
public class OneSevletContext implements Runnable {
    private static Logger logger = (Logger) LoggerFactory.getLogger(OneSevletContext.class);
    /**
     * Instantiates a new One sevlet context.
     */
    public OneSevletContext() {

    }

    @Override
    public void run() {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Server content from tmp
        ServletHolder holder = context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class,"/*");
        holder.setInitParameter("resourceBase","/");
        holder.setInitParameter("pathInfoOnly","true");
        holder.setInitParameter("dirAllowed","true");

        // Serve some hello world servlets
        context.addServlet(new ServletHolder(new HelloServlet()),"/*");
        //context.addServlet(new ServletHolder(new HelloServlet("Buongiorno Mondo")),"/it/*");
        //context.addServlet(new ServletHolder(new HelloServlet("Bonjour le Monde")),"/fr/*");

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            server.dump(System.err);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

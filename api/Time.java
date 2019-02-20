package com.nanoit.bkg.web.api;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;


@SuppressWarnings("serial")
public class Time extends HttpServlet {
    private static final TimeZone TZ = TimeZone.getDefault();
    private String Time = "time";


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        JSONObject jsonObject = new JSONObject();
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        long time = ( end - start )/1000;

        jsonObject.put(Time,time);
        resp.getWriter().println(jsonObject);

    }

}

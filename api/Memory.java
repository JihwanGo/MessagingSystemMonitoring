package com.nanoit.bkg.web.api;


import com.nanoit.bkg.permanence.dto.MessageDto;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Memory extends HttpServlet {

    private String Free = "free";
    private String Total = "total";
    private String Max = "max";

    private String getFormatUsingMemory1(long l) {
        return String.format("%,.1f", (l) / 1048576.0D);
    }

    @Override

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MessageDto messageDto = new MessageDto();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(Total ,getFormatUsingMemory1(Runtime.getRuntime().totalMemory()));
        jsonObject.put(Free, getFormatUsingMemory1(Runtime.getRuntime().freeMemory()));
        jsonObject.put(Max, getFormatUsingMemory1(Runtime.getRuntime().maxMemory()));
        resp.getWriter().println(jsonObject);
    }
}

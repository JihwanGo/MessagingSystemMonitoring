//
//  ========================================================================
//  Copyright (c) 1995-2016 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package com.nanoit.bkg.web.old;

import com.nanoit.bkg.core.manage.thread.ThreadDto;
import com.nanoit.bkg.permanence.DataBaseHandler;
import com.nanoit.bkg.permanence.DataBaseHandlerFactory;
import com.nanoit.bkg.permanence.dto.MessageDto;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.nanoit.bkg.core.manage.thread.ThreadManager.access;

@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {
    private static Logger logger = (Logger) LoggerFactory.getLogger(HelloServlet.class);
    private String greeting = "Hello";
    private int count;

    private Calendar calendar;

    HelloServlet() {
        count = 0;
        calendar = Calendar.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (count == 0) {
                MessageDto messageDto = new MessageDto();

                // AUTO REFRESH
                response.setIntHeader("Refresh", 10);

                response.setContentType("text/html;charset=UTF-8"); // 한글 보정
                response.setStatus(HttpServletResponse.SC_OK);

                // HTML
                response.getWriter().println("<!doctype html>");
                response.getWriter().println("<html lang=\"ko\">");
                response.getWriter().println("<head>");
                response.getWriter().println("<meta charset=\"utf-8\">");
                response.getWriter().println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
                response.getWriter().println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");

                response.getWriter().println("<link rel=\"stylesheet\" href=\"resources/bootstrap.min.css\">"); //부트스트랩 css 추가
                //response.getWriter().println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">"); //부트스트랩 css 추가

                response.getWriter().println("<title>AGENT MONITOR V1</title>");

                response.getWriter().println("</head>");

                /* BODY 시작! */
                response.getWriter().println("<body>");
                response.getWriter().println("<h1>" + greeting + " from HelloServlet</h1><br/>");
                response.getWriter().println("<h2>테스트 중.. </h2><br/>");

                response.getWriter().println("<div class=\"row\">\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "  <div class=\"col-md-1\">.col-md-1</div>\n" +
                        "</div>\n" +
                        "<div class=\"row\">\n" +
                        "  <div class=\"col-md-8\">.col-md-8</div>\n" +
                        "  <div class=\"col-md-4\">.col-md-4</div>\n" +
                        "</div>\n" +
                        "<div class=\"row\">\n" +
                        "  <div class=\"col-md-4\">.col-md-4</div>\n" +
                        "  <div class=\"col-md-4\">.col-md-4</div>\n" +
                        "  <div class=\"col-md-4\">.col-md-4</div>\n" +
                        "</div>\n" +
                        "<div class=\"row\">\n" +
                        "  <div class=\"col-md-6\">.col-md-6</div>\n" +
                        "  <div class=\"col-md-6\">.col-md-6</div>\n" +
                        "</div>");

                response.getWriter().println("<div class=\"col-md-12\">");

                for (Map.Entry<String, DataBaseHandler> entry : DataBaseHandlerFactory.getDataBaseHandler().entrySet()) {
                    response.getWriter().println(String.format("[WEB-MONITOR] KEY:%s, DBMS:%s, IP:[%s:%s] / 전송 대기중인 메시지:%s<br/>", entry.getKey(), entry.getValue().gePropertiesDTO().getDBMS(), entry.getValue().gePropertiesDTO().getIP(), entry.getValue().gePropertiesDTO().getPORT(), entry.getValue().monitorSelectCount("webmonitor-wait-for-transmission", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix())));
                }

                for (Pair<String, ThreadDto> threadDto : access().getSortMap()) {
                    calendar.setTimeInMillis(threadDto.getValue().getLastCheckRunningTime());
                    response.getWriter().println(String.format("[WEB-MONITOR] KEY [%s] 상태 [%s] 마지막 동작 체크 시간 [%s] 마지막 동작 체크 시간과 현재 시간의 차이 [%s]<br/>", threadDto.getKey(), threadDto.getValue().getStatus(), getBeforeCheckTime(), getDefferenceTime(threadDto.getValue())));
                }

                response.getWriter().println("</div>");

                /* 부트스트랩 js, jquery 추가 */
                response.getWriter().println("<script src=\"resources/jquery.min.js\"></script>");
                response.getWriter().println("<script src=\"resources/bootstrap.min.js\"></script>");

                /*response.getWriter().println("<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>");
                response.getWriter().println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>");
                response.getWriter().println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>");*/

                response.getWriter().println("<body>");
                response.getWriter().println("</html>");
                count++;
            } else {
                count = 0;
            }
        } catch (Exception e) {
            response.setIntHeader("Refresh", 10);
            response.setContentType("text/html;charset=UTF-8"); // 한글 보정
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            logger.error("MONITOR RESPONSE ERROR", e);
        }
    }

    private String getBeforeCheckTime() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss,SSS").format(calendar.getTime());
    }

    private String getDefferenceTime(ThreadDto threadDto) {
        return new SimpleDateFormat("mm:ss,SSS").format(new Date(System.currentTimeMillis() - threadDto.getLastCheckRunningTime()));
    }
}

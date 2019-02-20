package com.nanoit.bkg.web.api;

import com.nanoit.bkg.core.manage.thread.ThreadDto;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.nanoit.bkg.core.manage.thread.ThreadManager.access;

/**
 * @author cho_jeong_ha
 * @project DREAMLINE-BKG-AGENT
 * @update 2018-05-09
 **/
public class ThreadStatus extends HttpServlet {
    private Calendar calendar = Calendar.getInstance();

    private String THREAD = "service";
    private String STATUS = "status";
    private String BEFORE = "before";
    private String CURRENT = "current";
    private String LAST_CHECK_TIME = "lasttime";
    private String DIFFERENCE = "difference";

    private String DBMS = "dbms";
    private String IP = "ip";
    private String WAIT_SEND_COUNT = "count";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        for (Pair<String, ThreadDto> threadDto : access().getSortMap()) {
            calendar.setTimeInMillis(threadDto.getValue().getLastCheckRunningTime());
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(THREAD, threadDto.getKey());
            jsonObject.put(STATUS, threadDto.getValue().getStatus());
            jsonObject.put(LAST_CHECK_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date(threadDto.getValue().getLastCheckRunningTime()))); // 마지막 체크 시간
            jsonObject.put(DIFFERENCE, getDifferenceTime(threadDto.getValue())); // (현재 시간) - (마지막 체크 시간) = 시간 차이 표시
            // jsonObject.put(BEFORE, getBeforeCheckTime());
            // jsonObject.put(CURRENT,getDefferenceTime(threadDto.getValue()));
            jsonArray.put(jsonObject);
            // resp.getWriter().println(String.format("[WEB-MONITOR] KEY [%s] 상태 [%s] 마지막 동작 체크 시간 [%s] 마지막 동작 체크 시간과 현재 시간의 차이 [%s]<br/>", threadDto.getKey(), threadDto.getValue().getStatus(), getBeforeCheckTime(), getDefferenceTime(threadDto.getValue())));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thr",jsonArray);

        resp.getWriter().println(jsonObject);
    }

    private String getBeforeCheckTime() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss,SSS").format(calendar.getTime());
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss,SSS").format(new Date());
    }

    private String getDifferenceTime(ThreadDto threadDto) {
        return new SimpleDateFormat("mm:ss,SSS").format(new Date(System.currentTimeMillis() - threadDto.getLastCheckRunningTime()));
    }
}

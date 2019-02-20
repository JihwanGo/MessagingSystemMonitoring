package com.nanoit.bkg.web.api;

import com.nanoit.bkg.permanence.DataBaseHandler;
import com.nanoit.bkg.permanence.DataBaseHandlerFactory;
import com.nanoit.bkg.permanence.dto.MessageDto;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 전송 대기 메시지 파싱.
 *
 * @author cho_jeong_ha
 * @project NANOIT-BKG-AGENT
 * @update 2018-05-09
 **/
public class DeliveryToday extends HttpServlet {
    private String THREAD = "name";
    private String WAIT_SEND_COUNT = "fail";
    private String SENT_COUNT = "send";
    private String DONE = "done";
    private String TABLENAME = "tablename";
    private String TABLENAMEL = "tablenameL";

    private String msgTableName;
    private String logTableName;
    protected MessageDto fromPermanence;


    private static final TimeZone TZ = TimeZone.getDefault();
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MessageDto messageDto = new MessageDto();
        JSONArray jsonArray = new JSONArray();

        for (Map.Entry<String, DataBaseHandler> entry : DataBaseHandlerFactory.getDataBaseHandler().entrySet()) {
            JSONObject jsonObject = new JSONObject();

            messageDto.setMonitorStartTime(formatter.format(new Date()).substring(0, 8).concat("000000"));
            messageDto.setMonitorEndTime(formatter.format(new Date()).substring(0, 8).concat("235959"));
            messageDto.setMonitorStatus(0);

            if (entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB1") || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB2") || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB3")
                || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB4") || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB5")) {
                jsonObject.put("msg_type", "SMS");
                jsonObject.put("msb", entry.getValue().monitorSelectCount("webmonitor-for-today-succ", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                messageDto.setMonitorStatus(99);

                int fail = entry.getValue().monitorLogCount("webmonitor-for-today-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("msf", fail);
                jsonObject.put("mst", entry.getValue().monitorSelectCount("webmonitor-for-today-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());

            }

            if (entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB1") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB2") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB3")
                    || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB4") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB5")) {
                jsonObject.put("msg_type", "MMS");
                messageDto.setMonitorMMSFilecnt(0);
                jsonObject.put("msb", entry.getValue().monitorLogCount("webmonitor-for-today-succ", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                messageDto.setMonitorMMSStatus(99);
                messageDto.setMonitorMMSFilecnt(0);
                int fail = entry.getValue().monitorLogCount("webmonitor-for-today-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("msf", fail);
                messageDto.setMonitorMMSFilecnt(0);
                jsonObject.put("mst", entry.getValue().monitorSelectCount("webmonitor-for-today-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());


            }
            if (entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB1") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB2") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB3")
                    || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB4") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB5")) {

                jsonObject.put("msg_typeL", "LMS");
                messageDto.setMonitorLmsFilecnt(0);
                messageDto.setMonitorLmsStatus(0);
                jsonObject.put("lmsb", entry.getValue().monitorLogCount("webmonitor-for-today-succ-lms", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                messageDto.setMonitorLmsStatus(99);
                messageDto.setMonitorLmsFilecnt(0);
                int fail1 = entry.getValue().monitorLogCount("webmonitor-for-today-fail-lms", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("lmsf", fail1);
                messageDto.setMonitorLmsFilecnt(0);
                jsonObject.put("lmst", entry.getValue().monitorSelectCount("webmonitor-for-today-lms-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAMEL, messageDto.getMsgTableNM());

            }

            if (entry.getValue().gePropertiesDTO().getKEY().contains("KAKAODB1") || entry.getValue().gePropertiesDTO().getKEY().contains("KAKAODB2") || entry.getValue().gePropertiesDTO().getKEY().contains("KAKAODB3")) {
                jsonObject.put("msg_type", "KKO");
                jsonObject.put("kkob", entry.getValue().monitorLogCount("webmonitor-for-today-succ", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                messageDto.setMonitorStatus(99);
                int fail = entry.getValue().monitorLogCount("webmonitor-for-today-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("kkof", fail);
                jsonObject.put("kkot", entry.getValue().monitorSelectCount("webmonitor-for-today-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());
            }

            if (entry.getValue().gePropertiesDTO().getKEY().contains("GMS")) {
                jsonObject.put("msg_type","GMS");
                jsonObject.put("gmsb", entry.getValue().monitorSelectCount("webmonitor-for-today-succ", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                messageDto.setMonitorStatus(99);
                int fail = entry.getValue().monitorLogCount("webmonitor-for-today-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("gmsf", fail);
                jsonObject.put("gmst", entry.getValue().monitorSelectCount("webmonitor-for-today-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());
            }

            if (entry.getValue().gePropertiesDTO().getKEY().contains("SMS"))
                messageDto.setMonitorStatus(1);
            else
                messageDto.setMonitorStatus(2);

            jsonArray.put(jsonObject);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("today", jsonArray);

        resp.getWriter().println(jsonObject);

    }
}
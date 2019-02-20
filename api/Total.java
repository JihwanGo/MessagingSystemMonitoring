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
import java.util.Map;



public class Total extends HttpServlet {
    private String TOTAL = "total";
    private String SMS = "sms";
    private String MMS = "mms";
    private String LMS = "lms";
    private String KKO = "kko";
    private String GMS = "gms";
    private String SUCC = "succ";
    private String FAIL = "fail";
    private String SUCCL = "succl"; //lms
    private String FAILL = "faill"; //lms
    private String TABLENAME = "tablename";
    private String TABLENAMEL = "tablenameL";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        MessageDto messageDto = new MessageDto();
        JSONArray jsonArray = new JSONArray();

        for (Map.Entry<String, DataBaseHandler> entry : DataBaseHandlerFactory.getDataBaseHandler().entrySet()){
            JSONObject jsonObject = new JSONObject();

            messageDto.setMonitorStatus(0);
            if (entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB1") || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB2") || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB3")
                    || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB4") || entry.getValue().gePropertiesDTO().getKEY().contains("SMSDB5")) {
                jsonObject.put("msg_type", "SMS");
                jsonObject.put("mst", entry.getValue().monitorLogCount("webmonitor-wait-for-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                messageDto.setMonitorStatus(99);
                int fail = entry.getValue().monitorLogCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("msf", fail);
                messageDto.setMonitorStatus(0);
                jsonObject.put("mss", entry.getValue().monitorSelectCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());
//                int total = entry.getValue().monitorLogCount("webmonitor-wait-for-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
//                jsonObject.put(SMS, total);
//
//
//                messageDto.setMonitorStatus(99);
//                int fail = entry.getValue().monitorLogCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
//                jsonObject.put(FAIL, fail);
//
//
//                messageDto.setMonitorStatus(0);
//                jsonObject.put(SUCC, entry.getValue().monitorLogCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
            }

            if (entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB1") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB2") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB3")
                    || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB4") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB5")) {
                jsonObject.put("msg_type", "MMS");
                messageDto.setMonitorMMSFilecnt(1);
                int total = entry.getValue().monitorLogCount("webmonitor-for-total-mms", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("mmst", total);

                messageDto.setMonitorMMSStatus(99);
                messageDto.setMonitorMMSFilecnt(1);
                int fail = entry.getValue().monitorLogCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("mmsf", fail);

                messageDto.setMonitorMMSStatus(0);
                messageDto.setMonitorMMSFilecnt(1);
                int succ = entry.getValue().monitorLogCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("mmss", succ);
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());

            }
            if (entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB1") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB2") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB3")
                    || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB4") || entry.getValue().gePropertiesDTO().getKEY().contains("MMSDB5")) {
                jsonObject.put("msg_typeL", "LMS");
                messageDto.setMonitorLmsFilecnt(0);
                int total1 = entry.getValue().monitorLogCount("webmonitor-for-total-lms", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("lmst", total1);
                //문제없음

                messageDto.setMonitorLmsStatus(99);
                messageDto.setMonitorLmsFilecnt(0);
                int fail1 = entry.getValue().monitorLogCount("webmonitor-for-lms-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("lmsf", fail1);

                messageDto.setMonitorLmsStatus(0);
                messageDto.setMonitorLmsFilecnt(0);
                int succ1 = entry.getValue().monitorLogCount("webmonitor-for-lms-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("lmss", succ1);
                jsonObject.put(TABLENAMEL, messageDto.getMsgTableNM());

            }
            if (entry.getValue().gePropertiesDTO().getKEY().contains("KAKAO")){
                jsonObject.put("msg_type","KAKAO");
                int total = entry.getValue().monitorSelectCount("webmonitor-for-total", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("kkot", total);

                messageDto.setMonitorStatus(99);
                int fail = entry.getValue().monitorLogCount("webmonitor-for-total-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("kkof", fail);

                messageDto.setMonitorStatus(0);
                jsonObject.put("kkos", entry.getValue().monitorLogCount("webmonitor-for-total-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());

            }
            if (entry.getValue().gePropertiesDTO().getKEY().contains("GMS")){
                jsonObject.put("msg_type", "GMS");
                int total = entry.getValue().monitorSelectCount("webmonitor-for-today", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("gmst", total);

                messageDto.setMonitorStatus(99);
                int fail = entry.getValue().monitorLogCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix());
                jsonObject.put("gmsf", fail);

                messageDto.setMonitorStatus(0);
                jsonObject.put("gmss", entry.getValue().monitorLogCount("webmonitor-for-succ-or-fail", messageDto, entry.getValue().getMyBatisQueryName().getLogTableSuffix()));
                jsonObject.put(TABLENAME, messageDto.getMsgTableNM());
            }
            jsonArray.put(jsonObject);

        }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total",jsonArray);
            resp.getWriter().println(jsonObject);

    }
}

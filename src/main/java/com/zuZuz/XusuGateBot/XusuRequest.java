package com.zuZuz.XusuGateBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuZuz.Main;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class XusuRequest {
    private final static Logger logger = Logger.getLogger(XusuRequest.class);
    private final static ObjectMapper mapper = new ObjectMapper();

    private final static String API_URL = "https://xu.su/api/send";
    private final static String USER_AGENT = "Mozilla/5.0";
    private static URL url = null;

    static {
        try {
            url = new URL(API_URL);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static XusuMessage doRequest(String text, String uid) throws Exception {
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");

        XusuMessage message = new XusuMessage(uid, "main", text.getBytes("CP1251").toString());
        con.setRequestProperty("Content-Length", String.valueOf(mapper.writeValueAsString(message).length()));

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(mapper.writeValueAsString(message));
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String input;
        StringBuffer buffer = new StringBuffer();

        while ((input = in.readLine())!= null) {
            buffer.append(input);
        }
        in.close();

        return mapper.readValue(buffer.toString(), XusuMessage.class);
    }
}

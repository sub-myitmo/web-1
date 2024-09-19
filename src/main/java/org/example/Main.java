package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import com.fastcgi.*;

public class Main {
    private static final String BASE_RESPONSE = """
            Access-Control-Allow-Origin: *
            Connection: keep-alive
            Content-Type: application/json
            Content-Length: %d
                        
            %s
            """;

    public static void main(String[] args) {
        FCGIInterface fcgi = new FCGIInterface();

        while (fcgi.FCGIaccept() >= 0) {
            Date nowDate = new Date();
            HashMap<String, String> params = parse(FCGIInterface.request.params.getProperty("QUERY_STRING"));

            Checker check = new Checker(params);

            String response;

            if (check.validate()) {
                long time = new Date().getTime() - nowDate.getTime();
                response = createJson(BASE_RESPONSE, String.format("{\"result\": %b, \"time\": %d}", check.isHit(), time));
            } else {
                response = createJson(BASE_RESPONSE, "{\"error\": \" incorrect data\"}");
            }

            System.out.println(response);
        }
    }

    private static String createJson(String type, String answer) {
        return String.format(type, answer.getBytes(StandardCharsets.UTF_8).length, answer);
    }

    private static HashMap<String, String> parse(String jsonStr) {
        HashMap<String, String> params = new HashMap<String, String>();
        String[] pairs = jsonStr.split("&");
        for (String pair : pairs) {

            String[] keyValue = pair.split("=");

            if (keyValue.length > 1) {
                params.put(keyValue[0], keyValue[1]);
            } else {
                params.put(keyValue[0], "");
            }
        }

        return params;
    }

}
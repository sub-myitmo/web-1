package org.example;

import java.nio.charset.StandardCharsets;
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

            long startTime = System.nanoTime();

            HashMap<String, String> params = parse(FCGIInterface.request.params.getProperty("QUERY_STRING"));

            Checker check = new Checker(params);

            String response;

            if (check.validate()) {
                long endTime = System.nanoTime();
                long elapsedTime = (endTime - startTime)/1000;
                response = createJson(String.format("{\"result\": %b, \"time\": %d}", check.isHit(), elapsedTime));
            } else {
                response = createJson("{\"error\": \" incorrect data\"}");
            }

            System.out.println(response);
        }
    }

    private static String createJson(String answer) {
        return String.format(BASE_RESPONSE, answer.getBytes(StandardCharsets.UTF_8).length, answer);
    }

    private static HashMap<String, String> parse(String jsonStr) {
        HashMap<String, String> params = new HashMap<>();
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
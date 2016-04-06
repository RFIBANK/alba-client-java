package ru.rficb.alba;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;


public class RestRequester {
    private Logger logger;
    private int pendingRetries;
    private static final int SLEEP_WHILE_PENDING_MS = 5000;
    private static final int defaultPendingRetries = 10;
    private static final String disableLogParameter = "card";

    public RestRequester(Logger logger) {
        this.pendingRetries = defaultPendingRetries;
        this.logger = logger;
    }

    public int getPendingRetries() {
        return pendingRetries;
    }

    public void setPendingRetries(int pendingRetries) {
        this.pendingRetries = pendingRetries;
    }

    private String buildUrlParams(Map<String, String> params) throws UnsupportedEncodingException, AlbaFatalError {
        StringBuilder sb = new StringBuilder();

        for (Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            if (entry.getValue() == null) {
                throw new AlbaFatalError("The parameter " + entry.getKey() + " is required");
            }

            sb.append(String.format(
                    "%s=%s",
                    entry.getKey(),
                    URLEncoder.encode(entry.getValue(), "UTF-8")
                            .replaceAll("[+]", "%20")
            ));
        }
        return sb.toString();
    }

    private String readFromConnection(URLConnection conn) throws IOException {

        java.io.InputStream ins = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }

    public JSONObject getRequest(String url, Map<String, String> params) throws IOException, AlbaFatalError {
        String urlWithParams;

        if (params == null) {
            urlWithParams = url;
        } else {
            urlWithParams = url + "?" + buildUrlParams(params);
        }

        URL urlInstance = new URL(urlWithParams);
        URLConnection conn = urlInstance.openConnection();

        logger.log(Level.INFO, "GET request url: " + urlWithParams);

        conn.setDoOutput(true);
        conn.setDoInput(true);

        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setInstanceFollowRedirects(false);
            ((HttpsURLConnection) conn).setRequestMethod("GET");
        } else {
            ((HttpURLConnection) conn).setInstanceFollowRedirects(false);
            ((HttpURLConnection) conn).setRequestMethod("GET");
        }

        String result = readFromConnection(conn);
        logger.log(Level.INFO, "GET request result: " + result);
        return new JSONObject(result);
    }

    public JSONObject postRequest(String url, Map<String, String> params) throws IOException, AlbaFatalError {
        int retry;
        for(retry = 1; retry < pendingRetries + 1; retry++) {
            String urlParameters = buildUrlParams(params);
            logger.log(Level.INFO, "POST request url: " + url);
            if (!params.containsKey(disableLogParameter)) {
                logger.log(Level.INFO, "POST request parameters: " + urlParameters);
            }

            byte[] postData = urlParameters.getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;
            URL urlInstance = new URL(url);
            URLConnection conn = urlInstance.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn).setInstanceFollowRedirects(false);
                ((HttpsURLConnection) conn).setRequestMethod("POST");
            } else {
                ((HttpURLConnection) conn).setInstanceFollowRedirects(false);
                ((HttpURLConnection) conn).setRequestMethod("POST");
            }

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }

            String result = readFromConnection(conn);
            logger.log(Level.INFO, "POST request result: " + result);

            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.get("status").equals("pending")) {
                logger.log(
                        Level.INFO,
                        "Status is pending, retry : " +
                                Integer.toString(retry) + " of " + Integer.toString(pendingRetries)
                );

                try {
                    Thread.sleep(SLEEP_WHILE_PENDING_MS);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } else {
                return new JSONObject(result);
            }
        }

        throw new AlbaFatalError("Status is pending " + String.valueOf(retry) + "times");
    }
}

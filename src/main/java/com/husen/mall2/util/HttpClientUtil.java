package com.husen.mall2.util;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 11785
 */
@Service
public class HttpClientUtil {

    public static String getData(String adddress) throws IOException {
        URL url = new URL(adddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Charset", "utf-8");
        httpURLConnection.setDoOutput(true);
        InputStream inputStream = httpURLConnection.getInputStream();
        byte[] b = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        while (true){
            len = inputStream.read(b);
            if(len == -1){
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        String response = byteArrayOutputStream.toString("utf-8");
        return response;
    }
}

package com.example.core.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlUtil {

    /**
     * Get website: Ex: https://www.google.com/
     * @param request
     * @return
     */
    public static String getWebsite(HttpServletRequest request){
        String website = "";

        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString());
            String host  = url.getHost();
            String protocol = url.getProtocol();
            int port = url.getPort();

            website = protocol +"://" + host;
            if (port!=80 && port!=-1){
                website +=":"+port;
            }
        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
        }
        return website;
    }

    public static String getHost(HttpServletRequest request){
        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString());
            String host  = url.getHost();
            return host;
        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
        }
        return "";
    }

    public static int getPort(HttpServletRequest request){
        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString());
            int port = url.getPort();
            return port;
        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
        }
        return 80;
    }

    public static String getProtocol(HttpServletRequest request){
        URL url = null;
        try {
            url = new URL(request.getRequestURL().toString());
            String protocol = url.getProtocol();
            return protocol;
        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
        }
        return "";
    }

    /**
     * Get web path: Ex: path= /hello/student/abc , will get /hello/student/
     * @param request
     * @return
     */
    public static String getCurrentWebPath(HttpServletRequest request){

        String uri = request.getRequestURI();

        String webPath = "";
        int index = uri.lastIndexOf("/");

        if (index!=-1){
            int length = index + 1;
            webPath = uri.substring(0, length);
        }

        return webPath;
    }

    public static String getUrlEncode(String value){

        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.toString());
        }

        return "";
    }
}

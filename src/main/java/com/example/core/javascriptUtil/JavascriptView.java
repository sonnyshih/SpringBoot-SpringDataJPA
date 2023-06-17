package com.example.core.javascriptUtil;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class JavascriptView {

//    public static String showAlertAndDirect(String message, String url){
//        return "redirect:/showAlertAndDirect?message="+ UrlUtil.getUrlEncode(message) +"&url="+ UrlUtil.getUrlEncode(url);
//    }

    public static ModelAndView showAlertAndDirect(String message, String url){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", message);
        model.put("url", url);
        return new ModelAndView("javascriptView/show_alert_redirect_view.html", model);

    }
}

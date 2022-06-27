package com.example.controllerTest.controller.mailAndFreemarkerController;

import java.util.HashMap;

public class Config {

    public final static String ACTION_INSERT = "insert";

    public static HashMap<String, String> INSURANCE_MAP = new HashMap<String, String>() {{
        put("1", "健保");
        put("2", "勞保");
        put("3", "提撥勞退");
        put("4", "團體個人意外傷害醫療保險");
    }};

    public static HashMap<Integer, String> INSURANCE_MAP2 = new HashMap<Integer, String>() {{
        put(1, "健保");
        put(2, "勞保");
        put(3, "提撥勞退");
        put(4, "團體個人意外傷害醫療保險");
    }};

    public static String getHelloString(){
        return "Hello world";
    }
}

package com.example.core;

import java.util.HashMap;
import java.util.Map;

public class BaseConfig {
    public static Map<Integer, String> DAY = new HashMap<Integer, String>() {{
        put(1, "日");
        put(2, "一");
        put(3, "二");
        put(4, "三");
        put(5, "四");
        put(6, "五");
        put(7, "六");
    }};

    public final static String[] TIME_HOUR = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                                              "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

    public final static String[] TIME_MIN = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                                             "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                                             "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
                                             "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
                                             "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};


    public final static int MESSAGE_SUCCESS = 0;
    public final static int MESSAGE_FAIL = -1;

    // Page
    public final static String PAGE_INDEX = "index";
    public final static String PAGE_ADD = "add";
    public final static String PAGE_EDIT = "edit";
    public final static String PAGE_DETAIL = "detail";
    public final static String PAGE_LIST = "list";

    // Action
    public final static String ACTION_INSERT = "insert";
    public final static String ACTION_UPDATE = "update";
    public final static String ACTION_DELETE = "delete";
    public final static String ACTION_DELETE_FILE = "delete_file";
    public final static String ACTION_DOWNLOAD = "download";
    public final static String ACTION_COPY = "copy";
    public final static String ACTION_SUBMIT = "submit";
    public final static String ACTION_REJECT = "reject";
    public final static String ACTION_DISAPPROVE = "disapprove";
    public final static String ACTION_NOTIFY = "notify";
    public final static String ACTION_EXPORT = "export";
    public final static String ACTION_CHANGE = "change";

}

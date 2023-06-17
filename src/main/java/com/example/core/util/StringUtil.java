package com.example.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String getParameterStr(String value){
        if (!isEmpty(value)) {
            return value;
        }

        return "";
    }

    public static int getParameterInt(String value){
        if (!isEmpty(value)) {
            return Integer.valueOf(value);
        }

        return 0;
    }

    public static String getArrayString(String[] array){

        if (array==null || array.length==0){
            return "";
        }

        String arrayStr ="";
        for (String item: array) {
            if (isEmpty(arrayStr)){
                arrayStr = item;
            } else {
                arrayStr += ","+ item;
            }
        }

        return arrayStr;
    }

    // remove the duplicate item from array
    public static <T> List<T> removeDuplicateInArray(List<T> list)
    {

        ArrayList<T> newList = new ArrayList<T>();

        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        return newList;
    }

    // 取得副檔名
    public static String getExtensionName(String fileName){
        String extensionName = "";
        String[] stringArray = fileName.split("\\.");
        if (stringArray!=null && stringArray.length>1){
            extensionName =  stringArray[1];
        }

        return extensionName;
    }

    public static String filterSpecialChar(String value){
        String[] charList = {"\u000B"};

        for (String entity: charList) {
            value = value.replaceAll(entity, "");
        }

        return value;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().equals("") || value.length() == 0;
    }

    public static boolean isEmpty(List<String> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }

        for (String item : list) {
            if (isEmpty(item)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isLong(String str) {
        if ("0".equals(str.trim())) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[^0]\\d*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String str) {
        if (isLong(str)) {
            return true;
        }
        Pattern pattern = Pattern.compile("\\d*\\.{1}\\d+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String str, int precision) {
        String regStr = "\\d*\\.{1}\\d{" + precision + "}";
        Pattern pattern = Pattern.compile(regStr);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isNumber(String str) {
        if (isLong(str)) {
            return true;
        }
        Pattern pattern = Pattern.compile("(-)?(\\d*)\\.{0,1}(\\d*)");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isEMail(String str) {
        Pattern pattern = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher isEMail = pattern.matcher(str);
        if (!isEMail.matches()) {
            return false;
        }
        return true;
    }

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }

    public static String getRandomPassword(){
        //拿掉 "i","l","o"以防混淆
        String code[] = {"a","b","c","d","e","f","g","h","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z"};

        StringBuffer pw = new StringBuffer();
        int j, i, n;
        double a, v, e, r;
        String y;
        for(j=0; j<7; j++){
            a = Math.random()*23;
            v = Math.random()*2;
            e = Math.random()*2;
            r = Math.random()*2;
            y = code[(int)a];
            if((int)v == 0){
                y = y.toUpperCase();
            }
            i = pw.indexOf(y);
            if((int)e == 1 && i > 0){
                pw.replace(i, i + 1, "" + Integer.toHexString(i));
                y = Integer.toHexString(j);
            }
            if((int)e == 0 && i > 0){
                n = (int)y.charAt(0);
                char[] ca;
                while(n > 9){
                    ca = ("" + n).toCharArray();
                    n = 0;
                    for(char c: ca){
                        n += Integer.parseInt("" + c);
                    }
                }
                pw.replace(i, i+1, "" + n);
            }
            pw.append(y);
            if((int)r == 1){
                pw.reverse();
            }
        }
        //至少出現一碼數字
        String cipher[] = {"0","1","2","3","4","5","6","7","8","9"};
        pw.insert((int)(Math.random()*7), cipher[(int)(Math.random()*10)]);
        return pw.toString();

    }

}

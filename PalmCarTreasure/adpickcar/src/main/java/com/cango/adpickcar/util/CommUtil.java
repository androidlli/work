package com.cango.adpickcar.util;

import com.cango.adpickcar.ADApplication;
import com.cango.adpickcar.api.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * Created by cango on 2017/3/15.
 */

public class CommUtil {
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    public static <T> boolean checkIsNull(T reference) {
        if (reference == null)
            return true;
        else
            return false;
    }

    public static String getParmasMapToJsonByEncrypt(Map<String, Object> paramsMap) {
        String encrypt = null;
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String source = gson.toJson(paramsMap);
        try {
            encrypt = EncryptUtils.encrypt(Api.KEY, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypt;
    }

    public static String setParamsToJsonByEncrypt(Object object) {
        String encrypt = null;
        String source = new Gson().toJson(object);
//        Logger.d(source);
        try {
            encrypt = EncryptUtils.encrypt(Api.KEY, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypt;
    }

    public static boolean handingCodeLogin(String code){
        boolean isReturn = false;
        if ("212".equals(code)){
            isReturn = true;
        }
        return isReturn;
    }
}

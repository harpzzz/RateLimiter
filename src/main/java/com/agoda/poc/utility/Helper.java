package com.agoda.poc.utility;

import static com.agoda.poc.contstant.Constant.BASE_URL;

public class Helper {

    public static boolean isValidUrl(String uri) {
        if(uri.contains(BASE_URL)){
            String subUrl = uri.replace(BASE_URL,"");
            if(!subUrl.isEmpty()){
                return true;
            }
        }
        return false;

    }

    public static String getSubPath(String uri) {
        String[] split = uri.split("/");
        return split[3];

    }
}

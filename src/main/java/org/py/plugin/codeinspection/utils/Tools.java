package org.py.plugin.codeinspection.utils;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * @date 2019/8/2
 */
public class Tools {
    public static boolean isNumeric(@NotNull String str){
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    public static boolean isConst(String str) {
        return Pattern.matches("(_?[A-Z,0-9]{2,20}){1,6}", str);
    }
}

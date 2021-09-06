package com.internet.engineering.IECA8.utils;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static String quoteWrapper(String str) {
        return "'" + str + "'";
    }

    public static String stripTags(String str) {
        String stripStr = str;
        List<String> illegalWords = Arrays.asList("\\<.*?\\>", "'", "\"", "_", "%", "=");
        for (String illegalWord: illegalWords) {
            stripStr = stripStr.replaceAll(illegalWord, "");
        }
        stripStr = StringEscapeUtils.escapeSql(stripStr);
        return stripStr;
    }

    public static boolean hasIllegalChars(String str) {
        return !stripTags(str).equals(str);
    }

    public static String hashString(String str) throws Exception{
        return Integer.toString(str.hashCode());
    }
}

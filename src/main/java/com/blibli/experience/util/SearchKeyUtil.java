package com.blibli.experience.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SearchKeyUtil {

    public static String capitalizeSearchKey(String searchKey) {
        Matcher matcher = Pattern.compile("\\w+").matcher(searchKey);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(capitalize(matcher.group()));
            if (!matcher.hitEnd()) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static String lowerSearchKey(String searchKey) {
        Matcher matcher = Pattern.compile("\\w+").matcher(searchKey);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(matcher.group().toLowerCase());
            if (!matcher.hitEnd()) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty() || checkExceptionWords(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Boolean checkExceptionWords(String str) {
        String[] exceptions = new String[]{"ml", "l", "mg", "g", "kg", "pcs"};
        for (String exception : exceptions) {
            return str.equals(exception);
        }
        return false;
    }

}

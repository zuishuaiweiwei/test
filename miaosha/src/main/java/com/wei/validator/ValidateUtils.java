package com.wei.validator;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 为为
 * @create 1/25
 */
public class ValidateUtils {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(src);
        return matcher.matches();
    }
}
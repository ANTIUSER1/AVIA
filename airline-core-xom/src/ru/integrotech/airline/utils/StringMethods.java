package ru.integrotech.airline.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMethods {

    public static String milesRuleToRegexTransform(String mask) {
        String newMask = mask;
        newMask = newMask.replaceAll("\\*", ".*");
        for (int i = newMask.length(); i > 0; i--) {
            newMask = newMask.replaceAll("#{" + i + "}", "\\\\d{" + i + "}")
                    .replaceAll("\\?{" + i + "}", ".{" + i + "}");
        }
        newMask = newMask.replaceAll("^\\\\d\\{", "\\^\\\\d{");
        newMask = newMask.replaceAll("^.\\{", "^.\\{");
        if (!newMask.endsWith(".*")) {
            newMask = String.format("%s%s", newMask, "$");
        }
        return newMask;
    }

    public static boolean isFitsByRegexMasks(String value, List<String> masks) {

        boolean result = false;

        if (!isEmpty(masks) && !isEmpty(value)) {

            for (String mask : masks) {
                Pattern p = Pattern.compile(mask);
                Matcher m = p.matcher(value);
                if (m.matches()) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public static boolean isEmpty(List<String> string) {
        return  string == null || string.size() == 0;
    }

    public static boolean isEmpty(String string) {
        return  string == null || string.isEmpty();
    }
}

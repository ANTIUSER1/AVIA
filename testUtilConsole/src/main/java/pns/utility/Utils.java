package pns.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Utils {
    public static String jsonTransform(String json, String root) throws Exception {
        json = json.trim();
        if (root != null) {
            if (json.contains(root)) {
                root = "\"" + root + "\"";
                StringBuffer sb = new StringBuffer(json);
                int ddot = sb.indexOf(root, 0) + root.length();
                json = sb.substring(ddot).trim();

                sb = new StringBuffer(json.trim());
                sb.setCharAt(0, ' ');

                sb.setCharAt(sb.length() - 1, ' ');
                json = sb.toString();
            } else {
                String s = " ERROR:   Root element in JSON is incorrect. Given  " +
                        root + System.lineSeparator() +
                        "   ...   ...   ...   ........   ...";
                Exception e = new Exception(s);
                System.out.println(new Date() + s);
                throw e;
            }
        }
        return json.trim();
    }

    public static String[] breakeOnLines(String json) {
        return json.split(System.lineSeparator());
    }

    public static List<String> makeClearData(String json) {
        json = json.trim();
        List<String> res = new ArrayList<>();
        String[] jsonLines = breakeOnLines(json);
        for (int k = 0; k < jsonLines.length; k++) {
            if (jsonLines[k].trim().length() > 0) res.add(jsonLines[k]);
        }
        return res;
    }

    public static StringBuffer readFileData(String fileName) throws IOException {
        StringBuffer sbf = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line = br.readLine();

            while (line != null) {
                sbf.append(line);
                sbf.append(System.lineSeparator());
                line = br.readLine();
            }
        }
        return sbf;
    }

    public static Map<Integer, String> compareList(List<String> listOrigin, List<String> listTemplate) {
        Map<Integer, String> res = new HashMap<>();

        if (listOrigin.size() != listTemplate.size()) {
            res.put(1, "the size of JSONS are not equal ... ");
            return res;
        }

        for (int k = 0; k < listOrigin.size(); k++) {
            List<String> tokenListOrigin = createTokens(listOrigin.get(k));
            List<String> tokenListTemplate = createTokens(listTemplate.get(k));
            if (tokenListOrigin.size() != tokenListTemplate.size()) {
                res.put(1, "The sizes of " + k + "-th JSONS' line  are not equal ... ");
                return res;
            }

            for (int m = 0; m < tokenListOrigin.size(); m++) {
                if (!tokenListOrigin.get(m).equalsIgnoreCase(tokenListTemplate.get(m))) {
                    res.put(1, " The  " + m + "-th terms of the " + k + "-th line are not equas  .....");
                    return res;
                }
            }
        }
        res.put(0, " The data are equal...");
        return res;
    }

    private static List<String> createTokens(String s) {
        List<String> res = new ArrayList<>();
        StringTokenizer tok = new StringTokenizer(s, " ");

        while (tok.hasMoreTokens()) {
            String stt = tok.nextToken().trim();
            if (stt.length() > 0)
                res.add(stt);
            //    System.out.println(s);
        }
        return res;
    }

    public static boolean compareListBoolean(List<String> listOrigin, List<String> listTemplate) {
        if (listOrigin.size() != listTemplate.size()) return false;
        for (int k = 0; k < listOrigin.size(); k++) {
            if (!listOrigin.get(k).equalsIgnoreCase(listTemplate.get(k)))
                return false;
        }
        return true;
    }

}

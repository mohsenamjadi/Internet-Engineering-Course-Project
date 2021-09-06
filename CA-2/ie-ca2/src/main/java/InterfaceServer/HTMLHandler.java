package InterfaceServer;

import java.util.HashMap;
import java.util.Map;

public class HTMLHandler {
    public static String fillTemplate(String htmlFileString, HashMap<String, String> context, String delimiter) throws Exception {
        for(Map.Entry<String, String> entry : context.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            htmlFileString = htmlFileString.replaceAll(delimiter + key + delimiter, value);
        }
        return htmlFileString;
    }

    public static String fillTemplate(String htmlFileString, HashMap<String, String> context) throws Exception {
        return fillTemplate(htmlFileString, context, "%");
    }
}

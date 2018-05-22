package utils;

import com.sun.xml.internal.ws.util.StringUtils;

public class UtilityFunctions {

    public String getHumanReadable(String s){
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public String getClassName(String tableName) {
        String replace = tableName.replace("_", " ");
        String[] splitted = replace.split(" ");
        String className = "database.";

        for(String s : splitted){
            className = className.concat(StringUtils.capitalize(s));
        }
        className = className.replace(" ", "").concat("Entity");
        return className;
    }

}

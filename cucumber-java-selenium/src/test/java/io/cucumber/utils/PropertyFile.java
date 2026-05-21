package io.cucumber.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyFile {

    private static Properties properties = new Properties();

    public static void getPropertyFile(String folderName, String fileName) throws Exception {
        String path = System.getProperty("user.dir")
                + "/src/test/resources/"
                + folderName
                + "/" + fileName + ".properties";
        InputStream input = new FileInputStream(path);
        properties.load(input);
    }

    public static String getAttribute(String key) throws Exception {
        if (properties.isEmpty()) {
            throw new IllegalStateException("Property file not loaded");
        }
        String value = properties.getProperty(key);
        if (Objects.isNull(value)) {
            throw new IllegalStateException("Property value is null for key: " + key);
        }
        return value.trim();
    }
}

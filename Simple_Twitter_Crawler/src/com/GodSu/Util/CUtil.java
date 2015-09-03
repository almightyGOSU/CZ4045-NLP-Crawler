package com.GodSu.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CUtil {

    Properties objConfig = new Properties();

    public CUtil() {
        try {
            objConfig.load(new FileInputStream(""));
        } catch (IOException ex) {
        }
    }

    public String loadConfig(String pStrProperty) {
        return objConfig.getProperty(pStrProperty);
    }
}

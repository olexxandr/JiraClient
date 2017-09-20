package com.jira.customtestrunner;

import java.io.*;
import java.net.URL;
import java.util.Properties;


class ConfigManagement
{
    private final Properties configProp = new Properties();

    private ConfigManagement()
    {
        try {
            URL url = this.getClass().getResource("/config.properties");
            File f = new File(url.getFile());

            System.out.println(f);
            configProp.load(new FileInputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LazyHolder
    {
        private static final ConfigManagement INSTANCE = new ConfigManagement();
    }

    static ConfigManagement getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    String getProperty(String key){
        return configProp.getProperty(key);
    }

    public boolean containsKey(String key){
        return configProp.containsKey(key);
    }
}
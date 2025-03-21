package com.ti;//import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesService {
//    public static Logger LOG = Logger.getLogger(com.ti.PropertiesService.class);
    private static String mainPropertyFileName;
    private  String localPropertyFileName;

    public void setName(String name){
        this.localPropertyFileName = name;
    }

    public static void setGlobalPropertyFileName(String name){
        mainPropertyFileName = name;
    }
    public static String getGlobalProperty(String key){
        FileInputStream file;
        String path = "./" + mainPropertyFileName + ".properties";
//        System.out.println("File "+ mainPropertyFileName);
        try {
            file = new FileInputStream(path);
            Properties mainProperties = new Properties();
            mainProperties.load(file);
            file.close();
            return mainProperties.getProperty(key);
        } catch (java.io.IOException e) {
            System.out.println("File "+ mainPropertyFileName+ "  not found at "+ path);
//            LOG.info("File "+ mainPropertyFileName+ "  not found at "+ path);
            e.printStackTrace();
        }
        return null;
    }
    public static String getGlobalProperty(String key, String ifNullValue){
        String value = getGlobalProperty(key);
        if(value == null){
            value = ifNullValue;
            setGlobalProperty(key,value);
        }
        return value;
    }
    public static void setGlobalProperty(String key, String value){
        String path = "./" + mainPropertyFileName + ".properties";
        try {
            FileOutputStream file;
            Properties mainProperties = new Properties();
            mainProperties.load(new FileInputStream(path));
            mainProperties.setProperty(key,value);
            file = new FileOutputStream(path);
            mainProperties.store(file,"");
            file.close();
        } catch (java.io.IOException e) {
//            LOG.info("File "+ mainPropertyFileName+ "  not found at "+ path);
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        FileInputStream file;
        Properties mainProperties = new Properties();

        String path = "./" + localPropertyFileName + ".properties";
        try {
            file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();
            return mainProperties.getProperty(key);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getAllProperties(){
        Map<String, String> propertiesMap = new HashMap<>();
        Properties mainProperties = new Properties();

        String path = "./" + localPropertyFileName + ".properties";
        try (FileInputStream file = new FileInputStream(path)) {
            mainProperties.load(file);
            for (String key : mainProperties.stringPropertyNames()) {
                propertiesMap.put(key, mainProperties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesMap;
    }

    public void saveProperty(String key, String value){
        String path = "./" + localPropertyFileName + ".properties";
        FileOutputStream file;
        Properties mainProperties = new Properties();
        try {
            mainProperties.load(new FileInputStream(path));
            mainProperties.setProperty(key,value);
            file = new FileOutputStream(path);
            mainProperties.store(file,"");
            file.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

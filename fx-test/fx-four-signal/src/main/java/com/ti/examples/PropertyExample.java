package com.ti.examples;

import com.ti.PropertiesService;

import java.util.Map;

public class PropertyExample {
    public static void main(String[] args) {
        PropertiesService.setGlobalPropertyFileName(PropertyExample.class.getSimpleName());
        String prop = PropertiesService.getGlobalProperty("name");

        PropertiesService service = new PropertiesService();
        service.setName(prop);
        Map<String, String> map = service.getAllProperties();
        System.out.println(map);
    }
}

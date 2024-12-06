package com.ti.tests;

import com.ti.PropertiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class PropertiesServiceTest {

    @Test
    void getGlobalProperty(){
        PropertiesService.setGlobalPropertyFileName(PropertiesServiceTest.class.getSimpleName());
        String prop = PropertiesService.getGlobalProperty("prop");
        Assertions.assertEquals("testProperty", prop);
    }
}
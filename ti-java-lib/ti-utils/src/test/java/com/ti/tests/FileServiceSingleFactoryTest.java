package com.ti.tests;


import com.ti.FileService;
import com.ti.FileServiceSingleFactory;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;


public class FileServiceSingleFactoryTest {

    @Test
    public void newFileService() {
        FileServiceSingleFactory factory = new FileServiceSingleFactory();
        factory.appendDateToFileName();
        FileService service = factory.newFileService("testFile.bin", "data/");

        ByteBuffer byteBuffer = ByteBuffer.allocate(24);
        byteBuffer.asIntBuffer().put(new int[]{1,2,3,4,5,6});

        service.writeBytes(byteBuffer);
        service.writeBytes(byteBuffer);
        byte[] a = new byte[]{1,2,3,4,5, Character.getDirectionality('a')};
        service.writeBytes(ByteBuffer.wrap(a));

        int[] array = new int[6];
        ByteBuffer readBuffer = service.readBytes(24);
        readBuffer.rewind().asIntBuffer().get(array);
        System.out.println(Arrays.toString(array));
        readBuffer = service.readBytes(24);
        readBuffer.rewind().asIntBuffer().get(array);
        System.out.println(Arrays.toString(array));


        service.closeChannel();
    }
}
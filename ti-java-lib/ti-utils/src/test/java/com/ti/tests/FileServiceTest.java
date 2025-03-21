package com.ti.tests;

import com.ti.FileService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServiceTest {
//    private static final String TEST_FILE = "SerialService 2017-Oct-03 18-30-24.bin";
    private static final String TEST_FILE = "MechaBin.bin";
    @Test
    public void readBytesTest() throws Exception {
        Path file = Paths.get(this.getClass().getResource(TEST_FILE).toURI());
        FileService service = new FileService(file);
        ByteBuffer buffer = service.readBytes();
        byte[] expect = new byte[10];
        byte[] actual = new byte[]{(byte) 0x95, 3, 0, 0, (byte)0xb5, (byte)0xf4, 2, 0, (byte)0x9b, (byte)0x0c};
        buffer.rewind();
        buffer.get(expect);

        Assertions.assertArrayEquals(expect, actual);
    }

    @Test
    public void readAndWriteBytesTest() throws Exception {

        Path file = Paths.get(this.getClass().getResource(TEST_FILE).toURI());
        FileService readFileService = new FileService(file);
        // TODO: 21.01.2018 Add remove out.txt befor start test or erase and rewrite file
        String newFile = file.getParent().resolve("out.txt").toString();
        FileService writeFileService = new FileService(newFile);

        boolean empty = false;
        while(!empty){
            ByteBuffer buffer = readFileService.readBytes();
            int readByte = buffer.limit();
            int writeByte = writeFileService.writeBytes(buffer);
            if(readByte==0){
                empty = true;
            }
        }

        boolean eq = FileUtils.contentEquals(file.toFile(), new File(newFile));
        Assertions.assertTrue(eq);

    }
}
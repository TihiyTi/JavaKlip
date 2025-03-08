module com.ti.desktop{
//    uses org.slf4j.spi.SLF4JServiceProvider;
    exports com.ti.desktop;

    requires com.ti;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires java.desktop;
    requires com.fazecast.jSerialComm;

    //    requires ch.qos.logback.classic;
    requires org.apache.commons.io;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires org.slf4j;
//    requires jakarta.xml.bind;

//    requires org.scream3r.jssc;
//    exports my.package;


    opens com.ti.desktop to  javafx.graphics, javafx.fxml, javafx.controls;
    opens com.ti.dspview to  javafx.graphics, javafx.fxml, javafx.controls;
    opens com.ti.viewcore to javafx.controls, javafx.fxml, javafx.graphics;
    opens com.ti.remg to javafx.graphics, javafx.fxml, javafx.controls;
}

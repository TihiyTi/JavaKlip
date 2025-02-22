module com.ti.desktop{
    exports com.ti.desktop;

    requires com.ti;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires com.fazecast.jSerialComm;

//    requires org.fxmisc.richtext;
//    requires jakarta.xml.bind;

//    requires org.scream3r.jssc;
//    exports my.package;


    opens com.ti.desktop to  javafx.graphics, javafx.fxml, javafx.controls;
    opens com.ti.dspview to  javafx.graphics, javafx.fxml, javafx.controls;
    opens com.ti.viewcore to javafx.controls, javafx.fxml, javafx.graphics;
    opens com.ti.remg to javafx.graphics, javafx.fxml, javafx.controls;
}

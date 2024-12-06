package com.ti.desktop;

import com.ti.PropertiesService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FxFourSignal extends Application {
    public static final String SCENE = "panebutton.fxml";
    public static void main(String[] args) {Application.launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        PropertiesService.setGlobalPropertyFileName(FxFourSignal.class.getSimpleName());
        String prop = PropertiesService.getGlobalProperty("prop");
        System.out.println(prop);

//        System.out.println(System.getProperty("java.class.path"));
        FXMLLoader loader = new FXMLLoader( getClass().getResource(SCENE));
        BorderPane root = loader.load();
        Scene scene =  new Scene(root, 700,500);
        stage.setScene(scene);
        stage.setTitle("FxFourSignal");
        stage.show();
    }
}

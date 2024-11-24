package com.ti;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ResourceBundle;

@SpringBootApplication(scanBasePackages = "com.ti")
public class FxApplication  extends Application {
    public static final String SCENE = "wilo.fxml";
    private static final String KEY_PROPERTIES = "keys";

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(String.join(".", getClass().getPackageName(), KEY_PROPERTIES));
//        List<FXMLLoader> fxmlLoaders = getFXMLLoader(resourceBundle);
//        List<Stage> stages = Stream
//                .concat(
//                        Stream.of(mainStage),
//                        IntStream.range(1, fxmlLoaders.size()).mapToObj(i -> new Stage(StageStyle.DECORATED))
//                )
//                .toList();

//        URL resource = getClass().getResource("/wilo.fxml");
//        if (resource == null) {
//            throw new RuntimeException("FXML file not found: /wilo.fxml");
//        }
//        System.out.println(System.getProperty("java.class.path"));

        FXMLLoader loader = new FXMLLoader( getClass().getResource(SCENE));

        BorderPane root = loader.load();
        Scene scene =  new Scene(root, 700,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wilo");
        primaryStage.show();

        System.out.println("Test2");
    }


    @Override
    public void stop() {
//        applicationContext.close();
        Platform.exit();
    }
}

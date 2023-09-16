package com.fx.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pane.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("sheng-nivcat");
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();

        ControllerManage.controllers.put("pane", fxmlLoader.getController());
    }

    public static void main(String[] args) {
        launch();
    }
}
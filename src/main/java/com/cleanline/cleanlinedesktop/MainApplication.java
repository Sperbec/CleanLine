package com.cleanline.cleanlinedesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage crudStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoaderCRUD = new FXMLLoader(MainApplication.class.getResource("views/crud.fxml"));

        Scene crudScene = new Scene(fxmlLoaderCRUD.load(), 640, 480);
        crudScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        crudStage = primaryStage;
        crudStage.setTitle("CleanLine");
        crudStage.setScene(crudScene);

        crudStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getCrudStage() {
        return crudStage;
    }
}
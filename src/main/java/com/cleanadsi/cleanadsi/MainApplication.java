package com.cleanadsi.cleanadsi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoaderCRUD = new FXMLLoader(MainApplication.class.getResource("views/crud.fxml"));

        Scene crud_scene = new Scene(fxmlLoaderCRUD.load(), 700, 700);
        stage.setTitle("CleanLine");
        stage.setScene(crud_scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
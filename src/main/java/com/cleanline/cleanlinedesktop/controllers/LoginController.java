package com.cleanline.cleanlinedesktop.controllers;

import com.cleanline.cleanlinedesktop.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField textFieldUsuario;
    @FXML
    public PasswordField passwordFieldPassword;

    public void initialize(){
    }

    public void signIn(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/crud.fxml"));

        Scene crudScene= new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(crudScene);
        stage.setTitle("Crud");
        stage.show();
    }
}

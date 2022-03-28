package com.cleanadsi.cleanadsi.controllers;

import com.cleanadsi.cleanadsi.dao.UsuarioDao;
import com.cleanadsi.cleanadsi.models.UsuarioEntity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UsuarioController {

    // Table view fields

    @FXML
    public TableView tableViewUsuarios;
    @FXML
    public TableColumn<UsuarioEntity, Integer> tcIDUsuario;
    @FXML
    public TableColumn<UsuarioEntity, Integer> tcIDPersona;
    @FXML
    public TableColumn<UsuarioEntity, String> tcEmail;
    @FXML
    public TableColumn<UsuarioEntity, Timestamp> tcEmailVerifiedAt;
    @FXML
    public TableColumn<UsuarioEntity, String> tcPassword;
    @FXML
    public TableColumn<UsuarioEntity, String> tcRememberToken;
    @FXML
    public TableColumn<UsuarioEntity, Timestamp> tcCreatedAt;
    @FXML
    public TableColumn<UsuarioEntity, Timestamp> tcUpdatedAt;

    ObservableList<UsuarioEntity> usuariosList;

    // Form (VBOX) fields

    @FXML
    public TextField textFieldIDUsuario;
    @FXML
    public TextField textFieldIDPersona;
    @FXML
    public TextField textFieldEmail;
    @FXML
    public DatePicker datePickerEmailVerifiedAt;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public TextField textFieldRememberToken;
    @FXML
    public DatePicker datePickerCreatedAt;
    @FXML
    public DatePicker datePickerUpdatedAt;

    public void initialize() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuariosList = usuarioDao.getAll();
        tableViewUsuarios.setItems(usuariosList);

        tableViewUsuarios.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                cleanTextFields();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                UsuarioEntity usuarioEntity = (UsuarioEntity) tableViewUsuarios.getSelectionModel().getSelectedItem();

                textFieldIDUsuario.setText(String.valueOf(usuarioEntity.getIdUsuario()));
                textFieldIDPersona.setText(String.valueOf(usuarioEntity.getIdPersona()));
                textFieldEmail.setText((usuarioEntity.getEmail()));
                textFieldPassword.setText(usuarioEntity.getPassword());
                textFieldRememberToken.setText(usuarioEntity.getRememberToken());

                if (usuarioEntity.getEmailVerifiedAt() != null) {
                    datePickerEmailVerifiedAt.setValue(LocalDate.parse(simpleDateFormat.format(usuarioEntity.getEmailVerifiedAt()), DateTimeFormatter.ofPattern("dd/MM/yyy")));
                }

                if (usuarioEntity.getCreatedAt() != null) {
                    datePickerCreatedAt.setValue(LocalDate.parse(simpleDateFormat.format(usuarioEntity.getCreatedAt()), DateTimeFormatter.ofPattern("dd/MM/yyy")));
                }

                if (usuarioEntity.getUpdatedAt() != null) {
                    datePickerUpdatedAt.setValue(LocalDate.parse(simpleDateFormat.format(usuarioEntity.getCreatedAt()), DateTimeFormatter.ofPattern("dd/MM/yyy")));
                }
            }
        });

        /*-------------------------------------------------------------------------------
         *  the value of the property should be the same as the field in the Entity model
         *
         *  example: UserEntity.idUsers -> Property = idUsers
         *
         * -------------------------------------------------------------------------------*/

        tcIDUsuario.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Integer>("idUsuario"));
        tcIDPersona.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Integer>("idPersona"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, String>("email"));
        tcEmailVerifiedAt.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Timestamp>("emailVerifiedAt"));
        tcPassword.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, String>("password"));
        tcRememberToken.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, String>("rememberToken"));
        tcCreatedAt.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Timestamp>("createdAt"));
        tcUpdatedAt.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Timestamp>("updatedAt"));
    }

    public void refreshView() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuariosList = usuarioDao.getAll();
        tableViewUsuarios.setItems(usuariosList);
        tableViewUsuarios.refresh();
    }

    private void setFields(UsuarioEntity usuarioEntity) {
        usuarioEntity.setIdPersona(Integer.parseInt(textFieldIDPersona.getText()));
        usuarioEntity.setEmail(textFieldEmail.getText());
        if (datePickerEmailVerifiedAt.getValue() != null) {
            usuarioEntity.setEmailVerifiedAt(Timestamp.valueOf(datePickerEmailVerifiedAt.getValue().toString() + " " + LocalTime.now().toString()));
        }
        usuarioEntity.setPassword(textFieldPassword.getText());
        usuarioEntity.setRememberToken(textFieldRememberToken.getText());
        if (datePickerCreatedAt.getValue() != null) {
            usuarioEntity.setCreatedAt(Timestamp.valueOf(datePickerCreatedAt.getValue().toString() + " " + LocalTime.now().toString()));
        }
        if (datePickerUpdatedAt.getValue() != null) {
            usuarioEntity.setUpdatedAt(Timestamp.valueOf(datePickerUpdatedAt.getValue().toString() + " " + LocalTime.now().toString()));
        }
    }

    public void cleanTextFields() {
        textFieldIDUsuario.setText(null);
        textFieldIDPersona.setText(null);
        textFieldEmail.setText(null);
        datePickerEmailVerifiedAt.setValue(null);
        textFieldPassword.setText(null);
        textFieldRememberToken.setText(null);
        datePickerCreatedAt.setValue(null);
        datePickerUpdatedAt.setValue(null);
    }

    public void addDataAE(ActionEvent event) {
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        setFields(usuarioEntity);

        usuarioDao.addData(usuarioEntity);

        refreshView();
    }


    public void deleteDataAE(ActionEvent event) {
        UsuarioEntity usuarioEntity = (UsuarioEntity) tableViewUsuarios.getSelectionModel().getSelectedItem();

        /*-------------------------------
         *  Pop up an alert confirmation
         *-------------------------------*/

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmar Eliminación");
        alert.setContentText("¿Está seguro que desea eliminar este usuario?" +
                "\nID: " + usuarioEntity.getIdUsuario() +
                "\nEmail: " + usuarioEntity.getEmail());

        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.get() == ButtonType.OK) {
            UsuarioDao usuarioDao = new UsuarioDao();

            // get all fields on the selected item and assign in UsuarioEntity
            usuarioDao.deleteData(usuarioEntity);

            refreshView();
        }
    }

    public void updateDataAE(ActionEvent event) {
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioEntity usuarioEntity = (UsuarioEntity) tableViewUsuarios.getSelectionModel().getSelectedItem();

        setFields(usuarioEntity);

        usuarioDao.updateData(usuarioEntity);

        refreshView();
    }
}

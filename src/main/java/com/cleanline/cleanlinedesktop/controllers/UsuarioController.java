package com.cleanline.cleanlinedesktop.controllers;

import com.cleanline.cleanlinedesktop.dao.PersonaDao;
import com.cleanline.cleanlinedesktop.dao.UsuarioDao;
import com.cleanline.cleanlinedesktop.models.PersonaEntity;
import com.cleanline.cleanlinedesktop.models.UsuarioEntity;
import com.cleanline.cleanlinedesktop.utility.FXUtil;
import com.cleanline.cleanlinedesktop.utility.ValidateUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    // Form (VBOX) fields

    @FXML
    public ComboBox comboBoxIDPersona;
    @FXML
    public TextField textFieldEmail;
    @FXML
    public TextField textFieldPassword;

    ObservableList<UsuarioEntity> usuariosList;
    ObservableList<PersonaEntity> personasList;

    public void initialize() {
        cleanTextFields();
        fillColumnsCells();

        setComboBoxItems();
        fillFieldsOnKeyEnter();
    }

    /*----------------------
     *
     *  GUI Methods
     *
     * ----------------------*/

    public void refreshView() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuariosList = usuarioDao.getAll();
        tableViewUsuarios.setItems(usuariosList);
        tableViewUsuarios.refresh();
    }

    private void setFields(UsuarioEntity usuarioEntity) {
        if (comboBoxIDPersona.getValue() != null) {
            usuarioEntity.setIdPersona((Integer) comboBoxIDPersona.getSelectionModel().getSelectedItem());
        } else {
            FXUtil.showInformationAlert("Seleccione primero el ID de la persona");
        }

        if (textFieldEmail.getText() != null) {
            if (ValidateUtil.validateEmailFormat(textFieldEmail.getText())){
                usuarioEntity.setEmail(textFieldEmail.getText());
            } else {
                FXUtil.showInformationAlert("Email no válido");
            }
        } else {
            FXUtil.showInformationAlert("El email no puede estar vacío");
        }

        if (textFieldPassword.getText() != null) {
            if (ValidateUtil.validatePasswordLength(textFieldPassword.getText())) {
                usuarioEntity.setPassword(textFieldPassword.getText());
            } else {
                FXUtil.showInformationAlert("La longitud de la contraseña debe ser:\n- Mayor o igual a 8 \n- Menor o igual a 15");
            }
        } else {
            FXUtil.showInformationAlert("La contraseña no puede estar vacía");
        }
    }

    public void cleanTextFields() {
        comboBoxIDPersona.getSelectionModel().clearSelection();
        textFieldEmail.setText(null);
        textFieldPassword.setText(null);
    }

    public void setComboBoxItems () {
        PersonaDao personaDao = new PersonaDao();
        personasList = personaDao.getAll();

        for (PersonaEntity personaEntity: personasList) {
            comboBoxIDPersona.getItems().add(personaEntity.getIdPersona());
        }
    }

    public void fillFieldsOnKeyEnter() {
        tableViewUsuarios.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                cleanTextFields();

                UsuarioEntity usuarioEntity = (UsuarioEntity) tableViewUsuarios.getSelectionModel().getSelectedItem();

                comboBoxIDPersona.setValue(usuarioEntity.getIdPersona());
                textFieldEmail.setText((usuarioEntity.getEmail()));
                textFieldPassword.setText(usuarioEntity.getPassword());
            }
        });
    }

    public void fillColumnsCells() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuariosList = usuarioDao.getAll();

        tableViewUsuarios.setItems(usuariosList);

        /*-------------------------------------------------------------------------------
         *
         *  the value of the property should be the same as the field in the Entity model
         *
         *  example: UserEntity.idUsers -> Property = idUsers
         *
         * -------------------------------------------------------------------------------*/

        // registries on table

        tcIDUsuario.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Integer>("idUsuario"));
        tcIDPersona.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Integer>("idPersona"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, String>("email"));
        tcEmailVerifiedAt.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Timestamp>("emailVerifiedAt"));
        tcPassword.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, String>("password"));
        tcRememberToken.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, String>("rememberToken"));
        tcCreatedAt.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Timestamp>("createdAt"));
        tcUpdatedAt.setCellValueFactory(new PropertyValueFactory<UsuarioEntity, Timestamp>("updatedAt"));
    }

    /*----------------------
     *
     *  CRUD Methods
     *
     * ----------------------*/

    public void addDataAE(ActionEvent event) {
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        setFields(usuarioEntity);
        usuarioEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        usuarioDao.addData(usuarioEntity);

        refreshView();
        cleanTextFields();
    }

    public void deleteDataAE(ActionEvent event) {
        try {
            // get all fields on the selected item and assign in usuarioEntity
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

                usuarioDao.deleteData(usuarioEntity);

                refreshView();
                cleanTextFields();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

            Alert inf = new Alert(Alert.AlertType.INFORMATION);
            inf.setHeaderText(null);
            inf.setTitle("Información");
            inf.setContentText("Seleccione primero un registro de la tabla");
            inf.showAndWait();
        }
    }

    public void updateDataAE(ActionEvent event) {
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioEntity usuarioEntity = (UsuarioEntity) tableViewUsuarios.getSelectionModel().getSelectedItem();

        // wrap in a try-catch
        setFields(usuarioEntity);
        usuarioEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        usuarioDao.updateData(usuarioEntity);

        refreshView();
        cleanTextFields();
    }
}

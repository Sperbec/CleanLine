package com.cleanline.cleanlinedesktop.controllers;

import com.cleanline.cleanlinedesktop.dao.PersonaDao;
import com.cleanline.cleanlinedesktop.dao.UsuarioDao;
import com.cleanline.cleanlinedesktop.models.PersonaEntity;
import com.cleanline.cleanlinedesktop.models.UsuarioEntity;
import com.cleanline.cleanlinedesktop.utility.FXUtil;
import com.cleanline.cleanlinedesktop.utility.HibernateUtil;
import com.cleanline.cleanlinedesktop.utility.ValidateUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

    ObservableList<UsuarioEntity> usuariosList;
    ObservableList<PersonaEntity> personasList;

    // Form (VBOX) fields

    @FXML
    public ComboBox comboBoxIDPersona;
    @FXML
    public TextField textFieldEmail;
    @FXML
    public TextField textFieldPassword;

    public void initialize() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuariosList = usuarioDao.getAll();

        tableViewUsuarios.setItems(usuariosList);

        tableViewUsuarios.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                cleanTextFields();

                UsuarioEntity usuarioEntity = (UsuarioEntity) tableViewUsuarios.getSelectionModel().getSelectedItem();

                comboBoxIDPersona.setValue(usuarioEntity.getIdPersona());
                textFieldEmail.setText((usuarioEntity.getEmail()));
                textFieldPassword.setText(usuarioEntity.getPassword());
            }
        });

        /*-------------------------------------------------------------------------------
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

        PersonaDao personaDao = new PersonaDao();
        personasList = personaDao.getAll();

        for (PersonaEntity personaEntity: personasList) {
            comboBoxIDPersona.getItems().add(personaEntity.getIdPersona());
        }
    }

    public void refreshView() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuariosList = usuarioDao.getAll();
        tableViewUsuarios.setItems(usuariosList);
        tableViewUsuarios.refresh();
    }

    private void setFields(UsuarioEntity usuarioEntity) {
        usuarioEntity.setIdPersona((Integer) comboBoxIDPersona.getSelectionModel().getSelectedItem());
        if (ValidateUtil.validateEmail(textFieldEmail.getText())){
            usuarioEntity.setEmail(textFieldEmail.getText());
        }
        usuarioEntity.setPassword(textFieldPassword.getText());
    }

    public void cleanTextFields() {
//        textFieldIDPersona.setText(null); // !terminar validaciones
        comboBoxIDPersona.getSelectionModel().clearSelection();
        textFieldEmail.setText(null);
        textFieldPassword.setText(null);
    }

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

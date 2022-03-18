package com.cleanadsi.cleanadsi.controllers;

import com.cleanadsi.cleanadsi.dao.UsuarioDao;
import com.cleanadsi.cleanadsi.models.UsuarioEntity;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class UsuarioController {
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

    public void initialize() {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuariosList = usuarioDao.getAll();
        tableViewUsuarios.setItems(usuariosList);

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
}

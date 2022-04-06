package com.cleanline.cleanlinedesktop.controllers;

import com.cleanline.cleanlinedesktop.MainApplication;
import com.cleanline.cleanlinedesktop.dao.ProductoDao;
import com.cleanline.cleanlinedesktop.models.ProductoEntity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class ProductoController {
    // Table view fields

    @FXML
    public TableView tableViewProductos;
    @FXML
    public TableColumn<ProductoEntity, Integer> tcIDProducto;
    @FXML
    public TableColumn<ProductoEntity, String> tcNombre;
    @FXML
    public TableColumn<ProductoEntity, String> tcDescripcion;
    @FXML
    public TableColumn<ProductoEntity, String> tcSKU;
    @FXML
    public TableColumn<ProductoEntity, Double> tcPrecio;
    @FXML
    public TableColumn<ProductoEntity, Integer> tcCantidadExistencia;
    @FXML
    public TableColumn<ProductoEntity, String> tcFilePath;
    @FXML
    public TableColumn<ProductoEntity, String> tcImagen;
    @FXML
    public TableColumn<ProductoEntity, Integer> tcIDCategoria;
    @FXML
    public TableColumn<ProductoEntity, Timestamp> tcDeletedAt;
    @FXML
    public TableColumn<ProductoEntity, Timestamp> tcCreatedAt;
    @FXML
    public TableColumn<ProductoEntity, Timestamp> tcUpdatedAt;
    @FXML
    public TableColumn<ProductoEntity, Byte> tcEstado;

    ObservableList<ProductoEntity> productosList;

    // Form (VBOX) fields

    @FXML
    public TextField textFieldIDProducto;
    @FXML
    public TextField textFieldNombre;
    @FXML
    public TextField textFieldDescripcion;
    @FXML
    public TextField textFieldSKU;
    @FXML
    public TextField textFieldPrecio;
    @FXML
    public TextField textFieldCantidadExistencia;
    @FXML
    public TextField textFieldFilePath;
    @FXML
    public TextField textFieldImagen;
    @FXML
    public TextField textFieldIDCategoria;
    @FXML
    public CheckBox checkBoxEstado;

    @FXML

    public void initialize(){
        ProductoDao productoDao = new ProductoDao();
        productosList = productoDao.getAll();

        tableViewProductos.setItems(productosList);

        tableViewProductos.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                cleanTextFields();

                ProductoEntity productoEntity = (ProductoEntity) tableViewProductos.getSelectionModel().getSelectedItem();

                textFieldNombre.setText(String.valueOf(productoEntity.getNombre()));
                textFieldDescripcion.setText((productoEntity.getDescripcion()));
                textFieldSKU.setText(productoEntity.getSku());
                textFieldPrecio.setText(String.valueOf(productoEntity.getPrecio()));
                textFieldCantidadExistencia.setText(String.valueOf(productoEntity.getCantidadExistencia()));
                textFieldFilePath.setText(productoEntity.getFilePath());
                textFieldImagen.setText(productoEntity.getImagen());
                textFieldIDCategoria.setText(String.valueOf(productoEntity.getIdCategoria()));

                // Convert byte to boolean
                boolean b = productoEntity.getEstado() != 0;
                checkBoxEstado.setSelected(b);
            }
        });

        /*-------------------------------------------------------------------------------
         *  the value of the property should be the same as the field in the Entity model
         *
         *  example: UserEntity.idUsers -> Property = idUsers
         *
         * -------------------------------------------------------------------------------*/

        tcIDProducto.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Integer>("idProducto"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<ProductoEntity, String>("nombre"));
        tcDescripcion.setCellValueFactory(new PropertyValueFactory<ProductoEntity, String>("descripcion"));
        tcSKU.setCellValueFactory(new PropertyValueFactory<ProductoEntity, String>("sku"));
        tcPrecio.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Double>("precio"));
        tcCantidadExistencia.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Integer>("cantidadExistencia"));
        tcFilePath.setCellValueFactory(new PropertyValueFactory<ProductoEntity, String>("filePath"));
        tcImagen.setCellValueFactory(new PropertyValueFactory<ProductoEntity, String>("imagen"));
        tcIDCategoria.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Integer>("idCategoria"));
        tcDeletedAt.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Timestamp>("deletedAt"));
        tcCreatedAt.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Timestamp>("createdAt"));
        tcUpdatedAt.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Timestamp>("updatedAt"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<ProductoEntity, Byte>("estado"));
    }

    public void setFilePathAE(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src/main/resources/images"));

        File selectedDirectory = directoryChooser.showDialog(MainApplication.getCrudStage());
        textFieldFilePath.setText(String.valueOf(selectedDirectory));
    }

    public void setImagenAE(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/images"));

        File selectedFile = fileChooser.showOpenDialog(MainApplication.getCrudStage());
        textFieldImagen.setText(String.valueOf(selectedFile));
    }

    /*----------------------
     *
     *  GUI Methods
     *
     * ----------------------*/

    public void refreshView() {
        ProductoDao productoDao = new ProductoDao();
        productosList = productoDao.getAll();
        tableViewProductos.setItems(productosList);
        tableViewProductos.refresh();
    }

    private void setFields(ProductoEntity productoEntity) {
        productoEntity.setNombre(textFieldNombre.getText());
        if (textFieldDescripcion.getText() != null) {
            productoEntity.setDescripcion(textFieldDescripcion.getText());
        }
        productoEntity.setSku(textFieldSKU.getText());
        productoEntity.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
        productoEntity.setCantidadExistencia(Integer.parseInt(textFieldCantidadExistencia.getText()));
        if (textFieldFilePath.getText() != null) {
            productoEntity.setFilePath(textFieldFilePath.getText());
        }
        if (textFieldImagen.getText() != null) {
            productoEntity.setImagen(textFieldImagen.getText());
        }
        productoEntity.setIdCategoria(Integer.parseInt(textFieldIDCategoria.getText()));

        int i = checkBoxEstado.isSelected() ? 1 : 0;
        productoEntity.setEstado((byte) i);
    }

    private void cleanTextFields() {
        textFieldNombre.setText(null);
        textFieldDescripcion.setText(null);
        textFieldSKU.setText(null);
        textFieldPrecio.setText(null);
        textFieldCantidadExistencia.setText(null);
        textFieldFilePath.setText(null);
        textFieldImagen.setText(null);
        textFieldIDCategoria.setText(null);
        checkBoxEstado.setSelected(false);
    }

    /*----------------------
     *
     *  CRUD Methods
     *
     * ----------------------*/

    public void addDataAE(ActionEvent event) {
        ProductoDao productoDao = new ProductoDao();
        ProductoEntity productoEntity = new ProductoEntity();

        setFields(productoEntity);
        productoEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        productoDao.addData(productoEntity);

        refreshView();
        cleanTextFields();
    }

    public void deleteDataAE(ActionEvent event) {
        ProductoEntity productoEntity = (ProductoEntity) tableViewProductos.getSelectionModel().getSelectedItem();

        /*-------------------------------
         *  Pop up an alert confirmation
         *-------------------------------*/

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmar Eliminación");
        alert.setContentText("¿Está seguro que desea eliminar este producto?" +
                "\nID: " + productoEntity.getIdProducto() +
                "\nNombre: " + productoEntity.getNombre());

        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.get() == ButtonType.OK) {
            ProductoDao productoDao = new ProductoDao();

            // get all fields on the selected item and assign in ProductoEntity
            productoDao.deleteData(productoEntity);

            refreshView();
            cleanTextFields();
        }
    }

    public void updateDataAE(ActionEvent event) {
        ProductoDao productoDao = new ProductoDao();
        ProductoEntity productoEntity = (ProductoEntity) tableViewProductos.getSelectionModel().getSelectedItem();

        setFields(productoEntity);
        productoEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        productoDao.updateData(productoEntity);

        refreshView();
        cleanTextFields();
    }
}

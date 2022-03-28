package com.cleanadsi.cleanadsi.controllers;

import com.cleanadsi.cleanadsi.dao.ProductoDao;
import com.cleanadsi.cleanadsi.models.ProductoEntity;
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
    public DatePicker datePickerDeletedAt;
    @FXML
    public DatePicker datePickerCreatedAt;
    @FXML
    public DatePicker datePickerUpdatedAt;
    @FXML
    public CheckBox checkBoxEstado;

    public void initialize(){
        ProductoDao productoDao = new ProductoDao();
        productosList = productoDao.getAll();

        tableViewProductos.setItems(productosList);

        tableViewProductos.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                cleanTextFields();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                ProductoEntity productoEntity = (ProductoEntity) tableViewProductos.getSelectionModel().getSelectedItem();

                textFieldIDProducto.setText(String.valueOf(productoEntity.getIdProducto()));
                textFieldNombre.setText(String.valueOf(productoEntity.getNombre()));
                textFieldDescripcion.setText((productoEntity.getDescripcion()));
                textFieldSKU.setText(productoEntity.getSku());
                textFieldPrecio.setText(String.valueOf(productoEntity.getPrecio()));
                textFieldCantidadExistencia.setText(String.valueOf(productoEntity.getCantidadExistencia()));
                textFieldFilePath.setText(productoEntity.getFilePath());
                textFieldImagen.setText(productoEntity.getImagen());
                textFieldIDCategoria.setText(String.valueOf(productoEntity.getIdCategoria()));

                if (productoEntity.getDeletedAt() != null) {
                    datePickerDeletedAt.setValue(LocalDate.parse(simpleDateFormat.format(productoEntity.getDeletedAt()), DateTimeFormatter.ofPattern("dd/MM/yyy")));
                }

                if (productoEntity.getCreatedAt() != null) {
                    datePickerCreatedAt.setValue(LocalDate.parse(simpleDateFormat.format(productoEntity.getCreatedAt()), DateTimeFormatter.ofPattern("dd/MM/yyy")));
                }

                if (productoEntity.getUpdatedAt() != null) {
                    datePickerUpdatedAt.setValue(LocalDate.parse(simpleDateFormat.format(productoEntity.getCreatedAt()), DateTimeFormatter.ofPattern("dd/MM/yyy")));
                }

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

    public void refreshView() {
        ProductoDao productoDao = new ProductoDao();
        productosList = productoDao.getAll();
        tableViewProductos.setItems(productosList);
        tableViewProductos.refresh();
    }

    private void setFields(ProductoEntity productoEntity) {
        productoEntity.setNombre(textFieldNombre.getText());
        productoEntity.setDescripcion(textFieldDescripcion.getText());
        productoEntity.setSku(textFieldSKU.getText());
        productoEntity.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
        productoEntity.setCantidadExistencia(Integer.parseInt(textFieldCantidadExistencia.getText()));
        productoEntity.setFilePath(textFieldFilePath.getText());
        productoEntity.setImagen(textFieldImagen.getText());
        productoEntity.setIdCategoria(Integer.parseInt(textFieldIDCategoria.getText()));
        if (datePickerDeletedAt.getValue() != null) {
            productoEntity.setDeletedAt(Timestamp.valueOf(datePickerDeletedAt.getValue().toString() + " " + LocalTime.now().toString()));
        }
        if (datePickerCreatedAt.getValue() != null) {
            productoEntity.setCreatedAt(Timestamp.valueOf(datePickerCreatedAt.getValue().toString() + " " + LocalTime.now().toString()));
        }
        if (datePickerUpdatedAt.getValue() != null) {
            productoEntity.setUpdatedAt(Timestamp.valueOf(datePickerUpdatedAt.getValue().toString() + " " + LocalTime.now().toString()));
        }

        int i = checkBoxEstado.isSelected() ? 1 : 0;
        productoEntity.setEstado((byte) i);
    }

    private void cleanTextFields() {
        textFieldIDProducto.setText(null);
        textFieldNombre.setText(null);
        textFieldDescripcion.setText(null);
        textFieldSKU.setText(null);
        textFieldPrecio.setText(null);
        textFieldFilePath.setText(null);
        textFieldImagen.setText(null);
        textFieldIDCategoria.setText(null);
        datePickerDeletedAt.setValue(null);
        datePickerCreatedAt.setValue(null);
        datePickerUpdatedAt.setValue(null);
        checkBoxEstado.setSelected(false);
    }

    public void addDataAE(ActionEvent event) {
        ProductoDao productoDao = new ProductoDao();
        ProductoEntity productoEntity = new ProductoEntity();

        setFields(productoEntity);

        productoDao.addData(productoEntity);

        refreshView();
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
        }
    }

    public void updateDataAE(ActionEvent event) {
        ProductoDao productoDao = new ProductoDao();
        ProductoEntity productoEntity = (ProductoEntity) tableViewProductos.getSelectionModel().getSelectedItem();

        setFields(productoEntity);

        productoDao.updateData(productoEntity);

        refreshView();
    }
}

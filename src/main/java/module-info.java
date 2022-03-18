module com.cleanadsi.cleanadsi {
    /*------------------------------------------------------------
     *   This file module-info.java is essential to let the modules
     *   connect with another modules
     * ------------------------------------------------------------*/

    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;

    // module required for "java: cannot access javax.naming.Referenceable" error
    requires java.naming;

    opens com.cleanadsi.cleanadsi to javafx.fxml;
    exports com.cleanadsi.cleanadsi;
    opens com.cleanadsi.cleanadsi.models to javafx.fxml, org.hibernate.orm.core;
    exports com.cleanadsi.cleanadsi.models;
    opens com.cleanadsi.cleanadsi.controllers to javafx.fxml;
    exports com.cleanadsi.cleanadsi.controllers;
}
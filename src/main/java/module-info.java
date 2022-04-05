module com.cleanadsi.cleanadsi {
    /*------------------------------------------------------------
     *   This file module-info.java is essential to let the modules
     *   connect with another modules
     * ------------------------------------------------------------*/

    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.bootstrapicons; // delete later
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;

    // module required for "java: cannot access javax.naming.Referenceable" error
    requires java.naming;

    opens com.cleanline.cleanlinedesktop to javafx.fxml;
    exports com.cleanline.cleanlinedesktop;
    opens com.cleanline.cleanlinedesktop.models to javafx.fxml, org.hibernate.orm.core;
    exports com.cleanline.cleanlinedesktop.models;
    opens com.cleanline.cleanlinedesktop.controllers to javafx.fxml;
    exports com.cleanline.cleanlinedesktop.controllers;
}
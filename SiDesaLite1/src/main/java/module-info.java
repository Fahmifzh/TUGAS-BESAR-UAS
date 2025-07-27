module com.sidesalite1.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql; // Diperlukan untuk koneksi database MySQL

    opens com.sidesalite1.app to javafx.fxml;
    opens com.sidesalite1.app.controller to javafx.fxml;
    opens com.sidesalite1.app.model to javafx.base; // Diperlukan untuk TableView
    
    exports com.sidesalite1.app;
    exports com.sidesalite1.app.controller;
    exports com.sidesalite1.app.model;
    exports com.sidesalite1.app.dao;
    exports com.sidesalite1.app.util;
}

   
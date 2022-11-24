module hu.petrik.rimoczidaniel_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens hu.petrik.rimoczidaniel_javafxrestclientdolgozat to javafx.fxml, com.google.gson ;
    exports hu.petrik.rimoczidaniel_javafxrestclientdolgozat;
}
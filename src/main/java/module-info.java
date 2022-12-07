module com.mamchura.weatherlive {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    opens com.mamchura.weatherlive to javafx.fxml;
    exports com.mamchura.weatherlive;
}
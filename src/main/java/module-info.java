module hr.algebra.javafxmonopoly {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.algebra.javafxmonopoly to javafx.fxml;
    exports hr.algebra.javafxmonopoly;
    exports hr.algebra.javafxmonopoly.controllers;
    opens hr.algebra.javafxmonopoly.controllers to javafx.fxml;
}
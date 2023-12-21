module hr.algebra.javafxmonopoly {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;

    exports hr.algebra.javafxmonopoly.network.RMIChat;
    opens hr.algebra.javafxmonopoly to javafx.fxml;
    exports hr.algebra.javafxmonopoly;
    exports hr.algebra.javafxmonopoly.controllers;
    opens hr.algebra.javafxmonopoly.controllers to javafx.fxml;
    exports hr.algebra.javafxmonopoly.models;
    opens hr.algebra.javafxmonopoly.models to javafx.fxml;
    exports hr.algebra.javafxmonopoly.enums;
    opens hr.algebra.javafxmonopoly.enums to javafx.fxml;
    exports hr.algebra.javafxmonopoly.utils;
    opens hr.algebra.javafxmonopoly.utils to javafx.fxml;
}
module ppazosp.bingoserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens ppazosp.bingoserver to javafx.fxml;
    exports ppazosp.bingoserver;
}
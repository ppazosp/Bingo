module ppazosp.bingoclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens ppazosp.bingoclient to javafx.fxml;
    exports ppazosp.bingoclient;
}
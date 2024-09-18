package ppazosp.bingoserver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainViewController {
    @FXML
    private Label ballLabel;

    public void update(String ballVal) {
        ballLabel.setText(ballVal);
    }

    public void bingo(String mss)
    {
        ballLabel.setText(mss);
    }
}
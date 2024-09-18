package ppazosp.bingoclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private boolean bingo = false;
    private boolean winner = false;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), -1, -1);
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setApplication(this);
        stage.setTitle("Bingo Card");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        Thread client = new MulticastClientThread("client", mainViewController, this);
        client.start();
    }

    public static void main(String[] args) {
        launch();
    }


    public boolean isBingo() {
        return bingo;
    }

    public void bingo() {
        bingo = true;
    }

    public boolean isWinner() {
        return winner;
    }

    public void winner() {
        winner = true;
    }
}
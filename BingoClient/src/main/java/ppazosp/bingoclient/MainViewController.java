package ppazosp.bingoclient;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.*;

public class MainViewController {

    MainApplication application;

    @FXML
    private GridPane ballsGrid;

    @FXML
    private Label currentBall;

    public void setApplication(MainApplication application) {
        this.application = application;
    }

    @FXML
    protected void initialize() {

        ArrayList<Integer> balls = new ArrayList<Integer>();
        for(int i = 1; i <= 30; i++) {
            balls.add(i);
        }

        Random rand = new Random(System.currentTimeMillis());

        ArrayList<Integer> selectedBalls = new ArrayList<Integer>();
        int cardSize = 15;
        for(int i = 0; i < cardSize; i++) {
            int currentRandomInt = rand.nextInt(balls.size());
            selectedBalls.add(balls.get(currentRandomInt));
            balls.remove(currentRandomInt);
        }
        Collections.sort(selectedBalls);

        int i = 0;
        for(Node node : ballsGrid.getChildren())
        {
            if(node instanceof Label l)
            {
                l.setText(String.format("%02d", selectedBalls.get(i)));
                i++;
            }
        }
    }

    public boolean update(String ballVal)
    {
        currentBall.setText(ballVal);

        for(Node node : ballsGrid.getChildren())
        {
            if(node instanceof Label label)
            {
                if (label.getText().equals(ballVal)){
                    Background bc = new Background(new BackgroundFill(
                            Color.GREY,
                            CornerRadii.EMPTY,
                            Insets.EMPTY
                    ));

                    label.setBackground(bc);

                    break;
                }
            }
        }

        return checkBingo();
    }

    public boolean checkBingo()
    {
        for(Node node : ballsGrid.getChildren())
        {
            if (node instanceof Label label)
            {
                Background bc = label.getBackground();
                if ( bc != null && !bc.getFills().isEmpty()) {
                    BackgroundFill fill = bc.getFills().getFirst();
                    if (fill.getFill() instanceof Color color) {
                        if (!color.equals(Color.GREY)) return false;
                    }else
                        return false;
                }
                else return false;
            }
        }

        return true;
    }

    public void bingo()
    {
        if (application.isWinner()) currentBall.setText("bingo");
        else currentBall.setText("you lost");
    }
}
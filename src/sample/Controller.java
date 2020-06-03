package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class Controller {

    @FXML
    private GridPane inputArray;

    @FXML
    private GridPane sortedArray;

    @FXML
    private Label radixLabel;

    @FXML
    private Label noticeLabel;

    @FXML
    private TextField numOfDigit;

    @FXML
    void radixSort(ActionEvent event) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            private int radix = 0;

            @Override
            public void handle(ActionEvent actionEvent) {
                int[] initialArray = new int[8];
                int[] indexArray = new int[10];
                radixLabel.setText("Radix : " + radix);

                for (int i = 0; i < initialArray.length; i++)
                    initialArray[i] = Integer.parseInt(getText(inputArray, i).getText());

                for (int item : initialArray) {
                    int nthDigit = getNthDigit(item, radix);
                    indexArray[nthDigit]++;
                }
                for (int i = 1; i < indexArray.length; i++) indexArray[i] += indexArray[i - 1];
                for (int i = 9; i >= 1; i--) indexArray[i] = indexArray[i - 1];
                indexArray[0] = 0;


                for (int value : initialArray) {
                    getText(sortedArray, indexArray[getNthDigit(value, radix)]).setText(String.valueOf(value));
                    getText(inputArray, indexArray[getNthDigit(value, radix)]).setText(String.valueOf(value));
                    indexArray[getNthDigit(value, radix)]++;
                }
                radix++;
            }
        }));
        timeline.setCycleCount(maxRadix(inputArray));
        timeline.play();
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(3 * maxRadix(inputArray)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                noticeLabel.setVisible(true);
            }
        }));
        timeline1.play();
    }


    public static int maxRadix(GridPane gridPane) {
        int[] initialArray = new int[8];
        int max = 0;
        for (int i = 0; i < initialArray.length; i++)
            initialArray[i] = Integer.parseInt(getText(gridPane, i).getText());
        for (int i = 0; i < initialArray.length; i++) {
            int count = 0;
            while (initialArray[i] != 0) {
                initialArray[i] /= 10;
                count++;
            }
            if (count > max) max = count;
        }
        return max;
    }

    public static int getNthDigit(int element, int radix) {
        for (int i = 0; i < radix; i++) element /= 10;
        return element % 10;
    }

    @FXML
    void resetRandom(ActionEvent event) {
        int limit = (int) Math.pow(10, Integer.parseInt(numOfDigit.getText())) - 1;
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            ((Text) inputArray.getChildren().get(i)).setText(String.valueOf(random.nextInt(limit)));
            ((Text) sortedArray.getChildren().get(i)).setText("");
        }
        radixLabel.setText("Radix : ");
        noticeLabel.setVisible(false);
    }

    public static Text getText(GridPane gridPane, int index) {
        return (Text) gridPane.getChildren().get(index);
    }
}

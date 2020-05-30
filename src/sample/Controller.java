package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Random;

public class Controller {

    @FXML
    private GridPane inputArray;

    @FXML
    private GridPane countArray;

    @FXML
    private GridPane sortedArray;

    @FXML
    private Label radix;

    @FXML
    void radixSort(ActionEvent event) {
        for (int radix = 0; radix < 3; radix++) {
            int[] initialArray = new int[8];
            int[] indexArray = new int[10];

            for (int i = 0; i < initialArray.length; i++) {
                initialArray[i] = Integer.parseInt(((Text) inputArray.getChildren().get(i)).getText());
            }// initialize the array by getting data from element id ='inputArray' from fxml file

            for (int i = 0; i < initialArray.length; i++) {
                // animation, transition code here
                int nthDigit = getNthDigit(initialArray[i], radix);
                indexArray[nthDigit]++;
                ((Text) countArray.getChildren().get(i)).setText(String.valueOf(indexArray[nthDigit]));
                // animation, transition code here
            }

            for (int i = 1; i < indexArray.length; i++) {
                indexArray[i] += indexArray[i - 1];
                ((Text) countArray.getChildren().get(i)).setText(String.valueOf(indexArray[i]));
                // animation, transition code here
            }

            for (int i = 9; i >= 1; i--) indexArray[i] = indexArray[i - 1];
            indexArray[0] = 0;
            for (int i = 0; i < 10; i++) {
                ((Text) countArray.getChildren().get(i)).setText(String.valueOf(indexArray[i]));
                // animation, transition code here
            }

            for (int value : initialArray) {
                ((Text) sortedArray.getChildren().get(indexArray[getNthDigit(value, radix)])).setText(String.valueOf(value));
                ((Text) inputArray.getChildren().get(indexArray[getNthDigit(value, radix)])).setText(String.valueOf(value));
                indexArray[getNthDigit(value, radix)]++;
            }
        }
    }

    public static int getNthDigit(int element, int radix) {
        for (int i = 0; i < radix; i++) element /= 10;
        return element % 10;
    } // return digit at specific radix, for example getNthDigit(802,2) return 8

    @FXML
    void resetRandom(ActionEvent event) {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            ((Text) inputArray.getChildren().get(i)).setText(String.valueOf(random.nextInt(999)));
            ((Text) sortedArray.getChildren().get(i)).setText("");
        }
        for (int i = 0; i < 10; i++) {
            ((Text) countArray.getChildren().get(i)).setText("0");
        }
    }
}

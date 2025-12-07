import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SimpleCalculator extends Application {
    private TextField display = new TextField();
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Windows 11 Style Calculator");

        display.setFont(Font.font(24));
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setPrefHeight(60);
        display.setStyle("-fx-background-color: #EDEDED; -fx-text-fill: black; -fx-background-radius: 10;");

        Button[] numberButtons = new Button[10];
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = createButton(String.valueOf(i));
            final int number = i;
            numberButtons[i].setOnAction(e -> appendNumber(number));
        }

        Button addButton = createButton("+");
        Button subtractButton = createButton("-");
        Button multiplyButton = createButton("*");
        Button divideButton = createButton("/");
        Button equalsButton = createButton("=");
        Button clearButton = createButton("C");

        addButton.setOnAction(e -> setOperator("+"));
        subtractButton.setOnAction(e -> setOperator("-"));
        multiplyButton.setOnAction(e -> setOperator("*"));
        divideButton.setOnAction(e -> setOperator("/"));
        equalsButton.setOnAction(e -> calculate());
        clearButton.setOnAction(e -> clear());

        GridPane numberGrid = new GridPane();
        numberGrid.setHgap(10);
        numberGrid.setVgap(10);
        numberGrid.setAlignment(Pos.CENTER);

        int index = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                numberGrid.add(numberButtons[index], j, i);
                index++;
            }
        }
        numberGrid.add(numberButtons[0], 1, 3);
        Button dotButton = createButton(".");
        dotButton.setOnAction(e -> appendDot());
        numberGrid.add(dotButton, 2, 3);

        VBox operations = new VBox(10, addButton, subtractButton, multiplyButton, divideButton, equalsButton, clearButton);
        operations.setAlignment(Pos.CENTER);

        HBox mainBox = new HBox(10, numberGrid, operations);
        mainBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, display, mainBox);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F3F3F3;");

        Scene scene = new Scene(root, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(18));
        btn.setPrefSize(60, 60);
        btn.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 10;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-background-radius: 10;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 10;"));
        return btn;
    }

    private void appendNumber(int number) {
        if (startNewNumber) {
            display.clear();
            startNewNumber = false;
        }
        display.appendText(String.valueOf(number));
    }

    private void appendDot() {
        if (startNewNumber) {
            display.clear();
            startNewNumber = false;
        }
        if (!display.getText().contains(".")) {
            display.appendText(".");
        }
    }

    private void setOperator(String chosenOperator) {
        try {
            firstNumber = Double.parseDouble(display.getText());
            operator = chosenOperator;
            startNewNumber = true;
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private void calculate() {
        try {
            double secondNumber = Double.parseDouble(display.getText());
            double result;

            switch (operator) {
                case "+" -> result = firstNumber + secondNumber;
                case "-" -> result = firstNumber - secondNumber;
                case "*" -> result = firstNumber * secondNumber;
                case "/" -> {
                    if (secondNumber == 0) {
                        display.setText("Error: /0");
                        startNewNumber = true;
                        return;
                    }
                    result = firstNumber / secondNumber;
                }
                default -> result = secondNumber;
            }

            display.setText(String.valueOf(result));
            startNewNumber = true;

        } catch (NumberFormatException e) {
            display.setText("Error");
            startNewNumber = true;
        }
    }

    private void clear() {
        display.clear();
        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

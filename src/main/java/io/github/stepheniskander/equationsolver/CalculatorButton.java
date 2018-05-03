package io.github.stepheniskander.equationsolver;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class CalculatorButton extends Button {
    public CalculatorButton() {
        this("");
    }

    public CalculatorButton(String text) {
        super(text);
        this.setMaxWidth(Double.POSITIVE_INFINITY);
        this.setMaxHeight(Double.POSITIVE_INFINITY);
        GridPane.setHgrow(this, Priority.ALWAYS);
        GridPane.setVgrow(this, Priority.ALWAYS);
        this.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
    }
}

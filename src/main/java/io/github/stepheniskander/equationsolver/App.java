package io.github.stepheniskander.equationsolver;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

/**
 * @author Nick
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        ArrayList<String> inOutList = new ArrayList<>(); //Contains inputs and outputs as strings to be shown to the user
        TextField inField = new TextField(); //The 
        TextArea outField = new TextArea();
        outField.setEditable(false);
        outField.setMaxSize(300, 400); //This dissallows the user to input text in the output box
        inField.setMaxWidth(250);
        inField.setPromptText("Please enter your expression");
        Button btn = new Button();
        btn.setTranslateX(151);

        btn.setText("Enter");

        ExpressionParser parser = new ExpressionParser();
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                outField.clear();
                String s = inField.getText();
                String[] argus = s.split(" ");
                double result;
                if (s.trim().length() != 0) {
                    if(argus[0].equalsIgnoreCase("matrix")){
                        if(argus[1].charAt(0) == '['){
                            MatrixParser matrixParser = new MatrixParser();
                            String inputString = s.substring(7, s.length()).trim();
                            Matrix inputMatrix = matrixParser.parse(inputString);
                            inField.setText("");
                            inField.end();
                            inOutList.add("-------------\n" + inputMatrix.toString() + "\n-------------\n");
                        }
                    }else {
                        Expression ex = parser.parse(s);
                        result = ex.evaluateRpn();
                        inField.setText(String.valueOf(result));
                        inField.end();
                        inOutList.add(s + ":"); //All inputs and outputs will be added to the list in the order they were entered and shown to the user in the output field
                        inOutList.add("             " + String.valueOf(result));
                    }
                } else {
                    inField.setText("");
                }
                for (String item : inOutList) {
                    outField.appendText(item + "\n");
                }

            }
        });

        inField.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ENTER) {
                outField.clear();
                String s = inField.getText();
                String[] argus = s.split(" ");

                double result;
                if (s.trim().length() != 0) {
                    if(argus[0].equalsIgnoreCase("matrix")){
                        if(argus[1].charAt(0) == '['){
                            MatrixParser matrixParser = new MatrixParser();
                            String inputString = s.substring(7, s.length()).trim();
                            Matrix inputMatrix = matrixParser.parse(inputString);
                            inField.setText("");
                            inField.end();
                            inOutList.add("-------------\n" + inputMatrix.toString() + "\n-------------\n");
                        }
                    }else{
                        Expression ex = parser.parse(s);
                        result = ex.evaluateRpn();
                        inField.setText(String.valueOf(result));
                        inField.end();
                        inOutList.add(s + ":"); //All inputs and outputs will be added to the list in the order they were entered and shown to the user in the output field
                        inOutList.add("             " + String.valueOf(result));
                    }

                } else {
                    inField.setText("");
                }
                for (String item : inOutList) {
                    outField.appendText(item + "\n");
                }
            }
        });

        //These are the alignments to this pane that I have been experimenting with
        StackPane root = new StackPane();
        root.getChildren().addAll(btn, inField, outField);
        root.setAlignment(btn, Pos.BOTTOM_CENTER);
        root.setAlignment(inField, Pos.BOTTOM_CENTER);

        root.setAlignment(outField, Pos.TOP_CENTER);
        btn.toFront();
        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("MathBoy3000");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}

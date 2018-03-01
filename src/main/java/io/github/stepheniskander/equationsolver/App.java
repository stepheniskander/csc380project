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
 *
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
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                outField.clear();
                String s = inField.getText();
                if(s.trim().length()!=0) {

                    inOutList.add(s); //All inputs and outputs will be added to the list in the order they were entered and shown to the user in the output field
                }
                for(String item:inOutList){
                    outField.appendText(item + "\n");
                }

                inField.setText("");
            }
        });

        inField.setOnKeyPressed((KeyEvent ke) -> {
            if(ke.getCode() == KeyCode.ENTER) {
                outField.clear();
                String s = inField.getText();
                if(s.trim().length()!=0) {
                    inOutList.add(s); //All inputs and outputs will be added to the list in the order they were entered and shown to the user in the output field
                }
                    for (String item : inOutList) {
                    outField.appendText(item + "\n");
                }

                inField.setText("");
            }
        });

        //These are the allignments to this pane that I have been experimenting with
        StackPane root = new StackPane();
        root.getChildren().addAll(btn, inField,outField);
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

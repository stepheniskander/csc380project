/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiskeleton;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.control.TextField;

/**
 *
 * @author Nick
 */
public class GUISkeleton extends Application {
    ArrayList<String> inOutlist = new ArrayList();
    @Override
    public void start(Stage primaryStage) {
        TextField inOutField = new TextField();
        
        Button btn = new Button();
        btn.setText("Enter");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello Succ!");
            }
        });
        
        StackPane root = new StackPane();
        
        root.getChildren().addAll(btn, inOutField);
        
        Scene scene = new Scene(root, 800, 500);
        
        primaryStage.setTitle("GUI");
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

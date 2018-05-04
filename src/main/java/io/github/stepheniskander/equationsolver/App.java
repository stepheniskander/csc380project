package io.github.stepheniskander.equationsolver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nick
 */
public class App extends Application {
    private ArrayList<String> inOutList;
    private TextField inField;
    private Button btn;
    private TextArea outField;
    private HashMap<String,Matrix> matrixMap;
    private GridPane buttonPane;
    static AtomicInteger inOutListIndex;

    private void handleInput() {
        try {
            outField.clear();
            String s = inField.getText().trim();
            inOutList.add(s);
            String[] argus = s.split(" ");
            if (s.length() != 0) {
                if(s.matches("^[A-Z]$")) {
                    Matrix m = matrixMap.get(s);
                    if(m != null) {
                        inOutList.add("=        " + m.toString());
                        inField.setText(m.toString());
                    } else {
                        inOutList.add("         " + "matrix " + s + " is undefined");
                        inField.setText("");
                    }
                } else if(s.matches("^store\\(.+$")) {
                    try {
                        Matcher storeMatcher = Pattern.compile("store\\(([A-Z]+),(.+)\\)").matcher(s);
                        storeMatcher.matches();
                        String name = storeMatcher.group(1).trim();
                        Matrix m = Parser.parseMatrix(storeMatcher.group(2).trim());
                        matrixMap.put(name, m);
                        inField.setText("");
                        inOutList.add("         " + name + "←" + m.toString());
                    } catch(IllegalStateException e) {
                        inOutList.add("         " + "ERROR");
                    }
                } else if(s.matches("^mmul\\(.+$")) {
                    Matrix result;
                    if(!s.contains("[")) {
                        Matcher mmulMatcher = Pattern.compile("mmul\\(\\s*([A-Z])\\s*,\\s*([A-Z])\\s*\\)").matcher(s);
                        mmulMatcher.matches();
                        Matrix m1 = matrixMap.get(mmulMatcher.group(1));
                        Matrix m2 = matrixMap.get(mmulMatcher.group(2));
                        result = Matrix.matrixMultiply(m1, m2);
                    } else {
                        Matrix[] ms = new Matrix[2];
                        for(int i = 0; i < ms.length; i++) {
                            int start = s.indexOf("[");
                            int end = start + 1;
                            int lBracketCount = 1;
                            int rBracketCount = 0;
                            for (; end < s.length() && lBracketCount != rBracketCount; end++) {
                                if (s.charAt(end) == '[')
                                    lBracketCount++;
                                else if (s.charAt(end) == ']')
                                    rBracketCount++;
                            }
                            ms[i] = Parser.parseMatrix(s.substring(start, end));
                            s = s.substring(end);
                        }
                        result = Matrix.matrixMultiply(ms[0], ms[1]);
                    }
                    inOutList.add("=        " + result);
                    inField.setText(result.toString());
                } else {
                    try {
                        BigDecimal result;
                        Expression ex = Parser.parseExpression(s);
                        result = ex.evaluateRpn();
                        result = result.stripTrailingZeros();
                        inField.setText(String.valueOf(result));
                        //All inputs and outputs will be added to the list in the order they were entered and shown to the user in the output field
                        inOutList.add("=        " + result.toString());
                    } catch (NoSuchElementException|IllegalStateException e) {
                        inOutList.add("         " + "ERROR");
                    }
                }
            } else {
                inField.setText("");
            }
        }
        catch(ArithmeticException ae){
            inField.setText("Arithmetic exception: i.e. divide by 0");
        }
        finally {
            inField.end();
            inOutListIndex.set(inOutList.size());
            for (String item : inOutList) {
                outField.appendText(item + "\n");
            }
        }

    }
    
    @Override
    public void start(Stage primaryStage) {
        inOutList = new ArrayList<>(); //Contains inputs and outputs as strings to be shown to the user
        inField = new TextField();
        inField.setStyle("-fx-font-family: \"Hack\"; -fx-font-size: 16;");
        outField = new TextArea();
        outField.setStyle("-fx-font-family: \"Hack\"; -fx-font-size: 16;");
        outField.setEditable(false);
        // inField.setPromptText("Please enter your expression");
        btn = new Button();
        btn.setText("Enter");
        btn.managedProperty().bind(btn.visibleProperty());
        btn.setVisible(false);

        matrixMap = new HashMap<>();
        btn.setOnAction(event -> handleInput());

        //These are the alignments to this pane that I have been experimenting with

        VBox root = new VBox();
        VBox uiBox = new VBox();
        uiBox.setSpacing(10);
        uiBox.setPadding(new Insets(10, 10, 10, 10));
        VBox.setVgrow(uiBox, Priority.ALWAYS);
        VBox.setVgrow(outField, Priority.ALWAYS);
        HBox inputBox = new HBox();
        inputBox.setSpacing(5);
        HBox.setHgrow(inField, Priority.ALWAYS);
        inputBox.getChildren().addAll(inField, btn);
        uiBox.getChildren().addAll(outField, inputBox);

        MenuBar menuBar = generateMenuBar();
        root.getChildren().addAll(menuBar, uiBox);

        buttonPane = generateButtonPane();
        buttonPane.managedProperty().bind(buttonPane.visibleProperty());
        uiBox.getChildren().add(buttonPane);
        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("font.css").toExternalForm());

        inField.requestFocus();

        inOutListIndex = new AtomicInteger(inOutList.size() - 1);
        AtomicReference<String> partialInput = new AtomicReference<>("");

        inField.setOnKeyPressed(ke -> {
            switch (ke.getCode()) {
                case ENTER:
                    handleInput();
                    break;
                case UP:
                    if (inOutListIndex.get() == inOutList.size())
                        partialInput.set(inField.getText());
                    if (inOutListIndex.get() >= 2) {
                        inField.setText(inOutList.get(inOutListIndex.addAndGet(-2)));
                    }
                    inField.end();
                    ke.consume();
                    break;
                case DOWN:
                    if (inOutListIndex.get() == inOutList.size() || inOutListIndex.get() == inOutList.size() - 2) {
                        inField.setText(partialInput.get());
                        inOutListIndex.set(inOutList.size());
                    } else if (inOutListIndex.get() < inOutList.size() - 2) {
                        inField.setText(inOutList.get(inOutListIndex.addAndGet(2)));
                    }
                    inField.end();
                    break;
                default:
                    inOutListIndex.set(inOutList.size());
                    break;
            }
        });

        primaryStage.setTitle("MathBoy3000");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar generateMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        file.getItems().add(exit);

        Menu functions = new Menu("Functions");

        class FunctionMenuItem extends MenuItem {
            private FunctionMenuItem() {
                this("");
            }

            private FunctionMenuItem(String title) {
                super(title);
                this.setStyle("-fx-font-family: \"Hack\";");
            }
        }

        MenuItem integral = new FunctionMenuItem(String.format("%-20s\t%s", "Integral", "int(function, start, end)"));
        integral.setOnAction(e -> {
            inField.insertText(inField.getCaretPosition(), "int()");
            inField.backward();
        });
        MenuItem derivative = new FunctionMenuItem(String.format("%-20s\t%s", "Derivative", "der(function, point)"));
        derivative.setOnAction(e -> {
            inField.insertText(inField.getCaretPosition(), "der()");
            inField.backward();
        });
        MenuItem mmul = new FunctionMenuItem(String.format("%-20s\t%s", "Matrix Multiply", "mmul(m1, m2)"));
        mmul.setOnAction(e -> {
            inField.insertText(inField.getCaretPosition(), "mmul()");
            inField.backward();
        });
        MenuItem store = new FunctionMenuItem(String.format("%-20s\t%s", "Store Matrix", "store(dest, matrix)"));
        store.setOnAction(e -> {
            inField.insertText(inField.getCaretPosition(), "store()");
            inField.backward();
        });
        functions.getItems().addAll(integral, derivative, mmul, store);

        Menu view = new Menu("View");
        CheckMenuItem buttonToggle = new CheckMenuItem("Toggle Calculator Buttons");
        buttonToggle.setSelected(true);
        buttonToggle.setOnAction(e -> {
            CheckMenuItem m = (CheckMenuItem) e.getSource();

            if(m.isSelected()) {
                btn.setVisible(false);
                buttonPane.setVisible(true);
            } else {
                btn.setVisible(true);
                buttonPane.setVisible(false);
            }
        });
        view.getItems().add(buttonToggle);

        Menu help = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> {
            Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
            aboutDialog.setTitle("About");
            aboutDialog.setHeaderText(" ");
            try {
                ImageView logoView = new ImageView(new Image(this.getClass().getClassLoader().getResource("mathboylogo.png").toURI().toString()));
                logoView.setPreserveRatio(true);
                logoView.setSmooth(true);
                logoView.setFitHeight(100);
                aboutDialog.setGraphic(logoView);
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
            aboutDialog.setContentText("Literally the best calculator you've ever seen.");
            aboutDialog.showAndWait();
        });
        help.getItems().add(about);

        menuBar.getMenus().addAll(file, functions, view, help);
        return menuBar;
    }

    private GridPane generateButtonPane() {
        GridPane buttonPane = new GridPane();

        class CalculatorButton extends Button {
            private CalculatorButton() {
                this("");
            }

            private CalculatorButton(String text) {
                super(text);
                this.setMaxWidth(Double.POSITIVE_INFINITY);
                this.setMaxHeight(Double.POSITIVE_INFINITY);
                GridPane.setHgrow(this, Priority.ALWAYS);
                GridPane.setVgrow(this, Priority.ALWAYS);
                this.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
            }
        }

        CalculatorButton btn7 = new CalculatorButton("7");
        CalculatorButton btn8 = new CalculatorButton("8");
        CalculatorButton btn9 = new CalculatorButton("9");
        CalculatorButton btnDiv = new CalculatorButton("÷");
        CalculatorButton btnDel = new CalculatorButton("↵");
        CalculatorButton btnClr = new CalculatorButton("⌫");
        CalculatorButton btn4 = new CalculatorButton("4");
        CalculatorButton btn5 = new CalculatorButton("5");
        CalculatorButton btn6 = new CalculatorButton("6");
        CalculatorButton btnMul = new CalculatorButton("×");
        CalculatorButton btnLPar = new CalculatorButton("(");
        CalculatorButton btnRPar = new CalculatorButton(")");
        CalculatorButton btn1 = new CalculatorButton("1");
        CalculatorButton btn2 = new CalculatorButton("2");
        CalculatorButton btn3 = new CalculatorButton("3");
        CalculatorButton btnMin = new CalculatorButton("-");
        CalculatorButton btnExp = new CalculatorButton("xⁿ");
        CalculatorButton btn0 = new CalculatorButton("0");
        CalculatorButton btnDot = new CalculatorButton(".");
        CalculatorButton btnPlus = new CalculatorButton("+");
        CalculatorButton btnEq = new CalculatorButton("=");
        btnEq.setStyle(btnEq.getStyle() + "-fx-background-color: #2196F3;");

        EventHandler<ActionEvent> addLabelToInput = e -> {
            inField.appendText(((Button) e.getSource()).getText());
            inField.requestFocus();
            inField.deselect();
            inField.positionCaret(inField.getLength());
        };

        btn7.setOnAction(addLabelToInput);
        btn8.setOnAction(addLabelToInput);
        btn9.setOnAction(addLabelToInput);
        btn4.setOnAction(addLabelToInput);
        btn5.setOnAction(addLabelToInput);
        btn6.setOnAction(addLabelToInput);
        btnLPar.setOnAction(addLabelToInput);
        btnRPar.setOnAction(addLabelToInput);
        btn1.setOnAction(addLabelToInput);
        btn2.setOnAction(addLabelToInput);
        btn3.setOnAction(addLabelToInput);
        btnMin.setOnAction(addLabelToInput);
        btn0.setOnAction(addLabelToInput);
        btnDot.setOnAction(addLabelToInput);
        btnPlus.setOnAction(addLabelToInput);

        btnDiv.setOnAction(e -> {
            inField.appendText("/");
            inField.requestFocus();
            inField.deselect();
            inField.positionCaret(inField.getLength());

        });
        btnMul.setOnAction(e -> {
            inField.appendText("*");
            inField.requestFocus();
            inField.deselect();
            inField.positionCaret(inField.getLength());

        });
        btnExp.setOnAction(e -> {
            inField.appendText("^");
            inField.requestFocus();
            inField.deselect();
            inField.positionCaret(inField.getLength());

        });

        btnDel.setOnAction(e -> {
            inField.positionCaret(inField.getLength());
            inField.deletePreviousChar();
            inField.requestFocus();
            inField.deselect();
            inField.positionCaret(inField.getLength());
        });

        btnClr.setOnAction(e -> {
            if(inField.getText().isEmpty()) {
                outField.clear();
            } else {
                inField.clear();
            }
            inField.requestFocus();
            inField.deselect();
            inField.positionCaret(inField.getLength());
        });

        btnEq.setOnAction(e -> handleInput());

        buttonPane.add(btn7, 0, 0);
        buttonPane.add(btn8, 1, 0);
        buttonPane.add(btn9, 2, 0);
        buttonPane.add(btnDiv, 3, 0);
        buttonPane.add(btnDel, 4, 0);
        buttonPane.add(btnClr, 5, 0);

        buttonPane.add(btn4, 0, 1);
        buttonPane.add(btn5, 1, 1);
        buttonPane.add(btn6, 2, 1);
        buttonPane.add(btnMul, 3, 1);
        buttonPane.add(btnLPar, 4, 1);
        buttonPane.add(btnRPar, 5, 1);

        buttonPane.add(btn1, 0, 2);
        buttonPane.add(btn2, 1, 2);
        buttonPane.add(btn3, 2, 2);
        buttonPane.add(btnMin, 3, 2);
        buttonPane.add(btnExp, 4, 2);

        buttonPane.add(btn0, 0, 3);
        buttonPane.add(btnDot, 1, 3);
        buttonPane.add(btnPlus, 3, 3);
        buttonPane.add(btnEq, 4, 3, 2, 1);
        buttonPane.setHgap(5);
        buttonPane.setVgap(5);
        buttonPane.setMaxWidth(Double.POSITIVE_INFINITY);

        return buttonPane;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}

package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import static javafx.geometry.Pos.*;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;

/**
 * lytte
 *  needs Scrollbar addition, do not forget :(
 */
public class Main extends Application {
    public static int year;
    public static double loan;
    public double getInter(double loan, double interestR, int year) {
        double returnAble = loan * Math.pow((1+interestR), year);
        return returnAble;
    } //

    final ScrollBar scroll = new ScrollBar();
    Scene scene1, scene2;
    Stage window;
    Button loancalc = new Button("calculate loan");
    TextField inputYear = new TextField();
    TextField inputAmount = new TextField();// the above set the constants year and loan
    Label[] table = new Label[51]; // creates the looped through table
    @Override
    public void start(Stage primaryStage) throws Exception{
        //shadow for the Scrollbar, I believe
        Group root = new Group();
        scene2 = new Scene(root, 500, 800);
        scroll.setLayoutX(scene2.getWidth()-scroll.getWidth());
        scroll.setMin(0);
        scroll.setOrientation(Orientation.VERTICAL);
        scroll.setPrefHeight(500); //how will this change it?
        scroll.setMax(800); //ditto

        window = primaryStage;
        inputYear.setPromptText("Years");
        inputYear.setStyle("-fx-text-inner-color: #c875ff;");
        inputAmount.setPromptText("Loan");
        inputAmount.setStyle("-fx-text-inner-color: #c875ff;");
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.CEILING);
        DecimalFormat percent = new DecimalFormat("#.000");

        loancalc.setOnAction(e -> {
            year = Integer.parseInt(inputYear.getText());
            loan = Double.parseDouble(inputAmount.getText());
            double interestR = .04875;
            double months = year * 12;
            //sets the Text for the top of the Table
            table[0] = new Label("\t" + "Interest Rate" + "\t" + "Monthly Payment" + "\t" + "Total payment");
            GridPane gridshow = new GridPane(); //creates gridshow
            gridshow.setAlignment(TOP_CENTER);
            gridshow.setVgap(15.0);
            gridshow.setHgap(10.0);
            gridshow.add(table[0], 0, 0);
            //the below loops starts in the middle of gridshow, in order to add labels in a Table like manner.
            for(int i = 0; i < 25; i++) {
                interestR += .00125;
                double displayInterestR = interestR * 100;
                Double totalLoan = getInter(loan, interestR, year);
                double monthlyR = totalLoan / months;
                table[i] = new Label("\t" + "%" + percent.format(displayInterestR) + "\t" +"\t $" + df.format(monthlyR) + "\t" +"\t $" + df.format(totalLoan));
                gridshow.add(table[i], 0, i + 1); //adds the next iteration of the table
            }
            //The below code is not my own, This code is free provided by Oracle, link
            //http://docs.oracle.com/javafx/2/ui_controls/scrollbar.htm
            scroll.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    gridshow.setLayoutY(-new_val.doubleValue());
                }
            });
            //end of borrowed scrollbar
            root.getChildren().addAll(gridshow, scroll);
            window.setScene(scene2);
            window.show();

        });
        GridPane gridA = new GridPane();
        gridA.setAlignment(CENTER);
        gridA.setVgap(15.0);
        gridA.setHgap(10.0);
        gridA.setId("dummyID");
        gridA.add(loancalc, 1, 0);
        gridA.add(inputYear, 0, 0);
        gridA.add(inputAmount, 0, 1);
        scene1 = new Scene(gridA, 400, 600);
        window.setScene(scene1);
        window.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}


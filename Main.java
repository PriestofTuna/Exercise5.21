package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static javafx.geometry.Pos.*;

public class Main extends Application {
    public static int year;
    public static double loan;
    public double getInter(double loan, double interestR, int year) {
        double returnAble = loan * Math.pow((1+interestR), year);
        return returnAble;
    } //
    Scene scene1, scene2;
    Stage window;
    Button loancalc = new Button("calculate loan");
    TextField inputYear = new TextField();
    TextField inputAmount = new TextField();// the above set the constants year and loan
    Label[] table = new Label[51]; // creates the looped through table
    @Override
    public void start(Stage primaryStage) throws Exception{
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

            table[0] = new Label("Interest Rate" + "\t" + "Monthly Payment" + "\t" + "Total payment");
            GridPane gridshow = new GridPane();
            gridshow.setAlignment(TOP_CENTER);
            gridshow.setVgap(15.0);
            gridshow.setHgap(10.0);
            gridshow.add(table[0], 0, 0);
            for(int i = 0; i < 25; i++) {
                //System.out.println(table.length + "\t" + i);
                interestR += .00125;
                double displayInterestR = interestR * 100;
                Double totalLoan = getInter(loan, interestR, year);
                double monthlyR = totalLoan / months;
                table[i] = new Label("%" + percent.format(displayInterestR) + "\t" +"\t $" + df.format(monthlyR) + "\t" +"\t $" + df.format(totalLoan));
                gridshow.add(table[i], 0, i + 1);
                System.out.println("%" + percent.format(displayInterestR) + "\t" +"\t $" + df.format(monthlyR) + "\t" +"\t $" + df.format(totalLoan));
            }

            scene2 = new Scene(gridshow, 600, 800);
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


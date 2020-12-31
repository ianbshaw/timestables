/*Program to display multiplication table as lines on a circle
* Ian Bradshaw
* CS351 Project 1*/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;

public class TimesTable extends Application {

    /*main circle constraints*/
    final static double radius = 300.0f;
    final static double setX = 300.0f;
    final static double setY = 300.0f;
    /*default runtime values*/
    private double timesTable = 2;
    private int numPoints = 1;
    private int increment = 1;
    private double speed = 1;
    private boolean tourFlag = false;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        Group lineGroup = new Group();

        VBox input = new VBox();
        input.setPadding(new Insets(150, 12, 12, 15));
        HBox buttons = new HBox();

        /*slider creations to alter runtime values*/
        Slider numPointsSlider = new Slider(0,360, 1);
        numPointsSlider.setShowTickMarks(true);
        numPointsSlider.setShowTickLabels(true);
        numPointsSlider.setMajorTickUnit(10.00f);
        numPointsSlider.setBlockIncrement(1.00f);

        Slider timesTableSlider = new Slider(0, 360, 2);
        timesTableSlider.setShowTickMarks(true);
        timesTableSlider.setShowTickLabels(true);
        timesTableSlider.setMajorTickUnit(10.00f);
        timesTableSlider.setBlockIncrement(1.00f);

        Slider incrementSlider = new Slider(1, 10, 1);
        incrementSlider.setShowTickMarks(true);
        incrementSlider.setShowTickLabels(true);
        incrementSlider.setMajorTickUnit(1.0f);
        incrementSlider.setBlockIncrement(2.00f);

        Slider speedSlider = new Slider(1, 10, 1);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(1.0f);
        speedSlider.setBlockIncrement(2.00f);

        /*Labels for sliders*/
        Label numPointsSliderLabel = new Label("Number of Points");
        numPointsSliderLabel.setLabelFor(numPointsSlider);
        Label timesTableSliderLabel = new Label("Times Table");
        timesTableSliderLabel.setLabelFor(timesTableSlider);
        Label incrementSliderLabel = new Label("Increment");
        incrementSliderLabel.setLabelFor(incrementSlider);
        Label speedSliderLabel = new Label("Speed");
        speedSliderLabel.setLabelFor(speedSlider);

        /*buttons creation*/
        Button set = new Button("Set");
        Button run = new Button("Run");
        Button pause = new Button("Pause");
        Button restart = new Button("Restart");
        Button tour = new Button("Tour");

        /*put sliders labels and buttons into VBox*/
        buttons.getChildren().addAll(run, pause, restart, set);
        input.getChildren().addAll(numPointsSliderLabel, numPointsSlider, timesTableSliderLabel, timesTableSlider,
                incrementSliderLabel, incrementSlider, speedSliderLabel, speedSlider, buttons, tour);

        /*main circle structure*/
        Circle circle = new Circle();
        circle.setCenterX(setX);
        circle.setCenterY(setY);
        circle.setRadius(radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        Pane pane = new Pane(circle); //main circle pane that will hold points and lines

        /*VBox in order to move main circle structure*/
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(50, 12, 15, 100));
        vBox.getChildren().add(pane);

        root.setCenter(vBox);
        root.setLeft(input);

        stage.setScene(new Scene(root, 1000 ,700));
        stage.show();

        /*Timer to activate runtime*/
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private int c = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= (1_000_000_000 / speed)) {
                    double r, g, b;
                    Random rand = new Random();
                    r = rand.nextDouble();
                    g = rand.nextDouble();
                    b = rand.nextDouble();
                    Color color = new Color(r, g, b, 1);

                    if (timesTable > 360)
                        timesTable = 2;
                    if (numPoints > 360)
                        numPoints = 360;
                    if (!tourFlag) {
                        displayLineGroup(lineGroup, color, pane, numPoints, timesTable); //main function call
                        if (c < numPoints){
                            timesTable += increment;
                        } else {
                            numPoints += 1;
                            timesTable = 2;
                            c = 0;
                        }
                    } else {
                        timesTable = 2;
                        displayLineGroup(lineGroup, color, pane, numPoints, timesTable); //tour function call
                        if ( numPoints < 360) {
                            numPoints += 10;
                        } else numPoints = 1;
                    }
                    c++;
                    lastUpdate = now;
                }
            }
        };

        /*button action functions to run,pause,restart and set values*/
        run.setOnAction(event -> timer.start());
        set.setOnAction(event -> {
            timesTable = timesTableSlider.getValue();
            numPoints = (int) numPointsSlider.getValue();
            increment = (int) incrementSlider.getValue();
            speed = speedSlider.getValue();
            timer.start();
        });

        restart.setOnAction(event -> {
            numPoints = 1;
            timesTable = 2.0;
            increment = 1;
            speed = 1;
            tourFlag = false;
        });

        pause.setOnAction(event -> timer.stop());

        tour.setOnAction(event -> {
            numPoints = 1;
            tourFlag = true;
            timer.start();
        });

    }

    /*main function that will create points and lines once per animation timer tick*/
    public void displayLineGroup(Group lineGroup, Color color, Pane pane, int numPoints, double tableNum) {
        ArrayList<Circle> circleList = PointList.pointSetup(numPoints, setX, setY); //circle and point creation method call

        lineGroup.getChildren().clear();
        pane.getChildren().remove(lineGroup);

        for (int c = 0; c < numPoints; c++){
            lineGroup.getChildren().add(circleList.get(c));
        }

        for (int c = 0; c < numPoints; c++){
            lineGroup.getChildren().add(LineSetup.create(numPoints, circleList, tableNum, color, c)); //line creation method call
        }
        circleList.clear();                     //clear list for next function call

        pane.getChildren().add(lineGroup);
    }
}

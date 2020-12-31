import javafx.scene.shape.Circle;
import java.awt.*;
import java.util.ArrayList;

public class PointList {

    /*points with x y coordinates used to create circle structures
    * takes a number of points as well as x,y coordinates of main circle as input
    * outputs a list of circles tied to the created points*/
    public static ArrayList<Circle> pointSetup(int numPoints, double cX, double cY) {
        /*values in order to place points on main circle*/
        final int radius = 300;
        final double increment = 360d / numPoints;
        double degrees = 0;
        ArrayList<Point> pointList = new ArrayList<>();

        /*get x and y coordinates based on number of points and size of main circle*/
        for (int c = 0; c < numPoints + 1; c++) {
            double x = radius * Math.cos(Math.toRadians(degrees));
            double y = radius * Math.sin(Math.toRadians(degrees));

            degrees += increment;

            pointList.add(new Point((int) x, (int) y));
        }

        /*circle structures to represent points on main circle edge*/
        ArrayList<Circle> circleList = new ArrayList<>();

        for (Point p : pointList) {
            circleList.add(new Circle(cX + p.getX(), cY + p.getY(), 5));
        }
        return circleList;
    }
}

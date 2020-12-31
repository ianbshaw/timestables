import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;

public class LineSetup {

    /*single line structure to be called by main function
    * takes a list of circles and draws lines based on current index and product of index and multiplier*/
    public static Line create (int numPoints, ArrayList<Circle> circleList, double tableNum, Color color, int i) {
        double product = i * tableNum;
        if (product > numPoints)
            product %= numPoints;

        Line line = new Line(circleList.get(i).getCenterX(), circleList.get(i).getCenterY(),
                circleList.get((int) product).getCenterX(), circleList.get((int) product).getCenterY());

        line.setStroke(color);

        return line;
    }
}

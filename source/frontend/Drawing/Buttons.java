package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public interface Buttons {

    Figure draw(Point startPoint, Point endPoint, Color color, GraphicsContext gc);
}

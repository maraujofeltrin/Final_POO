package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public interface Buttons {



    Figure draw(Point startPoint, Point endPoint, javafx.scene.paint.Color color, GraphicsContext gc);
}

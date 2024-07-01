package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRectangle extends DrawFigure {

    @Override
    public Figure draw(Point startPoint, Point endPoint, Color color, GraphicsContext gc) {
        return new Rectangle(startPoint, endPoint);
    }
}

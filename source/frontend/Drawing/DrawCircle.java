package frontend.Drawing;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawFigure{

    @Override
    public Figure draw(Point startPoint, Point endPoint, Color color, GraphicsContext gc) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Circle(startPoint, circleRadius);
    }
}

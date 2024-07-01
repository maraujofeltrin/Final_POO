package frontend.Drawing;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawEllipse extends DrawFigure{

    @Override
    public Figure draw(Point startPoint, Point endPoint, Color color, GraphicsContext gc) {
        canDraw(startPoint,endPoint);
        Point centerPoint = new Point(Math.abs(endPoint.x + startPoint.x) / 2, (Math.abs((endPoint.y + startPoint.y)) / 2));
        double sMayorAxis = Math.abs(endPoint.x - startPoint.x);
        double sMinorAxis = Math.abs(endPoint.y - startPoint.y);
       return new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }
}

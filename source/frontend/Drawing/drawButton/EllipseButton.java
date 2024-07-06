package frontend.Drawing.drawButton;

import frontend.Drawing.BorderType;
import backend.ShadowType;
import backend.model.Point;
import frontend.Drawing.DrawEllipse;
import frontend.Drawing.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EllipseButton implements Buttons{

    @Override
    public DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width, Integer layer) {
        canDraw(startPoint,endPoint);
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new DrawEllipse(centerPoint, sMayorAxis, sMinorAxis, color,secColor, gc, shadow, border, width, layer);
    }
}

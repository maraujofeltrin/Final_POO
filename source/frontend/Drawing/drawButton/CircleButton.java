package frontend.Drawing.drawButton;

import frontend.Drawing.BorderType;
import backend.ShadowType;
import backend.model.Point;
import frontend.Drawing.DrawCircle;
import frontend.Drawing.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleButton implements Buttons{
    @Override
    public DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width, Integer layer) {
        canDraw(startPoint,endPoint);
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        DrawCircle resp = new DrawCircle(startPoint, circleRadius, color,secColor, gc, shadow, border, width, layer);

        return resp;
    }
}

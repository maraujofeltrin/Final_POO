package frontend.Drawing.drawButton;

import backend.model.Point;
import frontend.Drawing.DrawCircle;
import frontend.Drawing.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleButton implements Buttons{
    @Override
    public DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, GraphicsContext gc) {
        canDraw(startPoint,endPoint);
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        DrawCircle resp = new DrawCircle(startPoint, circleRadius, color, gc);

        return resp;
    }
}

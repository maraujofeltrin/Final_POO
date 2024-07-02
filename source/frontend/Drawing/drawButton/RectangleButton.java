package frontend.Drawing.drawButton;

import backend.model.Point;
import frontend.Drawing.DrawFigure;
import frontend.Drawing.DrawRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleButton implements Buttons{

    @Override
    public DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, GraphicsContext gc) {
        canDraw(startPoint,endPoint);
        DrawRectangle rectangle = new DrawRectangle(startPoint, endPoint, color, gc);
        return rectangle;
    }

}

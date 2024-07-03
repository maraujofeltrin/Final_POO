package frontend.Drawing.drawButton;

import backend.BorderType;
import backend.ShadowType;
import backend.model.Point;
import frontend.Drawing.DrawFigure;
import frontend.Drawing.DrawSquare;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareButton implements Buttons{
    @Override
    public DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width) {
        canDraw(startPoint,endPoint);
        return new DrawSquare(startPoint, endPoint, color, secColor, gc, shadow, border,width);
    }
}

package frontend.Drawing.drawButton;

import backend.model.Point;
import frontend.Drawing.DrawFigure;
import frontend.Drawing.DrawSquare;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareButton implements Buttons{
    @Override
    public DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc) {
        canDraw(startPoint,endPoint);
        return new DrawSquare(startPoint, endPoint, color, secColor, gc);
    }
}

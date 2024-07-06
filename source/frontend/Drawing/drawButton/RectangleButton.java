package frontend.Drawing.drawButton;

import frontend.Drawing.BorderType;
import backend.ShadowType;
import backend.model.Point;
import frontend.Drawing.DrawFigure;
import frontend.Drawing.DrawRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleButton implements Buttons{

    @Override
    public DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width, Integer layer) {
        canDraw(startPoint,endPoint);
        return new DrawRectangle(startPoint, endPoint, color, secColor, gc, shadow, border,width, layer);
    }

}

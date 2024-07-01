package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

 public abstract class DrawFigure implements Buttons{
     Color color;
     GraphicsContext gc;

    protected void canDraw(Point startPoint, Point endPoint){
        if( startPoint == null || endPoint == null){
            throw new IllegalArgumentException();
        }
    }
}

package frontend.Drawing.drawButton;

import backend.model.Figure;
import backend.model.Point;
import frontend.Drawing.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public interface Buttons {

    DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc);
    default void canDraw(Point startPoint, Point endPoint){
        if( startPoint == null || endPoint == null){
            throw new IllegalArgumentException();
        }
    }

}

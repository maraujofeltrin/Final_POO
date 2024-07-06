package frontend.Drawing.drawButton;

import frontend.Drawing.BorderType;
import backend.ShadowType;
import backend.model.Point;
import frontend.Drawing.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public interface Buttons {

    DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width, Integer layer);
    default void canDraw(Point startPoint, Point endPoint){
        if( startPoint == null || endPoint == null){
            throw new IllegalArgumentException();
        }
    }

}

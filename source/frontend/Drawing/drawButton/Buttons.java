package frontend.Drawing.drawButton;

import backend.BorderType;
import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import frontend.Drawing.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public interface Buttons {

    DrawFigure ButtonToAction(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width);
    default void canDraw(Point startPoint, Point endPoint){
        if( startPoint == null || endPoint == null){
            throw new IllegalArgumentException();
        }
    }

}

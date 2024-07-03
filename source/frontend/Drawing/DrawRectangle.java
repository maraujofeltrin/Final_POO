package frontend.Drawing;

import backend.BorderType;
import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class DrawRectangle extends DrawFigure {
    private Rectangle rectangle;

   public DrawRectangle(Point startPoint, Point endPoint, Color color, Color secColor,GraphicsContext gc, ShadowType shadow, BorderType border, double width){
       super(gc);
       figure=new Rectangle(startPoint, endPoint);
       rectangle=(Rectangle) figure;
       rectangle.setColor(color);
       rectangle.setSecondColor(secColor);
       rectangle.setBorderType(border);
       rectangle.setType(shadow);
       rectangle.setBorderWidth(width);
   }



    @Override
    protected void ShadowFigure(double difX, double difY) {
        gc.fillRect(rectangle.getType().move(rectangle.getTopLeft().getX(), MOVEMENT),
                rectangle.getType().move(rectangle.getTopLeft().getY(), MOVEMENT),
                difX,difY);
    }

    @Override
    protected void FillFigureAux(double difX, double difY) {
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                difX,difY);
        gc.strokeRect(rectangle.getTopLeft().getX(),rectangle.getTopLeft().getY(),
                difX, difY);
    }


    @Override
    public boolean equals(Object o) {
            return o instanceof DrawRectangle Drectangle &&
                    this.rectangle.equals(Drectangle.rectangle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rectangle);
    }

    @Override
    protected void setGradiant(Color col1, Color col2){
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, col1),
                new Stop(1, col2));
        gc.setFill(linearGradient);
    }
}

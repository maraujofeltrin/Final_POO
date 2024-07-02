package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawRectangle extends DrawFigure {
    private Rectangle rectangle;

   public DrawRectangle(Point startPoint, Point endPoint, Color color, GraphicsContext gc){
       super(gc);
       rectangle=new Rectangle(startPoint, endPoint);
       rectangle.setColor(color);
   }


    @Override
    public void FillFigure( Color col) {
        gc.setFill(rectangle.getShadowColor());
        Double difX= rectangle.DiffX();
        Double difY= rectangle.DiffY();
        gc.fillRect(rectangle.getType().move(rectangle.getTopLeft().getX(), MOVEMENT),
                rectangle.getType().move(rectangle.getTopLeft().getY(), MOVEMENT),
                difX,difY);

        gc.setFill(col);
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                difX,difY);

        gc.strokeRect(rectangle.getTopLeft().getX(),rectangle.getTopLeft().getY(),
                difX, difY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawRectangle that = (DrawRectangle) o;
        return Objects.equals(rectangle, that.rectangle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rectangle);
    }
}

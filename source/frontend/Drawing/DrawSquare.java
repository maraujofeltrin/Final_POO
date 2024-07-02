package frontend.Drawing;

import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawSquare extends DrawFigure {
    private Square square;
   public DrawSquare(Point startPoint, Point endPoint, Color color, GraphicsContext gc){
       super(gc);
       double size = Math.abs(endPoint.getX() - startPoint.getX());
       figure=new Square(startPoint, size);
       square=(Square)figure;
       square.setColor(color);
   }

       @Override
    protected void ShadowFigure(double difX, double difY) {
        gc.fillRect(square.getType().move(square.getTopLeft().getX(), MOVEMENT),
                square.getType().move(square.getTopLeft().getY(), MOVEMENT),
                difX,difY);
    }

    @Override
    protected void FillFigureAux(double difX, double difY) {
        gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                difX,difY);
        gc.strokeRect(square.getTopLeft().getX(),square.getTopLeft().getY(),
                difX, difY);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DrawSquare that = (DrawSquare) o;
        return Objects.equals(square, that.square);
    }

    @Override
    public int hashCode() {
        return Objects.hash(square);
    }
}

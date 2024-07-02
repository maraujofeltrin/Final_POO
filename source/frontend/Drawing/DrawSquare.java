package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawSquare extends DrawRectangle {
    //CHEQUEAR SI NO CONVIENE EXTENDER DE DRAWRECTANGLE

    private Square square;//CHEQUEAR SI ES NECESARIO
   public DrawSquare(Point startPoint, Point endPoint, Color color, GraphicsContext gc){
       super(startPoint, endPoint, color, gc);
       double size = Math.abs(endPoint.getX() - startPoint.getX());
       square=new Square(startPoint, size);
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

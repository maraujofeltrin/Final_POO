package frontend.Drawing;

import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawSquare extends DrawFigure {
    //CHEQUEAR SI NO CONVIENE EXTENDER DE DRAWRECTANGLE

    private Square square;//CHEQUEAR SI ES NECESARIO
   public DrawSquare(Point startPoint, Point endPoint, Color color, GraphicsContext gc){
       super(gc);
       double size = Math.abs(endPoint.getX() - startPoint.getX());
       square=new Square(startPoint, size);
   }

    @Override
    public void setShadow(ShadowType type, Color color) {
        square.setShadow(type, color);
    }

    public Color getColor(){
        return square.getColor();
    }



    public boolean belongs(Point eventPoint){
       return square.belongs(eventPoint);
    }

    @Override
    public void FillFigure(Color col) {
        gc.setFill(square.getShadowColor());
        Double difX= square.DiffX();
        Double difY= square.DiffY();
        gc.fillRect(square.getType().move(square.getTopLeft().getX(), MOVEMENT),
                square.getType().move(square.getTopLeft().getY(), MOVEMENT),
                difX,difY);

        gc.setFill(col);
        gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                difX,difY);

        gc.strokeRect(square.getTopLeft().getX(),square.getTopLeft().getY(),
                difX, difY);
    }

    @Override
    public void addDiff(double num1, double num2) {
        square.addDiff(num1,num2);
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

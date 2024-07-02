package frontend.Drawing;

import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class DrawSquare extends DrawFigure {
    private Square square;
   public DrawSquare(Point startPoint, Point endPoint, Color color, Color secColor, GraphicsContext gc){
       super(gc);
       double size = Math.abs(endPoint.getX() - startPoint.getX());
       figure=new Square(startPoint, size);
       square=(Square)figure;
       square.setColor(color);
       square.setSecondColor(secColor);
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

    @Override
    protected void setGradiant(Color col1, Color col2){
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, col1),
                new Stop(1, col2));
        gc.setFill(linearGradient);
    }
}

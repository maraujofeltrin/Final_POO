package frontend.Drawing;

import backend.ShadowType;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class DrawRectangle extends DrawFigure {
    private final Rectangle rectangle;

   public DrawRectangle(Point startPoint, Point endPoint, Color color, Color secColor,GraphicsContext gc, ShadowType shadow, BorderType border, double width, Integer layer){
       super(gc, border, width, layer);
       figure=new Rectangle(startPoint, endPoint);
       rectangle=(Rectangle) figure;
       rectangle.setColor(color);
       rectangle.setSecondColor(secColor);
       rectangle.setType(shadow);

   }

   //Drawing Methods:
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
    protected void setGradiant(Color col1, Color col2){
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, col1),
                new Stop(1, col2));
        gc.setFill(linearGradient);
    }

    public DrawFigure[] divideFigure(){
       Point[] points = rectangle.divide();

       DrawFigure res1=createFigure(points[0], points[1]);
       DrawFigure res2=createFigure(points[2], points[3]);
        return new DrawFigure[]{res1, res2};
    }

    @Override
    public DrawFigure duplicate() {
        Point aux = new Point(rectangle.getTopLeft().getX()+20,rectangle.getTopLeft().getY()+20);
        Point aux2 = new Point(rectangle.getBottomRight().getX()+20,rectangle.getBottomRight().getY()+20);
        return createFigure(aux, aux2);
    }

    private DrawFigure createFigure(Point topLeft, Point bottomRigh){
       return new DrawRectangle(topLeft, bottomRigh,rectangle.getColor(),rectangle.getSecondColor(),gc,rectangle.getType(),getBorderType(), getBorderWidth(), this.getLayer());
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
}

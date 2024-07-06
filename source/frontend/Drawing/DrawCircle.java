package frontend.Drawing;

import backend.ShadowType;
import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class DrawCircle extends DrawFigure{

private Circle circle;
public DrawCircle(Point point, Double Radius, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width, Integer layer){
    super(gc, border, width, layer);
    figure = new Circle(point, Radius);
    circle=(Circle) figure;
    circle.setColor(color);
    circle.setSecondColor(secColor);
    circle.setType(shadow);
}

    @Override
    public boolean equals(Object o) {
        return o instanceof DrawCircle Dcircle &&
                circle.equals(Dcircle.circle);
    }

    protected void FillFigureAux(double difX, double difY){
        gc.strokeOval(difX, difY, circle.getsMayorAxis(), circle.getsMinorAxis());
        gc.fillOval(difX, difY, circle.getsMayorAxis(), circle.getsMinorAxis());
    }

    @Override
    public DrawFigure duplicate() {
        Point aux = new Point(circle.getCenterPoint().getX()+20,circle.getCenterPoint().getY()+20);
        return createFigure(aux, circle.getRadius());
    }

    @Override
    public DrawFigure[] divideFigure() {
       Double[] radius= circle.divideAxis();
       Point[] points= circle.divideCenterPoints();

       DrawFigure res1= createFigure(points[0],radius[0]/2);
       DrawFigure res2= createFigure(points[1],radius[0]/2);
       DrawFigure[] res={res1, res2};
       return res;
}
    private DrawFigure createFigure(Point center, double radius){
    return new DrawCircle(center,radius, circle.getColor(),circle.getSecondColor(), gc, circle.getType(), getBorderType(), getBorderWidth(), getLayer());
    }
    protected void ShadowFigure(double difX, double difY){
        gc.fillOval(circle.getType().move(difX, MOVEMENT),
                circle.getType().move(difY, MOVEMENT), circle.getsMayorAxis(), circle.getsMinorAxis());

    }
    @Override
    public int hashCode() {
        return Objects.hash(circle);
    }

    @Override
    protected void setGradiant(Color col1, Color col2){
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, col1),
                new Stop(1, col2));
        gc.setFill(radialGradient);
    }
}


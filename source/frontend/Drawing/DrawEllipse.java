package frontend.Drawing;

import backend.ShadowType;
import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class DrawEllipse extends DrawFigure{

    private final Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color, Color secColor, GraphicsContext gc, ShadowType shadow, BorderType border, double width, Integer layer){
        super(gc, border, width, layer);
        figure = new Ellipse(centerPoint,sMayorAxis,sMinorAxis);
        ellipse=(Ellipse) figure;
        ellipse.setColor(color);
        ellipse.setSecondColor(secColor);
        ellipse.setType(shadow);
    }


    //Drawing Methods:
    protected void FillFigureAux(double difX, double difY){
        gc.strokeOval(difX, difY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(difX, difY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
    protected void ShadowFigure(double difX, double difY){
        gc.fillOval(ellipse.getType().move(difX, MOVEMENT),
                ellipse.getType().move(difY, MOVEMENT), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());

    }
    @Override
    protected void setGradiant(Color col1, Color col2){
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, col1),
                new Stop(1, col2));
        gc.setFill(radialGradient);
    }

    @Override
    public DrawFigure[] divideFigure() {
        Point[] points = ellipse.divideCenterPoints();
        Double[] Axis= ellipse.divideAxis();

        DrawFigure res1=createFigure(points[0], Axis[0], Axis[1]);
        DrawFigure res2=createFigure(points[1],Axis[0], Axis[1]);
        return new DrawFigure[]{res1, res2};
    }
    @Override
    public DrawFigure duplicate() {
        Point aux = new Point(ellipse.getCenterPoint().getX()+20,ellipse.getCenterPoint().getY()+20);
        return createFigure(aux, ellipse.getsMayorAxis(),ellipse.getsMinorAxis());
    }
    private DrawFigure createFigure(Point center, double MayorAxis, double MinorAxis){
        return new DrawEllipse(center,MayorAxis, MinorAxis, ellipse.getColor(),ellipse.getSecondColor(), gc, ellipse.getType(), getBorderType(), getBorderWidth(), this.getLayer());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DrawEllipse Dellipse &&
                this.ellipse.equals(Dellipse.ellipse);
    }
    @Override
    public int hashCode() {
        return Objects.hash(ellipse);
    }

}

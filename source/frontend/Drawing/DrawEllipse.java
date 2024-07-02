package frontend.Drawing;

import backend.ShadowType;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class DrawEllipse extends DrawFigure{

    private Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color, Color secColor, GraphicsContext gc){
        super(gc);
        figure = new Ellipse(centerPoint,sMayorAxis,sMinorAxis);
        ellipse=(Ellipse) figure;
        ellipse.setColor(color);
        ellipse.setSecondColor(secColor);
    }

    //CHEQUEAR SI LO USAMOS
    public Figure getFigure(){
        return ellipse;
    }


    protected void FillFigureAux(double difX, double difY){
        gc.strokeOval(difX, difY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(difX, difY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
    protected void ShadowFigure(double difX, double difY){
        gc.fillOval(ellipse.getType().move(difX, MOVEMENT),
                ellipse.getType().move(difY, MOVEMENT), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawEllipse that = (DrawEllipse) o;
        return Objects.equals(ellipse, that.ellipse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ellipse);
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

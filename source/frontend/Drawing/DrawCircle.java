package frontend.Drawing;

import backend.ShadowType;
import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class DrawCircle extends DrawFigure{

private Circle circle;
public DrawCircle(Point point, Double Radius, Color color, Color secColor, GraphicsContext gc){
    super(gc);
    figure = new Circle(point, Radius);
    circle=(Circle) figure;
    circle.setColor(color);
    circle.setSecondColor(secColor);
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


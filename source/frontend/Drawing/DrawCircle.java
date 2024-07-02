package frontend.Drawing;

import backend.ShadowType;
import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawCircle extends DrawFigure{

private Circle circle;
public DrawCircle(Point point, Double Radius, Color color, GraphicsContext gc){
    super(gc);
    figure = new Circle(point, Radius);
    circle=(Circle) figure;
    circle.setColor(color);
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DrawCircle that = (DrawCircle) o;
        return Objects.equals(circle, that.circle);
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
}


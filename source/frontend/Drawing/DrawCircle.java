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
    circle = new Circle(point, Radius);
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DrawCircle that = (DrawCircle) o;
        return Objects.equals(circle, that.circle);
    }

    @Override
    public void setShadow(ShadowType type, Color color) {
        circle.setShadow(type, color);
    }

    public Color getColor(){
        return circle.getColor();
    }
    public boolean belongs(Point eventPoint){
        return circle.belongs(eventPoint);
    }

    @Override
    public void FillFigure(Color col) {
        double difX = circle.DiffX();
        double difY = circle.DiffY();

        gc.setFill(circle.getShadowColor());
        gc.fillOval(circle.getType().move(difX, MOVEMENT),
                circle.getType().move(difY, MOVEMENT), circle.getsMayorAxis(), circle.getsMinorAxis());

        gc.setFill(col);

        gc.strokeOval(difX, difY, circle.getsMayorAxis(), circle.getsMinorAxis());
        gc.fillOval(difX, difY, circle.getsMayorAxis(), circle.getsMinorAxis());
    }

    @Override
    public void addDiff(double num1, double num2) {
    circle.addDiff(num1, num2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(circle);
    }
}


package frontend.Drawing;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawCircle extends DrawEllipse{

private Circle circle;
public DrawCircle(Point point, Double Radius, Color color, GraphicsContext gc){
    super(point, Radius, Radius, color, gc);
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
    public int hashCode() {
        return Objects.hash(circle);
    }
}


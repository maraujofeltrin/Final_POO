package backend.model;

import backend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public interface Figure {

    void addDiff(double incX, double incY);
    Color getColor();

    boolean belongs(Point eventPoint);

    void setShadow(ShadowType type, Color color);
}

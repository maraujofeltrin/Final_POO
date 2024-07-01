package backend.model;

import backend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public interface Figure {
    final static double MOVEMENT = 10.0;
    void addDiff(double incX, double incY);

    void FillFigure(GraphicsContext gc, Color col);

    boolean belongs(Point eventPoint);

    void setShadow(ShadowType type, Color color);
}

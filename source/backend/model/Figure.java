package backend.model;

import backend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public interface Figure {
    void addDiff(double incX, double incY);

    void FillFigure(GraphicsContext gc);

    boolean belongs(Point eventPoint);

    GraphicsContext setShadow(ShadowType type, Color color, GraphicsContext gc);
}

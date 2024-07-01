package backend.model;

import javafx.scene.canvas.GraphicsContext;

public interface Figure {
    void addDiff(double incX, double incY);

    void FillFigure(GraphicsContext gc);

    boolean belongs(Point eventPoint);
}

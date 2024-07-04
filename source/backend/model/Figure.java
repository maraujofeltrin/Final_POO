package backend.model;

import frontend.Drawing.BorderType;
import backend.ShadowType;
import javafx.scene.paint.Color;

public interface Figure {

    void addDiff(double incX, double incY);
    Color getColor();
    Color getShadowColor();
    Color getSecondColor();
    double DiffX();
    double DiffY();

    boolean belongs(Point eventPoint);

    void setShadow(ShadowType type, Color color);

    void setSecondColor(Color newCol);

    void setColor(Color col);
    Point getCenterPoint();
}

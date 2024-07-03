package backend.model;

import backend.BorderType;
import backend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public interface Figure extends Cloneable{

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

    void setBorderType(BorderType type);

    BorderType getBorderType();

    void setBorderWidth(double value);

    double getBorderWidth();
    Figure clone();
    Point getCenterPoint();
}

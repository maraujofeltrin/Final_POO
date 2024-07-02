package frontend.Drawing;

import backend.ShadowType;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DrawEllipse extends DrawFigure{

    private Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color, GraphicsContext gc){
        super(gc);
        ellipse = new Ellipse(centerPoint,sMayorAxis,sMinorAxis);
        ellipse.setColor(color);
    }

    public Figure getFigure(){
        return ellipse;
    }
    public void addDiff(double diffX, double diffY){
        ellipse.addDiff(diffX, diffY);
    }
    public void setShadow(ShadowType type, Color color){
        ellipse.setShadow(type, color);
    }
    public Color getColor(){
        return ellipse.getColor();
    }
    public boolean belongs(Point eventPoint){
        return ellipse.belongs(eventPoint);
    }
    @Override
    public void FillFigure(Color col) {
        double difX = ellipse.DiffX();
        double difY = ellipse.DiffY();

        gc.setFill(ellipse.getShadowColor());
        gc.fillOval(ellipse.getType().move(difX, MOVEMENT),
                ellipse.getType().move(difY, MOVEMENT), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());

        gc.setFill(col);

        gc.strokeOval(difX, difY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(difX, difY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawEllipse that = (DrawEllipse) o;
        return Objects.equals(ellipse, that.ellipse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ellipse);
    }
}

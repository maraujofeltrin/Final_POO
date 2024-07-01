package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawFigure {
    //CHEQUEAR SI NO CONVIENE EXTENDER DE DRAWRECTANGLE
    @Override
    public Figure draw(Point startPoint, Point endPoint, Color color, GraphicsContext gc) {
        canDraw(startPoint,endPoint);
        double size = Math.abs(endPoint.getX() - startPoint.getX());
        return new Square(startPoint, size);
    }


}

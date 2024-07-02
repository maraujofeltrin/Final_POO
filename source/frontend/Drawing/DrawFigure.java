package frontend.Drawing;

import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

 public abstract class DrawFigure {
     final static double MOVEMENT = 10.0;
     protected GraphicsContext gc;
     protected Figure figure;

     public abstract void setShadow(ShadowType type, Color color);
     public abstract Color getColor();
     public abstract boolean belongs(Point eventPoint);
     public DrawFigure(GraphicsContext gc){
         this.gc=gc;
     }
     public abstract void FillFigure( Color col);
     public abstract void addDiff(double num1, double num2);

}

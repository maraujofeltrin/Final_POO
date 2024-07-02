package frontend.Drawing;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

 public abstract class DrawFigure {
     final static double MOVEMENT = 10.0;
     protected GraphicsContext gc;

     public DrawFigure(GraphicsContext gc){
         this.gc=gc;
     }
    public abstract void FillFigure(Color col);
     public abstract void addDiff(double num1, double num2);

}

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

     public  void setShadow(ShadowType type, Color color){
         figure.setShadow(type, color);
     }
     public Color getColor(){
         return figure.getColor();
     }
     public  boolean belongs(Point eventPoint){
         return figure.belongs(eventPoint);
     }
     public void FillFigure(Color col) {
         double difX = figure.DiffX();
         double difY = figure.DiffY();
         setFill(figure.getShadowColor());
         ShadowFigure(difX, difY);
         setFill(col);
         FillFigureAux(difX,difY);
     }
     protected abstract void ShadowFigure(double difX, double difY);
     protected abstract void FillFigureAux(double difX, double difY);
     public void setFill(Color color){
         gc.setFill(color);
     }
     public void Diff(){
         double difX = figure.DiffX();
         double difY = figure.DiffY();
     }
     public DrawFigure(GraphicsContext gc){
         this.gc=gc;

     }

     public void addDiff(double num1, double num2){
         figure.addDiff(num1, num2);
     }

}

package frontend.Drawing;

import backend.BorderType;
import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class DrawFigure implements Cloneable{
     final static double MOVEMENT = 10.0;
     protected GraphicsContext gc;
     protected Figure figure;
    //protected BorderType border;
     //protected double width;

     public  void setShadow(ShadowType type, Color color){
         figure.setShadow(type, color);
     }
     public Color getColor(){
         return figure.getColor();
     }
     public Color getSecondColor(){
         return figure.getSecondColor();
     }
     public  boolean belongs(Point eventPoint){
         return figure.belongs(eventPoint);
     }
     public void FillFigure(Color col, Color col2) {
         double difX = figure.DiffX();
         double difY = figure.DiffY();
         setFill(figure.getShadowColor());
         ShadowFigure(difX, difY);

         setGradiant(col, col2);
         FillFigureAux(difX,difY);

         int[] values = figure.getBorderType().BorderVec();
         if(values.length == 1){
             gc.setLineDashes(values[0]);
         }else{
             gc.setLineDashes(values[0], values[1],values[2],values[3]);
         }
         gc.setLineWidth(figure.getBorderWidth());;
     }

     protected abstract void setGradiant(Color col1, Color col2);
     protected abstract void ShadowFigure(double difX, double difY);
     protected abstract void FillFigureAux(double difX, double difY);
     public void setFill(Color color){
         gc.setFill(color);
     }
     public void Diff(){ //CHEQUEAR PQ NO APARECE EN AZUL
         double difX = figure.DiffX();
         double difY = figure.DiffY();
     }
     public DrawFigure(GraphicsContext gc){
         this.gc=gc;
         //this.border = border;
         //this.width = value;
     }

     public void addDiff(double num1, double num2){
         figure.addDiff(num1, num2);
     }

     public void setSecondaryColor(Color color) {
         figure.setSecondColor(color);
     }

     public void setPrimaryColor(Color col) {
         figure.setColor(col);
     }

     public void setBorder(BorderType type, double value) {
         figure.setBorderType(type);
         figure.setBorderWidth(value);
     }

    @Override
    public DrawFigure clone() {
        try {
            DrawFigure cloned = (DrawFigure) super.clone();
            cloned.figure = figure.clone(); // Clonación profunda de la figura
            cloned.gc = gc;
            return cloned;
        } catch (CloneNotSupportedException e) {
            // Manejar la excepción apropiadamente, por ejemplo:
            throw new AssertionError();        }
    }
    public abstract DrawFigure[] divideFigure();

    public Point getCenterPoint() {
        return figure.getCenterPoint();
    }
}

package frontend.Drawing;

import backend.ShadowType;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawFigure{
     protected final static double MOVEMENT = 10.0;
     protected GraphicsContext gc;
     protected Figure figure;
     private BorderType border;
     private double width;
     private Integer layer;

    public DrawFigure(GraphicsContext gc, BorderType border, double width, Integer layer){
        this.gc=gc;
        this.border=border;
        this.width=width;
        this.layer=layer;
    }

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

         border.setBorder(gc, width);

         setFill(figure.getShadowColor());
         ShadowFigure(difX, difY);

         setGradiant(col, col2);
         FillFigureAux(difX,difY);

     }

     protected abstract void setGradiant(Color col1, Color col2);
     public BorderType getBorderType(){
         return border;
     }
     public double getBorderWidth(){
         return width;
     }
     protected abstract void ShadowFigure(double difX, double difY);
     protected abstract void FillFigureAux(double difX, double difY);
     public void setFill(Color color){
         gc.setFill(color);
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

     public void setBorder(BorderType type) {
         this.border = type;
     }

     public void setWidth(double value){
         this.width = value;
     }

     public abstract DrawFigure duplicate();

    public abstract DrawFigure[] divideFigure();

    public Point getCenterPoint() {
        return figure.getCenterPoint();
    }
    public ShadowType getShadowType(){
       return figure.getShadowType();
    }
    public String toString(){
        return figure.toString();
    }
    public Integer getLayer(){
        return layer;
    }
}

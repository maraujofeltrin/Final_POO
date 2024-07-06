package backend.model;

import backend.ShadowType;
import javafx.scene.paint.Color;

import static java.lang.Math.abs;

public abstract class Figure {
    private ShadowType type;
    private Color color, secondaryColor;

    public Figure(ShadowType type){
        this.type=type;
    }
    public Color getColor(){
       return color;
    }

    public Color getSecondColor(){
       return this.secondaryColor;
    }
    public Color getShadowColor(){
        return type.checkColor(color);
    }

    public ShadowType getType() {
        return type;
    }
    public abstract Point getCenterPoint();

    public void setShadow(ShadowType newType, Color newColor){
        setType(newType);
        setColor(newColor);
    }

    public void setSecondColor(Color secColor) {
        this.secondaryColor = secColor;
    }

    public void setColor(Color col){
        this.color = col;
    }

    public void setType(ShadowType type){
        this.type =type;
    }


    public abstract double DiffY();
    public abstract double DiffX();

    public abstract void Move(double incX, double incY);
    public abstract boolean belongs(Point eventPoint);

    public ShadowType getShadowType(){
        return type;
    }

}

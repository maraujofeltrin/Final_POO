package backend.model;

import backend.BorderType;
import backend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Objects;

public class Rectangle implements Figure {

    private final Point topLeft, bottomRight;
    private ShadowType type;
    private BorderType border;
    private Color color, secondaryColor;
    private double borderWidth;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.type = ShadowType.NONE;
        this.border = BorderType.NORMAL;
        this.borderWidth = 1;
    }

    public Color getColor() {
        return color;
    }

    public Color getSecondColor() {
        return secondaryColor;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Rectangle rectangle && topLeft.equals(rectangle.topLeft)
                && bottomRight.equals(rectangle.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }


    @Override
    public void addDiff(double diffX, double diffY) {
        topLeft.x += diffX; //CHEQUEAR QUE X E Y SON PUBLIC
        bottomRight.x += diffX;
        topLeft.y += diffY;
        bottomRight.y += diffY;
    }



    @Override
    public boolean belongs(Point eventPoint) {
        return eventPoint.getX() > getTopLeft().getX() && eventPoint.getX() < getBottomRight().getX() &&
                eventPoint.getY() > getTopLeft().getY() && eventPoint.getY() < getBottomRight().getY();
    }

    public void setType(ShadowType type){
        this.type =type;
    }


    public void setColor(Color newColor) {
        this.color = newColor;
    }

    public Color getShadowColor(){
        return type.checkColor(color);
    }

    public ShadowType getType() {
        return type;
    }

    public double DiffX(){
        return Math.abs(getTopLeft().getX() - getBottomRight().getX());

    }

    public double DiffY(){
        return Math.abs(getTopLeft().getY() - getBottomRight().getY());
    }

    @Override
    public void setShadow(ShadowType newType, Color newColor){
        setType(newType);
        setColor(newColor);
    }

    public void setSecondColor(Color secColor) {
        this.secondaryColor = secColor;
    }

    public void setBorderType(BorderType newType){
        this.border = newType;
    }

    @Override
    public BorderType getBorderType() {
        return border;
    }

    public void setBorderWidth(double value){
        this.borderWidth = value;
    }

    @Override
    public double getBorderWidth() {
        return borderWidth;
    }
}

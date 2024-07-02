package backend.model;

import backend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Objects;

public class Rectangle implements Figure {

    private final Point topLeft, bottomRight;
    private ShadowType type;
    private Color color, secondaryColor;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.type = ShadowType.NONE;
    }

    public Color getColor() {
        return color;
    }

    public Color getSecondColor() {
        return secondaryColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(topLeft, rectangle.topLeft) && Objects.equals(bottomRight, rectangle.bottomRight);
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
}

package backend.model;

import frontend.Drawing.BorderType;
import backend.ShadowType;
import javafx.scene.paint.Color;

import java.util.Objects;

import static java.lang.Math.abs;

public class Rectangle implements Figure {

    private Point topLeft;
    private Point bottomRight;
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

    public Point[] divide(){


        double auxY= (Math.abs(topLeft.getY()-bottomRight.getY()))/4.0;
        double auxX= (Math.abs(topLeft.getX()-bottomRight.getX()))/2.0;
        Point res1= new Point(topLeft.getX(), topLeft.getY()+auxY);
        Point res2= new Point(topLeft.getX()+auxX, topLeft.getY()+3*auxY);
        Point res3= new Point(res2.getX(), res1.getY());
        Point res4= new Point(res2.getX()+auxX,res2.getY());

       Point [] res={res1, res2, res3, res4};
       return res;
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
        return abs(getTopLeft().getX() - getBottomRight().getX());

    }

    public double DiffY(){
        return abs(getTopLeft().getY() - getBottomRight().getY());
    }

    @Override
    public void setShadow(ShadowType newType, Color newColor){
        setType(newType);
        setColor(newColor);
    }

    public void setSecondColor(Color secColor) {
        this.secondaryColor = secColor;
    }


    public Point getCenterPoint() {
        double centerX = (topLeft.getX() + bottomRight.getX()) / 2;
        double centerY = (topLeft.getY() + bottomRight.getY()) / 2;
        return new Point(centerX, centerY);
    }
}

package backend.model;

import frontend.Drawing.BorderType;
import backend.ShadowType;
import javafx.scene.paint.Color;

import java.util.Objects;

import static java.lang.Math.abs;

public class Rectangle extends Figure {

    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        super(ShadowType.NONE);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
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

    public Point[] divide(){

        double auxY= (Math.abs(DiffY()))/4.0;
        double auxX= (Math.abs(DiffX()))/2.0;
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
        topLeft.setX(topLeft.getX() + diffX);
        bottomRight.setX(bottomRight.getX() + diffX);
        topLeft.setY(topLeft.getY() + diffY);
        bottomRight.setY(bottomRight.getY() + diffY);
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return eventPoint.getX() > getTopLeft().getX() && eventPoint.getX() < getBottomRight().getX() &&
                eventPoint.getY() > getTopLeft().getY() && eventPoint.getY() < getBottomRight().getY();
    }

    @Override
    public double DiffY(){
        return abs(getTopLeft().getY() - getBottomRight().getY());
    }

    @Override
    public double DiffX(){
        return abs(getTopLeft().getX() - getBottomRight().getX());

    }

    @Override
    public Point getCenterPoint() {
        double centerX = (topLeft.getX() + bottomRight.getX()) / 2;
        double centerY = (topLeft.getY() + bottomRight.getY()) / 2;
        return new Point(centerX, centerY);
    }
}

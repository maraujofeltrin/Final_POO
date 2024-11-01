package backend.model;

import backend.ShadowType;


import java.util.Objects;

public class Ellipse extends Figure {
    private final Point centerPoint;
    private final double sMayorAxis,sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(ShadowType.NONE);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }
    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Ellipse ellipse &&
                Double.compare(sMayorAxis, ellipse.sMayorAxis) == 0
                && Double.compare(sMinorAxis, ellipse.sMinorAxis) == 0 &&
              centerPoint.equals(ellipse.centerPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public Point getCenterPoint() {
        return centerPoint;
    }



    @Override
    public void Move(double diffX, double diffY) {
       double y= getCenterPoint().getY() +diffY;
        double x=getCenterPoint().getX()+diffX;
        getCenterPoint().setX(x);
        getCenterPoint().setY(y);
    }


    @Override
    public boolean belongs(Point eventPoint) {
        return ((Math.pow(eventPoint.getX() - getCenterPoint().getX(), 2) / Math.pow(getsMayorAxis(), 2)) +
                (Math.pow(eventPoint.getY() - getCenterPoint().getY(), 2) / Math.pow(getsMinorAxis(), 2))) <= 0.30;
    }

    public double DiffX(){
        return centerPoint.getX() - (sMayorAxis / 2);
    }
    public double DiffY(){
        return centerPoint.getY() - (sMinorAxis / 2);
    }



    protected double addToCenterPoint(){
        return sMayorAxis/4.0;
    }
    public Point[] divideCenterPoints(){
        double aux1= addToCenterPoint();
        return new Point[]{new Point(centerPoint.getX()+aux1, centerPoint.getY())
                , new Point(centerPoint.getX()-aux1, centerPoint.getY())};
    }
    public Double[] divideAxis(){
        return new Double[]{sMayorAxis/2.0, sMinorAxis/2.0};
    }

}

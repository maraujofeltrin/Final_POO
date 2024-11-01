package backend.model;
public class Circle extends Ellipse {


    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2 * radius, 2 * radius);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }


    public double getRadius() {
        return getsMayorAxis()/2.0;
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < getRadius();
    }
    @Override
    protected double addToCenterPoint(){
        return getsMayorAxis()/2.0;
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof Circle circle && super.equals(o); //chequear el super
    }
}

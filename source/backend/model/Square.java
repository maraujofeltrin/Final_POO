package backend.model;

public class Square extends Rectangle {


    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }


    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Square square && super.equals(o);
    }
}

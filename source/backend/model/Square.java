package backend.model;

public class Square extends Rectangle {


    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.x + size, topLeft.y + size));
    }


    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Square square && super.equals(o); //chequear el super
    }
}

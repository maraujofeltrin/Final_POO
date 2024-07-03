package backend.model;

import backend.BorderType;
import backend.ShadowType;
import frontend.Drawing.DrawEllipse;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Objects;

public class Ellipse implements Figure {

    protected Point centerPoint;
    protected final double sMayorAxis, sMinorAxis;
    protected Color color, secondaryColor;
    protected BorderType border;
    protected ShadowType type;
    protected double borderWidth;
    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
        this.type = ShadowType.NONE;
        this.border = BorderType.NORMAL;
        borderWidth = 1;
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

    public Point getCenterPoint() {
        return centerPoint;
    }

    public Color getColor(){
        return color;
    }
    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public void addDiff(double diffX, double diffY) {
        getCenterPoint().x += diffX;
        getCenterPoint().y += diffY;
    }



    @Override
    public boolean belongs(Point eventPoint) {
        return ((Math.pow(eventPoint.getX() - getCenterPoint().getX(), 2) / Math.pow(getsMayorAxis(), 2)) +
                (Math.pow(eventPoint.getY() - getCenterPoint().getY(), 2) / Math.pow(getsMinorAxis(), 2))) <= 0.30;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(ShadowType type) {
        this.type = type;
    }

    public Color getShadowColor(){
        return type.checkColor(color);
    }

    public ShadowType getType() {
        return type;
    }

    public double DiffX(){
        return centerPoint.getX() - (sMayorAxis / 2);
    }
    public double DiffY(){
        return centerPoint.getY() - (sMinorAxis / 2);
    }

    @Override
    public void setShadow(ShadowType type, Color color){
        setColor(color);
        setType(type);

    }

    public Color getSecondColor() {
        return secondaryColor;
    }

    public void setSecondColor(Color newCol){
        this.secondaryColor = newCol;
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

    @Override
    public Ellipse clone() {
        try {
            Ellipse copy = (Ellipse) super.clone();
            // Realiza una copia profunda de los objetos mutables
            copy.centerPoint = new Point(this.centerPoint.getX()-30, this.centerPoint.getY()+30);
            // Copia otras propiedades necesarias
            copy.color = color;
            copy.secondaryColor = secondaryColor;
            copy.borderWidth = borderWidth;
            copy.border = border;
            copy.type = type;
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Esto no debería suceder porque estamos implementando Cloneable
        }
    }
}

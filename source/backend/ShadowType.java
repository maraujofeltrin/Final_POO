package backend;

import javafx.scene.paint.Color;

public enum ShadowType {
    SIMPLE{
        public Color checkColor(Color color){
            return Color.GRAY;
        }

        @Override
        public double move(double num1, double num2) {
            return num1 + num2;
        }
    },
    COLORED{
        public Color checkColor(Color color){
            return color.darker();
        }

        @Override
        public double move(double num1, double num2) {
            return num1 + num2;
        }
    },
    SIMPLE_INVERSED{
        public Color checkColor(Color color){
            return Color.GRAY;
        }

        @Override
        public double move(double num1, double num2) {
            return num1 - num2;
        }
    },
    COLORED_INVERSED{
        public Color checkColor(Color color){
            return color.darker();
        }

        @Override
        public double move(double num1, double num2) {
            return num1 - num2;
        }
    },
    NONE{
        public Color checkColor(Color color){
            return Color.TRANSPARENT; //CHEQUEAR
        }

        @Override
        public double move(double num1, double num2) {
            return 0;
        }
    };

    public abstract Color checkColor(Color color);

    public abstract double move(double num1, double num2);
}

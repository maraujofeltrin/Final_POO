package backend;

import javafx.scene.paint.Color;

public enum ShadowType {
    SIMPLE{
        public Color checkColor(Color color){
            return Color.GRAY;
        }
    },
    COLORED{
        public Color checkColor(Color color){
            return color.darker();
        }
    },
    SIMPLE_INVERSED{
        public Color checkColor(Color color){
            return Color.GRAY;
        }
    },
    COLORED_INVERSED{
        public Color checkColor(Color color){
            return color.darker();
        }
    },
    NONE{
        public Color checkColor(Color color){
            return Color.WHITE; //CHEQUEAR
        }
    };

    public abstract Color checkColor(Color color);

}

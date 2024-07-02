package backend;

import javafx.scene.canvas.GraphicsContext;

public enum BorderType {
    NORMAL{
        public void putBorder(GraphicsContext gc, double value){
            gc.setLineDashes(0);
        }
    },
    DOTTED_SIMPLE{
        public void putBorder(GraphicsContext gc, double value){
            gc.setLineDashes(10);
            gc.setLineWidth(value);
        }
    },
    DOTTED_COMPLEX{
        public void putBorder(GraphicsContext gc, double value){
            gc.setLineDashes(30, 10, 15, 10);
            gc.setLineWidth(value);
        }
    };

    public abstract void putBorder(GraphicsContext gc, double value);
}

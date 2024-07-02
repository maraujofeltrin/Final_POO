package backend;

import javafx.scene.canvas.GraphicsContext;

public enum BorderType {
    NORMAL{
        public void putBorder(GraphicsContext gc){
            gc.setLineDashes(0);
        }
    },
    DOTTED_SIMPLE{
        public void putBorder(GraphicsContext gc){
            gc.setLineDashes(10);
        }
    },
    DOTTED_COMPLEX{
        public void putBorder(GraphicsContext gc){
            gc.setLineDashes(30, 10, 15, 10);
        }
    };

    public abstract void putBorder(GraphicsContext gc);
}

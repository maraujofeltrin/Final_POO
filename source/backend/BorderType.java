package backend;

import javafx.scene.canvas.GraphicsContext;

public enum BorderType {
    NORMAL{
        public void putBorder(GraphicsContext gc, double value){
            gc.setLineDashes(0);
        }

        @Override
        public String toString(){
            return "Normal";
        }
    },
    DOTTED_SIMPLE{
        public void putBorder(GraphicsContext gc, double value){
            gc.setLineDashes(10);
            gc.setLineWidth(value);
        }

        @Override
        public String toString(){
            return "Punteado Simple";
        }
    },
    DOTTED_COMPLEX{
        public void putBorder(GraphicsContext gc, double value){
            gc.setLineDashes(30, 10, 15, 10);
            gc.setLineWidth(value);
        }
        @Override
        public String toString(){
            return "Punteado Complejo";
        }
    };

    public abstract void putBorder(GraphicsContext gc, double value);
}

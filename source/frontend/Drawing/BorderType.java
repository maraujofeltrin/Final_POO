package frontend.Drawing;

import javafx.scene.canvas.GraphicsContext;

public enum BorderType {

    NORMAL{
        public void setBorder(GraphicsContext gc, double width){
            gc.setLineDashes(0);
            gc.setLineWidth(width);
        }

        @Override
        public String toString(){
            return "Normal";
        }
    },
    DOTTED_SIMPLE{
        public void setBorder(GraphicsContext gc, double width){
            gc.setLineDashes(10);
            gc.setLineWidth(width);
        }

        @Override
        public String toString(){
            return "Punteado Simple";
        }
    },
    DOTTED_COMPLEX{

        public void setBorder(GraphicsContext gc, double width){
            gc.setLineDashes(30,10,15,10);
            gc.setLineWidth(width);
        }
        @Override
        public String toString(){
            return "Punteado Complejo";
        }
    };

    public abstract void setBorder(GraphicsContext gc, double width);

}

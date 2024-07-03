package backend;

import javafx.scene.canvas.GraphicsContext;

public enum BorderType {
    NORMAL{
        public int[] BorderVec(){
            int[] resp = new int[] {0};
            return resp;
        }

        @Override
        public String toString(){
            return "Normal";
        }
    },
    DOTTED_SIMPLE{
        public int[] BorderVec(){
            int[] resp = new int[] {10};
            return resp;
        }


        @Override
        public String toString(){
            return "Punteado Simple";
        }
    },
    DOTTED_COMPLEX{
        public int[] BorderVec(){
            int[] resp = new int[] {30,10,15,10};
            return resp;
        }
        @Override
        public String toString(){
            return "Punteado Complejo";
        }
    };

    public abstract int[] BorderVec();
}

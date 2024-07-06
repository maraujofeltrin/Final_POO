package frontend;

import java.util.Objects;

public class Layers implements Comparable<Layers>{
    private boolean on;
    private final Integer num;

    public Layers(Integer num){
        this.on = true;
        this.num = num;
    }

    public boolean isOn(){
        return on;
    }

    public Integer getNumLayer(){
        return num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num,on);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Layers l && Objects.equals(l.num, num) && l.on == on;
    }


    public int getID() {
        return num;
    }

    public void setOn(boolean selected) {
        this.on = selected;
    }

    @Override
    public int compareTo(Layers o) {
        int cmp = Integer.compare(num,o.num);
        if(cmp == 0){
            return Boolean.compare(on,o.on);
        }
        return cmp;
    }
}

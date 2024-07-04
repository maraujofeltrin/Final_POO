package frontend;

import java.util.Objects;

public class Layers {
    private boolean on;
    private Integer num;

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

    public void onLayer(){
        on = true;
    }

    public void offLayer(){
        on = false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num,on);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Layers l && l.num == num && l.on == on;
    }


    public int getID() {
        return num;
    }

    public void setOn(boolean selected) {
        this.on = selected;
    }
}

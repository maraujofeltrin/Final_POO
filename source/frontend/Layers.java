package frontend;

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
}

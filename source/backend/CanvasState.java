package backend;

import frontend.Drawing.DrawFigure;
import frontend.Layers;

import java.util.*;

public class CanvasState {

    private final SortedMap<Layers, List<DrawFigure>> map = new TreeMap<>();

    public void addLayer(){
        int aux;
        if(map.isEmpty()){
            aux = 1;
        }else{
            aux = map.lastKey().getNumLayer() +1;
        }

        map.put(new Layers(aux), new ArrayList<>());
    }

    public void addFigure(Layers layer,DrawFigure figure) {
        if(layer != null && layer.isOn()){
            if(!map.containsKey(layer)){
                addLayer();
            }
            map.get(layer).add(figure);
        }
    }

    public void deleteFigure(Layers layer,DrawFigure figure) {
        if(layer != null && layer.isOn()){
            map.get(layer).remove(figure);
        }
    }


    public Iterable<DrawFigure> figures(Layers layer) {
        return map.get(layer);
    }

    public int getLayers(){
        return map.lastKey().getNumLayer();
    }

    public void deleteLayer(Layers l){
        map.remove(l);
    }
}

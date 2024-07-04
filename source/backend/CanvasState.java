package backend;

import backend.model.Figure;
import frontend.Drawing.DrawFigure;
import frontend.Layers;

import java.util.*;

public class CanvasState {

    private SortedMap<Layers, List<DrawFigure>> map = new TreeMap<>();
    //private final List<DrawFigure> list = new ArrayList<>();

    public void addLayer(){
        Integer aux = map.lastKey().getNumLayer() +1;
        map.put(new Layers(aux), new ArrayList<>());
    }

    public void addFigure(Layers layer,DrawFigure figure) {
        if(layer != null && layer.isOn()){
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

}

package backend;

import backend.model.Figure;
import frontend.Drawing.DrawFigure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    private final List<DrawFigure> list = new ArrayList<>();

    public void addFigure(DrawFigure figure) {
        list.add(figure);
    }

    public void deleteFigure(DrawFigure figure) {
        list.remove(figure);
    }

    public Iterable<DrawFigure> figures() {
        return new ArrayList<>(list);
    }

}

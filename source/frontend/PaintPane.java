package frontend;

import backend.BorderType;
import backend.CanvasState;
import backend.ShadowType;
import backend.model.*;
import frontend.Drawing.*;
import frontend.Drawing.drawButton.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
	private ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private ToggleButton rectangleButton = new ToggleButton("Rectángulo");

	private ToggleButton circleButton = new ToggleButton("Círculo");
	private ToggleButton squareButton = new ToggleButton("Cuadrado");
	private ToggleButton ellipseButton = new ToggleButton("Elipse");
	private ToggleButton deleteButton = new ToggleButton("Borrar");

	private Label shadowLabel = new Label("Sombras");
	private ComboBox<ShadowType> shadowComboBox = new ComboBox<>();

	private Label fillerColor = new Label("Relleno");
	private ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);
	private ColorPicker secondFillColor = new ColorPicker(defaultFillColor);

	private Label border = new Label("Borde");

	Slider graduationSlider = new Slider(0, 10, 5);
	private ComboBox<BorderType> borderComboBox = new ComboBox<>();

	// Selector de color de relleno


	private Label actions = new Label("Acciones");

	private ToggleButton duplicateButton = new ToggleButton("Duplicar");
	private ToggleButton divideButton = new ToggleButton("Dividir");
	private ToggleButton moveButton = new ToggleButton("Mov. Centro");

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private DrawFigure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	// Colores de relleno de cada figura
	private Map<DrawFigure, Color> figureColorMap = new HashMap<>();

	private void SetButtons(){
		rectangleButton.setUserData(new RectangleButton());
		circleButton.setUserData(new CircleButton());
		squareButton.setUserData(new SquareButton());
		ellipseButton.setUserData(new EllipseButton());
	}
	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		shadowComboBox.getItems().addAll(ShadowType.SIMPLE, ShadowType.COLORED, ShadowType.SIMPLE_INVERSED, ShadowType.COLORED_INVERSED, ShadowType.NONE);
		shadowComboBox.setValue(ShadowType.NONE);  // Valor por defecto

		borderComboBox.getItems().addAll(BorderType.NORMAL, BorderType.DOTTED_SIMPLE, BorderType.DOTTED_COMPLEX);
		borderComboBox.setValue(BorderType.NORMAL); //Valor por defecto

		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);

		buttonsBox.getChildren().add(shadowLabel);
		buttonsBox.getChildren().add(shadowComboBox);

		buttonsBox.getChildren().add(fillerColor);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.getChildren().add(secondFillColor);

		buttonsBox.getChildren().add(border);
		graduationSlider.setShowTickLabels(true);
		buttonsBox.getChildren().add(graduationSlider);
		buttonsBox.getChildren().add(borderComboBox);

		ToggleButton[] advancedTools = {duplicateButton, divideButton, moveButton};
		ToggleGroup advTools = new ToggleGroup();
		for (ToggleButton tool : advancedTools) {
			tool.setMinWidth(90);
			tool.setToggleGroup(advTools);
			tool.setCursor(Cursor.HAND);
		}
		buttonsBox.getChildren().add(actions);
		buttonsBox.getChildren().addAll(advancedTools);

		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);


		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
			Toggle selected= tools.getSelectedToggle();
			try{
				Buttons aux=(Buttons) selected.getUserData();
			}catch(Exception ex){
				System.out.println("Seleccionar algun boton");
			}

		});

		canvas.setOnMouseReleased(event -> {
			SetButtons();
			Point endPoint = new Point(event.getX(), event.getY());
			DrawFigure newFigure = null;
			//HAY QUE CAMBIARLO IMPERATIVO
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY() || tools.getSelectedToggle()==null) {
				return ;
			}
			//CORREGIR ERRORES
			try{
				Toggle selected= tools.getSelectedToggle();
				Buttons aux=(Buttons) selected.getUserData();
				newFigure = aux.ButtonToAction(startPoint, endPoint, fillColorPicker.getValue(), secondFillColor.getValue(),gc);
				figureColorMap.put(newFigure, fillColorPicker.getValue());

				canvasState.addFigure(newFigure);
			}catch (Exception ex){
				System.out.println("No hay figura selecionada para su creacion");
			}
			//FALTAN CHEQUEOS

			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(DrawFigure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (DrawFigure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected() && selectedFigure != null) {
				figureColorMap.remove(selectedFigure);
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;

				selectedFigure.addDiff(diffX,diffY);
				figureColorMap.put(selectedFigure, selectedFigure.getColor());
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(DrawFigure figure : canvasState.figures()) {
			if(figure == selectedFigure && figure != null && figureColorMap.get(selectedFigure) != null) {
				gc.setStroke(Color.RED);
				figure.setPrimaryColor(fillColorPicker.getValue());
				figure.setSecondaryColor(secondFillColor.getValue());
				selectedFigure.setShadow(shadowComboBox.getValue(), figure.getColor());
				selectedFigure.setBorder(borderComboBox.getValue());
				selectedFigure.setBorderWidth(graduationSlider.getValue());
			} else {
				gc.setStroke(lineColor);
			}

			//ver devuelta
			if(figure != null) {
				figure.FillFigure(figure.getColor(), figure.getSecondColor());
			}
			/*if(figure instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) figure;
				gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
				gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
						Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
			} else if(figure instanceof Circle) {
				Circle circle = (Circle) figure;
				double diameter = circle.getRadius() * 2;
				gc.fillOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
				gc.strokeOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
			} else if(figure instanceof Square) {
				Square square = (Square) figure;
				gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
						Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
				gc.strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
						Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
			} else if(figure instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) figure;
				gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
				gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
			}*/
		}
	}

	boolean figureBelongs(DrawFigure figure, Point eventPoint) {
		if(figure != null){
			return figure.belongs(eventPoint);
		}
		return false;
		/*if(figure instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) figure;
			found = eventPoint.getX() > rectangle.getTopLeft().getX() && eventPoint.getX() < rectangle.getBottomRight().getX() &&
					eventPoint.getY() > rectangle.getTopLeft().getY() && eventPoint.getY() < rectangle.getBottomRight().getY();
		} else if(figure instanceof Circle) {
			Circle circle = (Circle) figure;
			found = Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
					Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
		} else if(figure instanceof Square) {
			Square square = (Square) figure;
			found = eventPoint.getX() > square.getTopLeft().getX() && eventPoint.getX() < square.getBottomRight().getX() &&
					eventPoint.getY() > square.getTopLeft().getY() && eventPoint.getY() < square.getBottomRight().getY();
		} else if(figure instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) figure;
			// Nota: Fórmula aproximada. No es necesario corregirla.
			found = ((Math.pow(eventPoint.getX() - ellipse.getCenterPoint().getX(), 2) / Math.pow(ellipse.getsMayorAxis(), 2)) +
					(Math.pow(eventPoint.getY() - ellipse.getCenterPoint().getY(), 2) / Math.pow(ellipse.getsMinorAxis(), 2))) <= 0.30;
		}
		return found;**/

	}

}

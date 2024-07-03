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
	private ChoiceBox<ShadowType> shadowChoiceBox = new ChoiceBox<>();

	private Label fillerColor = new Label("Relleno");
	private ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);
	private ColorPicker secondFillColor = new ColorPicker(defaultFillColor);

	private Label border = new Label("Borde");

	Slider graduationSlider = new Slider(0, 10, 5);
	private ChoiceBox<BorderType> borderChoiceBox = new ChoiceBox<>();

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

		shadowChoiceBox.getItems().addAll(ShadowType.SIMPLE, ShadowType.COLORED, ShadowType.SIMPLE_INVERSED, ShadowType.COLORED_INVERSED, ShadowType.NONE);
		shadowChoiceBox.setValue(ShadowType.NONE);  // Valor por defecto

		borderChoiceBox.getItems().addAll(BorderType.NORMAL, BorderType.DOTTED_SIMPLE, BorderType.DOTTED_COMPLEX);
		borderChoiceBox.setValue(BorderType.NORMAL); //Valor por defecto

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
		buttonsBox.getChildren().add(shadowChoiceBox);

		buttonsBox.getChildren().add(fillerColor);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.getChildren().add(secondFillColor);

		buttonsBox.getChildren().add(border);
		graduationSlider.setShowTickLabels(true);
		buttonsBox.getChildren().add(graduationSlider);
		buttonsBox.getChildren().add(borderChoiceBox);

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
				newFigure = aux.ButtonToAction(startPoint, endPoint, fillColorPicker.getValue(), secondFillColor.getValue(),gc, shadowChoiceBox.getValue(), borderChoiceBox.getValue(), graduationSlider.getValue());
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
				selectedFigure = null;
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

			for (DrawFigure figure : canvasState.figures()) {
				DrawFigure duplicate = null;
				if (figure == selectedFigure && figure != null && figureColorMap.get(selectedFigure) != null) {
					gc.setStroke(Color.RED);
					figure.setPrimaryColor(fillColorPicker.getValue());
					figure.setSecondaryColor(secondFillColor.getValue());
					selectedFigure.setShadow(shadowChoiceBox.getValue(), figure.getColor());
					selectedFigure.setBorder(borderChoiceBox.getValue(), graduationSlider.getValue());
					if(duplicateButton.isSelected()){
						duplicate = figure.clone();
					}
				} else {
					gc.setStroke(lineColor);
				}

				//ver devuelta
				if (figure != null) {
					figure.FillFigure(figure.getColor(), figure.getSecondColor());
					if(duplicateButton.isSelected() && duplicate != null){
						duplicate.FillFigure(duplicate.getColor(), duplicate.getSecondColor());
					}
				}

			}

	}

	boolean figureBelongs(DrawFigure figure, Point eventPoint) {
		if(figure != null){
			return figure.belongs(eventPoint);
		}
		return false;
	}
}

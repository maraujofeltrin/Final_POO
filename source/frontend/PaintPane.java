package frontend;

import frontend.Drawing.BorderType;
import backend.CanvasState;
import backend.ShadowType;
import backend.model.*;
import frontend.Drawing.*;
import frontend.Drawing.drawButton.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.util.StringConverter;

import java.util.*;

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

	private Slider graduationSlider = new Slider(0, 10, 5);
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
	//private SortedMap<Layers,Map<DrawFigure, Color>> LayersMap =new TreeMap<>();

	//Seguimiento de las capas
	private Set<Layers> layers = new TreeSet<>();
	private Label layer = new Label("Capas");
	private ChoiceBox<Integer> layerChoiceBox = new ChoiceBox();

	private RadioButton hideButton = new RadioButton("Ocultar");
	private RadioButton showButton = new RadioButton("Mostrar");


	private ToggleButton addLayerButton = new ToggleButton("Agregar Capa");
	private ToggleButton deleteLayerButton = new ToggleButton("Eliminar Capa");



	private void SetButtons(){
		rectangleButton.setUserData(new RectangleButton());
		circleButton.setUserData(new CircleButton());
		squareButton.setUserData(new SquareButton());
		ellipseButton.setUserData(new EllipseButton());
	}
	private void SetMapLayers(){
		for(int i=1 ; i<=3 ; i++){
			Layers aux = new Layers(i);
			layers.add(aux);
			canvasState.addLayer();
		}


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

		HBox headerBox = new HBox(10);
		headerBox.getChildren().add(layer);
		layerChoiceBox.setConverter(new StringConverter<Integer>() {
			@Override
			public String toString(Integer integer) {
				return "Capa %d".formatted(integer);
			}

			@Override
			public Integer fromString(String s) {
				return null;
			}
		});

		AddLayers();
		headerBox.getChildren().add(layerChoiceBox);

		showButton.setSelected(true);
		ToggleGroup statusButtons = new ToggleGroup();
		showButton.setToggleGroup(statusButtons);
		hideButton.setToggleGroup(statusButtons);
		headerBox.getChildren().add(showButton);
		headerBox.getChildren().add(hideButton);

		headerBox.getChildren().add(addLayerButton);
		headerBox.getChildren().add(deleteLayerButton);

		headerBox.setPadding(new Insets(5));
		headerBox.setStyle("-fx-background-color: #999");
		headerBox.setPrefWidth(100);
		headerBox.setAlignment(Pos.CENTER);
		setTop(headerBox);


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
		SetButtons();
		SetMapLayers();
		setLeft(buttonsBox);
		setRight(canvas);


		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
			try{

				if(selectedFigure != null){
				selectedFigure.setShadow(shadowChoiceBox.getValue(), selectedFigure.getColor());
				selectedFigure.setBorder(borderChoiceBox.getValue());
				selectedFigure.setWidth(graduationSlider.getValue());
				}
			}catch(Exception ex){
				System.out.println("Seleccionar algun boton");
			}
		});

		canvas.setOnMouseReleased(event -> {

			Point endPoint = new Point(event.getX(), event.getY());
			DrawFigure newFigure = null;
			//HAY QUE CAMBIARLO IMPERATIVO
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY() || tools.getSelectedToggle()==null) {
				return ;
			}
			//CORREGIR ERRORES
			try{
					Toggle selected = tools.getSelectedToggle();
					Buttons aux = (Buttons) selected.getUserData();
					if(!selectionButton.isSelected()) {
						newFigure = aux.ButtonToAction(startPoint, endPoint, fillColorPicker.getValue(), secondFillColor.getValue(), gc, shadowChoiceBox.getValue(), borderChoiceBox.getValue(), graduationSlider.getValue());
						addFigure(newFigure, fillColorPicker.getValue());
					}
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
			for(Layers l : layers) {
				if(l.isOn()) {
					for (DrawFigure figure : canvasState.figures(l)) {
						if (figureBelongs(figure, eventPoint)) {
							found = true;
							label.append(figure.toString());
						}
					}
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
				for(Layers layer : layers) {
					if(layer.isOn()) {
						for (DrawFigure figure : canvasState.figures(layer)) {
							if (figureBelongs(figure, eventPoint) && layer.isOn() && !found) {
								found = true;
								selectedFigure = figure;
								label.append(figure.toString());
							}
						}
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());

				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
			}
			redrawCanvas();
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

		fillColorPicker.setOnAction(event->{
			if(selectedFigure != null){
				selectedFigure.setPrimaryColor(fillColorPicker.getValue());
				redrawCanvas();
			}
		});

		secondFillColor.setOnAction(event->{
			if(selectedFigure != null){
				selectedFigure.setSecondaryColor(secondFillColor.getValue());
				redrawCanvas();
			}
		});


		shadowChoiceBox.setOnAction(event->{
			if(selectedFigure!=null) {
				selectedFigure.setShadow(shadowChoiceBox.getValue(), selectedFigure.getColor());
				redrawCanvas();
			}
		});

		borderChoiceBox.setOnAction(event->{
			if(selectedFigure!=null) {
				selectedFigure.setBorder(borderChoiceBox.getValue());
				redrawCanvas();
			}
		});

		graduationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedFigure != null) {
				selectedFigure.setWidth(newValue.doubleValue());
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			RemoveFigure();
		});

		divideButton.setOnAction(event -> {
			if(selectedFigure!=null) {
				DrawFigure[] divide = selectedFigure.divideFigure();
				Color coloraux = selectedFigure.getColor();
				addFigure(divide[0], coloraux);
				addFigure(divide[1], coloraux);
				RemoveFigure();
				redrawCanvas();
			}
		});

		moveButton.setOnAction(event -> {
			if (selectedFigure != null) {
				double canvasWidth = canvas.getWidth();
				double canvasHeight = canvas.getHeight();

				double centerX = canvasWidth / 2;
				double centerY = canvasHeight / 2;

				Point figureCenter = selectedFigure.getCenterPoint();

				double diffX = centerX - figureCenter.getX();
				double diffY = centerY - figureCenter.getY();

				selectedFigure.addDiff(diffX, diffY);

				redrawCanvas();
			}
		});

		duplicateButton.setOnAction(actionEvent -> {
			if(selectedFigure!=null){
				DrawFigure duplicate = selectedFigure.duplicate();
				canvasState.addFigure(new Layers(layerChoiceBox.getValue()),duplicate);
				figureColorMap.put(duplicate,duplicate.getColor());
				redrawCanvas();
			}
		});

		showButton.setOnAction(event ->{
			setVisiblility(true);
			redrawCanvas();
		});

		hideButton.setOnAction(event ->{
			setVisiblility(false);
			redrawCanvas();
		});

		addLayerButton.setOnAction(event ->{
			layers.add(new Layers(canvasState.getLayers()+1));
			canvasState.addLayer();
			layerChoiceBox.getItems().add(canvasState.getLayers());
			redrawCanvas();
		});

		layerChoiceBox.setOnAction(event -> {
			redrawCanvas();
		});

		deleteLayerButton.setOnAction(event->{
			if(layerChoiceBox.getValue() >3){
				for(Layers l : layers){
					if(l.getID() == layerChoiceBox.getValue()){
						layers.remove(l);
						canvasState.deleteLayer(l);
					}
				}

				layerChoiceBox.getItems().remove(layerChoiceBox.getValue());
				layerChoiceBox.setValue(1);
				redrawCanvas();
			}
		});

	}

	private void setVisiblility(boolean condition){
		for(Layers layer : layers){
			if(layer.getID() == layerChoiceBox.getValue()){
				layer.setOn(condition);
				return;
			}
		}
	}

	private void AddLayers() {
		for(Layers layer : layers){
			layerChoiceBox.getItems().addAll(layer.getID());
		}

		for(int i = 1; i<=3; i++){
			if(!layerChoiceBox.getItems().contains(i)){
				layerChoiceBox.getItems().add(i);
			}
		}
		layerChoiceBox.getItems().sort(Comparator.naturalOrder());
		layerChoiceBox.setValue(1);
	}


	void RemoveFigure(){
	if (selectedFigure != null) {
				canvasState.deleteFigure(new Layers(layerChoiceBox.getValue()),selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
	}
	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for(Layers l : layers) {
			if(l.getID() ==layerChoiceBox.getValue()){
				showButton.setSelected(l.isOn());
				hideButton.setSelected(!l.isOn());
			}
			if(l.isOn()) {
				for (DrawFigure figure : canvasState.figures(l)) {

					if (figure == selectedFigure && figure != null && figureColorMap.get(selectedFigure) != null) {
						gc.setStroke(Color.RED);
						fillColorPicker.setValue(figure.getColor());
						secondFillColor.setValue(figure.getSecondColor());
						shadowChoiceBox.setValue(figure.getShadowType());
						borderChoiceBox.setValue(figure.getBorderType());
						graduationSlider.setValue(figure.getBorderWidth());
					} else {
						gc.setStroke(lineColor);
					}

					//ver devuelta
					if (figure != null) {
						figure.FillFigure(figure.getColor(), figure.getSecondColor());
					}

				}
			}
		}
	}

	public void addFigure(DrawFigure figure, Color color){
		figureColorMap.put(figure, color);
		canvasState.addFigure(new Layers(layerChoiceBox.getValue()),figure);
	}
	boolean figureBelongs(DrawFigure figure, Point eventPoint) {
		if(figure != null){
			return figure.belongs(eventPoint);
		}
		return false;
	}
}

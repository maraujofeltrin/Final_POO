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
import javafx.util.StringConverter;

import java.util.*;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private final Color defaultFillColor = Color.YELLOW;
	private final Color defaultSecondColor= Color.BLUE;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");

	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");

	private final Label shadowLabel = new Label("Sombras");
	private final ChoiceBox<ShadowType> shadowChoiceBox = new ChoiceBox<>();

	private final Label fillerColor = new Label("Relleno");
	private final ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);
	private final ColorPicker secondFillColor = new ColorPicker(defaultSecondColor);

	private final Label border = new Label("Borde");

	private final Slider graduationSlider = new Slider(0, 10, 5);
	private final ChoiceBox<BorderType> borderChoiceBox = new ChoiceBox<>();

	// Selector de color de relleno


	private final Label actions = new Label("Acciones");

	private final Button duplicateButton = new Button("Duplicar");
	private final Button divideButton = new Button("Dividir");
	private final Button moveButton = new Button("Mov. Centro");

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private DrawFigure selectedFigure;

	// StatusBar
	private final StatusPane statusPane;

	//Seguimiento de las capas
	private final Set<Layers> layers = new TreeSet<>();
	private final Label layer = new Label("Capas");
	private final ChoiceBox<Integer> layerChoiceBox = new ChoiceBox<>();

	private final RadioButton hideButton = new RadioButton("Ocultar");
	private final RadioButton showButton = new RadioButton("Mostrar");


	private final Button addLayerButton = new Button("Agregar Capa");
	private final Button deleteLayerButton = new Button("Eliminar Capa");



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
		layerChoiceBox.setConverter(new StringConverter<>() {
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


		Button[] advancedTools = {duplicateButton, divideButton, moveButton};
		for (Button tool : advancedTools) {
			tool.setMinWidth(90);
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
			//CHEQUEAR
				 if(!selectionButton.isSelected()){
					statusPane.updateStatus("Seleccionar algun boton");
				}
		});

		canvas.setOnMouseReleased(event -> {

			Point endPoint = new Point(event.getX(), event.getY());
			DrawFigure newFigure;
			if(startPoint == null || tools.getSelectedToggle()==null) {
				return;
			}
			if((endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) && !selectionButton.isSelected()){
				AlertDirectionofDrawing();
				return;
			}
			Toggle selected = tools.getSelectedToggle();
			Buttons aux = (Buttons) selected.getUserData();
			//Condition to draw in a visible layer
			if(!selectionButton.isSelected() && aux !=null) {
				if(CanDraw(layerChoiceBox.getValue())){
					newFigure = aux.ButtonToAction(startPoint, endPoint, fillColorPicker.getValue(), secondFillColor.getValue(), gc, shadowChoiceBox.getValue(), borderChoiceBox.getValue(), graduationSlider.getValue(),layerChoiceBox.getValue());
					addFigure(newFigure, layerChoiceBox.getValue());
				} else{
					AlertselectedLayerHidden();
				}
			}
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
							label.append(figure);
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
								label.append(figure);
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
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;

				selectedFigure.Move(diffX,diffY);
				redrawCanvas();
			}
		});
		//COLOR PICKERS
		fillColorPicker.setOnAction(event->PrimaryColorPicker());
		secondFillColor.setOnAction(event->SecondaryColorPicker());
		//CHOOSE FIGURE SETTINGS
		shadowChoiceBox.setOnAction(event->chooseShadow());
		borderChoiceBox.setOnAction(event->chooseBorder());

		graduationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedFigure != null) {
				selectedFigure.setWidth(newValue.doubleValue());
				redrawCanvas();
			}
		});
		deleteButton.setOnAction(event -> RemoveFigure());

		divideButton.setOnAction(event -> divideAction());

		moveButton.setOnAction(event -> moveToCenterAction());

		duplicateButton.setOnAction(actionEvent -> duplicateAction());

		//LAYERS SETTINGS:
		showButton.setOnAction(event ->{
			setVisiblility(true);
			redrawCanvas();
		});
		hideButton.setOnAction(event ->{
			setVisiblility(false);
			redrawCanvas();
		});
		addLayerButton.setOnAction(event ->AddLayerAction());

		layerChoiceBox.setOnAction(event -> redrawCanvas());

		deleteLayerButton.setOnAction(event->deleteLayerAction());

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

	private void RemoveFigure(){
		if(selectedFigure != null) {
				canvasState.deleteFigure(new Layers(selectedFigure.getLayer()),selectedFigure);
				selectedFigure = null;
				redrawCanvas();
		}else{
			AlertNotSelectedFigure();
		}
	}
	//Draws all the figures in the canvas
	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Layers l : layers) {
			if(l.getID() ==layerChoiceBox.getValue()){
				showButton.setSelected(l.isOn());
				hideButton.setSelected(!l.isOn());
			}
			if(l.isOn()) {
				for (DrawFigure figure : canvasState.figures(l)) {
					if (figure != null) {
						if(figure.equals(selectedFigure) && selectionButton.isSelected()) {
							gc.setStroke(Color.RED);
							setMenu(figure);
						} else {
							gc.setStroke(lineColor);
						}
						figure.FillFigure(figure.getColor(), figure.getSecondColor());
					}
				}
			}
		}
	}

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

	private boolean CanDraw(int num){
		for(Layers l:layers){
			if(l.getNumLayer() == num){
				return l.isOn();
			}
		}
		return false;
	}

	private void setMenu(DrawFigure figure){
		fillColorPicker.setValue(figure.getColor());
		secondFillColor.setValue(figure.getSecondColor());
		shadowChoiceBox.setValue(figure.getShadowType());
		borderChoiceBox.setValue(figure.getBorderType());
		graduationSlider.setValue(figure.getBorderWidth());
	}

	private void addFigure(DrawFigure figure, Integer layer){
		canvasState.addFigure(new Layers(layer),figure);
	}
	private boolean figureBelongs(DrawFigure figure, Point eventPoint) {
		if(figure != null){
			return figure.belongs(eventPoint);
		}
		return false;
	}
	private void PrimaryColorPicker(){
		if(selectedFigure != null){
			selectedFigure.setPrimaryColor(fillColorPicker.getValue());
			redrawCanvas();
		}
	}
	private void SecondaryColorPicker(){
		if(selectedFigure != null){
			selectedFigure.setSecondaryColor(secondFillColor.getValue());
			redrawCanvas();
		}
	}
	private void chooseShadow(){
		if(selectedFigure!=null) {
			selectedFigure.setShadow(shadowChoiceBox.getValue(), selectedFigure.getColor());
			redrawCanvas();
		}
	}
	private void chooseBorder(){
		if(selectedFigure!=null) {
			selectedFigure.setBorder(borderChoiceBox.getValue());
			redrawCanvas();
		}
	}
	private void divideAction(){
		if(selectedFigure!=null) {
			DrawFigure[] divide = selectedFigure.divideFigure();
			addFigure(divide[0], selectedFigure.getLayer());
			addFigure(divide[1], selectedFigure.getLayer());
			RemoveFigure();
			redrawCanvas();
		}else{
			AlertNotSelectedFigure();
		}
	}
	private void moveToCenterAction(){
		if (selectedFigure != null) {
			double canvasWidth = canvas.getWidth();
			double canvasHeight = canvas.getHeight();
			double centerX = canvasWidth / 2;
			double centerY = canvasHeight / 2;
			Point figureCenter = selectedFigure.getCenterPoint();
			double diffX = centerX - figureCenter.getX();
			double diffY = centerY - figureCenter.getY();
			selectedFigure.Move(diffX, diffY);
			redrawCanvas();
		} else {
			AlertNotSelectedFigure();
		}
	}
	private void duplicateAction(){
		if(selectedFigure!=null){
			DrawFigure duplicate = selectedFigure.duplicate();
			canvasState.addFigure(new Layers(layerChoiceBox.getValue()),duplicate);
			redrawCanvas();
		}else{
			AlertNotSelectedFigure();
		}
	}
	private void AddLayerAction(){
		layers.add(new Layers(canvasState.getLayers()+1));
		canvasState.addLayer();
		layerChoiceBox.getItems().add(canvasState.getLayers());
		redrawCanvas();
	}
	private void deleteLayerAction(){
		if(layerChoiceBox.getValue() >3){
			Iterator<Layers> iterator = layers.iterator();
			while (iterator.hasNext()) {
				Layers l = iterator.next();
				if (l.getID() == layerChoiceBox.getValue()) {
					iterator.remove();
					canvasState.deleteLayer(l);
				}
			}
			redrawCanvas();

			layerChoiceBox.getItems().remove(layerChoiceBox.getValue());
			layerChoiceBox.setValue(1);
			redrawCanvas();
		}

	}

	//ALERTS FUNCTIONS:
	private void AlertNotSelectedFigure(){
		WarningAlert("Seleccione una figura");
	}
	private void AlertselectedLayerHidden(){
		WarningAlert("La capa esta oculta, para dibujar pongala visible");
	}
	private void AlertDirectionofDrawing(){
		WarningAlert("Dibujar de Izquierda a derecha y de arriba hacia abajo");
	}
	private void WarningAlert(String msg){
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("ERROR");
		alert.setHeaderText(msg);
		alert.showAndWait();
	}

}

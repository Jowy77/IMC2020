package dad.javafx.IMC;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class ImcApp extends Application {
	
	private Label pesoLabel,kgLabel;
	private Label alturaLabel,cmLabel;
	private Label imcLabel,resultadoLabel;
	private Label gordoLabel;
	private TextField pesoTextField;
	private TextField alturaTextField;
	
	private DoubleProperty pesoDoubleProperty;
	private DoubleProperty alturaDoubleProperty;
	private DoubleProperty imcDoubleProperty;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//INICIO LOS ELEMENTOS DE LA INTERFAZ
		pesoLabel = new Label(" Peso:");
		pesoTextField = new TextField();
		pesoTextField.setPrefWidth(80);
		kgLabel = new Label("Kg");
		
		alturaLabel = new Label("Altura:");
		alturaTextField = new TextField();
		alturaTextField.setPrefWidth(80);
		cmLabel = new Label("cm");
		
		imcLabel = new Label("IMC:");
		resultadoLabel = new Label();
		
		gordoLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
		
		//AHORA LOS ORGANIZO EN LAS CAJAS
		HBox pesoHBox = new HBox(5, pesoLabel,pesoTextField,kgLabel);
		pesoHBox.setFillHeight(false);
		pesoHBox.setAlignment(Pos.CENTER);
		HBox alturaHBox = new HBox(5, alturaLabel,alturaTextField,cmLabel);
		alturaHBox.setFillHeight(false);
		alturaHBox.setAlignment(Pos.CENTER);
		HBox imcHBox = new HBox(5, imcLabel,resultadoLabel);
		imcHBox.setFillHeight(false);
		imcHBox.setAlignment(Pos.CENTER);
		
		//AHORA LO METO LOS ELEMENTOS EN EL CONTENEDOR RAIZ
		VBox root = new VBox(5, pesoHBox,alturaHBox,imcHBox,gordoLabel);
		root.setFillWidth(false);
		root.setAlignment(Pos.CENTER);
		
		//HAGO LOS BINDEOS
		pesoDoubleProperty = new SimpleDoubleProperty();
		alturaDoubleProperty = new SimpleDoubleProperty();
		imcDoubleProperty = new SimpleDoubleProperty();
		
		Bindings.bindBidirectional(pesoTextField.textProperty(), pesoDoubleProperty, new NumberStringConverter());
		Bindings.bindBidirectional(alturaTextField.textProperty(), alturaDoubleProperty, new NumberStringConverter());
		
		//OPERACION PARA CONSEGUIR EL IMC
		imcDoubleProperty.bind(pesoDoubleProperty.divide((alturaDoubleProperty.divide(100)).multiply(alturaDoubleProperty.divide(100))));
		
		//AHORA MUESTRO EL RESULTADO DE LA OPERACION EN EL LABEL
		resultadoLabel.textProperty().bind(imcDoubleProperty.asString("%.2f"));
		
		resultadoLabel.textProperty().addListener(e -> {
			if (imcDoubleProperty.doubleValue() < 18.5)
				gordoLabel.setText("Bajo peso");
			else if (imcDoubleProperty.doubleValue() >= 18.5 && imcDoubleProperty.doubleValue() < 25)
				gordoLabel.setText("Peso normal");
			else if (imcDoubleProperty.doubleValue() >= 25 && imcDoubleProperty.doubleValue() < 30)
				gordoLabel.setText("Sobrepeso");
			else if (imcDoubleProperty.doubleValue() > 30)
				gordoLabel.setText("Obesidad");
		});
		
		//POR ULTIMO CREO LA ESCENA, LO METO EN EL PRIMARY STAGE Y LO MUESTRO
		Scene scene = new Scene(root,320,200);
		
		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

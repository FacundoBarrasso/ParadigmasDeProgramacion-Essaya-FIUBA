package Robots.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.function.BiConsumer;

public class ConfiguracionView {

    private final BiConsumer<Integer, Integer> onAceptar;
    private final Font fuente = Font.loadFont(getClass().getResourceAsStream("/font/CyberpunkCraftpixPixel.otf"), 24);
    private final Image imagenTitulo = new Image("file:src/main/resources/titulo/titulo.png");
    private final Image imagenFondo = new Image("file:src/main/resources/fondos/configuracion.png");
    private final Background fondoTitulo = new Background(new BackgroundImage(imagenTitulo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(500, 120, false, false, false, false)));

    public ConfiguracionView(BiConsumer<Integer, Integer> onAceptar) {
        this.onAceptar = onAceptar;
    }

    public void mostrar(Stage stage) {
        Background fondoVentana = new Background(new BackgroundImage(
            imagenFondo,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        ));
        Region fondo = new Region();
        fondo.setBackground(fondoVentana);

        Label textoTitulo = new Label("ROBOTS");
        textoTitulo.setFont(fuente);
        textoTitulo.setStyle("-fx-fill: #5b6e9c; -fx-effect: dropshadow(gaussian, #5b6e9c, 20, 0.8, 0, 0);");
        textoTitulo.setTranslateY(-17);
        textoTitulo.setScaleX(1.8);
        textoTitulo.setScaleY(1.4);

        HBox banner = new HBox(textoTitulo);
        banner.setBackground(fondoTitulo);
        banner.setPrefSize(500, 120);
        banner.setAlignment(Pos.CENTER);
        
        Label labelFilas = new Label("Filas:");
        labelFilas.setFont(fuente);
        labelFilas.setStyle(
            "-fx-text-fill: #a3c1e0;" +
            "-fx-effect: dropshadow(gaussian, black, 2, 1, 0, 0);");
        Spinner<Integer> filasInput = new Spinner<>();
        filasInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 20, 10));
        filasInput.setEditable(true);
        estilizarCampoTexto(filasInput);

        Label labelColumnas = new Label("Columnas:");
        labelColumnas.setFont(fuente);
        labelColumnas.setStyle(
            "-fx-text-fill: #a3c1e0;" +
            "-fx-effect: dropshadow(gaussian, black, 2, 1, 0, 0);" );
        Spinner<Integer> columnasInput = new Spinner<>();
        columnasInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 35, 10));
        columnasInput.setEditable(true);
        estilizarCampoTexto(columnasInput);

        Button aceptar = new BotonRobots("INICIAR");
        aceptar.setOnAction(e -> {
            int filas = filasInput.getValue();
            int columnas = columnasInput.getValue();
            onAceptar.accept(filas, columnas);
        });

        // Contenedor principal
        VBox layout = new VBox(20, banner, labelFilas, filasInput, labelColumnas, columnasInput, aceptar);
        layout.setAlignment(Pos.CENTER);
        layout.setTranslateY(40);

        StackPane root = new StackPane();
        root.getChildren().addAll(fondo, layout);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Configuración");
        stage.setMinHeight(800);
        stage.setMinWidth(600);
        stage.show();
    }

    private void estilizarCampoTexto(Spinner<?> campo) {
        campo.setPrefWidth(220);
        campo.setStyle(
                "-fx-background-radius: 10;" +
                        "-fx-border-radius: 9;" +
                        "-fx-border-color: #5b6e9c;" +
                        "-fx-border-width: 2px;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-family: 'Verdana';" +
                        "-fx-padding: 5 10;" +
                        "-fx-text-fill: #2c3e50;" +
                        "-fx-background-color: white;"
        );
    }

}

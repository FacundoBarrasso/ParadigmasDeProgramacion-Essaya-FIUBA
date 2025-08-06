package Robots.view;

import Robots.modelo.tablero.Juego;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VentanaPrincipal {
    private final Juego juego;
    private final int filas;
    private final int columnas;

    public VentanaPrincipal(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.juego = new Juego(filas, columnas);

    }

    public void start(Stage stage) {
        juego.iniciarPartida(1);

        JuegoView juegoView = new JuegoView(stage, filas, columnas);
        juegoView.iniciarJuegoView(juego);

        PanelView panelView = new PanelView(juego);
        ScoreView scoreView = new ScoreView(juego);
        BorderPane layout = new BorderPane();
        VentanaView ventanaView = new VentanaView(layout, juego);
        RobotsController controlador = new RobotsController(juego, juegoView, panelView, scoreView, ventanaView);

        layout.setTop(scoreView);
        layout.setCenter(juegoView);
        layout.setBottom(panelView);

        Scene scene = new Scene(layout);

        scene.setOnKeyPressed(controlador::manejarTecla);

        layout.prefWidthProperty().bind(scene.widthProperty());
        layout.prefHeightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.setMinHeight(filas * 32 + 100);
        stage.setMinWidth(columnas * 32 + 100);
        stage.show();
        controlador.iniciarLoop(scene);
    }
}
package Robots.view;

import Robots.modelo.tablero.Juego;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class JuegoView extends Group {
    private Juego juego;
    private final int filas;
    private final int columnas;
    private GridPane grilla;
    private Consumer<CeldaView> manejadorClick;

    public JuegoView(Stage stage, int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;

        Scene scene = new Scene(this, 800, 600);
        stage.setTitle("Robots");
        stage.setScene(scene);
        stage.show();
    }

    public void iniciarJuegoView(Juego juego) {
        this.juego = juego;
        this.getChildren().clear();
        this.grilla = new GridPane();
        this.getChildren().add(grilla);

        construirGrilla(this.juego, filas, columnas);
        actualizar();
    }

    private void construirGrilla(Juego juego, int filas, int columnas) {
        grilla.setHgap(0);
        grilla.setVgap(0);
        grilla.getChildren().clear();
        grilla.setAlignment(Pos.CENTER);
        grilla.setStyle("-fx-background-color: #808080;");

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                CeldaView celdaView = new CeldaView(juego.getCelda(fila, columna));
                celdaView.setOnMouseClicked(e -> {
                    if (manejadorClick != null) {
                        manejadorClick.accept(celdaView);
                    }
                });
                grilla.add(celdaView, columna, fila);
            }
        }
    }

    public void actualizar() {
        grilla.getChildren().forEach(n -> {
            if (n instanceof CeldaView) {
                CeldaView celdaView = (CeldaView) n;
                celdaView.setEntidades(juego.getHeroe(), juego.getRobots());
            }
        });
    }

    public void setOnCeldaClick(Consumer<CeldaView> handler) { this.manejadorClick = handler; }
}

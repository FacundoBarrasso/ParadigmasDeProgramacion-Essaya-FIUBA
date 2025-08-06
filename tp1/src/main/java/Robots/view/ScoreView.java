package Robots.view;

import Robots.modelo.tablero.Juego;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ScoreView extends HBox {
    private final Font font = Font.loadFont(getClass().getResourceAsStream("/font/CyberpunkCraftpixPixel.otf"), 20);
    private final Label nivel;
    private final Label score;
    private final Label recursosRestantes;

    public ScoreView(Juego juego) {
        this.nivel = new Label("Nivel: " + juego.getNivel());
        this.score = new Label("Score: " + juego.getScore());
        this.recursosRestantes = new Label("Diamantes: " + juego.getRecursosRestantes());

        score.setFont(font);
        nivel.setFont(font);
        recursosRestantes.setFont(font);
        score.setStyle("-fx-text-fill: #333");
        nivel.setStyle("-fx-text-fill: #333");
        recursosRestantes.setStyle("-fx-text-fill: #333");

        setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(nivel, score, recursosRestantes);
    }

    public void actualizarEstado(int scoreActual, int nivelActual, int recursosRestantesActuales) {
        score.setText("Score: " + scoreActual);
        nivel.setText("Nivel: " + nivelActual);
        recursosRestantes.setText("Diamantes: " + recursosRestantesActuales);
    }
}

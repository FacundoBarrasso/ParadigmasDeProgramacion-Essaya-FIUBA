package Robots.view;

import Robots.modelo.tablero.Juego;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

public class FinPartidaView {
    private static final Font fuente = Font.loadFont(FinPartidaView.class.getResourceAsStream("/font/CyberpunkCraftpixPixel.otf"), 30);
    private static final Image fondo = new Image("file:src/main/resources/fondos/finPartida.png");

    public static void mostrar(Juego juego, String mensaje, String mensajeBoton, Runnable onReiniciar) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);

        Label textoScore = new Label("Score: " + juego.getScore());
        textoScore.setFont(fuente);
        textoScore.setStyle("-fx-text-fill: #b2bec3; -fx-effect: dropshadow(gaussian, black, 4, 0.5, 2, 2);");

        String archivoSonido = mensaje.toLowerCase().contains("ganaste") ? "victory.mp3" : "gameover.mp3";
        reproducirSonido(archivoSonido);

        String iconPath = mensaje.toLowerCase().contains("ganaste") ?
                "file:src/main/resources/icons/trophy.gif" :
                "file:src/main/resources/icons/loser.gif";

        ImageView icono = new ImageView(new Image(iconPath));
        icono.setFitWidth(96);
        icono.setFitHeight(96);

        Text texto = new Text(mensaje.toUpperCase());
        texto.setFont(fuente);
        texto.setStyle("-fx-fill: #b2bec3; -fx-effect: dropshadow(gaussian, black, 4, 0.5, 2, 2);");

        BotonRobots botonReiniciar = new BotonRobots(mensajeBoton.toUpperCase());
        botonReiniciar.setOnAction(e -> {
            popup.close();
            onReiniciar.run();
        });

        BotonRobots botonSalir = new BotonRobots("SALIR");
        botonSalir.setOnAction(e -> Platform.exit());

        HBox botones = new HBox(20, botonReiniciar, botonSalir);
        botones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(25, textoScore, icono, texto, botones);
        layout.setAlignment(Pos.CENTER);

        layout.setBackground(new Background(
                new BackgroundImage(
                        fondo,
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(64, 64, false, false, false, false)
                )));

        layout.setScaleX(0.8);
        layout.setScaleY(0.8);
        ScaleTransition zoom = new ScaleTransition(Duration.millis(400), layout);
        zoom.setToX(1);
        zoom.setToY(1);
        zoom.play();

        Scene scene = new Scene(layout, 350, 280);
        popup.setScene(scene);
        popup.setResizable(false);

        popup.setOnCloseRequest(e -> Platform.exit());

        popup.show();
    }

    private static void reproducirSonido(String nombreArchivo) {
        try {
            var resourcePath = FinPartidaView.class.getResource("/sounds/" + nombreArchivo).toString();
            var audio = new AudioClip(resourcePath);
            audio.setVolume(0.1);
            audio.play();
        } catch (Exception e) {
            System.err.println("No se pudo reproducir el sonido: " + nombreArchivo);
            e.printStackTrace();
        }
    }
}

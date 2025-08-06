package Robots.view;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class BotonRobots extends Button {

    private final Font fuente = Font.loadFont(getClass().getResourceAsStream("/font/CyberpunkCraftpixPixel.otf"), 14);

    public BotonRobots(String texto) {
        super(texto);

        setEstiloNormal();
        setTextFill(Color.WHITE);
        setFont(fuente);

        // Animación hover
        ScaleTransition hoverAnim = new ScaleTransition(Duration.millis(150), this);
        hoverAnim.setToX(1.05);
        hoverAnim.setToY(1.05);

        ScaleTransition exitAnim = new ScaleTransition(Duration.millis(150), this);
        exitAnim.setToX(1);
        exitAnim.setToY(1);

        setOnMouseEntered(e -> {
            setEstiloHover();
            hoverAnim.playFromStart();
        });

        setOnMouseExited(e -> {
            setEstiloNormal();
            exitAnim.playFromStart();
        });

        focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                setFont(fuente);
            }
        });
    }

    public void actualizarTexto(String nuevoTexto) {
        setText(nuevoTexto);
    }

    public void setAccion(Runnable accion) {
        setOnAction(e -> accion.run());
    }

    private void setEstiloNormal() {
        setStyle(
                "-fx-background-color: linear-gradient(to bottom, #5b6e9c, #2c3e50);" +  
                        "-fx-border-color: #1c2833;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 8 16;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 3, 0, 1, 1);" +
                        "-fx-font-family: '" + fuente.getName() + "';" +  
                        "-fx-font-size: " + fuente.getSize() + "px;"
        );
    }

    private void setEstiloHover() {
        setStyle(
                "-fx-background-color: linear-gradient(to bottom, #6d84b4, #34495e);" +
                        "-fx-border-color: #1c2833;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 8 16;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 1, 2);" +
                        "-fx-font-family: '" + fuente.getName() + "';" +  
                        "-fx-font-size: " + fuente.getSize() + "px;"
        );
    }
}

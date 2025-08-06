package Robots.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HeroeView {
    private static final Image spriteHeroe = new Image("file:src/main/resources/sprites/perroIdle.png");
    private static final int frameWidth = 48;
    private static final int frameHeight = 48;
    private static final int totalFrames = 4;
    private int currentFrame = 0;
    private final ImageView heroeVista;


    public HeroeView() {
        heroeVista = new ImageView(spriteHeroe);
        heroeVista.setFitWidth(48);
        heroeVista.setFitHeight(48);
        heroeVista.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        iniciarAnimacion();
    }

    private void iniciarAnimacion() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            currentFrame = (currentFrame + 1) % totalFrames;
            int xOffset = currentFrame * frameWidth;
            heroeVista.setViewport(new Rectangle2D(xOffset, 0, frameWidth, frameHeight));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public ImageView crearVista() {
        return heroeVista;
    }
}

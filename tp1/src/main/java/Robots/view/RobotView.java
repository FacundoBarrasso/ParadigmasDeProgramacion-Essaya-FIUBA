package Robots.view;

import Robots.modelo.entidades.Robot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RobotView {
    private static final Image spriteRobot1x = new Image("file:src/main/resources/sprites/robot1x.png");
    private static final Image spriteRobot2x = new Image("file:src/main/resources/sprites/robot2x.png");
    private static final int frameWidth = 96;
    private static final int frameHeight = 96;
    private static final int totalFrames = 4;
    private int currentFrame = 0;
    private final Robot robot;
    private final ImageView robotVista;

    public RobotView(Robot robot) {
        this.robot = robot;

        if (robot.getTipoRobot() == 1) {
            robotVista = new ImageView(spriteRobot1x);
        } else {
            robotVista = new ImageView(spriteRobot2x);
        }
        robotVista.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        robotVista.setFitWidth(96);
        robotVista.setFitHeight(96);
        iniciarAnimacion();
    }

    private void iniciarAnimacion() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            currentFrame = (currentFrame + 1) % totalFrames;
            int xOffset = currentFrame * frameWidth;
            robotVista.setViewport(new Rectangle2D(xOffset, 0, frameWidth, frameHeight));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public ImageView crearVista() { return robotVista; }

}

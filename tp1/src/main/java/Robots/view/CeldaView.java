package Robots.view;

import Robots.modelo.entidades.Heroe;
import Robots.modelo.entidades.Robot;
import Robots.modelo.tablero.Celda;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class CeldaView extends StackPane {
    private static final Image fuego = new Image("file:src/main/resources/sprites/fire.gif");
    private static final Image recolectable = new Image("file:src/main/resources/sprites/diam.gif");
    private static final Background fondoCelda = new Background(new BackgroundImage(
        new Image("file:src/main/resources/fondos/fondoCelda.png"),
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        new BackgroundSize(48, 48, false, false, false, false)));

    private final Celda celda;
    private final List<RobotView> robotViews = new ArrayList<>();
    private HeroeView heroeView;

    public CeldaView(Celda celda) {
        this.celda = celda;
        setMaxSize(32, 32);
        setMinSize(32, 32);

        setBackground(fondoCelda);

        Rectangle clip = new Rectangle(32, 32);
        setClip(clip);

        actualizar();
    }

    public void setEntidades(Heroe heroe, List<Robot> robots) {
        this.heroeView = heroe != null && heroe.getCelda().equals(celda)
            ? new HeroeView() : null;

        robotViews.clear();
        for (Robot robot : robots) {
            if (robot.getCelda().equals(celda)) {
                robotViews.add(new RobotView(robot));
            }
        }
        actualizar();
    }

    public void actualizar() {
        getChildren().clear();

        ImageView imageView;
        if (celda.estaIncendiada()) {
            imageView = new ImageView(fuego);
            imageView.setTranslateY(-8);
        } else if (celda.tieneRecolectable()) {
            imageView = new ImageView(recolectable);
        } else {
            imageView = new ImageView();
        }

        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        getChildren().add(imageView);

        if (heroeView != null) {
            ImageView heroeSprite = heroeView.crearVista();
            heroeSprite.setFitWidth(32);
            heroeSprite.setFitHeight(32);
            heroeSprite.setTranslateX(4);
            heroeSprite.setTranslateY(-5);
            getChildren().add(heroeSprite);

        }
        robotViews.forEach(robotView -> {
            ImageView robotSprite = robotView.crearVista();
            robotSprite.setFitWidth(70);
            robotSprite.setFitHeight(70);
            robotSprite.setTranslateX(14);
            robotSprite.setTranslateY(-10);
            getChildren().add(robotSprite);
        });
    }

    public Celda getCeldaModelo() { return celda; }
}



package Robots.view;

import Robots.modelo.tablero.Juego;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class VentanaView {
    private final BorderPane layout;
    private final Juego juego;

    public VentanaView(BorderPane layout, Juego juego) {
        this.layout = layout;
        this.juego = juego;
    }

    public void setFondo() {
        String rutaImagen;
        int nivel = juego.getNivel();
        int a = nivel % 4;
        switch (a) {
            case 1:
                rutaImagen = "file:src/main/resources/fondos/2.png";
                break;
            case 2:
                rutaImagen = "file:src/main/resources/fondos/3.png";
                break;
            case 3:
                rutaImagen = "file:src/main/resources/fondos/4.png";
                break;
            default:
                rutaImagen = "file:src/main/resources/fondos/1.png";
                break;
        }
    
        layout.setBackground(new Background(
            new BackgroundImage(
                new Image(rutaImagen),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
            )
        ));
    }
}

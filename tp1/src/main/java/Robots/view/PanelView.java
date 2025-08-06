package Robots.view;

import Robots.modelo.tablero.Juego;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class PanelView extends HBox {
    Runnable teleportNormal;
    Runnable teleportSeguro;
    private final BotonRobots botonTeleportSeguro;

    public PanelView(Juego juego) {
        setSpacing(30);
        setAlignment(Pos.CENTER);

        BotonRobots botonTeleport = new BotonRobots("Teletransportar");
        botonTeleport.setAccion(() -> teleportNormal.run());

        this.botonTeleportSeguro = new BotonRobots("");
        botonTeleportSeguro.setAccion(() -> {teleportSeguro.run();actualizarTextoTeleportSeguro(juego);});
    
        actualizarTextoTeleportSeguro(juego);
        getChildren().addAll(botonTeleport, botonTeleportSeguro);
    }

    public void actualizarTextoTeleportSeguro(Juego juego) {
        int cantidad = juego.getHeroe().getTeletransportesSeguros();
        botonTeleportSeguro.actualizarTexto("Teletransporte Seguro: " + cantidad + " restantes");
    }

    public void setRunnable(Runnable manejarTeleportNormal, Runnable activarModoTeletransporteSeguro) {
        this.teleportNormal = manejarTeleportNormal;
        this.teleportSeguro = activarModoTeletransporteSeguro;
    }
}

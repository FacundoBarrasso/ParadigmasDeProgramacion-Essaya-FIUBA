package Robots.view;

import Robots.modelo.tablero.Direccion;
import Robots.modelo.tablero.Evento;
import Robots.modelo.tablero.Juego;
import Robots.modelo.tablero.Celda;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class RobotsController {
    private static final Map<KeyCode, Direccion> mapaDirecciones = Map.of(
            KeyCode.W, Direccion.NORTE,
            KeyCode.X, Direccion.SUR,
            KeyCode.A, Direccion.OESTE,
            KeyCode.D, Direccion.ESTE,
            KeyCode.Q, Direccion.NOROESTE,
            KeyCode.E, Direccion.NORESTE,
            KeyCode.Z, Direccion.SUROESTE,
            KeyCode.C, Direccion.SURESTE,
            KeyCode.S, Direccion.QUIETO
    );
    private final Juego juego;
    private final JuegoView juegoView;
    private final PanelView panelView;
    private final ScoreView scoreView;
    private final VentanaView ventanaView;
    private boolean esperandoTeletransporteSeguro = false;
    private final Set<KeyCode> teclasPresionadas = new HashSet<>();

    public RobotsController(Juego juego, JuegoView juegoView, PanelView panelView, ScoreView scoreView, VentanaView ventanaView) {
        this.juego = juego;
        this.juegoView = juegoView;
        this.panelView = panelView;
        this.scoreView = scoreView;
        this.ventanaView = ventanaView;
        this.juegoView.setOnCeldaClick(this::manejarClickCelda);
        this.panelView.setRunnable(this::manejarTeleportNormal, this::activarModoTeletransporteSeguro);
    }

    public void manejarTecla(KeyEvent e) { teclasPresionadas.add(e.getCode()); }

    public void iniciarLoop(Scene scene) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                ventanaView.setFondo();
                scoreView.actualizarEstado(juego.getScore(), juego.getNivel(), juego.getRecursosRestantes());
                for (KeyCode key : teclasPresionadas) {
                    Direccion dir = mapaDirecciones.get(key);
                    if (dir != null) {
                        Evento evento = juego.mover(dir);
                        juegoView.actualizar();
                        if (evento != null) manejarEvento(evento);
                    }
                }
                teclasPresionadas.clear();
            }
        }.start();
    }

    private void manejarClickCelda(CeldaView celdaView) {
        Celda celda = celdaView.getCeldaModelo();
        if (esperandoTeletransporteSeguro) {
            esperandoTeletransporteSeguro = false;
            Evento evento = juego.teletransportarSeg(celda.getFila(), celda.getColumna());
            panelView.actualizarTextoTeleportSeguro(juego);
            juegoView.actualizar();
            manejarEvento(evento);
        }
        else {
            int fila = celda.getFila();
            int columna = celda.getColumna();
            Celda origen = juego.getHeroe().getCelda();
            int df = fila - (origen.getFila());
            int dc = columna - (origen.getColumna());
            Direccion direccion = obtenerDireccion(df, dc);
            Evento evento = juego.mover(direccion);
            juegoView.actualizar();
            manejarEvento(evento);
        }
    }

    public void manejarEvento(Evento evento) {
        if (evento == Evento.VICTORIA) {
            juegoView.actualizar();
            FinPartidaView.mostrar(juego, "¡Ganaste!", "Siguiente Nivel", this::avanzarNivel);
        } else if (evento == Evento.GAME_OVER) {
            juegoView.actualizar();
            FinPartidaView.mostrar(juego, "Game Over", "Reiniciar", this::reiniciarPartida);
        }
    }

    public void reiniciarPartida() {
        juego.resetScore();
        juego.iniciarPartida(1);
        juegoView.getChildren().clear();
        juegoView.iniciarJuegoView(juego);
        juegoView.actualizar();
        panelView.actualizarTextoTeleportSeguro(juego);
    }

    public void avanzarNivel() {
        juego.avanzarNivel();
        juegoView.getChildren().clear();
        juegoView.iniciarJuegoView(juego);
        juegoView.actualizar();
        panelView.actualizarTextoTeleportSeguro(juego);
    }

    public void manejarTeleportNormal() {
        Evento evento = juego.teletransportar();
        juegoView.actualizar();
        manejarEvento(evento);
    }

    private Direccion obtenerDireccion(int df, int dc) {
        if (df < 0) {
            if (dc < 0) return Direccion.NOROESTE;
            if (dc > 0) return Direccion.NORESTE;
            return Direccion.NORTE;
        } else if (df > 0) {
            if (dc < 0) return Direccion.SUROESTE;
            if (dc > 0) return Direccion.SURESTE;
            return Direccion.SUR;
        } else {
            if (dc < 0) return Direccion.OESTE;
            if (dc > 0) return Direccion.ESTE;
            return Direccion.QUIETO;
        }
    }
    
    public void activarModoTeletransporteSeguro() { esperandoTeletransporteSeguro = true; }

}


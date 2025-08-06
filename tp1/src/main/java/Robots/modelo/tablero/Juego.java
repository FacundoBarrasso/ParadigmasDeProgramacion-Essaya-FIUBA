package Robots.modelo.tablero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;

import Robots.modelo.entidades.Heroe;
import Robots.modelo.entidades.Robot;
import Robots.modelo.entidades.Robot1x;
import Robots.modelo.entidades.Robot2x;

public class Juego {
    private static final int DIST_RECOLECTABLES = 3;
    private static final int SCORE_RECOLECTABLES = 100;
    private static final int SCORE_MOVIMIENTO = 5;
    private static final int SCORE_TELEPORT = 10;
    private static final int FACTOR_VICTORIA_ROBOT = 50;


    private final List<Robot> robots = new ArrayList<>();
    private final int filas;
    private final int columnas;
    private Heroe heroe;
    private Grilla grilla;
    private HashSet<Celda> colocadas;
    private int nivel;
    private int nRecolectables = 4;
    private int score = 0;
    

    public Juego(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
    }

    public void iniciarPartida(int nivel) {
        this.nivel = nivel;
        this.grilla = new Grilla(filas, columnas);

        Celda centro = grilla.getCentro();
        this.heroe = new Heroe(centro, grilla, nivel);
        centro.ubicar(heroe);

        robots.clear();
        nRecolectables = 4;

        colocadas = new HashSet<>();
        ubicarRecolectables(centro);
        crearRobots(nivel, Robot1x::new, centro);
        crearRobots(nivel*2, Robot2x::new, centro);
    }

    private void ubicarRecolectables(Celda centro) {
        while (colocadas.size() < nRecolectables) {
            Celda candidata = grilla.getCeldaAleatoria();

            boolean esValida = colocadas.stream().allMatch(
                    c -> grilla.distanciaEntre(c, candidata) > DIST_RECOLECTABLES
            );

            if (esValida && !candidata.estaIncendiada() && !candidata.equals(centro)) {
                candidata.setRecolectable();
                colocadas.add(candidata);
            }
        }
    }

    private void crearRobots(int cantidad, BiFunction<Celda, Grilla, Robot> creador, Celda centro) {
        while (robots.size() < cantidad) {
            Celda celda = grilla.getCeldaAleatoria();
            if (celda.equals(centro) || colocadas.contains(celda)) continue;
            Robot robot = creador.apply(celda, grilla);
            robots.add(robot);
            colocadas.add(celda);
        }
    }

    public Evento procesarTurno(Resultado resultado) {
        if (resultado == Resultado.GAME_OVER){
            return Evento.GAME_OVER;
        }
        if (resultado == Resultado.RECOLECTADO) {
            nRecolectables--;
            score += SCORE_RECOLECTABLES;
        }
        if (nRecolectables == 0) {
            score += nivel * SCORE_RECOLECTABLES;
            return Evento.VICTORIA;
        }
        for (Robot r : robots) {
            Resultado res = r.mover(heroe.getCelda());
            if (res == Resultado.GAME_OVER) {
                return Evento.GAME_OVER;
            }
        }
        robots.removeIf(Robot::estaMuerto);
        if (robots.isEmpty()) {
            score += nivel * FACTOR_VICTORIA_ROBOT;
            return Evento.VICTORIA;
        }

        score += SCORE_MOVIMIENTO;
        return Evento.EN_PROCESO;
    }

    public Evento mover(Direccion direccion) {
        if (direccion == Direccion.QUIETO) {
            return procesarTurno(Resultado.MOV_VALIDO);
        }
        Celda destino = heroe.calcularCeldaDestino(direccion);
        Resultado resJugador = heroe.mover(destino);
        return procesarTurno(resJugador);
    }

    public Evento teletransportar() {
        score -= SCORE_TELEPORT;
        return procesarTurno(heroe.teletransportar());
    }

    public Evento teletransportarSeg(int fila, int columna) {
        Resultado resJugador = heroe.teletransportarSeguro(grilla.getCelda(fila, columna));
        return procesarTurno(resJugador);
    }

    public void avanzarNivel() {
        int nuevoNivel = nivel + 1;
        iniciarPartida(nuevoNivel);
    }

    public int getNivel() { return nivel; }

    public Celda getCelda(int fila, int columna) { return grilla.getCelda(fila, columna); }

    public Heroe getHeroe() { return heroe; }

    public List<Robot> getRobots() { return robots; }

    public int getScore() { return score; }

    public int getRecursosRestantes() { return nRecolectables; }    

    public void resetScore() { score = 0; }
}

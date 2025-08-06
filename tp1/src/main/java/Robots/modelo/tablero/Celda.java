package Robots.modelo.tablero;

import Robots.modelo.entidades.Heroe;
import Robots.modelo.entidades.Robot;

public class Celda {
    int fila;
    int columna;
    private boolean incendio;
    private boolean recolectable;
    private boolean heroe;
    private Robot robot;

    public Celda(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        incendio = false;
        recolectable = false;
        robot = null;
    }

    public Resultado ubicar(Heroe h) {
        if (incendio || robot != null) {
            return Resultado.GAME_OVER;
        }
        if (recolectable) {
            recolectable = false;
            heroe = true;
            return Resultado.RECOLECTADO;
        }
        heroe = true;
        return Resultado.MOV_VALIDO;
    }

    public Resultado ubicar(Robot r) {
        if (heroe) {
            return Resultado.GAME_OVER;
        }
        if (incendio) {
            r.morir();
            return Resultado.DESTRUIDO;
        }
        if (robot != null) {
            robot.morir();
            r.morir();
            incendio = true;
            robot = null;
            return Resultado.INCENDIADO;
        }
        if (recolectable) {
            return Resultado.MOV_INVALIDO;
        }
        robot = r;
        return Resultado.MOV_VALIDO;
    }

    public int[] direccionHacia(Celda otra) {
        return new int[] {
                Integer.compare(otra.fila, this.fila),
                Integer.compare(otra.columna, this.columna)
        };
    }

    public boolean equals(Celda otra) { return fila == otra.fila && columna == otra.columna; }

    public int getFila() { return fila; }

    public int getColumna() { return columna; }

    public boolean estaIncendiada() { return incendio; }

    public boolean tieneRecolectable() { return recolectable; }

    public void borrarHeroe() { heroe = false; }

    public void borrarRobot() { robot = null; }

    public void setRecolectable() { recolectable = true; }
}

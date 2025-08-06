package Robots.modelo.entidades;

import Robots.modelo.tablero.Grilla;
import Robots.modelo.tablero.Celda;
import Robots.modelo.tablero.Resultado;

public abstract class Personaje {
    protected Grilla grilla;
    protected Celda celda;

    public Personaje(Celda celda, Grilla grilla) {
        this.grilla = grilla;
        this.celda = celda;
    }

    public Celda getCelda() {
        return celda;
    }

    public abstract Resultado mover(Celda destino);
}


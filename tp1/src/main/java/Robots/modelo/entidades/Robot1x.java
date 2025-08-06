package Robots.modelo.entidades;

import Robots.modelo.tablero.Celda;
import Robots.modelo.tablero.Grilla;

public class Robot1x extends Robot {
    public Robot1x(Celda celda, Grilla grilla) {
        super(celda, grilla);
        this.nCeldas = 1;
    }
}

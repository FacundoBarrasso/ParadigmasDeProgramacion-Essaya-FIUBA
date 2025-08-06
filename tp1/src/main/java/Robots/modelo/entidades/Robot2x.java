package Robots.modelo.entidades;

import Robots.modelo.tablero.Celda;
import Robots.modelo.tablero.Grilla;

public class Robot2x extends Robot {
    public Robot2x(Celda celda, Grilla grilla) {
        super(celda, grilla);
        this.nCeldas = 2;
    }
}
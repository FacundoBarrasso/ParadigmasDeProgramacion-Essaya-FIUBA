package Robots.modelo.entidades;

import Robots.modelo.tablero.Celda;
import Robots.modelo.tablero.Grilla;
import Robots.modelo.tablero.Resultado;

public abstract class Robot extends Personaje {
    protected int nCeldas;
    protected boolean estaVivo = true;

    public Robot(Celda celda, Grilla grilla) {
        super(celda, grilla);
    }

    public Resultado mover(Celda celdaObjetivo) {
        int[] direccion = celda.direccionHacia(celdaObjetivo);

        int nuevaFila = celda.getFila() + direccion[0] * nCeldas;
        int nuevaCol = celda.getColumna() + direccion[1] * nCeldas;
        Celda destino = grilla.getCelda(nuevaFila, nuevaCol);
        if (destino == null || destino.tieneRecolectable()) {
            destino = buscarDestinoAlternativo(celdaObjetivo);
            if (destino == null) return Resultado.MOV_INVALIDO;
        }
        Resultado res = destino.ubicar(this);
        celda.borrarRobot();
        this.celda = destino;
        return res;
    }

    private Celda buscarDestinoAlternativo(Celda objetivo) {
        Celda mejor = null;
        int mejorDistancia = Integer.MAX_VALUE;
    
        int filaActual = celda.getFila();
        int colActual = celda.getColumna();
    
        for (int df = -1; df <= 1; df++) {
            for (int dc = -1; dc <= 1; dc++) {
                // ignoramos movimiento nulo
                if (df == 0 && dc == 0) continue;
    
                int nuevaFila = filaActual + df * nCeldas;
                int nuevaCol = colActual + dc * nCeldas;
    
                Celda candidata = grilla.getCelda(nuevaFila, nuevaCol);
                if (candidata == null || candidata.tieneRecolectable()) continue;
    
                // solo consideramos celdas a nCeldas de distancia en alguna dirección
                int distancia = grilla.distanciaEntre(candidata, objetivo);
                if (distancia < mejorDistancia) {
                    mejor = candidata;
                    mejorDistancia = distancia;
                }
            }
        }
        return mejor;
    }

    public int getTipoRobot() {
        return nCeldas;
    }

    public boolean estaMuerto() {
        return !estaVivo;
    }

    public void morir() {
        estaVivo = false;
    }
}
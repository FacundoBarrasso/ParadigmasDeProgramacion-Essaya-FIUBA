package Robots.modelo.entidades;

import Robots.modelo.tablero.Celda;
import Robots.modelo.tablero.Direccion;
import Robots.modelo.tablero.Grilla;
import Robots.modelo.tablero.Resultado;

public class Heroe extends Personaje {
    private int teletransportesSeguros;

    public Heroe(Celda inicio, Grilla grilla, int nSeguros) {
        super(inicio, grilla);
        this.teletransportesSeguros = nSeguros;
    }

    public Resultado mover(Celda destino) {
        if (destino == null) return Resultado.MOV_INVALIDO;

        Resultado res = destino.ubicar(this);
        if (res == Resultado.MOV_VALIDO || res == Resultado.RECOLECTADO) {
            celda.borrarHeroe();
            celda = destino;
        }
        return res;
    }

    public Celda calcularCeldaDestino(Direccion direccion) {
        int fila = celda.getFila();
        int columna = celda.getColumna();

        switch (direccion) {
            case NORTE:
                fila--;
                break;
            case SUR:
                fila++;
                break;
            case ESTE:
                columna++;
                break;
            case OESTE:
                columna--;
                break;
            case NORESTE:
                fila--;
                columna++;
                break;
            case NOROESTE:
                fila--;
                columna--;
                break;
            case SURESTE:
                fila++;
                columna++;
                break;
            case SUROESTE:
                fila++;
                columna--;
                break;
            case QUIETO:
                break;
        }

        return grilla.getCelda(fila, columna);
    }

    public Resultado teletransportar() {
        Resultado resultado;
        Celda celda;
        do {
            celda = grilla.getCeldaAleatoria();
            resultado = celda.ubicar(this);
        } while (resultado != Resultado.MOV_VALIDO && resultado != Resultado.RECOLECTADO);
        return mover(celda);
    }

    public Resultado teletransportarSeguro(Celda destino) {
        if (teletransportesSeguros <= 0 || destino == null) return Resultado.MOV_INVALIDO;
        Resultado res = mover(destino);
        if (res == Resultado.MOV_VALIDO || res == Resultado.RECOLECTADO) {
            teletransportesSeguros -= 1;
        }
        return res;
    }

    public int getTeletransportesSeguros() {
        return teletransportesSeguros;
    }
}

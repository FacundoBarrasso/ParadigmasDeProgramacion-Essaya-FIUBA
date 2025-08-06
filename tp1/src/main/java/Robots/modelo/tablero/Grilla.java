package Robots.modelo.tablero;

public class Grilla {
    private final Celda[][] celdas;

    public Grilla(int filas, int columnas) {
        this.celdas = new Celda[filas][columnas];
        inicializarCeldas();
    }

    private void inicializarCeldas() {
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[i].length; j++) {
                celdas[i][j] = new Celda(i, j);
            }
        }
    }

    public int distanciaEntre(Celda c1, Celda c2) {
        int distFilas = c1.getFila() - c2.getFila();
        int distCol = c1.getColumna() - c2.getColumna();
        return (int) Math.sqrt(distFilas * distFilas + distCol * distCol);
    }

    public Celda getCentro() { return celdas[celdas.length / 2][celdas[0].length / 2]; }

    public Celda getCeldaAleatoria(){
        int fila = (int) (Math.random() * celdas.length);
        int col = (int) (Math.random() * celdas[0].length);
        return celdas[fila][col];
    }

    public Celda getCelda(int fila, int columna) {
        if (fila < 0 || fila >= celdas.length || columna < 0 || columna >= celdas[0].length) {
            return null;
        }
        return celdas[fila][columna];
    }
}

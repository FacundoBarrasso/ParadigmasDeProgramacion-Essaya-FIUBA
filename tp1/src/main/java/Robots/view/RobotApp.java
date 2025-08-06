package Robots.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class RobotApp extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        ConfiguracionView configuracionView = new ConfiguracionView((filas, columnas) -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(filas, columnas);
            ventanaPrincipal.start(stage);
        });
        configuracionView.mostrar(stage);
    }
}

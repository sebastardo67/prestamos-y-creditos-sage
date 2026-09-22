package main.java.org.sage.prestamos.creditos;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(
            Stage stage
    ) throws Exception {

        URL fxml =
                getClass().getResource(
                        "/resources/view/login-view.fxml"
                );

        if (fxml == null) {

            throw new RuntimeException(
                    "No se encontró login-view.fxml"
            );
        }

        FXMLLoader loader =
                new FXMLLoader(fxml);

        Scene scene =
                new Scene(
                        loader.load()
                );

        stage.setTitle(
                "Sistema de Préstamos - Inicio de sesión"
        );

        stage.setScene(scene);
        stage.setResizable(true); 
        stage.setMinWidth(600);
        stage.setMinHeight(420);
        stage.show();
    }

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}
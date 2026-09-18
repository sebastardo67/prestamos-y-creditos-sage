package main.java.org.sage.prestamos.creditos;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        URL fxml = getClass().getResource("/view/login-view.fxml");

        System.out.println("FXML encontrado: " + fxml);

        if (fxml == null) {
            throw new RuntimeException(
                "No se encontró el archivo /view/login-view.fxml"
            );
        }

        FXMLLoader loader = new FXMLLoader(fxml);

        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema de Préstamos");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
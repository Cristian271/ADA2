import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AppUI extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Holaaaaaa");
        Scene scene = new Scene(label, 500, 500);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

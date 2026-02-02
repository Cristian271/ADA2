import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usuario;
    @FXML private PasswordField password;


    private CSV csv = new CSV();

    @FXML
    private void login(ActionEvent event) {
        String user = usuario.getText();
        String pass = password.getText();

        if (csv.validar(user, pass)) {
            irAlMenu(event);
        } else {
            mostrarAlerta("Error al iniciar sesión", "Usuario o contraseña incorrectos");
        }
    }

    private void irAlMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menú");
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error al cargar", "No se puede abrir el menú principal.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
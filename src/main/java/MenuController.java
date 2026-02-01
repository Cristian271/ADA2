import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private ImageView cinta;
    @FXML
    private ImageView uadyLogo;
    @FXML
    private ImageView calificaciones;
    @FXML
    private ImageView pdf;
    @FXML
    private ImageView csvLogo;
    CSV csv = new CSV();
    List<Estudiante> alumnos = csv.read();

    public MenuController() throws FileNotFoundException {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image img = new Image(getClass().getResource("/images/cinta.jpg").toString());
        cinta.setImage(img);
        Image img2 = new Image(getClass().getResource("/images/calificaciones.png").toString());
        calificaciones.setImage(img2);
        Image img3 = new Image(getClass().getResource("/images/pdf.png").toString());
        pdf.setImage(img3);
        Image img4 = new Image(getClass().getResource("/images/csv.png").toString());
        csvLogo.setImage(img4);
        Image img5 = new Image(getClass().getResource("/images/uady.png").toString());
        uadyLogo.setImage(img5);
    }



    @FXML
    public void generarPDF(){
        PDF.generar(alumnos, "src/main/Reporte_Calificaciones.pdf");
    }

    @FXML
    public void generarCSV() {

        if (alumnos == null || alumnos.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Lista vacía", "No hay alumnos cargados en el sistema.");
            return;
        }

        boolean faltanCalificar = false;
        for (Estudiante alumno : alumnos) {
            if (alumno.getGrade() == 0) {
                faltanCalificar = true;
                break;
            }
        }

        if (!faltanCalificar) {
            csv.generarReporte(alumnos);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reporte Generado", null, "El archivo CSV se ha generado correctamente.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Pendientes de calificación", "No se puede generar el reporte: aún faltan alumnos por calificar.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void readFile() {

        for (Estudiante est : alumnos) {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Captura de Calificación");
            dialog.setHeaderText("Alumno: " + est.getFullName());
            dialog.setContentText("Introduce la calificación para la matrícula " + est.getMatricula() + ":");


            dialog.showAndWait().ifPresent(valor -> {
                try {
                    double nota = Double.parseDouble(valor);
                    est.setGrade((int) nota);
                } catch (NumberFormatException e) {
                    est.setGrade((int) 0.0);
                }
            });
        }

        mostrarAlerta(Alert.AlertType.INFORMATION, "Proceso Terminado", null, "Calificaciones capturadas en memoria.");

    }

    @FXML
    public void modificar() {
        // 1. Verificar que ya existan alumnos en memoria
        if (alumnos == null || alumnos.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin datos", null,
                    "Primero debes capturar las calificaciones para poder modificarlas.");
            return;
        }

        // Recorrer la lista para permitir la edición
        for (Estudiante est : alumnos) {
            // Calificacion actual como valor predeterminado
            String califActual = String.valueOf(est.getGrade());

            TextInputDialog dialog = new TextInputDialog(califActual);
            dialog.setTitle("Modificar Calificación");
            dialog.setHeaderText("Modificando a: " + est.getFullName());
            dialog.setContentText("Calificación previa: " + califActual + "\nIngrese la nueva calificación:");


            dialog.showAndWait().ifPresent(nuevoValor -> {
                try {
                    double nuevaNota = Double.parseDouble(nuevoValor);
                    // Solo actualizamos si el valor es distinto
                    if (nuevaNota != est.getGrade()) {
                        est.setGrade((int) nuevaNota);
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", null,
                            "El valor '" + nuevoValor + "' no es válido. No se realizaron cambios.");
                }
            });
        }

        mostrarAlerta(Alert.AlertType.INFORMATION, "Actualización Completa", null,
                "Se han revisado y modificado las calificaciones.");
    }

    public void exit(){
        System.exit(0);
    }

}

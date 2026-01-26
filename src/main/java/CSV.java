import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    private BufferedReader lector; // Lee el archivo
    private String linea; // recibe la linea de cada fila
    private String partes[]; // almacena cada linea leida
    public static final String RUTA = "src/main/ListaDeAlumnos.csv"; // ruta física
    public static final String RUTA_DEST = "src/main/Reporte_Califaciones.csv";
    public static final String RUTA_USR = "src/main/ListaDeUsuarios.csv"; // ruta física

    // Leer estudiantes
    public List<Estudiante> read() throws FileNotFoundException {
        List<Estudiante> lista = new ArrayList<>();
        try {
            lector = new BufferedReader(new FileReader(RUTA));
            while ((linea = lector.readLine()) != null) {
                partes = linea.split(",");
                if (partes.length >= 4) {
                    Estudiante e = new Estudiante(partes[0], partes[1], partes[2], partes[3]);
                    lista.add(e);
                }
            }
            lector.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
    }

    // Imprimir la última línea leída (útil para debug)
    public void print() {
        for (int i = 0; i < partes.length; i++)
            System.out.println(partes[i] + "  |  ");
    }

    // Generar reporte
    public void generarReporte(List<Estudiante> lista) {
        try (PrintWriter archivoSalida = new PrintWriter(new FileWriter(RUTA_DEST))) {
            for (Estudiante e : lista) {
                String calificacionFinal = (e.getGrade() == 0) ? "S/C" : String.valueOf(e.getGrade());
                archivoSalida.println(e.getMatricula() + ",Diseño de Software," + calificacionFinal);
            }
            JOptionPane.showMessageDialog(null, "Archivo generado con éxito.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
        }
    }

    // Validar usuario y contraseña
    public boolean validar(String usuario, String contrasena) {
        String con = "";
        for (int i = 0; i < contrasena.length(); i++) {
            char a = (char) (contrasena.charAt(i) + 2); // Encriptación simple
            con += a;
        }
        //System.out.println("Resultado de encriptamiento: " + con); // Para debug

        try {
            lector = new BufferedReader(new FileReader(RUTA_USR));
            while ((linea = lector.readLine()) != null) {
                partes = linea.split(",");
                if (partes.length >= 2) {
                    if (partes[0].trim().equals(usuario) && partes[1].trim().equals(con)) {
                        lector.close();
                        return true;
                    }
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se encontró " + RUTA_USR);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return false;
    }
}


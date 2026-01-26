public class Estudiante {
    // son los datos recibidos en el csv
    private String matricula;
    private String pApellido;
    private String mApellido;
    private String nombres;
    private int grade; //inicializado para indicar que no se ha puesto nada

    // Constructor para inicializar con los datos del CSV
    public Estudiante(String matricula, String pApellido, String mApellido, String nombres) {
        this.matricula = matricula;
        this.pApellido = pApellido;
        this.mApellido = mApellido;
        this.nombres = nombres;
    }

    // metodos get (los generé pero creo que se pueden borrar los que no se estan ocupando)
    public String getMatricula() {
        return matricula;
    }

    public String getPApellido() {
        return pApellido;
    }

    public String getMApellido() {
        return mApellido;
    }

    public String getNombres() {
        return nombres;
    }

    public int getGrade() {
        return grade;
    }

    // Metodo set para cambiar la calificación
    public void setGrade(int grade) {
        this.grade= grade;
    }

    // Metodo para mostrar el nombre completo
    public String getFullName() {
        return nombres + " " + pApellido + " " + mApellido;
    }
}
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.util.List;

public class PDF {

    public static void generar(List<Estudiante> alumnos, String rutaSalida) {
        try (PdfWriter writer = new PdfWriter(rutaSalida);
             PdfDocument pdf = new PdfDocument(writer);
             Document doc = new Document(pdf)) {

            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // Título
            doc.add(new Paragraph("Reporte de Calificaciones").setFont(bold).setFontSize(14));

            doc.add(new Paragraph(" ")); // espacio

            // 4 columnas: Matrícula, Nombre, Materia, Calificación
            Table table = new Table(4);

            // Encabezados
            table.addHeaderCell(new Cell().add(new Paragraph("Matrícula").setFont(bold)));
            table.addHeaderCell(new Cell().add(new Paragraph("Nombre").setFont(bold)));
            table.addHeaderCell(new Cell().add(new Paragraph("Materia").setFont(bold)));
            table.addHeaderCell(new Cell().add(new Paragraph("Calificación").setFont(bold)));

            // Filas
            for (Estudiante e : alumnos) {
                table.addCell(e.getMatricula());
                table.addCell(e.getFullName());
                table.addCell("Diseño de Software");
                String calificacion = (e.getGrade() == 0) ? "S/C" : String.valueOf(e.getGrade());
                table.addCell(calificacion);
            }

            doc.add(table);

        } catch (Exception ex) {
            throw new RuntimeException("Error generando PDF: " + ex.getMessage(), ex);
        }
    }
}




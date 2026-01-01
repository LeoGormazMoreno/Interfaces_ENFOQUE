/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces_enfoque;

import com.google.protobuf.TextFormat.ParseException;
import com.mysql.cj.xdevapi.Statement;
import static interfaces_enfoque.conexionSQL.obtenerConexion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;


/**
 *
 * @author Leo
 */
public class exportarReserva {
    
    private static int contadorPDF = 1;
    
     public void exportarReporte() {
        try {
            System.out.println("=== EXPORTANDO DESDE SQL ===");

            // UBICACION JRXML DE LA PLANTILLA DE JASPER STUDIO 
            File archivoJRXML = new File("Plantilla.jrxml");
            if (!archivoJRXML.exists()) {
                JOptionPane.showMessageDialog(null,
                        "No se encuentra Plantilla.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // COMPILAMOS EL ARCHIVO
            JasperReport reporte = JasperCompileManager.compileReport(archivoJRXML.getAbsolutePath());
            System.out.println("Reporte compilado");

            // NOS CONECTAMOS A LA BASE DE DATOS
            Connection conn = conexionSQL.obtenerConexion();
            System.out.println("Conexión correcta a la base de datos");

            // INSERTAMOS DATOS EN EL REPORTE
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Titulo", "Reporte desde MySQL");
            parametros.put("Fecha", new Date());

            // RELLENAMOS EL REPORTE CON LOS DIFERENTES PARAMETROS
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, conn);

            // EXPORTAMOS A PDF
            generarPDF(print);

            // CERRAMOS SESION
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al exportar desde SQL: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MÉTODO PARA EXPORTAR PDF
    private void generarPDF(JasperPrint print) throws JRException {
        String rutaPDF = "Reporte_" + (contadorPDF++) + ".pdf";
        JasperExportManager.exportReportToPdfFile(print, rutaPDF);

        File pdfFile = new File(rutaPDF);
        if (pdfFile.exists() && pdfFile.length() > 0) {
            JOptionPane.showMessageDialog(null,
                    "✅ PDF generado exitosamente!\n\n" +
                            "📄 Archivo: " + pdfFile.getName() + "\n" +
                            "📍 Ubicación: " + pdfFile.getAbsolutePath() + "\n" +
                            "💾 Tamaño: " + pdfFile.length() + " bytes",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "El PDF se generó pero está vacío",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}


        
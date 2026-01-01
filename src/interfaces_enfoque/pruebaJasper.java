/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces_enfoque;

/**
 *
 * @author Leo
 */
public class pruebaJasper {
    
    // AJUSTA ESTO A TU MYSQL
    private static final String URL  = "jdbc:mysql://localhost:3306/jasperdb?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        try {
            // Mostrar la carpeta de trabajo actual
            System.out.println("Carpeta de trabajo (user.dir): " + System.getProperty("user.dir"));

            // 1. Conexión a MySQL
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conectado a MySQL correctamente.");

            // 2. Crear tabla e insertar datos si hace falta
            prepararDatos(conn);

            // 3. Compilar el informe jrxml desde resources
            String rutaInforme = "reportes/InformeClientes.jrxml";
            System.out.println("Cargando informe desde el classpath: " + rutaInforme);

            JasperReport report = JasperCompileManager.compileReport(
                    MainJasperMySQL.class.getClassLoader().getResourceAsStream(rutaInforme));

            // 4. Parámetros del informe
            Map<String, Object> params = new HashMap<>();
            params.put("TITULO", "Listado de clientes desde MySQL");

            // 5. Rellenar el informe usando la conexión JDBC
            JasperPrint print = JasperFillManager.fillReport(report, params, conn);

            // 6. Mostrar visor
            JasperViewer.viewReport(print, false);

            // 7. Exportar a PDF con RUTA ABSOLUTA
            //    Lo vamos a guardar en la carpeta de trabajo actual, pero sacando la ruta exacta:
            File pdfFile = new File("InformeClientesMySQL.pdf");
            String rutaAbsolutaPdf = pdfFile.getAbsolutePath();
            System.out.println("Exportando PDF a: " + rutaAbsolutaPdf);

            JasperExportManager.exportReportToPdfFile(print, rutaAbsolutaPdf);
            System.out.println("Informe generado correctamente en: " + rutaAbsolutaPdf);

            conn.close();

        } catch (Exception e) {
            System.out.println("ERROR generando el informe:");
            e.printStackTrace();
        }
    }

    /**
     * Crea la tabla CLIENTES si no existe e inserta algunos registros de ejemplo
     * si la tabla está vacía.
     */
    private static void prepararDatos(Connection conn) throws SQLException {
        Statement st = conn.createStatement();

        // Crear tabla si no existe
        String createTable = "CREATE TABLE IF NOT EXISTS clientes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "email VARCHAR(150) NOT NULL" +
                ")";
        st.executeUpdate(createTable);

        // Comprobar si hay datos
        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM clientes");
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();

        if (count == 0) {
            System.out.println("Insertando datos de ejemplo en la tabla CLIENTES...");
            st.executeUpdate("INSERT INTO clientes (nombre, email) VALUES " +
                    "('Pepe', 'pepe@example.com')," +
                    "('Ana', 'ana@example.com')," +
                    "('Luis', 'luis@example.com')");
        } else {
            System.out.println("La tabla CLIENTES ya contiene datos (" + count + " filas).");
        }

        st.close();
    }
}

}

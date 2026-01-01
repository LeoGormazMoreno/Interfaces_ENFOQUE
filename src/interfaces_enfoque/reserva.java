/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces_enfoque;

import static interfaces_enfoque.conexionSQL.obtenerConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Leo
 */
public class reserva {
    
    public int insertar(String NumExpediente,java.util.Date FechaEntrada, int TiempoEstancia, String NombreCliente,String ApellidosCliente, String DNI,
                        String DatosFacturacion,String NumIdentificador, String DireccionVivienda,int M2,int NumHabitaciones, int NumBanios, float precio) {
    String sql = """
                 INSERT INTO reservas (NumExpediente,FechaEntrada,TiempoEstancia,NombreCliente,ApellidosCliente,DNI,
                                         DatosFacturacion,NumIdentificador,DireccionVivienda,M2,NumHabitaciones,NumBanios,precio) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)""";
    
    
     try { Connection connection = obtenerConexion();
          PreparedStatement ps = connection.prepareStatement(sql); 
        
        ps.setString(1, NumExpediente);
        ps.setDate(2, new java.sql.Date(FechaEntrada.getTime()));
        ps.setInt(3, TiempoEstancia);
        ps.setString(4, NombreCliente);
        ps.setString(5, ApellidosCliente);
        ps.setString(6, DNI);
        ps.setString(7, DatosFacturacion);
        ps.setString(8, NumIdentificador);
        ps.setString(9, DireccionVivienda);
        ps.setInt(10, M2);
        ps.setObject(11, NumHabitaciones);
        ps.setObject(12, NumBanios);
        ps.setBigDecimal(13, new java.math.BigDecimal(String.format(java.util.Locale.US, "%.2f", precio)));

        return ps.executeUpdate();

    } catch (java.sql.SQLException e) {
        String sqlState = e.getSQLState();
        int code = e.getErrorCode();
        String msg = e.getMessage() == null ? "" : e.getMessage();

        StringBuilder userHint = new StringBuilder("Error SQL (" + sqlState + "/" + code + "). ");

        // 23000: integridad referencial/única (PK/FK/UNIQUE)
        if ("23000".equals(sqlState)) {
            if (msg.contains("for key 'PRIMARY'")) {
                userHint.append("Reserva duplicada: ya existe ese expediente");
            } else if (msg.contains("reserva_cliente_1")) {
                userHint.append("El cliente no existe (id_cliente inválido). ");
            } else if (msg.contains("reserva_habitación_1") || msg.contains("reserva_habitaci")) {
                userHint.append("La habitación no existe (num_habitacion inválido). ");
            } else if (msg.toLowerCase().contains("foreign key")) {
                userHint.append("Alguna clave foránea no existe (cliente/habitación). ");
            } else {
                userHint.append("Violación de restricción (PK/FK/UNIQUE). ");
            }
        }
        // 22001: dato demasiado largo / fuera de rango
        else if ("22001".equals(sqlState)) {
            userHint.append("Valor fuera de rango/tamaño (revisa precio float(5,2) o longitudes). ");
        }
        // 22007/22008: fecha/hora inválida
        else if ("22007".equals(sqlState) || "22008".equals(sqlState)) {
            userHint.append("Fecha inválida o nula (revisa 'Desde' y 'Hasta'). ");
        }
        // HY000 + data truncated: problema de formato numérico/decimal
        else if ("HY000".equals(sqlState) && msg.toLowerCase().contains("data truncated")) {
            userHint.append("Dato truncado: comprueba formato numérico (usa punto decimal). ");
        } else {
            userHint.append("Consulta detalles en la consola. ");
        }

        // Log completo del stack y cadena de nextExceptions
        e.printStackTrace();
        for (java.sql.SQLException next = e.getNextException(); next != null; next = next.getNextException()) {
            next.printStackTrace();
        }

        throw new RuntimeException(
            "No se pudo insertar la reserva. " + userHint + "Mensaje DB: " + msg, e
        );
    }
    }
}

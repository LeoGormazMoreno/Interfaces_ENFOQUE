/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces_enfoque;

import static interfaces_enfoque.conexionSQL.obtenerConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

/**
 *
 * @author Leo
 */
public class vivienda {
    
   public int insertar(String numIdentificador, String direccion, String m2, Object numHab, Object numBan, float precio) {
    String sql = "INSERT INTO vivienda (NumIdentificador, direccion, m2, NumHabitaciones, NumBanios, precio) VALUES (?,?,?,?,?,?)";
    
    try { Connection connection = obtenerConexion();
          PreparedStatement ps = connection.prepareStatement(sql); 
        
        ps.setString(1, numIdentificador);
        ps.setString(2, direccion);
        ps.setString(3, m2);
        ps.setObject(4, numHab);
        ps.setObject(5, numBan);
        ps.setBigDecimal(6, new java.math.BigDecimal(String.format(java.util.Locale.US, "%.2f", precio)));
       
        

        return ps.executeUpdate();

    } catch (java.sql.SQLException e) {
        String sqlState = e.getSQLState();
        int code = e.getErrorCode();
        String msg = e.getMessage() == null ? "" : e.getMessage();

        StringBuilder userHint = new StringBuilder();

        // 23000: integridad referencial/única (PK/FK/UNIQUE)
        if ("23000".equals(sqlState)) {
            if (msg.contains("for key 'PRIMARY'")) {
                userHint.append("Ese número identificador ya existe");
            } 
        }
       

        // Log completo del stack y cadena de nextExceptions
        e.printStackTrace();
        for (java.sql.SQLException next = e.getNextException(); next != null; next = next.getNextException()) {
            next.printStackTrace();
        }

        throw new RuntimeException(
            "No se pudo insertar la vivienda. " + userHint);
    }
    }
  
    
    
}

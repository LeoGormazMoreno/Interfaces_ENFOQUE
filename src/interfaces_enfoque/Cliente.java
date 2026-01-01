/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces_enfoque;

import static interfaces_enfoque.conexionSQL.obtenerConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Leo
 */
public class Cliente {
    
    // MÉTODO PARA INSERTAR CLIENTES EN NUESTRA BD
    public int insertar(String nombre, String apellidos, String dni, Date fechaNac, String datosFact) {
    String sql = "INSERT INTO cliente (Nombre, Apellidos, DNI, FechaNacimiento, datosFactura) VALUES (?,?,?,?,?)";
    
    // OBTENEMOS CONEXIÓN
    try { Connection connection = obtenerConexion();
          PreparedStatement ps = connection.prepareStatement(sql); 
     
    // ASIGNAMOS VALORES A LOS ? DE LA SENTENCIA SQL      
        ps.setString(1, nombre);
        ps.setString(2, apellidos);
        ps.setString(3, dni);
        ps.setDate(4, new java.sql.Date(fechaNac.getTime()));
        ps.setString(5, datosFact);
       
        

        return ps.executeUpdate();

    } catch (java.sql.SQLException e) {
        String sqlState = e.getSQLState();
        int code = e.getErrorCode();
        String msg = e.getMessage() == null ? "" : e.getMessage();

        StringBuilder userHint = new StringBuilder();

        // 23000: integridad referencial/única (PK/FK/UNIQUE)
        if ("23000".equals(sqlState)) {
            if (msg.contains("for key 'PRIMARY'")) {
                userHint.append("Ya existe ese DNI");
            } 
        }
       

        // Log completo del stack y cadena de nextExceptions
        e.printStackTrace();
        for (java.sql.SQLException next = e.getNextException(); next != null; next = next.getNextException()) {
            next.printStackTrace();
        }

        throw new RuntimeException(
            "No se pudo insertar el cliente. " + userHint);
    }
    }
  
    }

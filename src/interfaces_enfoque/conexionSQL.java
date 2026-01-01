/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces_enfoque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author Leo
 */
public class conexionSQL {
    
    public static Connection obtenerConexion() throws SQLException {
        String BD = "smartocupation";
        String USUARIO = "root";
        String PASS = "";
        String HOST = "localhost";
        Calendar now = Calendar.getInstance();
        TimeZone zonahoraria = now.getTimeZone();

        return DriverManager.getConnection(
            "jdbc:mysql://" + HOST + "/" + BD +
            "?useLegacyDatetimeCode=false&serverTimezone=" + zonahoraria.getID(),
            USUARIO, PASS
        );
    }
}

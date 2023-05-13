/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Arlindo Halar
 */
public class Modulo {
    
    public static Connection conector(){
        
        Connection conexao = null;
        
        String driver = "com.mysql.jdbc.Driver";
        
        String url = "jdbc:mysql://localhost:3306/reserva";
        String user = "root";
        String pass = "";
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, pass);
            return conexao;
        } catch (Exception e) {
        
            return null;
        }
    
}
    
}

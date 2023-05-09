/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *

 */
public class MyConnection {
    private static MyConnection Test;
    private static MyConnection data;

    public static PreparedStatement prepareStatement(String requete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String url = "jdbc:mysql://127.0.0.1:3306/wassalnidb";
    public String login="root";
    public String pwd="";
    public Connection cn;
    
    public MyConnection() {
        
        try {
            cn = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connextion etablie !");
        } catch (SQLException ex) {
            System.out.println("Erreur de connextion");
            System.out.println(ex.getMessage());
        }
    }
    
    public static MyConnection getInstance ()
    {
        if (data==null)
           data = new MyConnection();
        return data ;
    }
    
     public Connection getCnx() {
        return cn;
    }
      
     public static MyConnection getTest() {
        if (Test == null) {
            Test = new MyConnection();
        }
        return Test;
    }
    public void setCn(Connection cnx) {
        this.cn = cn;
    }
    
   
    
}

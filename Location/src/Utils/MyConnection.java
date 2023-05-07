/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
private String login="root";
      private String password="";
      private Connection myconn;
   private static MyConnection instance;
       private String url="jdbc:mysql://localhost:3306/Wassalnidb";

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
       

    public MyConnection() {
        try{
        myconn= DriverManager.getConnection(url,login,password);
       System.out.println("reussi!!");
            
        }
        catch(SQLException ex){
                 System.out.println(ex.getMessage());
                    }
    }
       
    public Connection getConnection(){
        return myconn;
    }
     public static MyConnection getInstance() {
         if(instance == null)
         {
             instance = new MyConnection();
         }
         
       
   return instance;
     }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIServices.Wassim;

import PIClass.Event;
import PIClass.Participation;
import PIUtils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 

 */
public class ParticipationServices {
    
     Connection cnx = MyConnection.getInstance().getCnx();
         public void ajouterParticipation(Participation p) {
    try {
        String requete = "INSERT INTO event id_client VALUES (?)";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, p.getId_client());
        pst.executeUpdate();

        System.out.println("Participation ajoutée !");
    } catch (SQLException ex) {
        if (ex.getMessage().contains("Duplicata")) {
            System.out.println("Participation existe déjà !");
        } else {
            System.out.println(ex.getMessage());
        }
    }
}

         
         
      public ObservableList<Event> getOne(int id ) throws SQLException {
        String req = "select p.id_event,p.date,p.description,s.category,p.nom,p.image from event p where p.id_client = ?   ";
         PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, id);
        pst.executeUpdate();
        ObservableList<Event> list=FXCollections.observableArrayList();
        try {
           Statement st = cnx.createStatement();
           
            ResultSet rst = st.executeQuery(req);
            
           while(rst.next()){
               
              Event d=new Event(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
               list.add(d);
           }

        } catch (SQLException ex) {
            Logger.getLogger(EventServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
     
    }
			
			


    
}


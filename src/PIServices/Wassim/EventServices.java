/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIServices.Wassim;

import PIClass.Event;
import PIUtils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 21651
 */
public class EventServices {
     Connection cnx = MyConnection.getInstance().getCnx();

    public List<String> getAllType() {
        List<String> list = new ArrayList<String>();
        try {
            String requetee = "SELECT category FROM type";
            PreparedStatement pst = cnx.prepareStatement(requetee);
            ResultSet rs = pst.executeQuery();
            System.out.println(rs.toString());

            while (rs.next()) {
                list.add(rs.getString("category"));
            }

            return list;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public void ajouterEvent(Event e) {
       
        try {
            int id = 0;

            String requete = "SELECT id FROM type  WHERE category=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, e.getType());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }
            String requetee = "INSERT INTO `event` (`date`, `description`, `type`, `nom`, `image`) VALUES (?,?,?,?,?)";
            PreparedStatement pstt = cnx.prepareStatement(requetee);
            pstt.setString(1,e.getDate());
      

         
            pstt.setString(2,e.getDescription());
              pstt.setInt(3, id);
            pstt.setString(4,e.getNom());
             pstt.setString(5,e.getImage());
            System.out.println(e.getDate());
      
 System.out.println(e.getDescription());
           
            System.out.println(e.getType());
            System.out.println(e.getNom());

    

            pstt.executeUpdate();

            System.out.println("event ajouté !");
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicata")) {
                System.out.println("event existe deja!");
            } else {
                System.out.println(ex.getMessage());
            }
        }
 
       
    }
 
    public ObservableList<Event> getAll() {
        String req = "select p.id_event,p.date,p.description,s.category,p.nom,p.image from event p  inner join type s on p.type=s.id  ";

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
 
   
     public ObservableList<Event> RECHERCHE(String nom ) {
     
  
         ObservableList<Event> Event = FXCollections.observableArrayList();
     
        String req = "select p.date,p.description,p.type,p.nom,p.image from event p  inner join type s on p.type=s.id where p.nom LIKE '" + nom + "%'  ";
       
        try {
            Statement st = cnx.createStatement();
            ResultSet rst = st.executeQuery(req);
            
            while (rst.next()){
                Event s= new Event();
                
                  s.setNom(rst.getString("nom"));
               
                 s.setDesc(rst.getString("description"));
               s.setDate(rst.getString("date"));
              s.setType(rst.getString("type"));
                 s.setImage(rst.getString("image"));   
               
                Event.add(s);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EventServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Event);
     }
public void deleteEvent(Event t) {
     try {
        String requete = "DELETE FROM event WHERE id=?";
        PreparedStatement pst = MyConnection.getInstance().getCnx()
                .prepareStatement(requete);
        System.out.println("ID de l'événement à supprimer : " + t.getId()); // Ajout d'une instruction de débogage
        pst.setInt(1, t.getId());
        pst.executeUpdate();
        System.out.println("L'événement a été supprimé");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
 public ObservableList<Event> TriEventAs() {
     
  
         ObservableList<Event> cat = FXCollections.observableArrayList();
        String req = "SELECT * FROM event ORDER BY nom ASC";
        try {
            Statement st = cnx.createStatement();
            ResultSet resultat = st.executeQuery(req);
            
            while (resultat.next()){
                Event f= new Event();
                
                  f.setNom(resultat.getString("nom"));
               
                 f.setDesc(resultat.getString("description"));
               f.setDate(resultat.getString("date"));
              f.setType(resultat.getString("type"));
                 f.setImage(resultat.getString("image")); 
              
            
             
               
                cat.add(f);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cat);
     }
   
      /* public void updateProduit(int id,String date,String description,String nom,String image) {
         try {
           String requete = "UPDATE event SET date=?, description=?, nom=?, image=? WHERE id=?";
            
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1,date);
             pst.setString(2, description);
         
            pst.setString(3, nom);
              pst.setString(4, image);
            pst.setInt(5,id);
     
            
            pst.executeUpdate();
            System.out.println("event modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
   */ 
}

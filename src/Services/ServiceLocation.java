/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Voiture_location;
import Entities.Location; 
import Services.Iservice;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Utils.MyConnection;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 *
 * @author bahar
 */
public class ServiceLocation implements Iservice<Location>{
    Connection cnx;
    PreparedStatement ste;
    
     public ServiceLocation(){
        this.cnx = MyConnection.getInstance().getConnection();
    }
     
      
     public int ajouter(Location rental) {
    int rentalId = 0;

    try {
        String query = "SELECT prix_jour FROM voiture_location WHERE id_voiture = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, rental.getId_voiture());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int dailyPrice = rs.getInt("prix_jour");

            LocalDate start = LocalDate.parse(rental.getDate_debut());
            LocalDate end = LocalDate.parse(rental.getDate_fin());
            int rentalDays = (int) ChronoUnit.DAYS.between(start, end);
            int rentalPrice = dailyPrice * rentalDays;

            String insertQuery = "INSERT INTO location (date_debut, date_fin, prix_location, id_voiture, matricule, modele, image_voiture) "
                    + "SELECT ?, ?, ?, id_voiture, matricule, modele, image_voiture FROM voiture_location WHERE id_voiture = ?";
pstmt = cnx.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
pstmt.setString(1, rental.getDate_debut());
pstmt.setString(2, rental.getDate_fin());
pstmt.setInt(3, rentalPrice);
pstmt.setInt(4, rental.getId_voiture());
pstmt.executeUpdate();


            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                rentalId = generatedKeys.getInt(1);
                System.out.println("Rental added successfully. ID: " + rentalId);
            }
        } else {
            System.out.println("Could not retrieve daily price for car " + rental.getId_voiture());
        }
    } catch (SQLException ex) {
        System.out.println("Error adding rental.");
        System.out.println(ex.getMessage());
    }

    return rentalId;
}







     

@Override
public void modifier(Location a) {
    try {
        // Get the daily price of the car and calculate the rental price
        int prix_jour = 0;
        int numberOfDays = 0;
        String query = "SELECT prix_jour FROM voiture_location WHERE id_voiture = ?";
        PreparedStatement pstmt = cnx.prepareStatement(query);
        pstmt.setInt(1, a.getId_voiture());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            prix_jour = rs.getInt("prix_jour");
        }
        LocalDate debut = LocalDate.parse(a.getDate_debut());
        LocalDate fin = LocalDate.parse(a.getDate_fin());
        numberOfDays = (int) ChronoUnit.DAYS.between(debut, fin);
        int prix_location = prix_jour * numberOfDays;

        // Update the rental data in the "location" table
        String req = "UPDATE location "
                   + "INNER JOIN voiture_location ON location.id_voiture = voiture_location.id_voiture "
                   + "SET location.date_debut = ?, "
                   + "location.date_fin = ?, "
                   + "location.prix_location = ?, "
                   + "location.id_voiture = ?, "
                   + "location.matricule = voiture_location.matricule, "
                   + "location.modele = voiture_location.modele, "
                   + "location.image_voiture = voiture_location.image_voiture "
                   + "WHERE location.id_location = ?";
        PreparedStatement st1 = cnx.prepareStatement(req);
        st1.setString(1, a.getDate_debut());
        st1.setString(2, a.getDate_fin());
        st1.setInt(3, prix_location);
        st1.setInt(4, a.getId_voiture());
        st1.setInt(5, a.getId_location());
        st1.executeUpdate();
        System.out.println("Location modifié");
    } catch (SQLException ex) {
        System.out.println("Probleme");
        System.out.println(ex.getMessage());
    }
}




@Override
    public void supprimer(Location a) {
        
         String requete = "DELETE FROM location WHERE id_location= ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, a.getId_location());
            pst.executeUpdate();
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
        System.out.println("Location supprimée!");
        }
    
   

@Override
    public List<Location> afficher() {
      
        
        
          List<Location> list = new ArrayList<>();
    try {
        String requete = "SELECT * FROM location";
        ste = cnx.prepareStatement(requete);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            Location v = new Location();
            v.setId_location(rs.getInt(1));
            v.setDate_debut(rs.getString(2));
            v.setDate_fin(rs.getString(3));
            v.setPrix_location(rs.getInt(4));
            v.setId_voiture(rs.getInt(5));
            
            v.setMatricule(rs.getString(6));
            v.setModele(rs.getString(7));
            
            
            // yekhou l image ml base
            Blob blob = rs.getBlob(8);
            if (blob != null) {
                InputStream is = blob.getBinaryStream();
                Image image = new Image(is);
                v.setImage_voiture(new ImageView(image));
            }

            list.add(v);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return list;
      }
    
  
    
    


}

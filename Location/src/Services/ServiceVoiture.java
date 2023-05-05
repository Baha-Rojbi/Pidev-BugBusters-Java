/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Location;
import Entities.Voiture_location;
import Utils.MyConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 *
 * @author bahar
 */
public class ServiceVoiture implements Iservice<Voiture_location> {
      Connection cnx;
    PreparedStatement ste;
    
     public ServiceVoiture(){
        this.cnx = MyConnection.getInstance().getConnection();
    }
 ////////////////////////////////////    
     @Override
public int ajouter(Voiture_location voiture) {
    int result = 0;
    try {
        String sql = "INSERT INTO voiture_location (matricule, modele, carte_grise, prix_jour, image_voiture) VALUES (?, ?, ?, ?, ?)";
        ste = cnx.prepareStatement(sql);
        ste.setString(1, voiture.getMatricule());
        ste.setString(2, voiture.getModele());
        ste.setString(3, voiture.getCarte_grise());
        ste.setInt(4, voiture.getPrix_jour());
        ste.setBinaryStream(5, convertImageToStream(voiture.getImage_voiture()));
        result = ste.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return result;
}

private InputStream convertImageToStream(ImageView imageView) {
    InputStream inputStream = null;
    try {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", outputStream);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return inputStream;
}

/////////////////////////////////////////
@Override
public List<Voiture_location> afficher() {
    List<Voiture_location> list = new ArrayList<>();
    try {
        String requete = "SELECT * FROM voiture_location";
        ste = cnx.prepareStatement(requete);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            Voiture_location v = new Voiture_location();
            v.setId_voiture(rs.getInt(1));
            v.setMatricule(rs.getString(2));
            v.setModele(rs.getString(3));
            v.setCarte_grise(rs.getString(4));
            v.setPrix_jour(rs.getInt(5));
            
            // yekhou l image ml base
            Blob blob = rs.getBlob(6);
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

///////////////////////////////////////////////////
    @Override
public void supprimer(Voiture_location voiture) {
    try {
        String requete = "DELETE FROM voiture_location WHERE id_voiture = ?";
        ste = cnx.prepareStatement(requete);
        ste.setInt(1, voiture.getId_voiture());
        ste.executeUpdate();
        System.out.println("Voiture supprimée avec succès!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

////////////////////////////////////////////////
   @Override
public void modifier(Voiture_location voiture) {
    try {
        String requete = "UPDATE voiture_location SET matricule = ?, modele = ?, carte_grise = ?, prix_jour = ?, image_voiture = ? WHERE id_voiture = ?";
        ste = cnx.prepareStatement(requete);
        ste.setString(1, voiture.getMatricule());
        ste.setString(2, voiture.getModele());
        ste.setString(3, voiture.getCarte_grise());
        ste.setInt(4, voiture.getPrix_jour());
        ste.setBinaryStream(5, convertImageToStream(voiture.getImage_voiture()));
        ste.setInt(6, voiture.getId_voiture());
        ste.executeUpdate();
        System.out.println("Voiture modifiée avec succès!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

//////////////////////////////////////////////////////////
  public List<Voiture_location> rechercher(String modele) {
    List<Voiture_location> voitures = new ArrayList<>();
    try {
        String requete = "SELECT * FROM voiture_location WHERE modele LIKE ?";
        PreparedStatement st1 = cnx.prepareStatement(requete);
        st1.setString(1, "%" + modele + "%");
        ResultSet rs = st1.executeQuery();

        while (rs.next()) {
            Voiture_location voiture = new Voiture_location();
            voiture.setId_voiture(rs.getInt(1));
            voiture.setMatricule(rs.getString(2));
            voiture.setModele(rs.getString(3));
            voiture.setCarte_grise(rs.getString(4));
            voiture.setPrix_jour(rs.getInt(5));

            // Get the image from the result set
            Blob blob = rs.getBlob(6);
            if (blob != null) {
                InputStream is = blob.getBinaryStream();
                Image image = new Image(is);
                voiture.setImage_voiture(new ImageView(image));
            }
            voitures.add(voiture);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return voitures;
}




    

    
    


    
    
    
}

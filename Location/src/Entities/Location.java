/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author bahar
 */
public class Location {
    private int id_location;
    private String date_debut;
    private String date_fin;
    private int prix_location;
    private int id_voiture;
    private String matricule;
    private String modele;
     private ImageView image_voiture;

    public Location() {
    }
     public Location(String date_debut, String date_fin, int id_voiture) {
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_voiture = id_voiture;
        
    }
     
     public Location(String date_debut, String date_fin, int id_voiture, String matricule, String modele,ImageView image_voiture) {
    this.date_debut = date_debut;
    this.date_fin = date_fin;
    this.id_voiture = id_voiture;
    this.matricule = matricule;
    this.modele = modele;
    this.image_voiture=image_voiture;
}

    public Location(String date_debut, String date_fin, int prix_location, int id_voiture, String matricule, String modele,ImageView image_voiture) {
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix_location = prix_location;
        this.id_voiture = id_voiture;
        this.matricule = matricule;
        this.modele = modele;
         this.image_voiture=image_voiture;
    }


   
    

    public Location(int id_location, String date_debut, String date_fin, int id_voiture) {
        this.id_location = id_location;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_voiture = id_voiture;
    }

   

    
    

   

    @Override
    public String toString() {
        return "Location{" + "id_location=" + id_location + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", prix_location=" + prix_location + ", id_voiture=" + id_voiture + ", matricule=" + matricule + ", modele=" + modele + '}';
    }

    public ImageView getImage_voiture() {
        return image_voiture;
    }

    public void setImage_voiture(ImageView image_voiture) {
        this.image_voiture = image_voiture;
    }

    public int getId_location() {
        return id_location;
    }

    public void setId_location(int id_location) {
        this.id_location = id_location;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public int getPrix_location() {
        return prix_location;
    }

    public void setPrix_location(int prix_location) {
        this.prix_location = prix_location;
    }

    public int getId_voiture() {
        return id_voiture;
    }

    public void setId_voiture(int id_voiture) {
        this.id_voiture = id_voiture;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }
    
    
}

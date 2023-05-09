/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIClass;
         
         

/**
 *
 * @author 21651
 */
public class Event {
    
    private int id;
    private String date;
    private String description;
    private String type;
    private String nom;
 private String image;
    public Event(int id, String date, String description, String nom) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public Event(String date, String description, String type, String nom, String image) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.nom = nom;
        this.image = image;
    }

    public Event(int id, String date, String description, String type, String nom, String image) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.type = type;
        this.nom = nom;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public Event() {
    }

    public Event(int id, String date, String description, String type, String nom) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.type = type;
        this.nom = nom;
    }

    public Event(String date, String description, String type, String nom) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", date=" + date + ", description=" + description + ", type=" + type + ", nom=" + nom + '}';
    }

    public Event(String date, String description, String nom) {
        this.date = date;
        this.description = description;
        this.nom = nom;
    }

   

   
   
}


import Entities.Location;
import Services.ServiceLocation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahar
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        
        
        Location l = new Location(47,"2023-01-21", "2023-02-28", 28);
       
        ServiceLocation Service1 = new ServiceLocation();
       //System.out.println( Service1.ajouter(l));
        //Service1.supprimer(l);
        Service1.modifier(l);
       //System.out.println(Service1.afficher());
       
       //Voiture_location v = new Voiture_location("98TUN1060","peugeot 106","1438",54);
       //ServiceVoiture Service = new ServiceVoiture();
      //System.out.println( Service.ajouter(v));
       //System.out.println(Service.afficher());
        //System.out.println(Service.rechercher("k"));
    }
    
}

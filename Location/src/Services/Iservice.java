/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

/**
 *
 * @author bahar
 */

import java.util.List;
import Entities.Location;
import Entities.Voiture_location;

import java.util.List;


public interface Iservice< A> {
    public int ajouter(A a); //cat ph
    public void supprimer(A a);//cat ph
    public void modifier(A a);//ph
    public List<A> afficher();//cat ph
}


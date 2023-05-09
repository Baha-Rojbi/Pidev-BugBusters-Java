/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIServices;

import java.util.List;

/**
 *
 * @author ASUS
 * @param <T>
 */
public interface Iservice<T> {
    public int ajouter(T t); //cat ph
    public void supprimer(T t);//cat ph
    public void modifier(T t);//ph
    public List<T> afficher();//cat ph
}

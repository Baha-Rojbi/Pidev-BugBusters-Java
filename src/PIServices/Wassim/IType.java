/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIServices.Wassim;

import PIClass.Type;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author 21651
 */
public interface IType <T> {
   public void ajouter(T t);
     public void supprimer(T t);
      public void update(int id,String category,String description);
     public List <T> display();
     public   ObservableList<T> getAll() ;  
      public ArrayList<Type> getTypesByCategoryOrID(String category);
}

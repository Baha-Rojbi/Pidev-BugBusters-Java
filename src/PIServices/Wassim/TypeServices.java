/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIServices.Wassim;

import PIClass.Type;
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
public class TypeServices implements IType<Type> {
    Connection cnx=MyConnection.getInstance().getCnx();
   
    @Override
    public void ajouter(Type t) {
       try{
            String req="INSERT INTO  type(category,description)"
                    +"VALUES(?,?)";
            PreparedStatement pst=cnx.prepareStatement(req);
            pst.setString(1,t.getCategory());
              pst.setString(2,t.getDescription());
               pst.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(TypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
    public void supprimer(Type t) {
        try {
            String requete = "DELETE FROM type WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("type event supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }

      @Override
    public void update(int id,String category,String description) {
         try {
            String requete = "UPDATE type SET category=?, description=?  WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1,category);
                pst.setString(2,description);
            pst.setInt(3,id);
     
            
            pst.executeUpdate();
            System.out.println("type modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    @Override
    public List<Type> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
   public  ObservableList<Type> getAll() {
  
         ObservableList<Type> c = FXCollections.observableArrayList();
        String req = "SELECT * FROM type";
        try {
            Statement st = cnx.createStatement();
            ResultSet rst = st.executeQuery(req);
            
            while (rst.next()){
                Type s= new Type();
                s.setId(rst.getInt("id"));
       
                s.setCategory(rst.getString("category"));
                s.setDescription(rst.getString("description"));
                c.add(s);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (c);
    }
     @Override
 public ArrayList<Type> getTypesByCategoryOrID(String category){
         ArrayList<Type> res = new ArrayList<>();
        try {
            String requete = "SELECT * FROM Type WHERE category LIKE ? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, '%'+category+'%');
          
            ResultSet rs;
            rs = pst.executeQuery();
            
            while(rs.next()){
                  Type temp = new Type(rs.getInt(1),rs.getString(2),rs.getString(3));
                res.add(temp);
            }
           return res;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return res;
        }
    }
 
     public ObservableList<Type> TriTypeAs() {
     
  
         ObservableList<Type> cat = FXCollections.observableArrayList();
        String req = "SELECT * FROM type ORDER BY category ASC";
        try {
            Statement st = cnx.createStatement();
            ResultSet resultat = st.executeQuery(req);
            
            while (resultat.next()){
                Type f= new Type();
                
                
              
                f.setId(resultat.getInt("id"));
               f.setCategory(resultat.getString("category"));
              f.setDescription(resultat.getString("description"));
               
                cat.add(f);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cat);
     }
   
    
}

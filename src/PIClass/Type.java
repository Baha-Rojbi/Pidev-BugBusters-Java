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
public class Type {

    public Type(int id, String category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }

    private int id;
    private String category;
  private String description;
    public Type(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public Type(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public Type() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    

    @Override
    public String toString() {
        return "TypeEvent{" + "id=" + id + ", category=" + category + '}';
    }

    public Type(String category) {
        this.category = category;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIClass;

/**
 *
 
 */
public class Participation {
    
    private int id_part;
    private int id_client;
    private int id_event;

    public Participation() {
    }
    
    

    public Participation(int id_part, int id_client, int id_event) {
        this.id_part = id_part;
        this.id_client = id_client;
        this.id_event = id_event;
    }

    public int getId_part() {
        return id_part;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_part(int id_part) {
        this.id_part = id_part;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public Participation(int id_client, int id_event) {
        this.id_client = id_client;
        this.id_event = id_event;
    }
  
    
    
}

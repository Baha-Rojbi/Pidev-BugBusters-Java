/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Entities.Location;
import Entities.Voiture_location;
import Services.ServiceLocation;
import Services.ServiceVoiture;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author bahar
 */
public class ListeLocationController implements Initializable {
Connection cnx;
    PreparedStatement ste;
    ServiceLocation sl1= new ServiceLocation();
    ObservableList<Location> ls1 = FXCollections.observableArrayList();
    @FXML
    private AnchorPane brand;
    @FXML
    private TableView<Location> TV;
    @FXML
    private TableColumn<Location, String> colDebut;
    @FXML
    private TableColumn<Location, String> colFin;
    @FXML
    private TableColumn<Location, String> colModele;
    @FXML
    private TableColumn<Location, String> colMatricule;
   
    @FXML
    private TableColumn<Location, Integer> colPrix;
    @FXML
    private TableColumn<Location, Image> colImage;
    @FXML
    private Label labeImage;
    @FXML
    private Pane pane_m;
    @FXML
    private Button to_profile;
    @FXML
    private Button to_panier;
    @FXML
    private Button Goto_Cov;
    @FXML
    private Button Alerts;
    @FXML
    private Label Goto_liv;
    @FXML
    private Button Evenements;
    @FXML
    private Label Goto_event;
    @FXML
    private Button Proposition;
    @FXML
    private Label Goto_location;
    @FXML
    private Circle circle;
    @FXML
    private Button nom_u;
    @FXML
    private Button to_menu;
    @FXML
    private Button Accueil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ls1.addAll(sl1.afficher());
    TV.setItems(ls1);
     colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
      colFin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    colModele.setCellValueFactory(new PropertyValueFactory<>("modele"));
    colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
    colPrix.setCellValueFactory(new PropertyValueFactory<>("prix_location"));
    

colImage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Location, Image>, ObservableValue<Image>>() {
    @Override
    public ObservableValue<Image> call(TableColumn.CellDataFeatures<Location, Image> param) {
        return new SimpleObjectProperty<>(param.getValue().getImage_voiture().getImage());
    }
});

colImage.setCellFactory(new Callback<TableColumn<Location, Image>, TableCell<Location, Image>>() {
    @Override
    public TableCell<Location, Image> call(TableColumn<Location, Image> param) {
        return new TableCell<Location, Image>() {
            final ImageView imageView = new ImageView();
            
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setImage(item);
                    setGraphic(imageView);
                }
            }
        };
    }
});  

        
       }    

    @FXML
    private void To_Profile(ActionEvent event) {
    }

    @FXML
    private void To_Panier(ActionEvent event) {
    }

    @FXML
    private void Deconnexion(ActionEvent event) {
    }

    @FXML
    private void Reclamations(ActionEvent event) {
    }

    @FXML
    private void Alerts(ActionEvent event) {
    }

    @FXML
    private void Evenements(ActionEvent event) {
    }

    @FXML
    private void Proposition(ActionEvent event) {
    }

    @FXML
    private void ToMenu(ActionEvent event) {
    }

    @FXML
    private void To_Accueil(ActionEvent event) {
    }
    
}

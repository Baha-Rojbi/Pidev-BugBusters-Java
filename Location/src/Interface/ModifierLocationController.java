/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Entities.Location;
import Entities.Voiture_location;
import Services.ServiceLocation;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author bahar
 */
public class ModifierLocationController implements Initializable {
    
    private static final String ACCOUNT_SID = "AC2b31d2375146f65bcb7502fd6718e49a";
    private static final String AUTH_TOKEN = "bc40c1ad62f044b8fc6ee85353e0cba0";

    // The Twilio phone number you want to use to send SMS messages
    private static final String TWILIO_NUMBER = "+15673131649";

    // The recipient phone number you want to send an SMS message to
    private static final String RECIPIENT_NUMBER = "+21653802106";
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
    private TableColumn<Location, Button> colSupprimer;
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
    @FXML
    private DatePicker txtDebut;
    @FXML
    private DatePicker txtFin;
    @FXML
    private Label labelModele;
    @FXML
    private Label labelPrix;
    @FXML
    private ImageView ImageVoiture;

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
colSupprimer.setCellFactory(new Callback<TableColumn<Location, Button>, TableCell<Location, Button>>() {
    @Override
    public TableCell<Location, Button> call(TableColumn<Location, Button> param) {
        return new TableCell<Location, Button>() {
            final Button button = new Button("Annuler");
            
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        Location location = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de la suppression");
                        alert.setHeaderText("Annuler la reservation de " + location.getModele());
                        alert.setContentText("Êtes-vous sûr de vouloir annuler cette reservation ?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            sl1.supprimer(location);
                            ls1.remove(location);
                            TV.setItems(ls1);
                        } else {
                            
                        }
                    });
                    setGraphic(button);
                    button.setStyle("-fx-background-color: #ff0000;");
                }
            }
        };
    }
});
TV.setOnMousePressed(new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            // yekhou el row eli khtarou
            Location location = TV.getSelectionModel().getSelectedItem();
            if (location != null) {
             
                labelModele.setText(location.getModele());
               
                labelPrix.setText(Integer.toString(location.getPrix_location()));
            
                ImageVoiture.setImage(location.getImage_voiture().getImage());
                 txtDebut.setValue(LocalDate.parse(location.getDate_debut()));
               txtFin.setValue(LocalDate.parse(location.getDate_fin()));
            }
        }
    }
});

         
    
  
    
}
    @FXML
private void modifierLocation(ActionEvent event){
    LocalDate debut = txtDebut.getValue();
    LocalDate fin = txtFin.getValue();
    Location r = TV.getSelectionModel().getSelectedItem();
    Integer id_location = r.getId_location();
   if (r!=null){
       Integer id_voiture = r.getId_voiture();
        Location c = new Location(id_location,debut.toString(), fin.toString(), id_voiture);
         ServiceLocation rm = new ServiceLocation();
    rm.modifier(c);
    JOptionPane.showMessageDialog(null, "modification effectué avec succès.");
     Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send an SMS message using the Twilio API
        Message message = Message.creator(
            new PhoneNumber(RECIPIENT_NUMBER),
            new PhoneNumber(TWILIO_NUMBER),
            "Reservation modifiée!"
        ).create();

        // Print the message SID to the console
        System.out.println("SMS message sent with SID: " + message.getSid());
    ArrayList<Location> publiciteList = (ArrayList<Location>) rm.afficher();
        ObservableList<Location> donnee = FXCollections.observableArrayList(publiciteList); 
        TV.setItems(donnee);
   }
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

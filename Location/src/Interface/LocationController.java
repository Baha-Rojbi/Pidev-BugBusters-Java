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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author bahar
 */
public class LocationController implements Initializable {
    

    Connection cnx;
    PreparedStatement ste;

    @FXML
    private AnchorPane brand;
    @FXML
    private Button btnajout;
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
    private DatePicker txtDate_fin;
    @FXML
    private DatePicker txtDate_debut;
    @FXML
    private GridPane gridPane;
   @FXML
private ScrollPane scrollPane;
@FXML
private HBox voitureHBox;

private int selectedCarId;
private int selectedCarPrix;




  private static final String ACCOUNT_SID = "AC1d39955a23413357ea2300498f56f78c";
    private static final String AUTH_TOKEN = "b8baa4a49c5ef0ae44419814244ced2a";

    // The Twilio phone number you want to use to send SMS messages
    private static final String TWILIO_NUMBER = "+16318835768";

    // The recipient phone number you want to send an SMS message to
    private static final String RECIPIENT_NUMBER = "+21653802106";

    
    ServiceLocation sl= new ServiceLocation();
    ObservableList<Location> ls = FXCollections.observableArrayList();
    
    ServiceVoiture sl1= new ServiceVoiture();
    ObservableList<Voiture_location> ls1 = FXCollections.observableArrayList();
    @FXML
    private Label label_prix;
    @FXML
    private Label label_date1;
    @FXML
    private Label label_date2;
    @FXML
    private TextField txtRechercher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtRechercher.textProperty().addListener((observable, oldValue, newValue) -> {
    updateDisplayedCars(newValue);
});
        

        
        
        

        // Get all Voiture_location objects from the database
// Get all Voiture_location objects from the database

List<Voiture_location> voitureList = sl1.afficher();

// Create a HBox to hold the GridPanes
HBox voitureHBox = new HBox();
voitureHBox.setSpacing(10);

// Add each Voiture_location object to a GridPane and add the GridPane to the HBox
for (int i = 0; i < voitureList.size(); i++) {
    Voiture_location voiture = voitureList.get(i);

    GridPane voiturePane = new GridPane();
    voiturePane.setAlignment(Pos.CENTER);
    voiturePane.setHgap(10);
    voiturePane.setVgap(10);

    // Create label to display modele and prix_jour
    Label modelePrixLabel = new Label(voiture.getModele() + " - " + voiture.getPrix_jour() + " DT/jour");
    modelePrixLabel.setAlignment(Pos.BOTTOM_CENTER);

    // Create image view to display image_voiture
    ImageView voitureImage = voiture.getImage_voiture();
    voitureImage.setFitHeight(150);
    voitureImage.setFitWidth(150);

    // Add label and image view to grid pane
    voiturePane.add(voitureImage, 0, 0);
    voiturePane.add(new Separator(), 0, 1);
    voiturePane.add(modelePrixLabel, 0, 2);

    // Add grid pane to main grid pane
    voitureHBox.getChildren().add(voiturePane);
    if (i != voitureList.size() - 1) {
        voitureHBox.getChildren().add(new Separator(Orientation.VERTICAL));
    }
    // Create a button to select the car
// Create a button to select the car
Button selectButton = new Button("Choisir");
selectButton.setStyle("-fx-background-color: #4dc47d;");
selectButton.setOnAction(e -> {
    selectedCarId = voiture.getId_voiture();
    selectedCarPrix=voiture.getPrix_jour();
});

// Add the select button to the grid pane
voiturePane.add(selectButton, 0, 3);

}



// Set the HBox as the content of the ScrollPane
scrollPane.setContent(voitureHBox);

  txtDate_debut.valueProperty().addListener((observable, oldValue, newValue) -> {
        updatePriceLabel();
    });
         txtDate_fin.valueProperty().addListener((observable, oldValue, newValue) -> {
        updatePriceLabel();
    });
        
        
        
        // TODO
    }
    private void updateDisplayedCars(String modele) {
    // Get all Voiture_location objects from the database
    List<Voiture_location> voitureList = sl1.afficher();

    // Create a new HBox to hold the filtered GridPanes
    HBox filteredVoitureHBox = new HBox();
    filteredVoitureHBox.setSpacing(10);

    // Loop through each Voiture_location object
    for (Voiture_location voiture : voitureList) {
        // If the modele matches the user input, add the GridPane to the filtered HBox
        if (voiture.getModele().toLowerCase().contains(modele.toLowerCase())) {
            GridPane voiturePane = createVoiturePane(voiture);
            filteredVoitureHBox.getChildren().add(voiturePane);
            if (filteredVoitureHBox.getChildren().size() != 1) {
                filteredVoitureHBox.getChildren().add(new Separator(Orientation.VERTICAL));
            }
        }
    }

    // Set the filtered HBox as the content of the ScrollPane
    scrollPane.setContent(filteredVoitureHBox);
}
    private GridPane createVoiturePane(Voiture_location voiture) {
    GridPane voiturePane = new GridPane();
    voiturePane.setAlignment(Pos.CENTER);
    voiturePane.setHgap(10);
    voiturePane.setVgap(10);

    // Create label to display modele and prix_jour
    Label modelePrixLabel = new Label(voiture.getModele() + " - " + voiture.getPrix_jour() + " DT/jour");
    modelePrixLabel.setAlignment(Pos.BOTTOM_CENTER);

    // Create image view to display image_voiture
    ImageView voitureImage = voiture.getImage_voiture();
    voitureImage.setFitHeight(150);
    voitureImage.setFitWidth(150);

    // Add label and image view to grid pane
    voiturePane.add(voitureImage, 0, 0);
    voiturePane.add(new Separator(), 0, 1);
    voiturePane.add(modelePrixLabel, 0, 2);

    // Create a button to select the car
    Button selectButton = new Button("Choisir");
    selectButton.setStyle("-fx-background-color: #4dc47d;");
    selectButton.setOnAction(e -> {
        selectedCarId = voiture.getId_voiture();
        selectedCarPrix = voiture.getPrix_jour();
    });

    // Add the select button to the grid pane
    voiturePane.add(selectButton, 0, 3);

    return voiturePane;
}
    
    


    private void updatePriceLabel() {
    LocalDate debut = txtDate_debut.getValue();
    LocalDate fin = txtDate_fin.getValue();
    
  
    if (debut != null && fin != null) {
        if (debut.isAfter(fin)) {
            label_date1.setText("La date de début doit être antérieure à la date de fin !");
            label_prix.setText("");
        } else {
            label_date1.setText("");
            long nbJours = ChronoUnit.DAYS.between(debut, fin);
            
           
                
                int prixTotal = selectedCarPrix * (int) nbJours;
                label_prix.setText(String.valueOf(prixTotal) + " DT");
            }
        }
    }

      
    
    @FXML
private void addLocation(ActionEvent event) {
    LocalDate debut = txtDate_debut.getValue();
    LocalDate fin = txtDate_fin.getValue();
    

    if (debut == null || fin == null ) {
        // Show an error message if any required field is not filled
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs obligatoires !");
        alert.showAndWait();
        return;
    }

    if (debut.isEqual(fin)) {
        // Show an error message if start date is the same as end date
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("La date de début ne peut pas être la même que la date de fin !");
        alert.showAndWait();
        return;
    }

    if (debut.isAfter(fin)) {
        // Show an error message if start date is after end date
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("La date de début doit être antérieure à la date de fin !");
        alert.showAndWait();
        return;
    }
    if (selectedCarId == 0) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText("Veuillez choisir une voiture !");
    alert.showAndWait();
    return;
}

    // Create a new Location object with the data from the UI
    Location c = new Location(debut.toString(), fin.toString(), selectedCarId);
    ServiceLocation sc = new ServiceLocation();
    sc.ajouter(c);
    JOptionPane.showMessageDialog(null, "Ajout effectué avec succès.");
     Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send an SMS message using the Twilio API
        Message message = Message.creator(
            new PhoneNumber(RECIPIENT_NUMBER),
            new PhoneNumber(TWILIO_NUMBER),
            "Reservation Confirmée!"
        ).create();

        // Print the message SID to the console
        System.out.println("SMS message sent with SID: " + message.getSid());
    }


    @FXML
    private void load(ActionEvent event) {
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
    
    /////////////////////////////////
    


    
   



   

 
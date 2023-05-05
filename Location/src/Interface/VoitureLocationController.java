/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Entities.Voiture_location;
import Services.ServiceVoiture;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author bahar
 */
public class VoitureLocationController implements Initializable {
    
     private static final String ACCOUNT_SID = "AC2b31d2375146f65bcb7502fd6718e49a";
    private static final String AUTH_TOKEN = "bc40c1ad62f044b8fc6ee85353e0cba0";

    // The Twilio phone number you want to use to send SMS messages
    private static final String TWILIO_NUMBER = "+15673131649";

    // The recipient phone number you want to send an SMS message to
    private static final String RECIPIENT_NUMBER = "+21653802106";
     Connection cnx;
    PreparedStatement ste;
    ServiceVoiture sl1= new ServiceVoiture();
    ObservableList<Voiture_location> ls1 = FXCollections.observableArrayList();

    @FXML
    private AnchorPane brand;
    @FXML
    private Button btnajout;
    @FXML
    private TextField txtModele;
    @FXML
    private TextField txtMatricule;
    @FXML
    private TextField txtCarte;
    @FXML
    private TextField txtPrix;
    @FXML
    private Button Ajouter_image;
    @FXML
    private ImageView ImageVoiture;
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
    private TableView<Voiture_location> TV;
    @FXML
    private TableColumn<Voiture_location, String> colModele;
    @FXML
    private TableColumn<Voiture_location, String> colMatricule;
    @FXML
    private TableColumn<Voiture_location, String> colCarte;
    @FXML
    private TableColumn<Voiture_location, Integer> colPrix;
    @FXML
    private TableColumn<Voiture_location, Image> colImage;
    @FXML
    private TableColumn<Voiture_location, Button> colSupprimer;
    @FXML
    private Button btnmodifier;
    @FXML
    private Label labelModele;
    @FXML
    private Label labelMatricule;
    @FXML
    private Label labelCarte;
    @FXML
    private Label labelPrix;
    @FXML
    private Label labeImage;

    /**
     * Initializes the controller class.
     */
    @Override
public void initialize(URL url, ResourceBundle rb) {
    ls1.addAll(sl1.afficher());
    TV.setItems(ls1);
    colModele.setCellValueFactory(new PropertyValueFactory<>("modele"));
    colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
    colCarte.setCellValueFactory(new PropertyValueFactory<>("carte_grise"));
    colPrix.setCellValueFactory(new PropertyValueFactory<>("prix_jour"));

colImage.setCellValueFactory(new Callback<CellDataFeatures<Voiture_location, Image>, ObservableValue<Image>>() {
    @Override
    public ObservableValue<Image> call(CellDataFeatures<Voiture_location, Image> param) {
        return new SimpleObjectProperty<>(param.getValue().getImage_voiture().getImage());
    }
});

colImage.setCellFactory(new Callback<TableColumn<Voiture_location, Image>, TableCell<Voiture_location, Image>>() {
    @Override
    public TableCell<Voiture_location, Image> call(TableColumn<Voiture_location, Image> param) {
        return new TableCell<Voiture_location, Image>() {
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

colSupprimer.setCellFactory(new Callback<TableColumn<Voiture_location, Button>, TableCell<Voiture_location, Button>>() {
    @Override
    public TableCell<Voiture_location, Button> call(TableColumn<Voiture_location, Button> param) {
        return new TableCell<Voiture_location, Button>() {
            final Button button = new Button("Supprimer");
            
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        Voiture_location voiture = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de la suppression");
                        alert.setHeaderText("Suppression de la voiture " + voiture.getModele());
                        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette voiture ?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            sl1.supprimer(voiture);
                            ls1.remove(voiture);
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
///////////
TV.setOnMousePressed(new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            // yekhou el row eli khtarou
            Voiture_location voiture = TV.getSelectionModel().getSelectedItem();
            if (voiture != null) {
             
                txtModele.setText(voiture.getModele());
                txtMatricule.setText(voiture.getMatricule());
                txtCarte.setText(voiture.getCarte_grise());
                txtPrix.setText(Integer.toString(voiture.getPrix_jour()));
            
                ImageVoiture.setImage(voiture.getImage_voiture().getImage());
                
            }
        }
    }
});
/////////////////
txtModele.textProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.trim().isEmpty()) {
        labelModele.setText("Modele cannot be empty.");
    } else {
        labelModele.setText("");
    }
});
txtMatricule.textProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.trim().isEmpty()) {
        labelMatricule.setText("Matricule cannot be empty.");
    } else {
        labelMatricule.setText("");
    }
});
txtCarte.textProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.trim().isEmpty()) {
        labelCarte.setText("Carte cannot be empty.");
    } else {
        labelCarte.setText("");
    }
});
txtPrix.textProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.trim().isEmpty()) {
        labelPrix.setText("Prix cannot be empty.");
    } else {
        labelPrix.setText("");
    }
});




   
}




    @FXML
private void Ajouter_image(ActionEvent event) {
    // create file chooser
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image File");

    // set extension filter to only show image files
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
    fileChooser.getExtensionFilters().add(extFilter);

    // show file chooser dialog and get selected file
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        // read image file as InputStream
        try (InputStream is = new FileInputStream(selectedFile)) {
            // create image object from input stream
            Image image = new Image(is);

            // set image to ImageView
            ImageVoiture.setImage(image);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


@FXML
private void Ajouter_voiture(ActionEvent event) {
     
   if (txtModele.getText().isEmpty() || txtMatricule.getText().isEmpty() || txtCarte.getText().isEmpty() || txtPrix.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs vides");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
        return;
    }
   
                      
    String matricule = txtMatricule.getText();
    String modele = txtModele.getText();
    String carteGrise = txtCarte.getText();
    String prix_jour = txtPrix.getText();
        if (!prix_jour.matches("\\d+")) { // check if prix_jour is not a positive integer
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le prix doit être un entier positif");
            alert.showAndWait();
            return;
        }
        int prixJour = Integer.parseInt(prix_jour);
    ImageView imageVoiture = ImageVoiture;
    
     if (!matricule.matches("\\d{3}[A-Z]{3}\\d{4}")) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Matricule Format");
        alert.setContentText("Matricule should have the format XXXTUNXXXX where X is a number.");

        alert.showAndWait();
        return;
    }
     
     

 
    Voiture_location voiture = new Voiture_location(matricule, modele, carteGrise, prixJour, imageVoiture);

  
    int rowsAdded = sl1.ajouter(voiture);
  

    
    if (rowsAdded > 0) {
        ls1.add(voiture);
        TV.setItems(ls1);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Voiture ajoutée");
        alert.setHeaderText(null);
        alert.setContentText("La voiture a été ajoutée avec succès.");
        alert.showAndWait();
         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send an SMS message using the Twilio API
        Message message = Message.creator(
            new PhoneNumber(RECIPIENT_NUMBER),
            new PhoneNumber(TWILIO_NUMBER),
            "Voiture de location ajoutée!"
        ).create();

        // Print the message SID to the console
        System.out.println("SMS message sent with SID: " + message.getSid());
    }
  


    txtMatricule.setText("");
    txtModele.setText("");
    txtCarte.setText("");
    txtPrix.setText("");
    ImageVoiture.setImage(null);
}

@FXML
private void modifier_voiture(ActionEvent event){
    if (txtModele.getText().isEmpty() || txtMatricule.getText().isEmpty() || txtCarte.getText().isEmpty() || txtPrix.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs vides");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez choisir la voiture a modifier et remplir tout les champs !");
        alert.showAndWait();
        return;
    }
    String matricule = txtMatricule.getText();
    String modele = txtModele.getText();
    String carteGrise = txtCarte.getText();
    int prixJour = Integer.parseInt(txtPrix.getText());
    ImageView imageVoiture = ImageVoiture;
       Voiture_location v = TV.getSelectionModel().getSelectedItem();
         Voiture_location voiture = new Voiture_location(v.getId_voiture(),matricule, modele, carteGrise, prixJour, imageVoiture);
         ServiceVoiture rm = new ServiceVoiture();
    rm.modifier(voiture);
     JOptionPane.showMessageDialog(null, "modification effectué avec succès.");
     
     ArrayList<Voiture_location> publiciteList = (ArrayList<Voiture_location>) rm.afficher();
        ObservableList<Voiture_location> donnee = FXCollections.observableArrayList(publiciteList); 
        TV.setItems(donnee);
       
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
